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

function BasketItemDTO (itemName, quantityDesired) {
    this.itemName = itemName
    this.quantityDesired = quantityDesired
}


export default {
    
    name: 'item',
    data () {
      return {
        name: '',
        price: '',
        order: '',
        orderType: '',
        errorItem: '',
        errorOrder: '',
        errorOrderItem: '',
        shoppableItems: [],
        unavailableItems: [],
        orderItems: [],
        basketItems: []
      }
    },

    created: function () {

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

    methods: {
        addToBasket: function(newItemName, quantityDesired){

            


            if(quantityDesired == null || quantityDesired < 1){
                removeFromBasket(newItemName)
            }
            else{

                for (let i = 0; i < this.shoppableItems.length; i++) {
                    if(this.shoppableItems[i].name == newItemName){
                        
                        if(this.shoppableItems[i].quantityAvailable < quantityDesired){
                            return console.error();
                        }
                    }
                }

                for (let i = 0; i < this.basketItems.length; i++) {
                    if(this.basketItems[i].itemName == newItemName){
                        this.basketItems.splice(i, 1);
                    }
                }
            }

            var p = new BasketItemDTO(newItemName, quantityDesired)
            this.basketItems.push(p)
        },

        removeFromBasket: function(nameToDelete){
            for (let i = 0; i < this.basketItems.length; i++) {
              
                if(this.basketItems[i].itemName == nameToDelete){
                    this.basketItems.splice(i, 1);
                }
            }
            
        },

        createOrder: function(type){

            AXIOS.post('/create_order', {}, {
                params: {
                    orderType: type,
                    email: window.localStorage.getItem('email')
                    //email: 'Jeff@me'
                }
              })
                .then(response => {
              
                  // JSON responses are automatically parsed.
                    this.order = response.data

                  for (let i = 0; i < this.basketItems.length; i++) {
        
                    AXIOS.post('/create_order_item', {}, {
                        params: {
                            quantity: this.basketItems[i].quantityDesired,
                            itemName: this.basketItems[i].itemName,
                            orderId: this.order.id
                        }
                      })
                        .then(response => {
                      
                          // JSON responses are automatically parsed.
                          this.orderItems.push(response.data)
                
                        })
                        .catch(e => {
                          this.errorOrderItem = e
                        })
    
                  }
                  this.basketItems = [];
        
                })
                .catch(e => {
                  this.errorOrder = e
                })
        }

    }
}