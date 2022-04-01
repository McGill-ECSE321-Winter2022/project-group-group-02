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
			errorUpdate: ''
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
				this.errorUpdate = e.message,
					console.log(this.errorUpdate)
			})
	},

	methods: {
		updateowner: function (password, confirmPassword, name) {
			if (password != confirmPassword) {
				swal("ERROR", "Passwords do not match.", "error");
			} else {
				AXIOS.put('/update_owner_password/', {}, {
					params: {
						newPassword: password,
					}
				})
					.then(response => {
						if (response.status === 201) {
							this.password = '',
								this.confirmPassword = '',
								this.name = '',
								this.email = '',
								swal("Success", "Information updated successfully!", "success");
						}
					})
					.catch(e => {
						swal("ERROR", e.response.data, "error");
					})


				AXIOS.put('/update_owner_name/', {}, {
					params: {
						newName: name,
					}
				})
					.then(response => {
						if (response.status === 201) {
							this.password = '',
								this.confirmPassword = '',
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
