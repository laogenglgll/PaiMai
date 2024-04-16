import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Login from '@/components/login'
import Index from '@/components/index'
import Shenhe from '@/components/shenhe'
import User from '@/components/user'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
	{
		path:'/index',
		component:Index,
		children:[
			{
				path:'shenhe',
				component:Shenhe,
			},
			{
				path:'user',
				component:User
			}
		]
	}
  ]
})
