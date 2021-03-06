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
        component: ()=> import('@/views/Class')
      },
      {
        path: 'searchGrade',
        name: 'searchGrade',
        component: ()=> import('@/views/SearchGrade')
      },
      {
        path: 'graph',
        name: 'graph',
        component: ()=> import('@/views/Graph')
      },
      {
        path: 'selectCourse',
        name: 'selectCourse',
        component: ()=> import('@/views/SelectCourse')
      },
      {
        path: 'searchCourse',
        name: 'searchCourse',
        component: ()=> import('@/views/SearchCourse')
      },
      {
        path: 'college',
        name: 'College',
        component: ()=> import('@/views/College')
      },
      {
        path: 'course',
        name: 'Course',
        component: ()=> import('@/views/Course')
      },
      {
        path: 'opencourse',
        name: 'OpenCourse',
        component: () => import('@/views/OpenCourse')
      },
      {
        path: 'loadscore',
        name: 'LoadCoursse',
        component: () => import('@/views/LoadScore')
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
