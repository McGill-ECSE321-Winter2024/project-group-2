import Vue from 'vue'
import Router from 'vue-router'

import Hello from '@/components/Hello'
import OwnerPage from '@/components/OwnerPage'


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

    }
  ]
})
