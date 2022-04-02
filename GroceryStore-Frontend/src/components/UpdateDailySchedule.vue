<template>
  <div id="addDailySchedule">
    <div class="center">
      <div class="overlay"></div>
      <div class="container">
        <br />

        <h2>Add Daily Schedule</h2>

        <form action="#" class="appointment">
          <div class="form-group">
            <div class="form-field">
              <div class="select-wrap"></div>
            </div>
          </div>

          <div class="form-group">
            <label for="dayoffweek">Enter a day of week :</label>

            <select name="Day of Week" id="dayofweek">
              <option value="Monday">Monday</option>
              <option value="Tuesday">Tuesday</option>
              <option value="Wednesday">Wednesday</option>
              <option value="Thursday">Thursday</option>
              <option value="Friday">Friday</option>
              <option value="Saturday">Saturday</option>
              <option value="Sunday">Sunday</option>
            </select>
          </div>

          <div class="form-group">
            <label for="startTime">Select a start time:</label>
            <input type="time" id="startTime" name="startTime" />
          </div>

          <div class="form-group">
            <label for="endTime">Select an end time:</label>
            <input type="time" id="endTime" name="endTime" />
          </div>

          <div class="form-group">
            <h5 v-if="errorLogin" style="color: red; padding-top: 20px">Error: {{ errorLogin }}</h5>
          </div>

          <div class="form-group">
            <button
              type="button"
              class="btn btn-dark py-50 px-6"
              v-bind:disabled=" false "
              @click="null"
            >Update</button>
          </div>
        </form>
        <br />
      </div>
    </div>

    <h2>Delete Daily Schedule</h2>
    <table
      id="deleteDailyScheduleTable"
      style="border:1px solid black;margin-left:auto;margin-right:auto;"
    >
      <tr>
        <th>Day of Week</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Select Schedules to Delete</th>
      </tr>
      <tr>
        <td>Monday</td>
        <td>08:00</td>
        <td>20:00</td>
        <td>
          <input type="checkbox" name="scheduletodelete1" />
        </td>
      </tr>
      <tr>
        <td>Tuesday</td>
        <td>08:00</td>
        <td>20:00</td>
        <td>
          <input type="checkbox" name="scheduletodelete2" />
        </td>
      </tr>
      <tr>
        <td>Sunday</td>
        <td>08:00</td>
        <td>18:00</td>
        <td>
          <input type="checkbox" name="scheduletodelete3" />
        </td>
      </tr>
    </table>

    <br />
    <input type="submit" class="btn btn-dark py-50 px-6" value="Delete Schedule" />
    <br />
    <div class="col-md-12">
      <a href="/#/ownermenu" class="button" id="button-1">Go back to menu</a>
    </div>
  </div>
</template>
<script>
</script>
<style>
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