import axios from 'axios'
var config = require('../../../config')

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
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port


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
        review: '',
        rating: '',
        subtotal: '$0',
        errorReview: '',
        items: [],
        orders: [],
        reviews:[]
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
          //email: window.localStorage.getItem('email')
          email: 'Jeff@me'
        }
      })
        .then(response => {
      
          // JSON responses are automatically parsed.
          this.orders = response.data
        })
        .catch(e => {
          this.errorOrder = e
        })

      

      AXIOS.get('/view_reviews_for_customer', {
          params: {
            //customerEmail: window.localStorage.getItem('email')
            customerEmail: 'Jeff@me'
          }
        })
        .then(response => {
      
          // JSON responses are automatically parsed.
          this.reviews = response.data
          
        })
        .catch(e => {
          this.errorReview = e
        })
      },

      
    
    methods: {
        
        createReview: function (rating, description, order){

          AXIOS.post('/create_review/', {}, {
            params: {
              rating: rating,
              description: description,
              customerEmail: window.localStorage.getItem('email'),
              orderId: order.id,
            }
          })
            .then(response => {

              AXIOS.get('/view_reviews_for_customer', {
                params: {
                  //customerEmail: localStorage.getItem('email')
                  customerEmail: 'Jeff@me'
                }
              })
                .then(response => {
                  this.reviews = response.data
                })
                .catch(e => {
                  this.reviews = []
    
                })
    
              swal("Success", "Thank you for your feedback!", "success");
    
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })

        }

    }
  }