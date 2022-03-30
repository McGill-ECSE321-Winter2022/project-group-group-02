<template>
<div id="updateaccountemployee">
 <div class="overlay">
	</div>
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
                    type="password"
                    class="form-control"
                    v-model="password"
                    placeholder="Password"
                  />
                </div>
              </div>
              <div class="col-md-12">
                <div class="form-group">
                  <h5 v-if="errorLogin" style="color: red; padding-top: 20px">
                    Error: {{ errorLogin }}
                  </h5>
                </div>
              </div>
              <div class="col-md-12">
                <div class="form-group">
                  <button
                    type="button"
                    class="btn btn-dark py-50 px-6"
                    v-bind:disabled="!username || !password || !address || !name"
                    @click="login(username, password)"
                  >
                    Update
                  </button>
              </div>
            </div>
          </form>
          <br />
        </div>
      </div>
    </div>
</div>

</template>
<script>


</script>
<style>
</style>