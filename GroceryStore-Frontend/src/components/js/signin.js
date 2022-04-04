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
		/*** Function to sign in a customer into the system.
		 * @author anaelle.drai
		 */
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
							// If the creatin of the customer was successfull, empty all the fields and display a success message!
							this.password = '',
							this.confirmPassword = '',
							this.address = '',
							this.name = '',
							this.email = '',
							this.successSignIn = 'Account created successfully!',
							localStorage.setItem('email', this.user.email)
							localStorage.setItem('type', this.type)
						}
					})
					.catch(e => {
						// Display the error message.
						this.errorSignIn = e.response.data
						console.log(this.errorLogIn)
					})

			}

		}
	}
}
