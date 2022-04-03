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
      orders: []
    }
  },
  created: function () {
    if (localStorage.getItem('type').localeCompare("employee") == 0) {
        this.isEmployee = true
    } else if (localStorage.getItem('type').localeCompare("owner") == 0) {
        this.isOwner = true
    }

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

    updateStatus: function (status, order){

      AXIOS.put('/update_order', {}, {
        params: {

          orderStatus: status,
          orderId: order.id,

        }

      })

        .then(response =>{

          AXIOS.get('/view_all_orders', {})
            .then(response => {

              // JSON responses are automatically parsed.
              this.orders = response.data
            })
            .catch(e => {
              this.errorOrder = e
            })


          swal("Success", "Updated successfully !", "success");

        })
        .catch(e => {
          swal("ERROR", e.response.data, "error");
        })
    },

    getUpdatedStatus: function (order){

      return order.orderStatus


    }


  }


}
