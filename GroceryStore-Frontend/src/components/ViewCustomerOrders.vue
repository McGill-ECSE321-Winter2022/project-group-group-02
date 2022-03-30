<template>
<div id="viewCustomerOrders">
    <h2>My Orders</h2>
  <table id="OrderTable">
    <tr>
      <th>Date</th>
      <th>Time</th>
      <th>Type</th>
      <th>Status</th>
      <th>Items</th>
      <th>Subtotal</th>
      <th>Review</th>

    </tr>
    <tr>
      <td>April 12th</td>
      <td>4h53</td>
      <td>Delivery</td>
      <td>Confirmed</td>
      <td>None</td>
      <td>$0</td>
      <td>Still hasn't arrived, this is too slow</td>

    </tr>
     <tr>
      <td>March 20th</td>
      <td>12h45</td>
      <td>Pickup</td>
      <td>Complete</td>
      <td>Pizza, Vegetables</td>
      <td>$32</td>
      <td>
        <button
                    type="button"
                    class="btn btn-dark py-50 px-6"
                    v-bind:disabled="false"
                    @click="null"
                  >
                    Add Review
        </button>
      </td>

    </tr>
    </table>


</div>

</template>
<script>


</script>
<style>
  #OrderTable{
    width: 80%;
    margin-left: auto;
    margin-right: auto;
    border: 2px solid black;
    border-collapse: collapse;
    text-align: center;
    padding: 5px;
  }
  th, td {
    border: 1px solid black;
    padding: 10px;
}


</style>