import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import GroceryStore from '@/components/GroceryStore'
import SignInCustomer from '@/components/SignInCustomer'
import Login from '@/components/Login'
import ViewCustomerOrders from '@/components/ViewCustomerOrders'
import ChangeOrderStatus from '@/components/ChangeOrderStatus'
import CreateItem from '@/components/CreateItem'
import EmployeeManagement from '@/components/EmployeeManagement'

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
    ,
    {
      path: '/ChangeOrderStatus',
      name: 'changeorderstatus',
      component: ChangeOrderStatus
    },

    {
      path: '/EmployeeManagement',
      name: 'employeeManagement',
      component: EmployeeManagement
    }
  ]
})
