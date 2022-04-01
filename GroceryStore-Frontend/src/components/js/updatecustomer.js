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
	name: 'updatecustomer',
	data() {
		return {
			email: '',
			password: '',
			confirmPassword: '',
			address: '',
			name: '',
			errorUpdate: ''
		}
	},
	created: function () {
		this.email = localStorage.getItem('email')
		AXIOS.get('/get_customer/?email='.concat(this.email))
            .then(response => {
				this.user = response.data
				this.address = this.user.address
				this.name = this.user.name
				document.getElementById('namefield').setAttribute('value', this.name)
				document.getElementById('addressfield').setAttribute('value', this.address);
			})
            .catch(e => {
                this.errorUpdate = e.message,
				console.log(this.errorUpdate)
            })
	},

	methods: {
		updatecustomer: function (password, confirmPassword, name, address) {
			if (password != confirmPassword) {
				swal("ERROR", "Passwords do not match.", "error");
			} else {
				var email = localStorage.getItem('email')
				AXIOS.put('/update_customer/', {}, {
					params: {
						email: email,
						address: address,
						name: name,
						password: password,
					}
				})
					.then(response => {
						if (response.status === 201) {
								this.password = '',
								this.confirmPassword = '',
								this.address = '',
								this.name = '',
								this.email = '',
							swal("Success", "Information updated successfully!", "success");
						}
					})
					.catch(e => {
						swal("ERROR", e.response.data, "error");
					})

			}

		}
	}
}
