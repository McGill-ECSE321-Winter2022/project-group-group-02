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
    name: 'item',
    data () {
      return {
        name : '',
        price : '',
        errorItem: '',
        quantityAvailable : '',
        shoppableItems: [],
        unavailableItems: []

      }
    },
    created: function () {
        // Initializing shoppable items from backend
        AXIOS.get('/view_all_shoppable_item')
      .then(response => {
        // JSON responses are automatically parsed.
        this.shoppableItems = response.data
      })
      .catch(e => {
        this.errorItem = e
      })

      AXIOS.get('/view_all_unavailable_item')
      .then(response => {
        // JSON responses are automatically parsed.
        this.unavailableItems = response.data
      })
      .catch(e => {
        this.errorItem = e
      })
       

      
      },

    //   name: 'unavailable item',
    //   data () {
    //   return {
    //     name : '',
    //     price : '',
    //     items: []

    //   }
    // },
    
    
    methods: {
      
        createUnavailableItem: function (name,price) {
            // Create a new Unavailable item and add it to the list of order
            AXIOS.post('/create_unavailable_item', {}, {
            params: {
              name: name,
              price: price,
              
            }
          })
            .then(response => {

              AXIOS.get('/view_all_unavailable_items', {})
                .then(response => {
                  this.unavailableItems = response.data
                })
                .catch(e => {
                  
    
                })
    
              swal("Success", "Item successfully added", "success");
    
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })

          
      },

        createShoppableItem: function (name,price,quantityAvailable) {
          AXIOS.post('/create_shoppable_item', {}, {
            params: {
              name: name,
              price: price,
              quantityAvailable: quantityAvailable,
              
            }
          })
            .then(response => {

              AXIOS.get('/view_all_shoppable_items', {})
                .then(response => {
                  this.shoppableItems = response.data
                })
                .catch(e => {
                  
    
                })
    
              swal("Success", "Item successfully added", "success");
    
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })

        },
      
       
      
        replenishInventory: function (name,newQuantityAvailable) {
          // Create a new Shoppable item and add it to the list of order
            AXIOS.put('/update_shoppable_item_quantity_available', {
            params: {
              name: name,
              newQuantityAvailable: newQuantityAvailable
            }
          })
            .then(response => {

              AXIOS.get('/view_all_shoppable_items', {})
                .then(response => {
                  this.shoppableItems = response.data
                })
                .catch(e => {
                  
    
                })
    
              swal("Success", "", "success");
    
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })
        },

        updateShoppableItemPrice: function (name, price) {
          // Create a new Unavailable item and add it to the list of order
            AXIOS.put('/update_shoppable_item_price', {
            params: {
              name: name,
              newPrice: price              
            }
          })
            .then(response => {

              AXIOS.get('/view_all_shoppable_items', {})
                .then(response => {
                  this.shoppableItems = response.data
                })
                .catch(e => {
                  
    
                })
    
              swal("Success", "", "success");
    
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })
        },

        updateUnavailableItemPrice: function (name,newPrice) {
          // Create a new Unavailable item and add it to the list of order
            AXIOS.put('/update_unavailable_item_price', {}, {
            params: {
              name: name,
              newPrice: newPrice              
            }
          })
            .then(response => {

              AXIOS.get('/view_all_unavailable_items', {})
                .then(response => {
                  this.unavailableItems = response.data
                })
                .catch(e => {
                  
    
                })
    
              swal("Success", "", "success");
    
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })
        },

        deleteShoppableItem: function (name) {
          // Create a new Unavailable item and add it to the list of order
            AXIOS.delete('/delete_shoppable_item',{}, {
            params: {
              name: name,
            }
          })
            .then(response => {

              AXIOS.get('/view_all_shoppable_items', {})
                .then(response => {

                  this.shoppableItems = response.data
                })
                .catch(e => {
                  this.errorItem = e
    
                })
    
              swal("success", "", "success");
    
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })
        },

        deleteUnavailableItem: function (name) {
          // Create a new Unavailable item and add it to the list of order
            AXIOS.delete('/delete_unavailable_item', {
            params: {
              name: name,
            }
          })
            .then(response => {

              AXIOS.get('/view_all_unavailable_items', {})
                .then(response => {
                  this.unavailableItems = response.data
                })
                .catch(e => {
                  
    
                })
    
              swal("Success", "", "success");
    
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })
        }
        

    }
}
  