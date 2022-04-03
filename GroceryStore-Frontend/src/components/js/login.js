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
	name: 'login',
	data() {
		return {
			user: '',
			type: '',
			email: '',
			password: '',
			errorLogin: '',
			response: []
		}
	},
	created: function () {
		AXIOS.get('/view_owner/')
			.catch(e => {
				console.log(e.response.data)
				AXIOS.post('/create_owner/', {}, {
					params: {
						email: "admin@grocerystore.com",
						password: "1234",
						name: "Owner",
					}
				}).catch(e => {
					console.log(e.response)
				})
			})
	},
	methods: {
		login: function (email, password) {
			AXIOS.post('/login/', {}, {
				params: {
					email: email,
					password: password,
				}
			})
				.then(response => {
					if (response.status === 200) {
						this.user = response.data
						this.type = this.user.userType
						console.log(this.user)
						localStorage.setItem('email', this.user.email)
						localStorage.setItem('type', this.type)
						console.log(localStorage.getItem('email'))
						if (this.type.localeCompare("customer") == 0) {

							window.location.href = "/#/customermenu"
						}
						else if (this.type.localeCompare("employee") == 0) {
							window.location.href = "/#/employeemenu"
						}
						else {
							window.location.href = "/#/ownermenu"
						}

						location.reload();
					}
				})
				.catch(e => {
					this.errorLogin = e.response.data
					console.log(this.errorLogIn)
				})
		}
	}

}