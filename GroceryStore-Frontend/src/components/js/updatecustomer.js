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
			errorUpdate: '',
			successUpdate: '',
			errorDelete: '',
			passwordDelete: '',
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
                this.errorUpdate = e.response,
				console.log(this.errorUpdate)
            })
	},

	methods: {
		updatecustomer: function (password, confirmPassword, name, address) {
			if (password != confirmPassword) {
				this.errorUpdate= "Passwords do not match."
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
						if (response.status === 200) {
								this.password = '',
								this.confirmPassword = '',
								this.errorUpdate = '',
								this.successUpdate = 'Account updated successfully!'
						}
					})
					.catch(e => {
					this.errorUpdate = e.response.data
					console.log(this.errorUpdate)					})

			}

		},
		deletecustomer: function (password) {
			if (password != this.user.password) {
				this.errorDelete= "The password is incorrect."
			} else {
				var email = localStorage.getItem('email')
				console.log(email)
				AXIOS.delete('/delete_customer/?email='.concat(email))
					.then(response => {
						if (response.status === 200) {
							window.location.href = "/#/login"
						}
					})
					.catch(e => {
					this.errorDelete = e.response.data
					console.log(this.errorDelete)					})

			}
		}
	}
}
