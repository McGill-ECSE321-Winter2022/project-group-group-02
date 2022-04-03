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

function dailyScheduleDto (dayOfWeek,startTime,endTime) {
  this.dayOfWeek = dayOfWeek
  this.startTime = startTime
  this.endTime = endTime
}



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
        successDelete: ''
      }
    },

    created: function () {

       // Test data
    //const p1 = new dailyScheduleDto('Monday','08:00','20:00')
    //const p2 = new dailyScheduleDto('Tuesday','08:00','20:00')
    // Sample initial content
    //this.dailyschedules = [p1, p2]

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

            AXIOS.post('/create_dailyschedule', {}, {
            params: {
              dayOfWeek:dayOfWeek,
              startTime:startTime,
              endTime:endTime

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

                  // if (response.status === 200) {
                  // 	this.dayOfWeek = '',
                  // 		this.startTime = '',
                  //     this.endTime = '',
                  // 		this.successAdd = 'Daily Schedule added successfully!'
                  // }
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
          // Create a new Unavailable item and add it to the list of order
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
