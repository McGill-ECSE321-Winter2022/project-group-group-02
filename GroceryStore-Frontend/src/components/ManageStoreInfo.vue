<template>
<div id="managestoreinfo">
<div class = "center">	
    <div class="overlay">
		</div>
    <div class="container">

          <h2>Manage Store Information</h2>
          <form action="#" class="appointment">
     
                <div class="form-group">
                  <div class="form-field">
                    <div class="select-wrap"></div>
                  </div>
                </div>
             
           
                <div class="form-group">
                  <input
                    type="text"
                    class="form-control"
                    v-model="town"
                    placeholder="Town"
                  />
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
           </div>
      
          </div>
        </div>
      


</template>
<script>
</script>
<style>
</style>