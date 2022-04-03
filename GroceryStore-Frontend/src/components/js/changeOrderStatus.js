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
  name: 'orderStatus',
  data() {
    return {
      orderType: '',
      orderStatus: '',
      isOwner: '',
      isEmployee: '',
      updatedOrderStatus: '',
      orders: [],
      errorChange: '',
    }
  },
  created: function () {
    if (localStorage.getItem('type').localeCompare("employee") == 0) {
        this.isEmployee = true
    } else if (localStorage.getItem('type').localeCompare("owner") == 0) {
        this.isOwner = true
    }


    /**
     * Refresh all orders in system
     * @author Karl Rouhana
     */

    AXIOS.get('/view_all_orders')
      .then(response => {

        // JSON responses are automatically parsed.
        this.orders = response.data
      })
      .catch(e => {
        this.errorOrder = e
      })




  },


  methods:{

    /**
     * Update an orders' status in the system
     * @author Karl Rouhana
     */
    updateStatus: function (status, order){

      //Call backend controllers to update the order
      AXIOS.put('/update_order', {}, {
        params: {

          //Using specific params
          orderStatus: status,
          orderId: order.id,

        }

      })

        .then(response =>{

          //Refresh the orders in system
          AXIOS.get('/view_all_orders', {})
            .then(response => {

              // JSON responses are automatically parsed.
              this.orders = response.data
            })
            .catch(e => {

            })




        })
        .catch(e => {
          //Catch and output errors
        this.errorChange = e.response.data
          console.log(this.errorChange)

        })
    },




  }


}
