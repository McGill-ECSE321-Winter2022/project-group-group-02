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
    name: 'dailyschedule',
    data () {
      return {
        dailyschedule:'',
        dailyschedules:[],
        dayOfWeek: '',
        startTime:'',
        endTime:'',
        errorAdd: '',
        successAdd: '',
        errorDelete: '',
        successDelete: '',
        dayofweek:'',
      }
    },

    created: function () {

        AXIOS.get('/view_dailyschedules')
        .then(response => {
          // JSON responses are automatically parsed.
          this.dailyschedules = response.data
        })
        .catch(e => {
          this.errorAdd = e
        })


      },


    methods: {

      /*** Method to add a daily schedule
       * @author cora.cheung
       */

        addDailySchedule: function (dayOfWeek,startTime,endTime) {

            AXIOS.post('/create_dailyschedule/',{}, {
            params: {
              dayOfWeek: dayOfWeek,
              startTime:startTime,
              endTime: endTime

            }
          })
            .then(response => {
              AXIOS.get('/view_dailyschedules')
                .then(response => {
                  // JSON responses are automatically parsed.
                  this.dailyschedules = response.data
                  this.dayOfWeek = ''
                  this.startTime = ''
                  this.endTime = ''
                  this.successAdd = 'Daily Schedule added successfully!'
                  this.errorAdd=''
                })
                .catch(e => {
                  this.errorAdd = e.response.data
                  console.log(this.errorAdd)
                  this.successAdd=''

                })

                })
              .catch(e => {
                this.errorAdd = e.response.data
                console.log(this.errorAdd)
                this.successAdd=''


              })


      },


      /*** Method to delete a daily schedule
       * @author cora.cheung
       */

        deleteDailySchedule: function (id) {
          AXIOS.delete('/delete_dailyschedule/?DailyScheduleId='.concat(id))
            .then(response => {

              AXIOS.get('/view_dailyschedules')
                .then(response => {
                  // JSON responses are automatically parsed.
                  this.dailyschedules = response.data
                  this.errorAdd=''
                  this.successAdd=''
                })
                .catch(e => {
                  this.errorDelete = e.response.data
                  this.errorAdd=''
                  this.successAdd=''
                })
            })
            .catch(e => {
              this.errorAdd = e.response.data
              console.log(this.errorAdd)
              this.errorAdd=''
              this.successAdd=''
            })
        }



    }
}
