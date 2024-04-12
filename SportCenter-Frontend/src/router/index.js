import Vue from 'vue'
import Router from 'vue-router'

// import Vue components used in the routing
import Home from '@/components/Home'
import Sessions from '@/components/Sessions'
import Login from '@/components/Login'
import MyAccount from '@/components/MyAccount'
import CreateAccount from '@/components/CreateAccount'
import OwnerPage from '@/components/OwnerPage'

// router plugin to the Vue instance
Vue.use(Router)

// export a new Router instance with the defined routes
export default new Router({
  // array containing route definitions
  routes: [
    {
      path: '/',  // URL path for the route (Home)
      name: 'Home',
      component: Home  // component to render when this route is active
    },
    {
      path: '/Sessions/:classType?',  // optional parameter "classType" for more dynamic routing
      name: 'Sessions',
      component: Sessions,
      props: true  // enables passing route params as props to the component
    },
    {
      path: '/login',
      name: 'Login',
      component: Login  // user login component
    },
    {
      path: '/owner',
      name: 'OwnerPage',
      component: OwnerPage  // owner page component 
    },
    {
      path: '/MyAccount',
      name: 'MyAccount',
      component: MyAccount  // user account page component
    },
    {
      path: '/CreateAccount',
      name: 'CreateAccount',
      component: CreateAccount  // create account page component 
    }
  ]
})
