 <template>
  <div id="ownermenu">
    <div class="overlay"></div>
    <div class="container-menu">
    <h1>Menu</h1>
    <div class="row cf">
      <div class="six col">
        <a href='/#/updateaccountadmin' class="button" id="button-1">Update Account</a>
      </div>
      <div class="six col">
        <a href="/#/changeorderstatus" class="button" id="button-1">Manage Orders</a>
      </div>
    </div>
    <div class="row cf">
      <div class="six col">
        <a href='/#/employeeManagement' class="button" id="button-1">Manage Employees</a>
      </div>
      <div class="six col">
        <a href="/#/manageStoreInfo" class="button" id="button-1">Manage Store</a>
      </div>
    </div>
        <div class="row cf">
      <div class="six col">
        <a href='/#/addDailySchedule' class="button" id="button-1">Add Daily Schedules</a>
      </div>
      <div class="six col">
        <a href="/#/deleteDailySchedule" class="button" id="button-1">Delete Daily Schedules</a>
      </div>
    </div>
    <div class="row cf">
      <div class="six col">
        <a href="/#/viewitemsowner" class="button" id="button-1">Add Items</a>
      </div>
      <div class="six col">
        <a href="/#/login" class="button" id="button-1">Logout</a>
      </div>
    </div>
  </div>
  </div>
</template>
<script>
</script>
<style>
html, body{
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  font-family: 'Open Sans', sans-serif;
}

a{
  text-decoration: none;
}

/* GRID */

.twelve { width: 100%; }
.eleven { width: 91.53%; }
.ten { width: 83.06%; }
.nine { width: 74.6%; }
.eight { width: 66.13%; }
.seven { width: 57.66%; }
.six { width: 49.2%; }
.five { width: 40.73%; }
.four { width: 32.26%; }
.three { width: 23.8%; }
.two { width: 15.33%; }
.one { width: 6.866%; }

/* COLUMNS */

.col {
	display: block;
	float:left;
	margin: 1% 0 1% 1.6%;
}

.col:first-of-type {
  margin-left: 0;
}

.container-menu{
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
  position: relative;
  text-align: center;
}

/* CLEARFIX */

.cf:before,
.cf:after {
    content: " ";
    display: table;
}

.cf:after {
    clear: both;
}
.cf {
    *zoom: 1;
}

/* ALL BUTTONS */

.button{
  display: block;
  padding: 20px 30px;
  margin: 20px 0;
  position: relative;
  color: #ecf0f1;
}

/* BUTTON 1 */

#button-1{
  border: 2px solid #34495e;
  color: #34495e;
  overflow: hidden;
  -webkit-transition: all 0.3s ease-in-out;
  -o-transition: all 0.3s ease-in-out;
  transition: all 0.3s ease-in-out;
}

#button-1:before{
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

#button-1:hover{
  color: #ecf0f1;
}

#button-1:hover:before{
  left: 0;
}

</style>