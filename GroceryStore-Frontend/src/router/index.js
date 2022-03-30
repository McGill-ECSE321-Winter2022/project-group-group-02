import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import GroceryStore from '@/components/GroceryStore'
import SignInCustomer from '@/components/SignInCustomer'
import Login from '@/components/Login'
import ViewCustomerOrders from '@/components/ViewCustomerOrders'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/app',
      name: 'GroceryStore',
      component: GroceryStore
    },
    {
      path: '/SignInCustomer',
      name: 'signincustomer',
      component: SignInCustomer
    },
    {
      path: '/Login',
      name: 'login',
      component: Login
    },
    {
      path: '/ViewCustomerOrders',
      name: 'viewCustomerOrders',
      component: ViewCustomerOrders
    }
  ]
})
