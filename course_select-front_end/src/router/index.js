import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
  {
    path: '/',
    name: 'Layout',
    // redirect: '/student',
    meta:{
      requireAuth: true,
    },
    component: Layout,
    children: [
      {
        path: 'student',
        name: 'Student',
        component: ()=> import('@/views/Student')
      },
      {
        path: 'teacher',
        name: 'Teacher',
        component: ()=> import('@/views/Teacher')
      },
      {
        path: 'select',
        name: 'Select',
        component: ()=> import('@/views/Select')
      },
      {
        path: 'score',
        name: 'Score',
        component: ()=> import('@/views/Score')
      },
      {
        path: 'college',
        name: 'College',
        component: ()=> import('@/views/College')
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
