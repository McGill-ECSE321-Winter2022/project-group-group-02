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
			errorUpdate: '',
			successUpdate: '',
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
                this.errorUpdate = e.response,
				console.log(this.errorUpdate)
            })
	},

	methods: {
		updateemployee: function (password, confirmPassword, name) {
			if (password != confirmPassword) {
				this.errorUpdate= "Passwords do not match."
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
						if (response.status === 200) {
								this.password = '',
								this.confirmPassword = '',
								this.errorUpdate = '',
								this.successUpdate = 'Account updated successfully!'
						}
					})
					.catch(e => {
						this.errorUpdate = e.response.data
						console.log(this.errorUpdate)					
					})

			}

		}
	}
}
