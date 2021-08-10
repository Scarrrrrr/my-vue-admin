import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path:'/register',
        name:'register',
        meta:{title:'注册'},
        component:() => import('@/views/register.vue')
    },
    {
        path: '/',
        name: 'main',
        redirect: '/home',
    },
    {
        path: '/home',
        name: 'home',
        component:() => import('@/views/Home'),
        redirect: '/home/display',
        children:[
          {
            path:'/home/display',
            component:() => import('@/views/demo/layout-test')
          },
          {
            path:'/home/search',
            component:() => import('@/views/demo/search')
          },
          {
            path: "/home/personal", 
            name: "personal", 
            component: ()=>import("@/views/personal-center/personal") 
          },
          { 
            path: "/home/attend", 
            name: "attend", 
            meta: { title: "参与活动" }, 
            component: ()=>import("@/views/personal-center/attend-list") 
          },
          { 
            path: "/home/activities", 
            name: "activities", 
            component: () => import('@/views/manage/activities')
          },
          {
            path: "/home/users",
            name: "users",
            component: () => import("@/views/demo/users")
          },
          {
            path:'/home/specific/:id',
            name:"specific",
            component: () => import('@/views/demo/specific')
          }
        ]
    },

]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
  
export default router