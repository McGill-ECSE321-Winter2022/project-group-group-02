<template>


<div id="addDailySchedule">
<div class = "center">	
    <div class="overlay">
		</div>
    <div class="container">
      <div class="row d-md-flex">
        <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
          <h2 class="mb-4">Add Daily Schedule</h2>
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
                <label for="dayoffweek">Enter a day of week :</label>
                  <input
                    type="text"
                    class="form-control"
                    v-model="dayofweek"
                    placeholder="Day of Week"
                    size="10"
                  />
                </div>
              </div>
                <div class="row-md-6">
                <div class="form-group">
                   <label for="startTime">Select a start time:</label>
                    <input type="time" id="startTime" name="startTime">
                </div>
              </div>

              <div class="row-md-6">
                <div class="form-group">
                   <label for="endTime">Select an end time:</label>
                    <input type="time" id="endTime" name="endTime">
                </div>
              </div>

              
              
              <div class="col-md-12">
                <div class="form-group">
                  <h5 v-if="errorLogin" style="color: red; padding-top: 20px">
                    Error: {{ errorLogin }}
                  </h5>
                </div>
              </div>

              <div class="col-md-12">
                <div class="form-group">
                  <button
                    type="button"
                    class="btn btn-dark py-50 px-6"
                    v-bind:disabled=" false "
                    @click="null"
                  >
                    Update
                  </button>
              </div>
              </div>
          </form>
          <br />
          </div>
        </div>
      </div>
    </div>
</div></div>

</template>
<script>


</script>
<style>
</style>