 <template>
  <div id="ownermenu">
    <div class="overlay"></div>
    <div class="container">
      <div class="col-md-12">
        <button
          type="button"
          class="block"
          @click="login(email, password, confirmPassword, name, address)"
        >Update Account</button>
      </div>
      <br />
      <div class="col-md-12">
        <button
          type="button"
          class="block"
          @click="login(email, password, confirmPassword, name, address)"
        >Manage Orders</button>
      </div>
      <br />
      <div class="col-md-12">
        <button
          type="button"
          class="block"
          @click="login(email, password, confirmPassword, name, address)"
        >Add Items</button>
      </div>
      <br />
	        <div class="col-md-12">
        <button
          type="button"
          class="block"
          @click="login(email, password, confirmPassword, name, address)"
        >Manage Employees</button>
      </div>
      <br />
	  	        <div class="col-md-12">
        <button
          type="button"
          class="block"
          @click="login(email, password, confirmPassword, name, address)"
        >Manage Schedules</button>
      </div>
      <br />
    </div>
  </div>
</template>
<script src="./js/signin.js">
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