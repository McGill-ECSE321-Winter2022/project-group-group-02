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
	name: 'updateowner',
	data() {
		return {
			email: '',
			password: '',
			confirmPassword: '',
			name: '',
			errorUpdate: '',
			successUpdate: '',

		}
	},
	created: function () {
		this.email = localStorage.getItem('email')
		AXIOS.get('/view_owner/')
			.then(response => {
				this.user = response.data
				this.name = this.user.name
				document.getElementById('namefield').setAttribute('value', this.name)
			})
			.catch(e => {
                this.errorUpdate = e.response,
				console.log(this.errorUpdate)
			})
	},

	methods: {
		updateowner: function (password, confirmPassword, name) {
			if (password != confirmPassword) {
				this.errorUpdate= "Passwords do not match."
			} else {
				AXIOS.put('/update_owner_password/', {}, {
					params: {
						newPassword: password,
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


				AXIOS.put('/update_owner_name/', {}, {
					params: {
						newName: name,
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

		}
	}
}
