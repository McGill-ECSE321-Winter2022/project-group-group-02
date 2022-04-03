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
	/*** Get the employee information to prefill the name field.
	 * @author anaelle.drai
	 */
	created: function () {
		this.email = localStorage.getItem('email')
		AXIOS.get('/get_employee/?email='.concat(this.email))
            .then(response => {
				// Fill the name field
				this.user = response.data
				this.name = this.user.name
				this.salary= this.user.salary
				document.getElementById('namefield').setAttribute('value', this.name)
			})
            .catch(e => {
				// Display the error message
                this.errorUpdate = e.response,
				console.log(this.errorUpdate)
            })
	},

	methods: {
		/*** Method to update an employee's information
		 * @author anaelle.drai
		 */
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
								// If the update was successfull, empty all the fields and display a success message
								this.password = '',
								this.confirmPassword = '',
								this.errorUpdate = '',
								this.successUpdate = 'Account updated successfully!'
						}
					})
					.catch(e => {
						// Display the error message
						this.errorUpdate = e.response.data
						console.log(this.errorUpdate)					
					})

			}

		}
	}
}
