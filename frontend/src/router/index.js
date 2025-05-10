import App from '@/App.vue'
import NoteView from '@/views/NoteView.vue'
import { createRouter, createWebHistory } from 'vue-router'
// import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
  {
      path: '/',
      name: 'Authentication',
      component: App,
    },
    {
      path: '/home',
      name: 'home',
      component: NoteView,
    },
    {
      path: '/account',
      name: 'account',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AccountView.vue'),
    },
  ],
})

export default router
