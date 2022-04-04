<template>
  <div id="addDailySchedule">
    <div class="center">
      <div class="overlay"></div>
      <div class="container">
        <br />

        <h2>Add Store Opening Hour</h2>

        <form action="#" class="appointment">
          <div class="form-group">
            <div class="form-field">
              <div class="select-wrap"></div>
            </div>
          </div>

          <div class="form-group">
            <label for="dayofweek">Enter a day of week :</label>

            <select name="Day of Week" id="dayofweek" v-model="dayofweek">
              <option value=Monday>Monday</option>
              <option value=Tuesday>Tuesday</option>
              <option value=Wednesday>Wednesday</option>
              <option value=Thursday>Thursday</option>
              <option value=Friday>Friday</option>
              <option value=Saturday>Saturday</option>
              <option value=Sunday>Sunday</option>
            </select>
          </div>

          <div class="form-group">
            <label for="startTime">Select a start time:</label>
            <input type="time" id="startTime" name="startTime" v-model="startTime"/>
          </div>

          <div class="form-group">
            <label for="endTime">Select an end time:</label>
            <input type="time" id="endTime" name="endTime" v-model="endTime"/>
          </div>

          <div class="form-group">
            <h5 v-if="errorAdd" style="color: red; padding-top: 20px">Error: {{ errorAdd }}</h5>
          </div>

          <div class="form-group">
            <h5 v-if="successAdd" style="color: green; padding-top: 20px">{{ successAdd }}</h5>
          </div>

          <div class="form-group">
            <button
              type="button"
              class="btn btn-dark py-50 px-6"
              v-bind:disabled="false"
             @click="addDailySchedule(dayofweek,startTime,endTime)"
            >Create</button>
          </div>
        </form>
        <br />
      </div>
    </div>

    <h2>Delete Store Opening Hour</h2>
    <table
      id="deleteDailyScheduleTable"
      style="border:1px solid black;margin-left:auto;margin-right:auto;"
    >
      <tr>

        <th>Day of Week</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Select Schedule to Delete</th>
      </tr>
     <tr v-for="schedule in dailyschedules" :key=schedule.id>

				<td>{{schedule.dayOfWeek}}</td>
				<td>{{schedule.startTime}}</td>
        <td>{{schedule.endTime}}</td>
        <td>

					<button
						type="button"
						class="btn btn-dark py-50 px-6"
						v-bind:disabled="false"
						@click="deleteDailySchedule(schedule.id)"
						>
						Delete
					</button>


				</td>

			</tr>
    </table>

    <div class="form-group">
            <h5 v-if="errorDelete" style="color: red; padding-top: 20px">Error: {{ errorDelete }}</h5>
          </div>


    <br />
    <div class="col-md-12">
      <a href="/#/ownermenu" class="button" id="button-1">Go back to menu</a>
    </div>
  </div>
</template>
<script src="./js/updateDailySchedule.js">
</script>
<style scoped>
#activeOrdersTable {
  width: 80%;
  margin-left: auto;
  margin-right: auto;
  border: 2px solid black;
  border-collapse: collapse;
  text-align: center;
  padding: 5px;
}
th,
td {
  border: 1px solid black;
  padding: 10px;
}
input[type="text"] {
  width: 200px;
  margin-left: auto;
  margin-right: auto;
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
