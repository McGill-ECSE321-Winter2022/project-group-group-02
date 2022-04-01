import axios from 'axios'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})



function UnavailableItemDto (name, price) {

    this.name = name
    this.price=price

}

function ShoppableItemDto (name, price,quantityAvailable) {

    this.name = name
    this.price=price
    this.quantityAvailable=quantityAvailable

}



export default {
    name: 'unavailable item',
    data () {
      return {
        name : '',
        price : '',

      }
    },
    created: function () {
        // Test data
        const o1 = new UnavailableItemDto('Guiness Book',13.02)
        const o2 = new UnavailableItemDto('Magazine',9.89)
       

      
      },

      name: 'shoppable item',
      data () {
      return {
        name : '',
        price : '',
        items: []

      }
    },
    created: function () {
        // Test data
        const o1 = new UnavailableItemDto('Guiness Book',13.02)
        const o2 = new UnavailableItemDto('Magazine',9.89)
       

      
      },
    
    methods: {
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
                  this.items = response.data
                })
                .catch(e => {
                  
    
                })
    
              swal("Success", "Item successfully added", "success");
    
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })

        }
      
       
      },
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
                  this.items = response.data
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
          // Create a new Unavailable item and add it to the list of order
            AXIOS.put('/update_shoppable_item_quantity_available/{name}', {}, {
            params: {
              name: name,
              quantityAvailable: newQuantityAvailable
              
            }
          })
            .then(response => {

              AXIOS.get('/view_all_unavailable_items', {})
                .then(response => {
                  this.items = response.data
                })
                .catch(e => {
                  
    
                })
    
              swal("Success", "", "success");
    
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })
        },
        updateShoppableItemPrice: function (name,newPrice) {
          // Create a new Unavailable item and add it to the list of order
            AXIOS.put('/update_shoppable_item_price/{name}', {}, {
            params: {
              name: name,
              newPrice: newPrice              
            }
          })
            .then(response => {

              AXIOS.get('/view_all_shoppable_items', {})
                .then(response => {
                  this.items = response.data
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
            AXIOS.put('/update_unavailable_item_price/{name}', {}, {
            params: {
              name: name,
              newPrice: newPrice              
            }
          })
            .then(response => {

              AXIOS.get('/view_all_unavailable_items', {})
                .then(response => {
                  this.items = response.data
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
            AXIOS.put('/delete_shoppable_item/{name}', {}, {
            params: {
              name: name,
            }
          })
            .then(response => {

              AXIOS.get('/view_all_shoppable_items', {})
                .then(response => {
                  this.items = response.data
                })
                .catch(e => {
                  
    
                })
    
              swal("Success", "", "success");
    
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })
        },
        deleteUnavailableItem: function (name) {
          // Create a new Unavailable item and add it to the list of order
            AXIOS.put('/delete_unavailable_item/{name}', {}, {
            params: {
              name: name,
            }
          })
            .then(response => {

              AXIOS.get('/view_all_unavailable_items', {})
                .then(response => {
                  this.items = response.data
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
  