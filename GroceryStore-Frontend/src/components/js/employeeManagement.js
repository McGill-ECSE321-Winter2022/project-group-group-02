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

    }
  },

    created: function() {

      AXIOS.get('/view_dailyschedules', {})
        .then(response => {

          // JSON responses are automatically parsed.
          this.dailySchedules = response.data
        })
        .catch(e => {
          this.errorOrder = e
        })



      AXIOS.get('/view_employees', {})
        .then(response => {

          // JSON responses are automatically parsed.
          this.employees = response.data
        })
        .catch(e => {
          this.errorOrder = e
        })



    },



    methods:{

      hireEmployee: function (email, password, name, salary){

        AXIOS.post('/create_employee',{},{
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
              })
              .catch(e => {
                this.errorOrder = e
              })


            swal("Success", "Hire Succesfully !", "success");

          })
          .catch(e => {
            swal("ERROR", e.response.data, "error");
          })
      },




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
              })
              .catch(e => {
                this.errorOrder = e
              })


            swal("Success", "Updated Succesfully !", "success");

          })
          .catch(e => {
            swal("ERROR", e.response.data, "error");
          })
      },




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
              })
              .catch(e => {
                this.errorOrder = e
              })

            AXIOS.get('/view_dailyschedules', {})
              .then(response => {

                // JSON responses are automatically parsed.
                this.dailySchedules = response.data
              })
              .catch(e => {
                this.errorOrder = e
              })


            swal("Success", "Fired Succesfully !", "success");

          })
          .catch(e => {
            swal("ERROR", e.response.data, "error");
          })
      },







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
              })
              .catch(e => {
                this.errorOrder = e
              })

            AXIOS.get('/view_dailyschedules', {})
              .then(response => {

                // JSON responses are automatically parsed.
                this.dailySchedules = response.data
              })
              .catch(e => {
                this.errorOrder = e
              })


            swal("Success", "Assigned Succesfully !", "success");

          })
          .catch(e => {
            swal("ERROR", e.response.data, "error");
          })
      },






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
                this.errorOrder = e
              })

            AXIOS.get('/view_dailyschedules', {})
              .then(response => {

                // JSON responses are automatically parsed.
                this.dailySchedules = response.data
              })
              .catch(e => {
                this.errorOrder = e
              })

            swal("Success", "Unasigned Succesfully !", "success");

          })
          .catch(e => {
            swal("ERROR", e.response.data, "error");
          })
      }






    },
  }











