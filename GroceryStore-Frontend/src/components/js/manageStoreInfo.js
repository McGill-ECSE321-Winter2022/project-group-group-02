import axios from 'axios'
import JQuery from 'jquery'
let $ = JQuery
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

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
			successUpdate: '',
		}
	},

	methods: {
		managestoreinfo: function (town,deliveryfee) {
				AXIOS.put('/update_store', {}, {
					params: {
                        town: town,
                        deliveryFee: deliveryfee,
                        storeID:1
					}
				})
					.then(response => {
						if (response.status === 200) {
							this.town = '',
								this.deliveryfee = '',
								this.successUpdate = 'Store Information Updated successfully!'
						}
					})
					.catch(e => {
					this.errorUpdate = e.response.data
					console.log(this.errorUpdate)
					})

			}

		}
	}
