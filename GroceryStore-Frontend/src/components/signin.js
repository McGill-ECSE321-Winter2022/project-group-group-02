function CustomerDto(email, name, password, confirmPassword, address) {
	this.password = password
	this.name = name
	this.email = email
	this.address = address
	this.confirmPassword = confirmPassword
}

export default {
	name: 'signin',
	data() {
		return {
			email: '',
			password: '',
			address: '',
			name: '',
		}
	},
	methods: {
		signin: function (email, password, confirmPassword, name, address) {
			if (password != confirmPassword) {
				swal("ERROR", "Passwords do not match.", "error");
			} else {
				AXIOS.post('/create_customer/', {}, {
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
							swal("Success", "Account created successfully!", "success");
						}
					})
					.catch(e => {
						swal("ERROR", e.response.data, "error");
					})

			}

		}
	}
}
