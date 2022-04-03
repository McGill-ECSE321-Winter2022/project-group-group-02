<template>
<div id="employeeManagement">

  <div class="overlay">
  </div>
  <div class="vertical-center">



  <div class="container">
    <div class="row d-md-flex">
      <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5" id = "firstHeader">
        <h2 class="mb-4" >Hire new employee</h2>
        <form action="#" class="appointment">
          <div class="col-md-12">
            <div class="form-group">
              <div class="form-field">
                <div class="select-wrap"></div>
              </div>
            </div>
          </div>
          <div class="row-md-6">
            <div class="form-group">
              <input
                type="text"
                class="form-control"
                v-model="email"
                placeholder="Email"
              />
            </div>
          </div>
          <div class="row-md-6">
            <div class="form-group">
              <input
                type="text"
                class="form-control"
                v-model="name"
                placeholder="Name"
              />
            </div>
          </div>
          <div class="row-md-6">
            <div class="form-group">
              <input
                type="password"
                class="form-control"
                v-model="password"
                placeholder="Password"
              />
            </div>
          </div>
          <div class="row-md-6">
            <div class="form-group">
              <input
                type="text"
                class="form-control"
                v-model="salary"
                placeholder="Salary"
              />
            </div>
          </div>
          <div class="col-md-12">
            <div class="form-group">
              <button
                type="button"
                class="btn btn-dark py-50 px-6"
                v-bind:disabled="null"
                @click="hireEmployee(email, password, name, salary)"
              >
                Hire Employee
              </button>
            </div>
          </div>
          <div class="form-group">
            <h5
              v-if="errorHire"
              style="color: red; padding-top: 20px"
            >Error: {{ errorHire }}</h5>
          </div>

          <div class="form-group">
            <h5
              v-if="sucessHire"
              style="color: green; padding-top: 20px"
            >{{ sucessHire }}</h5>
          </div>

        </form>
        <br />
      </div>
    </div>
  </div>





    <div class="container">
      <div class="row d-md-flex">
        <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5" id = "secondHeader">
          <h2 class="mb-4" >Update existing employee</h2>
          <form action="#" class="appointment">
            <div class="col-md-12">
              <div class="form-group">
                <div class="form-field">
                  <div class="select-wrap"></div>
                </div>
              </div>
            </div>
            <div class="row-md-6">
              <div class="form-group">
                <input
                  type="text"
                  class="form-control"
                  v-model="emailUpdate"
                  placeholder="Email"
                />
              </div>
            </div>
            <div class="row-md-6">
              <div class="form-group">
                <input
                  type="text"
                  class="form-control"
                  v-model="nameUpdate"
                  placeholder="Name"
                />
              </div>
            </div>
            <div class="row-md-6">
              <div class="form-group">
                <input
                  type="password"
                  class="form-control"
                  v-model="passwordUpdate"
                  placeholder="Password"
                />
              </div>
            </div>
            <div class="row-md-6">
              <div class="form-group">
                <input
                  type="text"
                  class="form-control"
                  v-model="salaryUpdate"
                  placeholder="Salary"
                />
              </div>
            </div>
            <div class="col-md-12">
              <div class="form-group">
                <button
                  type="button"
                  class="btn btn-dark py-50 px-6"
                  @click="updateEmployee(emailUpdate, passwordUpdate, nameUpdate, salaryUpdate)"
                >
                  Update Employee
                </button>
              </div>
            </div>

            <div class="form-group">
              <h5
                v-if="errorUpdate"
                style="color: red; padding-top: 20px"
              >Error: {{ errorUpdate }}</h5>
            </div>


            <div class="form-group">
              <h5
                v-if="sucessUpdate"
                style="color: green; padding-top: 20px"
              >{{ sucessUpdate }}</h5>
            </div>

          </form>
          <br />

        </div>
      </div>
    </div>




    <div class="container">
      <div class="row d-md-flex">
        <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5" id = "thirdHeader">
          <h2 class="mb-4">Fire Employee</h2>
          <form action="#" class="appointment">
            <div class="col-md-12">
              <div class="form-group">
                <div class="form-field">
                  <div class="select-wrap"></div>
                </div>
              </div>
            </div>
            <div class="row-md-63">
              <div class="form-group3">
                <input v-model="emailToBeFired"
                  type="text"
                  class="form-control"
                  placeholder="Employee Email"

                />
                <row>  </row>
              </div>
            </div>
            <div class="row-md-6">

            <div class="col-md-12">


              <div class="form-group">
                <button
                  type="button"
                  class="btn btn-dark py-50 px-6"

                  @click="fireEmployee(emailToBeFired)"
                >
                  Fire Employee
                </button>
              </div>
            </div>
            </div>
            <div class="form-group">
              <h5
                v-if="errorFire"
                style="color: red; padding-top: 20px"
              >Error: {{ errorFire }}</h5>
            </div>


            <div class="form-group">
              <h5
                v-if="sucessFire"
                style="color: green; padding-top: 20px"
              >{{ sucessFire }}</h5>
            </div>


          </form>
          <br />
        </div>
      </div>
    </div>






  <div class="container">
    <div class="row d-md-flex">
      <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5" id = fourthHeader>
        <h2 class="mb-4">Assign schedule</h2>
        <form action="#" class="appointment">
          <div class="col-md-12">
            <div class="form-group">
              <div class="form-field">
                <div class="select-wrap"></div>
              </div>
            </div>
          </div>
          <div class="row-md-984">
            <div class="form-group3">
              <input
                type="text"
                class="form-control"
                v-model="employeeEmailToAssignDailySchedule"
                placeholder="Employee Email"
              />
            </div>
          </div>

          <div class="row-md-0432">
            <div class="form-group3234">
              <select v-model="selected"  class="form-control"  >
                <option disabled value="">Choose Schedule</option>
                <option v-for="dailyScheduleToBeAssigned in dailySchedules" :value = "dailyScheduleToBeAssigned">{{dailyScheduleToBeAssigned.dayOfWeek}}, Start Time: {{dailyScheduleToBeAssigned.startTime}}, End Time: {{dailyScheduleToBeAssigned.endTime}} </option>

              </select>
            </div>
          </div>

          <div class="col-md-12">
            <div class="form-group">

              <button
                type="button"
                class="btn btn-dark py-50 px-6"
                v-bind:disabled="null"

                @click="assignSchedule(employeeEmailToAssignDailySchedule, selected)"
              >
                Assign new schedule
              </button>


            </div>
          </div>

          <div class="form-group">
            <h5
              v-if="errorAssign"
              style="color: red; padding-top: 20px"
            >Error: {{ errorAssign }}</h5>
          </div>

          <div class="form-group">
            <h5
              v-if="sucessAssign"
              style="color: green; padding-top: 20px"
            >{{ sucessAssign }}</h5>
          </div>


        </form>
        <br />
      </div>
    </div>
  </div>







    <h2 class="mb-4">Employees</h2>

    <table id="currentEmployees">
      <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Salary</th>
        <th>Schedules</th>

      </tr>


      <tr v-for="employee in employees" :key=employee.name >

        <td>{{ employee.name }}</td>
        <td>{{ employee.email }}</td>
        <td>{{ employee.salary }}</td>

        <td>
          <table id="schedules" v-if="employee.dailySchedules[0] != null">
            <tr>
              <th>Day of Week</th>
              <th>Start Time</th>
              <th>End Time</th>
              <th>Remove</th>

            </tr>
            <tr v-for=" dailySchedule in employee.dailySchedules" :key=dailySchedule.dayOfWeek>

              <td>{{ dailySchedule.dayOfWeek }}</td>
              <td>{{ dailySchedule.startTime }}</td>
              <td>{{ dailySchedule.endTime }}</td>
              <td>
                <button
                  type="button"
                  class="btn btn-dark py-50 px-6"

                  @click="unassignSchedule(employee, dailySchedule)"
                >
                  Remove
                </button>



              </td>


            </tr>
          </table>

          <div class="form-group">
            <h5
              v-if="errorUnasign"
              style="color: red; padding-top: 20px"
            >Error: {{ errorUnasign }}</h5>
          </div>

          <div class="form-group">
            <h5
              v-if="sucessUnasign"
              style="color: green; padding-top: 20px"
            >{{ sucessUnasign }}</h5>
          </div>

        </td>


      </tr>








    </table>



<br />
                      <div class="col-md-12">
              <a href="/#/ownermenu" class="button" id="button-1">Go back to menu</a>
            </div>







</div>





</div>







</template>





<script src= "./js/employeeManagement.js">
export default {
  name: "EmployeeManagement"
}
</script>

<style scoped>
.vertical-center {


  text-align: center;
  padding: 5px;
  position: center;


}

#employeeManagement{
  text-align: center;
  position: center;
  margin-left: auto;
  margin-right: auto;
}

#firstHeader{
  text-align: center;
  position: center;
  margin-left: auto;
  margin-right: auto;
}
#secondHeader{
  text-align: center;
  position: center;
  margin-left: auto;
  margin-right: auto;
}
#thirdHeader{
  text-align: center;
  position: center;
  margin-left: auto;
  margin-right: auto;
}
#fourthHeader{
  text-align: center;
  position: center;
  margin-left: auto;
  margin-right: auto;
}

#currentEmployees{
  width: 60%;
  margin-left: auto;
  margin-right: auto;
  border: 2px solid black;
  text-align: center;
  border-collapse: collapse;

  padding: 5px;
}
th, td {
  border: 1px solid black;
  padding: 10px;
}

/* ALL BUTTONS */

.button {
  display: block;
  padding: 20px 30px;
  margin: 20px 0;
  position: relative;
  color: #ecf0f1;
        position: center;
      width: 20%;
      margin-left: auto;
      margin-right: auto;
      text-align: center;
      padding: 10px;
}


/* BUTTON 1 */

#button-1 {
  border: 2px solid #34495e;
  color: #34495e;
  overflow: hidden;
  -webkit-transition: all 0.3s ease-in-out;
  -o-transition: all 0.3s ease-in-out;
  transition: all 0.3s ease-in-out;
}

#button-1:before {
  content: "";
  z-index: -1;
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: -100%;
  background-color: #34495e;
  -webkit-transition: all 0.3s ease-in-out;
  -o-transition: all 0.3s ease-in-out;
  transition: all 0.3s ease-in-out;
}

#button-1:hover {
  color: #ecf0f1;
}

#button-1:hover:before {
  left: 0;
}

</style>
