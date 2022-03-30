function OrderDto (date, time, orderType, orderStatus, review, rating) {

    this.date = date
    this.time = time
    this.orderType = orderType
    this.orderStatus = orderStatus
    this.review = review
    this.rating = rating

}



export default {
    name: 'order',
    data () {
      return {
        date: '',
        time: '',
        orderType: '',
        orderStatus: '',
        review: '',
        rating: '',
        items: [],
        orders: []
      }
    },
    created: function () {
        // Test data
        const o1 = new OrderDto('12 March', '12:43', 'Delivery', 'Complete', 'Good', 'Arrived on time')
        const o2 = new OrderDto('2 April', '4:22', 'Pickup', 'Preparing', 'Okay', 'Could be faster')
        // Sample initial content
        this.orders = [o1, po]
      },
  }