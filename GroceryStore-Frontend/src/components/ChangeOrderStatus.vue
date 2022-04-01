<template>
<div id="activeOrders">
  <h2>Active Orders </h2>
  <table id="activeOrdersTable">
    <tr>
      <th>Order ID</th>
      <th>Order Type</th>
      <th>Customer Email</th>
      <th>Current Status</th>
      <th>Update Status</th>
    </tr>

    <tr v-for="order in orders" :key=order.id>
      <td>
        <button
          type="button"
          class="btn btn-dark py-50 px-6"
          v-bind:disabled="false"
          @click="null"
        >
          {{ order.id }}
        </button>
      </td>

      <td>{{ order.orderType }}</td>
      <td>{{ order.email }}</td>
      <td>{{ order.orderStatus }}</td>
      <td>
        <select class="form-control"
        v-model="newStatus"
        >
          <option value="" selected disabled>Please select a new Status</option>
          <option>Confirmed</option>
          <option>Preparing</option>
          <option>Cancelled</option>
          <option>Ready</option>
          <option>Delivering</option>
          <option>Fulfilled</option>

        </select>
        <button
          type="button"
          class="btn btn-dark py-50 px-6"

          @click="updateStatus(newStatus, order)"
        >
          Update Status
        </button>

      </td>

    </tr>


  </table>

</div>

</template>

<script src= "./js/changeOrderStatus.js">
export default {
  name: "ChangeOrderStatus"
}
</script>
<style>

#activeOrdersTable{
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
