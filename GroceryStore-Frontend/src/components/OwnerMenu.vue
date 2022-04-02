 <template>
  <div id="ownermenu">
    <div class="overlay"></div>
    <div class="container">
      <div class="col-md-12">
        <button
          type="button"
          class="block"
          onClick="location.href='/#/updateaccountadmin'"
        >Update Account</button>
      </div>
      <br />
      <div class="col-md-12">
        <button
          type="button"
          class="block"
          onClick="location.href='/#/changeorderstatus'"
        >Manage Orders</button>
      </div>
      <br />
      <div class="col-md-12">
        <button
          type="button"
          class="block"
          onClick="location.href='/#/viewitemsowner'"
        >Add Items</button>
      </div>
      <br />
	        <div class="col-md-12">
        <button
          type="button"
          class="block"
          onClick="location.href='/#/employeeManagement'"
        >Manage Employees</button>
      </div>
	  	        <div class="col-md-12">
        <button
          type="button"
          class="block"
          onClick="location.href='/#/manageStoreInfo'"
        >Manage Store Information</button>
      </div>
      	  	        <div class="col-md-12">
        <button
          type="button"
          class="block"
          onClick="location.href='/#/addDailySchedule'"
        >Add Daily Schedules</button>
      </div>
      <br />
            	  	        <div class="col-md-12">
        <button
          type="button"
          class="block"
          onClick="location.href='/#/deleteDailySchedule'"
        >Delete Daily Schedules</button>
      </div>
      <br />
    </div>
  </div>
</template>
<script>
</script>
<style>
.block {
  display: block;
  width: 100%;
  border: none;
  background-color: #6495ed;
  padding: 14px 28px;
  font-size: 16px;
  cursor: pointer;
  text-align: center;
  border-radius: 20px;
}
</style>