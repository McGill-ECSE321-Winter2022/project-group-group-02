<template>
  <div id="login">
    <div class="center">
      <div class="overlay"></div>
      <div class="vertical-center">
        <div class="container">
          <div class="row d-md-flex">
            <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
              <h2 class="mb-4">Login</h2>
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
                    <input type="text" class="form-control" v-model="email" placeholder="Email" />
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
                <div class="row-md-12">
                  <div class="form-group">
                    <h5
                      v-if="errorLogin"
                      style="color: red; padding-top: 20px"
                    >Error: {{ errorLogin }}</h5>
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="form-group">
                    <button
                      type="button"
                      class="btn btn-dark py-50 px-6"
                      v-bind:disabled="!email || !password"
                      @click="login(email, password)"
                    >Login</button>
                  </div>
                </div>
                <br />

  
            <div class="col-md-12">
                      <a href="/#/signincustomer" class="button" id="button-1">Click Here To Sign In</a>

            </div>
              </form>
              <br />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="./js/login.js">
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

.button{
  display: block;
  padding: 20px 20px;
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