import axios from 'axios'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

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
        customerEmail: 'Jeff@me',
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
          email: this.customerEmail
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
            customerEmail: this.customerEmail
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
        createOrder: function (date, time, orderType, orderStatus, rating, review) {
            // Create a new order and add it to the list of order
            var p = new OrderDto(date, time, orderType, orderStatus,rating, review)
            this.orders.push(p)
            
        },

        createReview: function (rating, description, order){

        }

    }
  }