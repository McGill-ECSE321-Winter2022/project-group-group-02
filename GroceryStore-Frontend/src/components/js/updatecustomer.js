import axios from 'axios'
var config = require('../../../config')

// Setup frontend and backend urls 
var backendConfigurer = function(){
	switch(process.env.NODE_ENV){
      case 'development':
          return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
      case 'production':
          return 'https://' + config.build.backendHost + ':' + config.build.backendPort ;
	}
};

var frontendConfigurer = function(){
	switch(process.env.NODE_ENV){
      case 'development':
          return 'http://' + config.dev.host + ':' + config.dev.port;
      case 'production':
          return 'https://' + config.build.host + ':' + config.build.port ;
	}
};

var backendUrl = backendConfigurer();
var frontendUrl = frontendConfigurer();

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
	/*** Get the customer information to prefill the name and address fields.

	 * @author anaelle.drai
	 */
	created: function () {
		this.email = localStorage.getItem('email')
		AXIOS.get('/get_customer/?email='.concat(this.email))
			.then(response => {
				this.user = response.data
				this.address = this.user.address
				this.name = this.user.name
				// Fill the name and attribute fields
				document.getElementById('namefield').setAttribute('value', this.name)
				document.getElementById('addressfield').setAttribute('value', this.address);
			})
			.catch(e => {
				// Display the error message
				this.errorUpdate = e.response,
				console.log(this.errorUpdate)
			})
	},

	methods: {
		/*** Method to update a customer's information
		 * @author anaelle.drai
		 */
		updatecustomer: function (password, confirmPassword, name, address) {
			if (password != confirmPassword) {
				this.errorUpdate = "Passwords do not match."
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
							// If the update was successfull, empty all the fields and display a success message
							this.password = '',
								this.confirmPassword = '',
								this.errorUpdate = '',
								this.successUpdate = 'Account updated successfully!'
						}
					})
					.catch(e => {
						// Display the error message.
						this.errorUpdate = e.response.data
						this.successUpdate = ''
						console.log(this.errorUpdate)
					})

			}

		},
		/*** Method to delete a customer from the system
		 * @author anaelle.drai
		 */
		deletecustomer: function (password) {
			if (password != this.user.password) {
				this.errorDelete = "The password is incorrect."
			} else {
				var email = localStorage.getItem('email')
				console.log(email)
				AXIOS.delete('/delete_customer/?email='.concat(email))
					.then(response => {
						if (response.status === 200) {
							// When the customer is deleted, go to the login page.
							window.location.href = "/#/login"
						}
					})
					.catch(e => {
						// Display the error message
						this.errorDelete = e.response.data
						console.log(this.errorDelete)
					})

			}
		}
	}
}
