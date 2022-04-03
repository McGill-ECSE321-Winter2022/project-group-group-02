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
                min="1"
				v-model="item.quantityDesired"
				placeholder="Quantity"
			/>

        </td>
        <td> 
        <button
                    type="button"
                    class="btn btn-dark py-50 px-6"
                    v-bind:disabled="false"
                    @click="addToBasket(item.name, item.quantityDesired)"
                  >
                    Add to cart
        </button>

        </td>
    </tr>

    </table>
              <div class="col-md-12">
                <div class="form-group">
                  <h5
                    v-if="errorOrder"
                    style="color: red; padding-top: 20px"
                  >Error: {{ errorOrder }}</h5>
                </div>
              </div>
<br />
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

<br />

<h2> Your Cart</h2>
<table id="ItemTable">
    <tr>
      <th>Item</th>
      <th>Quantity</th>

    </tr>
    <tr v-for="item in basketItems" :key=item.name>
      <td>{{ item.itemName }}</td>
      <td>{{ item.quantityDesired }}</td>
      <td>
      <button
                    type="button"
                    class="btn btn-dark py-50 px-6"
                    v-bind:disabled="false"
                    @click="removeFromBasket(item.itemName)"
                  >
                    Remove from cart
        </button>
        </td>
    </tr>
</table>
<br />

<h5>Choose Order Type: </h5>
<select  @change="null" v-model="orderType">
            <option disabled value>Please Select</option>
            <option>Pick Up</option>
            <option>Delivery</option>
</select>
<button
                    type="button"
                    class="btn btn-dark py-50 px-6"
                    v-bind:disabled="(basketItems[0] == null) || (orderType == '')"
                    @click="createOrder(orderType)"
                  >
                    Confirm Order
</button>

</div>

</template>

<script src= "./js/browseItems.js">

</script>

<style>
  #ItemTable{
    width: 60%;
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