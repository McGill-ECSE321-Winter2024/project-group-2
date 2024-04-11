import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Sessions from '@/components/Sessions'
import Login from '@/components/Login'
import MyAccount from '@/components/MyAccount'
import CreateAccount from '@/components/CreateAccount';

import OwnerPage from '@/components/OwnerPage'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/owner',
      name: 'OwnerPage',
      component: OwnerPage

    },
    {
      path: '/MyAccount',
      name: 'MyAccount',
      component: MyAccount
    },
    {
      path: '/CreateAccount',
      name: 'CreateAccount',
      component: CreateAccount


    }
  ]
})
