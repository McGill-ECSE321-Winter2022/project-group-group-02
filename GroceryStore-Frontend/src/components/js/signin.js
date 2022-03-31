import axios from 'axios'
import JQuery from 'jquery'
let $ = JQuery
var config = require ('../../../config')

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
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port

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
		}
	},
	methods: {
		signin: function (email, password, confirmPassword, name, address) {
			if (password != confirmPassword) {
				swal("ERROR", "Passwords do not match.", "error");
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
						if (response.status === 201) {
								this.password = '',
								this.confirmPassword = '',
								this.address = '',
								this.names = '',
								this.email = '',
							swal("Success", "Account created successfully!", "success");
						}
					})
					.catch(e => {
						swal("ERROR", e.response.data, "error");
					})

			}

		}
	}
}
