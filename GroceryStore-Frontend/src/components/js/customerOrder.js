import axios from 'axios'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})



function OrderDto (date, time, orderType, orderStatus, rating, review) {

    this.date = date
    this.time = time
    this.orderType = orderType
    this.orderStatus = orderStatus
    this.review = review
    this.rating = rating

}



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
        subtotal: '',
        customerEmail: '',
        errorReview: '',
        items: [],
        orders: [],
        reviews:[]
      }
    },
    created: function () {
        // // Test data
        // const o1 = new OrderDto('12 March', '12:43', 'Delivery', 'Complete', 'Good', 'Arrived on time', 0)
        // const o2 = new OrderDto('2 April', '4:22', 'Pickup', 'Preparing', 'Okay', 'Could be faster', 1)
        // // Sample initial content
        // this.orders = [o1, o2]

      AXIOS.get('/view_all_orders_for_customer', {
        params: {
          customerEmail: this.customerEmail
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
            customerEmail: customerEmail
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
            var p = new OrderDto(date, time, orderType, orderStatus, rating, review)
            this.orders.push(p)
            
        }
    }
  }