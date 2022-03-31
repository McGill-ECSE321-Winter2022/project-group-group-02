<template>
	<div id="deleteDailySchedule">
  		<h2> Delete Daily Schedule </h2>
  		<table id="deleteDailyScheduleTable" style="border:1px solid black;margin-left:auto;margin-right:auto;">
    		<tr>
				<th> Day of Week </th>
                <th> Start Time </th>
                <th> End Time </th>
				<th>
				</th>
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
 <input type="submit" value="Delete Schedule">


	</div>

</template>
<script>
</script>
<style>

</style>