<template>
  <div id="updateaccountcustomer">
    <div class="overlay"></div>
    <div class="vertical-center">
      <div class="container">
        <div class="row d-md-flex">
          <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
            <h2 class="mb-4">Update Account</h2>
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
                    id="namefield"
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
                    id="addressfield"
                    type="text"
                    class="form-control"
                    v-model="address"
                    placeholder="Address"
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
                    type="password"
                    class="form-control"
                    v-model="confirmPassword"
                    placeholder="Confirm Password"
                  />
                </div>
              </div>
              <div class="col-md-12">
                <div class="form-group">
                  <h5
                    v-if="errorUpdate"
                    style="color: red; padding-top: 20px"
                  >Error: {{ errorUpdate }}</h5>
                </div>
              </div>
              <div class="row-md-12">
                <div class="form-group">
                  <h5
                    v-if="successUpdate"
                    style="color: green; padding-top: 20px"
                  >{{ successUpdate }}</h5>
                </div>
              </div>
              <div class="col-md-12">
                <div class="form-group">
                  <button
                    type="button"
                    class="btn btn-dark py-50 px-6"
                    v-bind:disabled="!password || !address || !name || !confirmPassword"
                    @click="updatecustomer(password, confirmPassword, name, address)"
                  >Update</button>
                </div>
              </div>
              <br />
            </form>
            <br />

            <h2 class="mb-4">Delete Account</h2>
            <div class="row-md-6">
              <div class="form-group">
                <input
                  type="password"
                  class="form-control"
                  v-model="passwordDelete"
                  placeholder="Confirm Entering Password"
                />
              </div>
            </div>
            <br />
             
            <div class="col-md-12">
                <div class="form-group">
                  <h5
                    v-if="errorDelete"
                    style="color: red; padding-top: 20px"
                  >Error: {{ errorDelete }}</h5>
                </div>
              </div>
            <div class="col-md-12">
              <div class="form-group">
                <button
                  type="button"
                  class="btn btn-dark py-50 px-6"
                  v-bind:disabled="!passwordDelete"
                  @click="deletecustomer(passwordDelete)"
                >Delete</button>
              </div>
            </div>
            <br />
            <div class="col-md-12">
              <a href="/#/customermenu" class="button" id="button-1">Go back to menu</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script src="./js/updatecustomer.js">
created();
</script>
<style scoped>
.vertical-center {
  width: 140%;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
  padding: 5px;
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