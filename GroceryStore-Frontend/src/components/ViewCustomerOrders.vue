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
        <th>Rating</th>
        <th>Review</th>
      </tr>
      <tr v-for="order in orders" :key="order.id">
        <td>{{ order.date }}</td>
        <td>{{ order.time }}</td>
        <td>{{ order.orderType }}</td>
        <td>{{ order.orderStatus }}</td>
        <td>
          <table id="ItemTable" v-if="order.orderItems[0] != null">
            <tr>
              <th>Name</th>
              <th>Quantity</th>
              <th>U. price</th>
            </tr>
            <tr v-for="orderItem in order.orderItems" :key="orderItem.shoppableItem.name">
              <td>{{ orderItem.shoppableItem.name }}</td>
              <td>{{ orderItem.quantity }}</td>
              <td>${{ orderItem.shoppableItem.price }}</td>
            </tr>
          </table>
          <div v-if="order.orderItems[0] == null">NA</div>
        </td>
        <td>${{ order.subtotal }}</td>
        <td>
          <div>Current rating: {{ order.rating }}</div>
          <select class="form-control" @change="null" v-model="order.r">
            <option disabled value>Please Select</option>
            <option>VeryPoor</option>
            <option>Poor</option>
            <option>Okay</option>
            <option>Good</option>
            <option>VeryGood</option>
          </select>
        </td>
        <td>
          <div>Current review: {{ order.description }}</div>
          <input
            type="text"
            class="form-control"
            v-model="order.d"
            placeholder="Insert review"
          />
        </td>

        <td>
          
            <button
              
              type="button"
              class="btn btn-dark py-50 px-6"
              v-bind:disabled="false"
              @click="createReview(order.r, order.d, order)"
            >Create review</button>
          
          
            <button
              
              type="button"
              class="btn btn-dark py-50 px-6"
              v-bind:disabled="false"
              @click="updateReview(order.r, order.d, order)"
            >Change review</button>
          
          
            <button
              
              type="button"
              class="btn btn-dark py-50 px-6"
              v-bind:disabled="false"
              @click="deleteReview(order)"
            >Delete review</button>
          
        </td>
      </tr>
    </table>
    <br />
    <div class="col-md-12">
      <a href="/#/customermenu" class="button" id="button-1">Go back to menu</a>
    </div>
  </div>
</template>

<script src= "./js/customerOrder.js">
</script>

<style>
#OrderTable {
  width: 90%;
  margin-left: auto;
  margin-right: auto;
  border: 2px solid black;
  border-collapse: collapse;
  text-align: center;
  padding: 5px;
}
#OrderTable th,
#OrderTable td {
  border: 1px solid black;
  padding: 10px;
}

#ItemTable, #ItemTable th,
#ItemTable td {
  border: none;
}
</style>