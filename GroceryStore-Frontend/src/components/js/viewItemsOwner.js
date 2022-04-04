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
var frontendUrl = frontendConfigurer();

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})



export default {
    name: 'item',
    data () {
      return {
        name : '',
        unavailableItemName: '',
        unavailableItemPrice: '',
        unavailableItemName: '',
        unavailableItemPrice: '',
        newUnavailablePrice: '',
        price : '',

        errorCreateShoppableItem: '',
        successCreateShoppableItem: '',

        errorCreateUnavailableItem: '',
        successCreateUnavailableItem: '',

        errorUpdateShoppableItemPrice: '',
        successUpdateShoppableItemPrice: '',

        errorUpdateUnavailableItemPrice: '',
        successUpdateUnavailableItemPrice: '',

        errorReplenishInventory: '',
        successReplenishInventory: '',

        successItem: '',
        quantityAvailable : '',
        isOwner: '',
        isEmployee: '',
        shoppableItems: [],
        unavailableItems: []

      }
    },

    /**
   * @author Ralph Nassar
   * @description initializes page with the all the items in the system
   */

    created: function () {
          if (localStorage.getItem('type').localeCompare("employee") == 0) {
        this.isEmployee = true
    } else if (localStorage.getItem('type').localeCompare("owner") == 0) {
        this.isOwner = true
    }
        // Initializing shoppable items from backend
        AXIOS.get('/view_all_shoppable_item')
      .then(response => {
        // JSON responses are automatically parsed.
        this.shoppableItems = response.data
      })
      .catch(e => {
        this.errorItem = e.response.data
      })

      AXIOS.get('/view_all_unavailable_item')
      .then(response => {
        // JSON responses are automatically parsed.
        this.unavailableItems = response.data
      })
      .catch(e => {
        this.errorItem = e.response.data
      })
       

      
      },

    
    computed: {
      orderedShoppableItems: function() {
        return _.orderBy(this.shoppableItems, 'item.name')
      }
    },
    
    methods: {

        /**
         * 
         * @param {String} name 
         * @param {Double} price
         * @description create an unavailable item in the system and add it in the table
         */

        createUnavailableItem: function (name,price) {
            // Create a new Unavailable item and add it to the list of order
            AXIOS.post('/create_unavailable_item', {}, {
            params: {
              name: name,
              price: price,
              
            }
          })
            .then(response => {

              AXIOS.get('/view_all_unavailable_item')
      .then(response => {
        // JSON responses are automatically parsed.
        this.errorCreateShoppableItem=''
        this.successCreateShoppableItem=''

        this.errorCreateUnavailableItem= ''
        this.successCreateUnavailableItem='Item successfully created!'

        this.errorUpdateShoppableItemPrice=''
        this.successUpdateShoppableItemPrice=''

        this.errorUpdateUnavailableItemPrice=''
        this.successUpdateUnavailableItemPrice=''

        this.errorReplenishInventory=''
        this.successReplenishInventory=''

        this.unavailableItems = response.data
      })
      .catch(e => {
        this.errorCreateShoppableItem=''
        this.successCreateShoppableItem=''

        this.errorCreateUnavailableItem= e.response.data
        this.successCreateUnavailableItem=''

        this.errorUpdateShoppableItemPrice=''
        this.successUpdateShoppableItemPrice=''

        this.errorUpdateUnavailableItemPrice=''
        this.successUpdateUnavailableItemPrice=''

        this.errorReplenishInventory=''
        this.successReplenishInventory=''
      })
    
    
            })
            .catch(e => {
               this.errorCreateShoppableItem=''
                this.successCreateShoppableItem=''

                this.errorCreateUnavailableItem= e.response.data
                this.successCreateUnavailableItem=''

                this.errorUpdateShoppableItemPrice=''
                this.successUpdateShoppableItemPrice=''

                this.errorUpdateUnavailableItemPrice=''
                this.successUpdateUnavailableItemPrice=''

                this.errorReplenishInventory=''
                this.successReplenishInventory=''
            })

          
      },
        /**
         * 
         * @param {String} name 
         * @param {Double} price
         * @param {Integer} quantityAvailable
         * @author Ralph Nassar
         * 
         * @description create an unavailable item in the system and add it in the table
         */
        createShoppableItem: function (name,price,quantityAvailable) {
          AXIOS.post('/create_shoppable_item', {}, {
            params: {
              name: name,
              price: price,
              quantityAvailable: quantityAvailable,
              
            }
          })
            .then(response => {

                  // Initializing shoppable items from backend
        AXIOS.get('/view_all_shoppable_item')
      .then(response => {
        // JSON responses are automatically parsed.
        this.errorCreateShoppableItem=''
        this.successCreateShoppableItem='Item successfully created'

        this.errorCreateUnavailableItem=''
        this.successCreateUnavailableItem=''

        this.errorUpdateShoppableItemPrice=''
        this.successUpdateShoppableItemPrice=''

        this.errorUpdateUnavailableItemPrice=''
        this.successUpdateUnavailableItemPrice=''

        this.errorReplenishInventory=''
        this.successReplenishInventory=''

        this.shoppableItems = response.data
      })
      .catch(e => {
        this.errorCreateShoppableItem=e.response.data
        this.successCreateShoppableItem=''

        this.errorCreateUnavailableItem=''
        this.successCreateUnavailableItem=''

        this.errorUpdateShoppableItemPrice=''
        this.successUpdateShoppableItemPrice=''

        this.errorUpdateUnavailableItemPrice=''
        this.successUpdateUnavailableItemPrice=''

        this.errorReplenishInventory=''
        this.successReplenishInventory=''
        
      })
    
            })
            .catch(e => {
              this.errorCreateShoppableItem = e.response.data
              this.successCreateShoppableItem=''

              this.errorCreateUnavailableItem= ''
              this.successCreateUnavailableItem= ''

              this.errorUpdateShoppableItemPrice=''
              this.successUpdateShoppableItemPrice=''

              this.errorUpdateUnavailableItemPrice=''
              this.successUpdateUnavailableItemPrice=''

              this.errorReplenishInventory=''
              this.successReplenishInventory=''
            })

        },
      
       /**
        * 
        * @param {String} name 
        * @param {Integer} newQuantityAvailable
        * @author Ralph Nassar
        * @description update the quantity of a shoppable item and display the change in the table
        */
      
        replenishInventory: function (name,newQuantityAvailable) {
          // Create a new Shoppable item and add it to the list of order
        AXIOS.put('/update_shoppable_item_quantity_available/?name='.concat(name, "&newQuantityAvailable=", newQuantityAvailable))            
        .then(response => {
              this.successReplenishInventory='Inventory replenished!'
              AXIOS.get('/view_all_shoppable_item')
      .then(response => {
        // JSON responses are automatically parsed.

        this.errorCreateShoppableItem = ''
        this.successCreateShoppableItem=''

        this.errorCreateUnavailableItem= ''
        this.successCreateUnavailableItem= ''

        this.errorUpdateShoppableItemPrice=''
        this.successUpdateShoppableItemPrice=''

        this.errorUpdateUnavailableItemPrice=''
        this.successUpdateUnavailableItemPrice=''

        this.errorReplenishInventory=''
        this.successReplenishInventory='Inventory replenished!'

        this.shoppableItems = response.data
      })
      .catch(e => {

        this.errorCreateShoppableItem=''
        this.successCreateShoppableItem=''

        this.errorCreateUnavailableItem= ''
        this.successCreateUnavailableItem= ''

        this.errorUpdateShoppableItemPrice=''
        this.successUpdateShoppableItemPrice=''

        this.errorUpdateUnavailableItemPrice=''
        this.successUpdateUnavailableItemPrice=''

        this.errorReplenishInventory = e.response.data
        this.successReplenishInventory=''
      })
    
    
            })
            .catch(e => {
              this.errorCreateShoppableItem=''
              this.successCreateShoppableItem=''

              this.errorCreateUnavailableItem= ''
              this.successCreateUnavailableItem= ''

              this.errorUpdateShoppableItemPrice=''
              this.successUpdateShoppableItemPrice=''

              this.errorUpdateUnavailableItemPrice=''
              this.successUpdateUnavailableItemPrice=''

              this.errorReplenishInventory = e.response.data
              this.successReplenishInventory=''
            })
        },

        /**
        * 
        * @param {String} name 
        * @param {Double} price
        * @author Ralph Nassar
        * @description update the price of a shoppable item and display the change in the table
        */

        updateShoppableItemPrice: function (name, price) {
          // Create a new Unavailable item and add it to the list of order
            AXIOS.put('/update_shoppable_item_price/?name='.concat(name, "&newPrice=", price))
            .then(response => {

              AXIOS.get('/view_all_shoppable_item')
      .then(response => {
        // JSON responses are automatically parsed.
        this.errorCreateShoppableItem=''
        this.successCreateShoppableItem=''

        this.errorCreateUnavailableItem= ''
        this.successCreateUnavailableItem= ''

        this.errorUpdateShoppableItemPrice=''
        this.successUpdateShoppableItemPrice='Price updated!'

        this.errorUpdateUnavailableItemPrice=''
        this.successUpdateUnavailableItemPrice=''

        this.errorReplenishInventory = ''
        this.successReplenishInventory=''

        this.shoppableItems = response.data
      })
      .catch(e => {
        this.errorCreateShoppableItem=''
        this.successCreateShoppableItem=''

        this.errorCreateUnavailableItem= ''
        this.successCreateUnavailableItem= ''

        this.errorUpdateShoppableItemPrice=e.response.data
        this.successUpdateShoppableItemPrice=''

        this.errorUpdateUnavailableItemPrice=''
        this.successUpdateUnavailableItemPrice=''

        this.errorReplenishInventory = ''
        this.successReplenishInventory=''      
      })
                
    
            })
            .catch(e => {
              this.errorCreateShoppableItem=''
              this.successCreateShoppableItem=''

              this.errorCreateUnavailableItem= ''
              this.successCreateUnavailableItem= ''

              this.errorUpdateShoppableItemPrice=e.response.data
              this.successUpdateShoppableItemPrice=''

              this.errorUpdateUnavailableItemPrice=''
              this.successUpdateUnavailableItemPrice=''

              this.errorReplenishInventory = ''
              this.successReplenishInventory=''
            })
        },
        
        /**
        * 
        * @param {String} name 
        * @param {Double} price
        * @author Ralph Nassar
        * @description update the price of an unavailable item and display the change in the table
        */

        updateUnavailableItemPrice: function (name,newPrice) {
          // Create a new Unavailable item and add it to the list of order
            AXIOS.put('/update_unavailable_item_price', {}, {
            params: {
              name: name,
              newPrice: newPrice              
            }
          })
            .then(response => {


              AXIOS.get('/view_all_unavailable_item')
      .then(response => {
        // JSON responses are automatically parsed.
        this.errorCreateShoppableItem=''
        this.successCreateShoppableItem=''

        this.errorCreateUnavailableItem= ''
        this.successCreateUnavailableItem= ''

        this.errorUpdateShoppableItemPrice=''
        this.successUpdateShoppableItemPrice=''

        this.errorUpdateUnavailableItemPrice=''
        this.successUpdateUnavailableItemPrice='Price updated!'

        this.errorReplenishInventory = ''
        this.successReplenishInventory=''

        this.unavailableItems = response.data
      })
      .catch(e => {
        this.errorCreateShoppableItem=''
        this.successCreateShoppableItem=''

        this.errorCreateUnavailableItem= ''
        this.successCreateUnavailableItem= ''

        this.errorUpdateShoppableItemPrice=''
        this.successUpdateShoppableItemPrice=''

        this.errorUpdateUnavailableItemPrice=e.response.data
        this.successUpdateUnavailableItemPrice=''

        this.errorReplenishInventory = ''
        this.successReplenishInventory=''
      })
    
    
            })
            .catch(e => {
              this.errorCreateShoppableItem=''
              this.successCreateShoppableItem=''

              this.errorCreateUnavailableItem= ''
              this.successCreateUnavailableItem= ''

              this.errorUpdateShoppableItemPrice=''
              this.successUpdateShoppableItemPrice=''

              this.errorUpdateUnavailableItemPrice=e.response.data
              this.successUpdateUnavailableItemPrice=''

              this.errorReplenishInventory = ''
              this.successReplenishInventory=''
            })
        },

        /**
         * 
         * @param {String} name
         * @author Ralph Nassar
         * @description deletes a shoppable item from the system and removes it from the table
         */

        deleteShoppableItem: function (name) {
          // Create a new Unavailable item and add it to the list of order
            AXIOS.delete('/delete_shoppable_item/?name='.concat(name))
            .then(response => {

              AXIOS.get('/view_all_shoppable_item')
      .then(response => {
        // JSON responses are automatically parsed.
        this.errorCreateShoppableItem=''
        this.successCreateShoppableItem=''

        this.errorCreateUnavailableItem= ''
        this.successCreateUnavailableItem= ''

        this.errorUpdateShoppableItemPrice=''
        this.successUpdateShoppableItemPrice=''

        this.errorUpdateUnavailableItemPrice=''
        this.successUpdateUnavailableItemPrice=''

        this.errorReplenishInventory = ''
        this.successReplenishInventory=''

        this.shoppableItems = response.data
      })
      .catch(e => {
        this.errorCreateShoppableItem=''
        this.successCreateShoppableItem=''

        this.errorCreateUnavailableItem= ''
        this.successCreateUnavailableItem= ''

        this.errorUpdateShoppableItemPrice=''
        this.successUpdateShoppableItemPrice=''

        this.errorUpdateUnavailableItemPrice=''
        this.successUpdateUnavailableItemPrice=''

        this.errorReplenishInventory = ''
        this.successReplenishInventory=''
      })
    
    
            })
            .catch(e => {
              this.errorCreateShoppableItem=''
              this.successCreateShoppableItem=''

              this.errorCreateUnavailableItem= ''
              this.successCreateUnavailableItem= ''

              this.errorUpdateShoppableItemPrice=''
              this.successUpdateShoppableItemPrice=''

              this.errorUpdateUnavailableItemPrice=''
              this.successUpdateUnavailableItemPrice=''

              this.errorReplenishInventory = ''
              this.successReplenishInventory=''
            })
        },

        /**
         * 
         * @param {String} name
         * @author Ralph Nassar
         * @description deletes an unavailable item from the system and removes it from the table
         */

        deleteUnavailableItem: function (name) {
          // Create a new Unavailable item and add it to the list of order
            AXIOS.delete('/delete_unavailable_item', {
            params: {
              name: name,
            }
          })
            .then(response => {

             AXIOS.get('/view_all_unavailable_item')
      .then(response => {
        // JSON responses are automatically parsed.
        this.errorCreateShoppableItem=''
        this.successCreateShoppableItem=''

        this.errorCreateUnavailableItem= ''
        this.successCreateUnavailableItem= ''

        this.errorUpdateShoppableItemPrice=''
        this.successUpdateShoppableItemPrice=''

        this.errorUpdateUnavailableItemPrice=''
        this.successUpdateUnavailableItemPrice=''

        this.errorReplenishInventory = ''
        this.successReplenishInventory=''

        this.unavailableItems = response.data
      })
      .catch(e => {
        this.errorCreateShoppableItem=''
        this.successCreateShoppableItem=''

        this.errorCreateUnavailableItem= ''
        this.successCreateUnavailableItem= ''

        this.errorUpdateShoppableItemPrice=''
        this.successUpdateShoppableItemPrice=''

        this.errorUpdateUnavailableItemPrice=''
        this.successUpdateUnavailableItemPrice=''

        this.errorReplenishInventory = ''
        this.successReplenishInventory=''
      })
    
    
            })
            .catch(e => {
              this.errorCreateShoppableItem=''
              this.successCreateShoppableItem=''

              this.errorCreateUnavailableItem= ''
              this.successCreateUnavailableItem= ''

              this.errorUpdateShoppableItemPrice=''
              this.successUpdateShoppableItemPrice=''

              this.errorUpdateUnavailableItemPrice=''
              this.successUpdateUnavailableItemPrice=''

              this.errorReplenishInventory = ''
              this.successReplenishInventory=''
            })
        }
        

    }
}
  