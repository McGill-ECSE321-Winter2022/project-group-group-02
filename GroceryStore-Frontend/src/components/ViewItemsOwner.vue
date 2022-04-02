<template>
	<div id="viewItemsOwner">
  		<h2> Shoppable Items </h2>
  		<table id="viewItemsOwnerTable">
    		<tr>
				<th>Items</th>
				<th>Current Price</th>
				<th>New Price</th>
				<th>Quantity</th>
				<th></th>
			</tr>
			<tr v-for="item in shoppableItems" :key=item.name>
				<td>{{item.name}}</td>
				<td>${{item.price}}</td>
				<td>
					<input
						type="text"
						class="form-control"
						v-model="newPrice"
						placeholder="New Price"
					/>
					<button
						type="button"
						class="btn btn-dark py-50 px-6"
						v-bind:disabled="false"
						@click="updateShoppableItemPrice(item.name,newPrice)"
						
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


		<div id="addShoppableItem">
		<div class = "center">
			<div class = "overplay">
			</div>
			<div class = "container">
				<div class = "row d-md-flex">
					<div class = "col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
						<h2 class = "mb-4">Add Shoppable Item</h2>
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
							<div class="col-md-12">
								<div class="form-group">
									<h5 v-if="errorCreate" style="color: red; padding-top: 20px">
										Error: {{ errorLogin }}
									</h5>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<button
										type="button"
										class="btn btn-dark py-50 px-6"
										v-bind:disabled="false"
										@click="createShoppableItem(name, price, quantityAvailable)"
									>
										Create Shoppable Item
									</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		</div>


		  <h2> Unavailable Items </h2>
		<table id="viewUnavailableItemsOwnerTable">
			<tr>
			<th>Items</th>
				<th>Current Price</th>
				<th>New Price</th>
				<th></th>
			</tr>
			<tr v-for="unavailableItem in unavailableItems" :key=unavailableItem.name>
				<td>{{unavailableItem.name}}</td>
				<td>${{unavailableItem.price}}</td>
				<td>
					<input
						type="text"
						class="form-control"
						v-model="newPrice"
						placeholder="New Price"
					/>
					<button
						type="button"
						class="btn btn-dark py-50 px-6"
						v-bind:disabled="false"
						
						@click="null"
						
						>
					Update Price
					</button>

				</td>
				
				<td>
					<button
						type="button"
						class="btn btn-dark py-50 px-6"
						v-bind:disabled="false"
						@click="deleteUnavailableItem(unavailableItem.name)"
						>
						Delete
					</button>

				</td>

			</tr>




		</table>

		

		

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
							
							<div class="col-md-12">
								<div class="form-group">
									<h5 v-if="errorCreate" style="color: red; padding-top: 20px">
										Error: {{ errorLogin }}
									</h5>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<button
										type="button"
										class="btn btn-dark py-50 px-6"
										v-bind:disabled="false"
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

	</div>

</template>

<script src= "./js/viewItemsOwner.js">
</script>

<style>
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
</style>