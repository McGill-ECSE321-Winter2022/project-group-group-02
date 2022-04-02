<template>

<div id="addDailySchedule">
<div class = "center">	
    <div class="overlay">
		</div>
    <div class="container">
      <br>
        
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
                    <input type="time" id="startTime" name="startTime">
                </div>
              

              
                <div class="form-group">
                   <label for="endTime">Select an end time:</label>
                    <input type="time" id="endTime" name="endTime">
                </div>
              

              
              
      
                <div class="form-group">
                  <h5 v-if="errorLogin" style="color: red; padding-top: 20px">
                    Error: {{ errorLogin }}
                  </h5>
                </div>
             

              
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
          </form>
          <br />
          </div>
        </div>
     
  


		<h2> Delete Daily Schedule </h2>
  		<table id="deleteDailyScheduleTable" style="border:1px solid black;margin-left:auto;margin-right:auto;">
    		<tr>
				<th> Day of Week </th>
                <th> Start Time </th>
                <th> End Time </th>
                 <th> Select Schedules to Delete </th>
		
			</tr>
			<tr>
				<td>Monday</td>
                <td>08:00</td>
                <td>20:00</td>
				<td>
					 <input type="checkbox" name="scheduletodelete1">

				</td>

			</tr>
            <tr>
				<td>Tuesday</td>
                <td>08:00</td>
                <td>20:00</td>
				<td>
					 <input type="checkbox" name="scheduletodelete2">

				</td>

			</tr>
            <tr>
				<td>Sunday</td>
                <td>08:00</td>
                <td>18:00</td>
				<td>
					 <input type="checkbox" name="scheduletodelete3">

				</td>

			</tr>

			
		</table>

<br>
 <input type="submit" class="btn btn-dark py-50 px-6" value="Delete Schedule">
	</div>

</template>
<script>


</script>
<style>
#activeOrdersTable{
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
input[type="text"] {
    width: 200px;
    margin-left: auto;
margin-right: auto;
}
</style>