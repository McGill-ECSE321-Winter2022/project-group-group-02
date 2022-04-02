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
        name: '',
        price: '',
        order: '',
        errorItem: '',
        shoppableItems: [],
        unavailableItems: [],
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

        

    }
}