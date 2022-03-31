function CustomerDto(email, name, password, confirmPassword, address) {
	this.password = password
	this.name = name
	this.email = email
	this.address = address
	this.confirmPassword = confirmPassword
}

export default {
	name: 'updatecustomer',
	data() {
		return {
			email: '',
			password: '',
			address: '',
			name: '',
		}
	},
	methods: {
		updatecustomer: function (email, password, confirmPassword, name, address) {
			if (password != confirmPassword) {
				swal("ERROR", "Passwords do not match.", "error");
			} else {
				AXIOS.post('/update_customer/', {}, {
					params: {
						email: email,
						address: address,
						name: name,
						password: password,
					}
				})
					.then(response => {
						if (response.status === 201) {
								this.password = '',
								this.confirmPassword = '',
								this.address = '',
								this.names = '',
								this.email = '',
							swal("Success", "Information updated successfully!", "success");
						}
					})
					.catch(e => {
						swal("ERROR", e.response.data, "error");
					})

			}

		}
	}
}
