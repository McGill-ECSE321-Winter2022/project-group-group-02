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

	/*** @author anaelle.drai
	 * Create an instance of owner if it does not exist in the system
	 */
	created: function () {
		AXIOS.get('/view_owner/')
			.catch(e => {
				// If there is an error because the owner does not exist, create it.
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

		AXIOS.get('/view_store/?id='.concat(1))
			.catch(e => {
				// If there is an error because the owner does not exist, create it.
				console.log(e.response.data)
				AXIOS.post('/create_store/', {}, {
					params: {
						storeId: 1,
						town: "Montreal",
						deliveryFee: "10",
					}
				}).catch(e => {
					console.log(e.response)
				})
			})
	},
	methods: {
		/*** @author anaelle.drai
 		* Login the user into the system.
		*/
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
						// Get the user type to determine the corresponding page it should be sent to.
						this.type = this.user.userType
						console.log(this.user)
						
						// Store the customer information
						localStorage.setItem('email', this.user.email)
						localStorage.setItem('type', this.type)

						if (this.type.localeCompare("customer") == 0) {

							window.location.href = "/#/customermenu"
						}
						else if (this.type.localeCompare("employee") == 0) {
							window.location.href = "/#/employeemenu"
						}
						else {
							window.location.href = "/#/ownermenu"
						}
						// Send the customer to the next page
						location.reload();
					}
				})
				.catch(e => {
					// Display the error
					this.errorLogin = e.response.data
					console.log(this.errorLogIn)
				})
		}
	}

}