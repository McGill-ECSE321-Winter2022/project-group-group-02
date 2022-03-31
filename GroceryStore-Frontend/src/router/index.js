import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import GroceryStore from '@/components/GroceryStore'
import SignInCustomer from '@/components/SignInCustomer'
import Login from '@/components/Login'
import ViewCustomerOrders from '@/components/ViewCustomerOrders'
import ChangeOrderStatus from '@/components/ChangeOrderStatus'
import CreateItem from '@/components/CreateItem'
import UpdateCustomer from '@/components/UpdateAccountCustomer'
import UpdateEmployee from '@/components/UpdateAccountEmployee'
import UpdateAdmin from '@/components/UpdateAccountAdmin'
import EmployeeManagement from '@/components/EmployeeManagement'
import ManageStoreInfo from '@/components/ManageStoreInfo'
import DeleteDailySchedule from '@/components/DeleteDailySchedule'
import AddDailySchedule from '@/components/AddDailySchedule'

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
      path: '/UpdateAccountCustomer',
      name: 'updateaccountcustomer',
      component: UpdateCustomer
    },
    {
      path: '/UpdateAccountEmployee',
      name: 'updateaccountemployee',
      component: UpdateEmployee
    },
    {
      path: '/UpdateAccountAdmin',
      name: 'updateaccountadmin',
      component: UpdateAdmin
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
    },

    {
      path: '/ManageStoreInfo',
      name: 'manageStoreInfo',
      component: ManageStoreInfo
    },

    {
      path: '/DeleteDailySchedule',
      name: 'deleteDailySchedule',
      component: DeleteDailySchedule
    },

    {
      path: '/AddDailySchedule',
      name: 'addDailySchedule',
      component: AddDailySchedule
    }
  ]
  
})
