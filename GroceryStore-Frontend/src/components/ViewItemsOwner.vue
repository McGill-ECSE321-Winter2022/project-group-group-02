<template>
	<div id="viewItemsOwner">
  		<h2> Items </h2>
  		<table id="viewItemsOwnerTable">
    		<tr>
				<th>Item</th>
				<th>Current Price</th>
				<th>New Price</th>
				<th>Quantity</th>
				<th></th>
			</tr>
			<tr>
				<td>Protein Shake</td>
				<td>5.00</td>
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
						v-bind:disabled="!newPrice"
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
						@click="increment"
						>
						-
					
					</button>
					4
					<button
						type="button"
						class="btn btn-dark py-50 px-6"
						v-bind:disabled="false"
						@click="increment"
						>
						+
					
					</button>

				</td>
				<td>
					<button
						type="button"
						class="btn btn-dark py-50 px-6"
						v-bind:disabled="false"
						@click="null"
						>
						Delete
					</button>

				</td>

			</tr>

			<tr>
				<td>Orange Juice</td>
				<td>4.20</td>
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
						v-bind:disabled="!newPrice"
						@click="null"
						>
					Update Price
					</button>

				</td>
				<td>
					<button
						type="toggle"
						class="btn btn-dark py-50 px-6"
						v-bind:disabled="false"
						@click="increment"
						>
						-
					</button>
					10
				
					
					<button
						type="toggle"
						class="btn btn-dark py-50 px-6"
						v-bind:disabled="false"
						@click="increment"
						>
						+
					</button>

				</td>
				<td>
					<button
						type="button"
						class="btn btn-dark py-50 px-6"
						v-bind:disabled="false"
						@click="null"
						>
						Delete
					</button>

				</td>

			</tr>




		</table>
		<div class = "center">
			<div class = "overplay">
			</div>
			<div class = "container">
				<div class = "row d-md-flex">
					<div class = "col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
						<h2 class = "mb-4">Create Item</h2>
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
									type="text"
									class="form-control"
									v-model="price"
									placeholder="Price"
								/>
								</div>
							</div>
							<div class="row-md-6">
								<div class="form-group">
								<input
									type="text"
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
										v-bind:disabled="!name || !price || !quantityAvailable"
										@click="createItem(name, price, quantityAvailable)"
									>
										Create Item
									</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

	</div>

</template>

<script>
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
</style>