<template>
  <div id="managestoreinfo">
    <div class="center">
      <div class="overlay"></div>
      <div class="container">
        <h2>Manage Store Information</h2>
        <form action="#" class="appointment">
          <div class="form-group">
            <div class="form-field">
              <div class="select-wrap"></div>
            </div>
          </div>

          <div class="form-group">
            <input type="text" class="form-control" v-model="town" placeholder="Town" />
          </div>

          <div class="form-group">
            <input
              type="text"
              class="form-control"
              v-model="deliveryfee"
              placeholder="Delivery Fee"
            />
          </div>

          <div class="form-group">
            <h5 v-if="errorUpdate" style="color: red; padding-top: 20px">Error: {{ errorUpdate }}</h5>
          </div>

          <div class="form-group">
             <h5
                    v-if="successUpdate"
                    style="color: green; padding-top: 20px"
                  >{{ successUpdate }}</h5>
                </div>

          <div class="form-group">
            <button
              type="button"
              class="btn btn-dark py-50 px-6"
               v-bind:disabled="!town || !deliveryfee"
                  @click="managestoreinfo(town,deliveryfee)"
            >Update</button>
          </div>
        </form>
        <div class="col-md-12">
          <a href="/#/ownermenu" class="button" id="button-1">Go back to menu</a>
        </div>
      </div>
    </div>
  </div>
</template>
<script src="./js/manageStoreInfo.js">
</script>
<style scoped>
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
