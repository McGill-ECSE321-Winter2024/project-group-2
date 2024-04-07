import Vue from 'vue'
import Router from 'vue-router'

import Hello from '@/components/Hello'
import OwnerPage from '@/components/OwnerPage'
import Owner from '@/components/Owner'

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
      name: 'OwnerPage',
      component: OwnerPage

    },
    {
      path: '/owner',
      name: 'Owner',
      component: Owner
    }
  ]
})
