<template>
<div id="employeeManagement">

  <div class="overlay">
  </div>




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
                @click="null"
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
        <h2 class="mb-4">Assign schedule</h2>
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
                placeholder="Employee Email"
              />
            </div>
          </div>

          <div class="row-md-6">
            <div class="form-group">
              <select class="form-control" @change="null">
                <option value="" selected disabled>Choose Schedule</option>
                <option v-for="dailySchedule in dailySchedules" :value="dailySchedule.id" :key="dailySchedule.id">{{ dailySchedule.schedule }}</option>
              </select>
            </div>
          </div>

          <div class="col-md-12">
            <div class="form-group">

              <button
                type="button"
                class="btn btn-dark py-50 px-6"
                v-bind:disabled="null"
                @click="null"
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


  <div class="container">
    <div class="row d-md-flex">
      <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
        <h2 class="mb-4">Fire employee</h2>
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
                placeholder="Employee Email"
              />
            </div>
          </div>

          <div class="col-md-12">
            <div class="form-group">
              <button
                type="button"
                class="btn btn-dark py-50 px-6"
                v-bind:disabled="null"
                @click="null"
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







</div>







</template>

<script>
export default {
  name: "EmployeeManagement"
}
</script>

<style scoped>

</style>
