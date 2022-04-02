<template>
<div id="browseItems">
    <h2>Our Top Products</h2>
  <table id="ItemTable">
    <tr>
      <th>Item</th>
      <th>Price</th>
      <th>Quantity</th>
      <th>Order</th>      

    </tr>
    <tr v-for="item in shoppableItems" :key=item.name>
      <td>{{ item.name }}</td>
      <td>{{ item.price }}</td>

        <td> 
            <input
				type="number"
				class="form-control"
				v-model="quantityAvailable"
				placeholder="Quantity"
			/>

        </td>
        <td> 
            <button
                    v-if="quantityAvailable != null"
                    type="button"
                    class="btn btn-dark py-50 px-6"
                    v-bind:disabled="false"
                    @click="null"
                  >
                    Add to order
        </button>
            
        </td>

    </tr>
    </table>


<h2> Exclusively available in store!</h2>
<table id="ItemTable">
    <tr>
      <th>Item</th>
      <th>Price</th>
    </tr>
    <tr v-for="item in unavailableItems" :key=item.name>
      <td>{{ item.name }}</td>
      <td>{{ item.price }}</td>
    </tr>
</table>

</div>

</template>

<script src= "./js/browseItems.js">

</script>

<style>
  #ItemTable{
    width: 90%;
    margin-left: auto;
    margin-right: auto;
    border: 2px solid black;
    border-collapse: collapse;
    text-align: center;
    padding: 5px;
  }
  #ItemTable th, #ItemTable td{
    border: 1px solid black;
    padding: 10px; 
  } 


</style>