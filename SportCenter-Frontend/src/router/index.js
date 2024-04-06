import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Sessions from '@/components/Sessions'
import Login from '@/components/Login'
import MyAccount from '@/components/MyAccount'
import Owner from '@/components/Owner'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/Login',
      name: 'Login',
      component: Login
    },
    {
      path: '/Home',
      name: 'Home',
      component: Home
    },
    {
      path: '/Sessions',
      name: 'Sessions',
      component: Sessions
    },
    {
      path: '/MyAccount',
      name: 'MyAccount',
      component: MyAccount
    },
    {
      path: '/Owner',
      name: 'Owner',
      component: Owner
    }
  ]
})
