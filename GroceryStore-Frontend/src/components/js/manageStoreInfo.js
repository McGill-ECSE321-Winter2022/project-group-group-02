import axios from 'axios'
import JQuery from 'jquery'
let $ = JQuery
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
	name: 'store',
	data() {
		return {
			town: '',
			deliveryfee: '',
			errorUpdate: '',
			successUpdate: ''
		}
	},

	methods: {

		/*** Method to update store information
		 * @author cora.cheung
		 */
		managestoreinfo: function (town, deliveryfee) {
			AXIOS.put('/update_store', {}, {
				params: {
					town: town,
					deliveryFee: deliveryfee,
					storeId: 1
				}
			})
				.then(response => {
					if (response.status === 201) {
						this.town = '',
							this.deliveryfee = '',
							this.errorUpdate = ''
						this.successUpdate = 'Store Information Updated successfully!'
					}
				})
				.catch(e => {
					this.successUpdate = ''
					this.errorUpdate = e.response.data
					console.log(this.errorUpdate)
				})

		}

	}
}
