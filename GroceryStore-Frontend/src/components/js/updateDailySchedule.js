import axios from 'axios'
import JQuery from 'jquery'

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
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})



export default {
    name: 'dailyschedule',
    data () {
      return {
        dayOfWeek: '',
        startTime:'',
        endTime:'',
        errorUpdate: '',
        successUpdate: '',

      }
    },
    
    created: function () {
        AXIOS.get('/view_dailyschedules')
        .then(response => {
          // JSON responses are automatically parsed.
          this.dailyschedule = response.data
        })
        .catch(e => {
          this.errorUpdate = e
        })

     
      },


    
    
    methods: {
      
        addDailySchedule: function (dayOfWeek,startTime,endTime) {

            AXIOS.post('/create_dailyschedule', {}, {
            params: {
              dayOfWeek:dayOfWeek,
              startTime:startTime,
              endTime:endTime
              
            }
          })
            .then(response => {
						if (response.status === 200) {
							this.dayOfWeek = '',
								this.startTime = '',
                                this.endTime = '',
								this.successUpdate = 'Daily Schedule added successfully!'
						}
					})
					.catch(e => {
					this.errorUpdate = e.response.data
					console.log(this.errorUpdate)
					})

          
      },


        deleteDailySchedule: function (id) {
          // Create a new Unavailable item and add it to the list of order
            AXIOS.delete('/delete_dailyschedule',{}, {
            params: {
                DailyScheduleId:id
            }
          })
            .then(response => {

              AXIOS.get('/view_dailyschedules', {})
                .then(response => {

                  this.dailyschedule = response.data
                })
                .catch(e => {
                  this.errorUpdate = e
    
                })
    
              swal("success", "", "success");
    
            })
            .catch(e => {
              swal("ERROR", e.response.data, "error");
            })
        },

        

    }
}
  