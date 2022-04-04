import axios from 'axios'
import JQuery from 'jquery'
let $ = JQuery
var config = require('../../../config')

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
	/*** Method to prefill the owner's name field
	 * @author anaelle.drai
	 */
	created: function () {
		this.email = localStorage.getItem('email')
		AXIOS.get('/view_owner/')
			.then(response => {
				this.user = response.data
				this.name = this.user.name
				// Fill the name field
				document.getElementById('namefield').setAttribute('value', this.name)
			})
			.catch(e => {
				// Display the error message
				this.errorUpdate = e.response,
					console.log(this.errorUpdate)
			})
	},

	methods: {
		/*** Method to update the owner's information
		 * @author anaelle.drai
		 */
		updateowner: function (password, confirmPassword, name) {
			if (password != confirmPassword) {
				this.errorUpdate = "Passwords do not match."
			} else {
				// Update the owner's password
				AXIOS.put('/update_owner_password/', {}, {
					params: {
						newPassword: password,
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
				// Update the owner's name
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
						// Display the error message.
						this.errorUpdate = e.response.data
						console.log(this.errorUpdate)
					})
			}

		}
	}
}
