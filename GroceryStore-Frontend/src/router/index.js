import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import GroceryStore from '@/components/GroceryStore'
import SignInCustomer from '@/components/SignInCustomer'
import Login from '@/components/Login'
import ChangeOrderStatus from '@/components/ChangeOrderStatus'

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
      name: 'signin',
      component: SignInCustomer
    },
    ,
    {
      path: '/Login',
      name: 'login',
      component: Login
    }
    ,
    {
      path: '/ChangeOrderStatus',
      name: 'changeorderstatus',
      component: ChangeOrderStatus
    }
  ]
})
