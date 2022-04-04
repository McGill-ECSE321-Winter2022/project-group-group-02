import axios from 'axios'
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
  name: 'newEmployee',
  data() {
    return {
      name: '',
      password: '',
      email: '',
      salary: '',
      employeeEmail: '',
      employees: [],
      dailySchedule: '',
      dailySchedules: [],
      errorHire:'',
      errorFire:'',
      errorUpdate:'',
      errorAssign:'',
      errorUnasign:'',
      errorViewSchedules: '',
      errorViewEmployees : '',
      emailUpdate: '',
      passwordUpdate:'',
      emailToBeFired:'',
      nameUpdate:'',
      salaryUpdate:'',
      employeeEmailToAssignDailySchedule:'',
      selected:'',
      sucessHire:'',
      sucessFire:'',
      sucessUpdate:'',
      sucessAssign:'',
      sucessUnasign:'',
      sucessViewSchedules: '',
      sucessViewEmployees : '',

    }
  },

    created: function() {

    //get schedules in the system
      AXIOS.get('/view_dailyschedules', {})
        .then(response => {

          // JSON responses are automatically parsed.
          this.dailySchedules = response.data
        })
        .catch(e => {
          this.errorViewSchedules = e.response.data
          console.log(this.errorViewSchedules)
        })



      //get employees in system
      AXIOS.get('/view_employees', {})
        .then(response => {

          // JSON responses are automatically parsed.
          this.employees = response.data
        })
        .catch(e => {
          this.errorViewEmployees = e.response.data
          console.log(this.errorViewEmployees)
        })



    },



    methods:{


      /**
       * Create an employee in the system
       * @author Karl Rouhana
       */
      hireEmployee: function (email, password, name, salary){

        //Call backend with correct parameters
        AXIOS.post('/create_employee',{},{
          params:{

            email: email,
            password: password,
            name: name,
            salary: salary,

          }


        })

          .then(response =>{

            //Refresh the employees
            AXIOS.get('/view_employees', {})
              .then(response => {

                // JSON responses are automatically parsed.
                this.employees = response.data
                this.sucessHire = 'Hire successfully !'
                this.errorHire =''
              })
              .catch(e => {
                this.sucessHire =''
                this.errorHire = e.response.data
                console.log(this.errorHire)
              })



          })
          .catch(e => {
            this.sucessHire =''
            this.errorHire = e.response.data
            console.log(this.errorHire)
          })
      },



      /**
       * Update an employee already the system
       * @author Karl Rouhana
       */

      updateEmployee: function (email, password, name, salary){

        AXIOS.put('/update_employee',{},{
          params:{

            email: email,
            password: password,
            name: name,
            salary: salary,

          }

        })

          .then(response =>{

            AXIOS.get('/view_employees', {})
              .then(response => {

                // JSON responses are automatically parsed.
                this.employees = response.data
                this.sucessUpdate = 'Updated successfully !'
                this.errorUpdate =''
              })
              .catch(e => {
                this.errorUpdate = e.response.data
                console.log(this.errorUpdate)
              })




          })
          .catch(e => {
            this.successUpdate = ''
            this.errorUpdate = e.response.data
            console.log(this.errorUpdate)
          })
      },



      /**
       * Fire an employee already the system
       * @author Karl Rouhana
       */

      fireEmployee: function (email){

        AXIOS.delete('/delete_employee',{
          params:{
            email: email
          }

        })

          .then(response =>{

            AXIOS.get('/view_employees', {})
              .then(response => {

                // JSON responses are automatically parsed.
                this.employees = response.data
                this.sucessFire = 'Fired successfully !'
                this.errorFire = ''
              })
              .catch(e => {
                this.errorFire = e.response.data
                this.successFire = ''
                console.log(this.errorFire)
              })

            AXIOS.get('/view_dailyschedules', {})
              .then(response => {

                // JSON responses are automatically parsed.
                this.dailySchedules = response.data
              })
              .catch(e => {
                this.errorFire = e.response.data
                console.log(this.errorFire)
              })



          })
          .catch(e => {
            this.successFire = ''
            this.errorFire = e.response.data
            console.log(this.errorFire)
          })
      },






      /**
       * Assign a schedule to an employee already the system
       * @author Karl Rouhana
       */
      assignSchedule: function (email, dailySchedule){

        AXIOS.post('/add_dailySchedule',{},{
          params:{

            email: email,
            id: dailySchedule.id,

          }

        })

          .then(response =>{

            AXIOS.get('/view_employees', {})
              .then(response => {

                // JSON responses are automatically parsed.
                this.employees = response.data
                this.sucessAssign = 'Assigned successfully'
                this.errorAssign = ''
              })
              .catch(e => {
                this.errorAssign = e.response.data
                this.successAssign= ''
                console.log(this.errorAssign)
              })

            AXIOS.get('/view_dailyschedules', {})
              .then(response => {

                // JSON responses are automatically parsed.
                this.dailySchedules = response.data
              })
              .catch(e => {
                this.errorAssign = e.response.data
                console.log(this.errorAssign)
              })



          })
          .catch(e => {
            this.errorAssign = e.response.data
            console.log(this.errorAssign)
          })
      },





      /**
       * Unassign a schedule to an employee already the system
       * @author Karl Rouhana
       */
      unassignSchedule: function (employee, dailySchedule){

        AXIOS.delete('/remove_dailySchedule',{
          params:{

            email: employee.email,
            id: dailySchedule.id,

          }

        })

          .then(response =>{

            AXIOS.get('/view_employees', {})
              .then(response => {

                // JSON responses are automatically parsed.
                this.employees = response.data
              })
              .catch(e => {
                this.errorUnassign = e.response.data
                console.log(this.errorUnassign)
              })

            AXIOS.get('/view_dailyschedules', {})
              .then(response => {

                // JSON responses are automatically parsed.
                this.dailySchedules = response.data
                this.sucessUnasign = 'Removed successfully !'
              })
              .catch(e => {
                this.errorUnassign = e.response.data
                console.log(this.errorUnassign)
              })



          })
          .catch(e => {
            this.errorUnassign = e.response.data
            console.log(this.errorUnassign)
          })
      }

    },
  }











