<template>
  <div id="activeOrders">
    <h2>Active Orders</h2>
    <table id="activeOrdersTable">
      <tr>
        <th>Order ID</th>
        <th>Order Type</th>
        <th>Customer Email</th>
        <th>Current Status</th>
        <th>Update Status</th>
      </tr>

      <tr v-for="order in orders" :key="order.id">
        <td>
         {{ order.id }}
        </td>

        <td>{{ order.orderType }}</td>
        <td>{{ order.customer.email }}</td>
        <td>{{ order.orderStatus }}</td>
        <td>
          <select class="form-control" v-model="order.newStatus">
            <option disabled value="Please select a new status">Please select a new status</option>
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
            v-bind:disabled="!order.newStatus"
            @click="updateStatus(order.newStatus, order)"
          >Update Status</button>
        </td>
      </tr>
    </table>
    <br />
    <div class="col-md-12" v-if="isOwner">
      <a href="/#/ownermenu" class="button" id="button-1">Go back to menu</a>
    </div>
    <br />
    <div class="col-md-12" v-if="isEmployee">
      <a href="/#/employeemenu" class="button" id="button-2">Go back to menu</a>
    </div>
  </div>
</template>

<script src= "./js/changeOrderStatus.js">
export default {
  name: "ChangeOrderStatus"
};
</script>
<style scoped>
#activeOrdersTable {
  width: 80%;
  margin-left: auto;
  margin-right: auto;
  border: 2px solid black;
  border-collapse: collapse;
  text-align: center;
  padding: 5px;
}
th,
td {
  border: 1px solid black;
  padding: 10px;
}

/* ALL BUTTONS */

.button {
  display: block;
  padding: 20px 30px;
  margin: 20px 0;
  position: relative;
  color: #ecf0f1;
        position: center;
      width: 20%;
      margin-left: auto;
      margin-right: auto;
      text-align: center;
      padding: 10px;
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



/* BUTTON 2 */

#button-2 {
  border: 2px solid #34495e;
  color: #34495e;
  overflow: hidden;
  -webkit-transition: all 0.3s ease-in-out;
  -o-transition: all 0.3s ease-in-out;
  transition: all 0.3s ease-in-out;
}

#button-2:before {
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

#button-2:hover {
  color: #ecf0f1;
}

#button-2:hover:before {
  left: 0;
}
</style>
