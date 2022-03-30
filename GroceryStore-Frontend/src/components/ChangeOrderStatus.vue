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

    <tr>
      <td>
        <button
          type="button"
          class="btn btn-dark py-50 px-6"
          v-bind:disabled="false"
          @click="null"
        >
          106
        </button>
      </td>

      <td>Delivery</td>
      <td>georges@deschamps.ca</td>
      <td>Preparing</td>
      <td>
        <select class="form-control" @change="null">
          <option value="" selected disabled>Choose</option>
          <option v-for="oderStatus in oderStatus" :value="oderStatus.id" :key="oderStatus.id">{{ oderStatus.status }}</option>
        </select>
        <button
          type="button"
          class="btn btn-dark py-50 px-6"
          v-bind:disabled="false"
          @click="null"
        >
          Update Status
        </button>

      </td>

    </tr>

    <tr>
      <td>
        <button
        type="button"
        class="btn btn-dark py-50 px-6"
        v-bind:disabled="false"
        @click="null"
      >
        987
      </button>
      </td>
      <td>PickUp</td>
      <td>scotties@michael.ca</td>
      <td>Ready</td>
      <td>
        <select class="form-control" @change="null">
          <option value="" selected disabled>Choose</option>
          <option v-for="oderStatus in oderStatus" :value="oderStatus.id" :key="oderStatus.id">{{ oderStatus.status }}</option>
        </select>

        <button
          type="button"
          class="btn btn-dark py-50 px-6"
          v-bind:disabled="false"
          @click="null"
        >
          Update Status
        </button>

      </td>

    </tr>



  </table>





</div>




</template>

<script>
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
