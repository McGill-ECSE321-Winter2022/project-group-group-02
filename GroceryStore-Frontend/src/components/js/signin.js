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
	name: 'signin',
	data() {
		return {
			email: '',
			password: '',
			address: '',
			name: '',
			errorSignIn: '',
			successSignIn: '', 
			confirmPassword: '',
		}
	},
	methods: {
		signin: function (email, password, confirmPassword, name, address) {
			if (password != confirmPassword) {
				this.errorSignIn = "Passwords do not match."
			} else {
				AXIOS.post('/create_customer/', {}, {
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
								this.address = '',
								this.names = '',
								this.email = '',
								this.successSignIn = 'Account created successfully!',
								localStorage.setItem('email', this.user.email)
								localStorage.setItem('type', this.type)
						}
					})
					.catch(e => {
					this.errorSignIn = e.response.data
					console.log(this.errorLogIn)
					})

			}

		}
	}
}
