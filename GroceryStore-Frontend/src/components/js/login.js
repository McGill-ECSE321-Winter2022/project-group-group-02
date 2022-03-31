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
var frontendUrl = frontendConfigurer();

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
	name:'login',
	data () {
		return {
			user: '',
			type:'',
			email: '',
			password: '',
			errorLogin: '',
			response: []
		}
	},
	methods: {
		login (email, password) {
			AXIOS.post('/login/',$.param({email: email, password: password}))
			.then(response => {
				this.user = response.data
				if (response.status===200) {
					this.type = this.user.userType
					window.localStorage.setItem('email', this.user.email)
					window.localStorage.setItem('type', this.type)
					if(this.type.localeCompare("customer")==0){

						window.location.href = "/#/customermenu"
					}
					else if(this.type.localeCompare("employee")==0){
						window.location.href = "/#/employeemenu"
					}
					else {
						window.location.href = "/#/ownermenu"
					}

					location.reload();
				}
			})
			.catch(e => {

				swal("ERROR", e.response.data, "error");

			})
		}
	}

}