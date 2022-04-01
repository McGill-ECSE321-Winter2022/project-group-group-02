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
    <tr v-for="order in orders" :key=order.date>
      <td>{{ order.date }}</td>
      <td>{{ order.time }}</td>
      <td>{{ order.orderType }}</td>
      <td>{{ order.orderStatus }}</td>
      <td>
        <table id="ItemTable">
          <tr>
            <th>Name</th>
            <th>Quantity</th>
            <th>U. price</th>
          </tr>
          <tr v-for="orderItem in order.orderItems" :key=orderItem.shoppableItem.name>

            <td>{{ orderItem.shoppableItem.name }}</td>
            <td>{{ orderItem.quantity }}</td>
            <td>${{ orderItem.shoppableItem.price }}</td>
          </tr>
        </table>
      </td>
      <td>${{ order.subtotal }}</td>
      <!-- <td>{{ order.rating }}</td>
      <td>{{ order.review }}</td>       -->
      <td>
        <select class="form-control" @change="null">
          <option disabled value="">Please Select</option>
          <option>Very Poor</option>
          <option>Poor</option>
          <option>Okay</option>
          <option>Good</option>
          <option>Very Good</option>
        </select>  
      </td>
      <td>
        <input
                    type="text"
                    class="form-control"
                    v-model="order.review"
                    placeholder="Insert review"
                  />
      </td>


      <td>
        <button
                    type="button"
                    class="btn btn-dark py-50 px-6"
                    
                    @click="null"
                  >
                    Change review
        </button>
      </td>

    </tr>
     <!-- <tr>
      <td>March 20th</td>
      <td>12h45</td>
      <td>Pickup</td>
      <td>Complete</td>
      <td>Pizza, Vegetables</td>
      <td>$32</td>
      <td colspan="2">
        <button
                    type="button"
                    class="btn btn-dark py-50 px-6"
                    
                    @click="null"
                  >
                    Review order
        </button>
      </td>

    </tr> -->
    </table>


</div>

</template>

<script src= "./js/customerOrder.js">

</script>

<style>
  #OrderTable{
    width: 90%;
    margin-left: auto;
    margin-right: auto;
    border: 2px solid black;
    border-collapse: collapse;
    text-align: center;
    padding: 5px;
  }
  #OrderTable th, #OrderTable td{
    border: 1px solid black;
    padding: 10px; 
  } 

  #ItemTable th, #ItemTable td{
    border: none;
  }


</style>