<template>
	<div id="viewItemsOwner">
  		<h2> Shoppable Items </h2>
  		<table id="viewItemsOwnerTable">
    		<tr>
				<th>Items</th>
				<th>Price</th>
				<th>Quantity</th>
			</tr>
			<tr v-for="item in shoppableItems" :key=item.name>
				<td>{{item.name}}</td>
				<td>
					<input
						type="text"
						class="form-control"
						v-model="item.price"
						placeholder="New Price"
					/>
					<button
						type="button"
						class="btn btn-dark py-6 px-6"
						v-bind:disabled="!item.price"
						@click="updateShoppableItemPrice(item.name,item.price)"
						
						>
					Update Price
					</button>

              
				</td>
				<td>
					
					
					{{item.quantityAvailable}}
					<button
						type="button"
						class="btn btn-dark py-50 px-6"
						v-bind:disabled="false"
						@click="replenishInventory(item.name,item.quantityAvailable+10)"
						>
						Replenish
					</button>
				</td>
				
				<td>
					
					<button
						type="button"
						class="btn btn-dark py-50 px-6"
						v-bind:disabled="false"
						@click="deleteShoppableItem(item.name)"
						>
						Delete
					</button>
					

				</td>

			</tr>
  		</table>
		
		<div class="row-md-12">
			<div class="form-group">
				<h5
				v-if="successUpdateShoppableItemPrice"
				style="color: green; padding-top: 20px"
				>{{ successUpdateShoppableItemPrice }}</h5>
			</div>
		</div>
		<div class="row-md-12">
			<div class="form-group">
				<h5
				v-if="successReplenishInventory"
				style="color: green; padding-top: 20px"
				>{{ successReplenishInventory }}</h5>
			</div>
		</div>


		<div id="addShoppableItem">
		<div class = "center">
			<div class = "overlay">
			</div>
			<div class = "container">
				<div class = "row d-md-flex">
					<div class = "col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
						<h2 class = "mb-4">Add Shoppable Item</h2>
						<!-- <form action="#" class="appointment"> -->
							
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
									type="decimal"
									class="form-control"
									v-model="price"
									placeholder="Price"
								/>
								</div>
							</div>
							<div class="row-md-6">
								<div class="form-group">
								<input
									type="number"
									class="form-control"
									v-model="quantityAvailable"
									placeholder="Quantity"
								/>
								</div>
							</div>
							<div class="form-group">
								<h5 v-if="errorCreateShoppableItem" style="color: red; padding-top: 20px">Error: {{ errorCreateShoppableItem }}</h5>
							</div>
							<div class="form-group">
								<h5 v-if="successCreateShoppableItem" style="color: green; padding-top: 20px">{{ successCreateShoppableItem }}</h5>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<button
										type="button"
										class="btn btn-dark py-50 px-6"
										v-bind:disabled="!(name && price && quantityAvailable)"
										@click="createShoppableItem(name, price, quantityAvailable)"
									>
										Create Shoppable Item
									</button>
								</div>
							</div>
							

						<!-- </form> -->
					</div>
				</div>
			</div>
		</div>
		</div>
		


		  <h2> Unavailable Items </h2>
		<table id="viewUnavailableItemsOwnerTable">
			<tr>
			<th>Items</th>
				<th>Price</th>
			</tr>
			<tr v-for="item in unavailableItems" :key=item.name>
				<td>{{item.name}}</td>
				<td>${{item.price}}</td>
				<td>
					<input
						type="text"
						class="form-control"
						v-model="newUnavailablePrice"
						placeholder="New Price"
					/>
					<button
						type="button"
						class="btn btn-dark py-50 px-6"
						v-bind:disabled="!item.price"
						
						@click="updateUnavailableItemPrice(item.name, item.price)"
						
						>
					Update Price
					</button>

				</td>
				
				<td>
					<button
						type="button"
						class="btn btn-dark py-50 px-6"
						v-bind:disabled="false"
						@click="deleteUnavailableItem(item.name)"
						>
						Delete
					</button>

				</td>

			</tr>




		</table>

		<div class="row-md-12">
			<div class="form-group">
				<h5
				v-if="successUpdateUnavailableItemPrice"
				style="color: green; padding-top: 20px"
				>{{ successUpdateUnavailableItemPrice }}</h5>
			</div>
		</div>

		

		

		<div id="addUnavailableItem">
		<div class = "center">
			<div class = "overplay">
			</div>
			<div class = "container">
				<div class = "row d-md-flex">
					<div class = "col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
						<h2 class = "mb-4">Add Unavailable Item</h2>
						<form action="#" class="appointment">
							<div class="col-md-12">
								<div class = "form-group">
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
									v-model="unavailableItemName"
									placeholder="Name"
								/>
								</div>
							</div>
							<div class="row-md-6">
								<div class="form-group">
								<input
									type="text"
									class="form-control"
									v-model="unavailableItemPrice"
									placeholder="Price"
								/>
								</div>
							</div>
							
							<div class="form-group">
								<h5 v-if="errorCreateUnavailableItem" style="color: red; padding-top: 20px">Error: {{ errorCreateUnavailableItem }}</h5>
							</div>
							<div class="form-group">
								<h5 v-if="successCreateUnavailableItem" style="color: green; padding-top: 20px">{{ successCreateUnavailableItem }}</h5>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<button
										type="button"
										class="btn btn-dark py-50 px-6"
										v-bind:disabled="!unavailableItemName"
										@click="createUnavailableItem(unavailableItemName, unavailableItemPrice)"
									>
										Create Unavailable Item
									</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		</div>
            <br />
            <div class="col-md-12"  v-if="isOwner">
              <a href="/#/ownermenu" class="button" id="button-1">Go back to menu</a>
            </div>
            <br />
            <div class="col-md-12"  v-if="isEmployee">
               
              <a href="/#/employeemenu" class="button" id="button-1">Go back to menu</a>
            </div>
	</div>

</template>

<script src= "./js/viewItemsOwner.js">
</script>

<style scoped>
#viewItemsOwnerTable{
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

#viewUnavailableItemsOwnerTable{
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

#addShoppableItem {
	width: 140%;
    margin-left: auto;
    margin-right: auto;
    
    text-align: center;
    padding: 5px;
}
#addUnavailableItem {
	width: 140%;
    margin-left: auto;
    margin-right: auto;
    
    text-align: center;
    padding: 5px;
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

#button-1{
  border: 2px solid #34495e;
  color: #34495e;
  overflow: hidden;
  -webkit-transition: all 0.3s ease-in-out;
  -o-transition: all 0.3s ease-in-out;
  transition: all 0.3s ease-in-out;
}

#button-1:before{
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

#button-1:hover{
  color: #ecf0f1;
}

#button-1:hover:before{
  left: 0;
}

</style>