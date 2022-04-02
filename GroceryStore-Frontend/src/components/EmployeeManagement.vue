<template>
<div id="employeeManagement">

  <div class="overlay">
  </div>
  <div class="vertical-center">


  <div class="container">
    <div class="row d-md-flex">
      <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
        <h2 class="mb-4">Hire new employee</h2>
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
        </form>
        <br />
      </div>
    </div>
  </div>





    <div class="container">
      <div class="row d-md-flex">
        <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
          <h2 class="mb-4">Update existing employee</h2>
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
          </form>
          <br />
        </div>
      </div>
    </div>




    <div class="container">
      <div class="row d-md-flex">
        <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
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
              </div>
            </div>

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
          </form>
          <br />
        </div>
      </div>
    </div>






  <div class="container">
    <div class="row d-md-flex">
      <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
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
        </form>
        <br />
      </div>
    </div>
  </div>







    <h2>Employees </h2>
    <table id="currentEmployees">
      <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Salary</th>
        <th>Schedules</th>

      </tr>


      <tr v-for="employee in employees" :key=employee.name>
        <td>{{ employee.name }}</td>
        <td>{{ employee.email }}</td>
        <td>{{ employee.salary }}</td>

        <td>
          <table id="schedules">
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
                  Remove, {{employee.email}}
                </button>


              </td>


            </tr>
          </table>
        </td>


      </tr>








    </table>












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
  width: 140%;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
  padding: 5px;

}


#currentEmployees{
  width: 80%;
  margin-left: auto;
  margin-right: auto;
  border: 2px solid black;
  border-collapse: collapse;
  text-align: center;
  padding: 5px;
}
th, td {
  border: 1px solid black;
  padding: 10px;
}


</style>