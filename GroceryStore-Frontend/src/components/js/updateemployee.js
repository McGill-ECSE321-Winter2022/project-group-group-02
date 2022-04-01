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
	name: 'updateemployee',
	data() {
		return {
			email: '',
			password: '',
			confirmPassword: '',
			name: '',
			salary: '',
			errorUpdate: ''
		}
	},
	created: function () {
		this.email = localStorage.getItem('email')
		AXIOS.get('/get_employee/?email='.concat(this.email))
            .then(response => {
				this.user = response.data
				this.name = this.user.name
				this.salary= this.user.salary
				document.getElementById('namefield').setAttribute('value', this.name)
			})
            .catch(e => {
                this.errorUpdate = e.message,
				console.log(this.errorUpdate)
            })
	},

	methods: {
		updateemployee: function (password, confirmPassword, name) {
			if (password != confirmPassword) {
				swal("ERROR", "Passwords do not match.", "error");
			} else {
				var email = localStorage.getItem('email')
				AXIOS.put('/update_employee/', {}, {
					params: {
						email: email,
						name: name,
						password: password,
						salary: this.salary,
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
