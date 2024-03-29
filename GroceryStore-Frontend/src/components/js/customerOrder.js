import axios from 'axios'
var config = require('../../../config')

// Setup the backend and frontend urls
var backendConfigurer = function(){
	switch(process.env.NODE_ENV){
      case 'development':
          return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
      case 'production':
          return 'https://' + config.build.backendHost + ':' + config.build.backendPort ;
	}
};

var frontendConfigurer = function(){
	switch(process.env.NODE_ENV){
      case 'development':
          return 'http://' + config.dev.host + ':' + config.dev.port;
      case 'production':
          return 'https://' + config.build.host + ':' + config.build.port ;
	}
};

var backendUrl = backendConfigurer();
var frontendUrl = frontendConfigurer();


var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'order',
    data () {
      return {
        date: '',
        time: '',
        orderType: '',
        orderStatus: '',
        subtotal: '$0',
        total: '$0',
        errorReview: '',
        successReview: '',
        errorOrder: '',
        items: [],
        orders: [],
        reviews: [],
      }
    },
    created: function () {
        // Test data
        // const o1 = new OrderDto('12 March', '12:43', 'Delivery', 'Complete', 'Good', 'Arrived on time', 0)
        // const o2 = new OrderDto('2 April', '4:22', 'Pickup', 'Preparing', 'Okay', 'Could be faster', 1)
        // // Sample initial content
        // this.orders = [o1, o2]

      AXIOS.get('/view_all_orders_for_customer', {
        params: {
          email: window.localStorage.getItem('email')
          //email: 'Jeff@me'
        }
      })
        .then(response => {
      
          // JSON responses are automatically parsed.
          this.orders = response.data

          for (let i = 0; i < this.orders.length; i++) {
        

            AXIOS.get('/get_order_price/?id='.concat(this.orders[i].id))
              .then(response => {
                
                this.orders[i].total = response.data
              })

              .catch(e => {
                console.log(e)
              })

              AXIOS.get('/view_review_for_order', {
              params: {
                orderId: this.orders[i].id
              }
            })
              .then(response => {
                
                this.orders[i].description = response.data.description
                this.orders[i].rating = response.data.rating
              })

              .catch(e => {
                this.errorReview = e  
              })

          }

        })
        .catch(e => {
          this.errorOrder = e
        })
      

      },

      

    

    methods: {
        
        /**
         * @author Matthieu Hakim
         * @param {Rating} rating 
         * @param {String} description 
         * @param {Order} order
         * @description creates a review for an order and add it in the table 
         */

        createReview: function (rating, description, order){

          AXIOS.post('/create_review/', {}, {
            params: {
              rating: rating,
              description: description,
              customerEmail: window.localStorage.getItem('email'),
              //customerEmail: 'Jeff@me',
              orderId: order.id,
            }
          })
            .then(response => {

              AXIOS.get('/view_review_for_order', {
                params: {
                  orderId: order.id
                }
              })
                .then(response => {
                  order.description = response.data.description
                  order.rating = response.data.rating

                })
                .catch(e => {
                  this.errorReview = e
    
                })
    
              this.successReview = 'Thank you for your Feedback!'
    
            })
            .catch(e => {
              this.errorReview = 'Please enter both a rating and a review'
              console.log(this.errorReview)
            })

        },

        /**
         * @author Matthieu Hakim
         * @param {Order} order
         * @description takes as input an order and return its associated review, if any
         */

        getReviewForOrder: function(order){

          AXIOS.get('/view_review_for_order', {
            params: {
              orderId: order.id
            }
          })
            .then(response => {
          
              // JSON responses are automatically parsed.
              order.description = response.data.description
              order.rating = response.data.rating
              reviews.push(response.data)

            })
            .catch(e => {
              this.errorReview = e
            })
            return this.review

        },

        /**
         * @author Matthieu Hakim
         * @param {Rating} rating 
         * @param {String} description 
         * @param {Order} order
         * @description updates the review and rating of an order 
         */
        updateReview: function (rating, description, order){

          AXIOS.put('/update_review/', {}, {
            params: {
              orderId: order.id,
              newDescription: description,
              rating: rating,
            }
          })
            .then(response => {

              AXIOS.get('/view_review_for_order', {
                params: {
                  orderId: order.id
                }
              })
                .then(response => {
                  order.description = response.data.description
                  order.rating = response.data.rating

                 
                })
                .catch(e => {
                  this.errorReview = e
    
                })
    
                this.successReview = 'Thank you for your Feedback!'
    
            })
            .catch(e => {
              this.errorReview = 'Please enter both a rating and a review'
              console.log(this.errorReview)
            })

        },

        /**
         * @author
         * @param {Order} order
         * @description deletes the review of the input order 
         */
        
        deleteReview: function (order){

          AXIOS.delete('/delete_review/', {
            params: {
              orderId: order.id,
            }
          })
            .then(response => {

              AXIOS.get('/view_review_for_order', {
                params: {
                  orderId: order.id
                }
              })
                .then(response => {
                  order.description = null
                  order.rating = null
                })
                .catch(e => {
                  this.errorReview = e
    
                })
    
                this.successReview = "Feedback deleted successfully"
    
            })
            .catch(e => {
              this.errorReview = e.response.data
              console.log(this.errorReview)
            })

        }

    }
  }