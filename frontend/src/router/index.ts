import { createRouter, createWebHistory } from 'vue-router'
import AppLayout from '../layouts/AppLayout.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'

const router = createRouter({
  history: createWebHistory(), routes: [
    { path: '/login', name: 'login', component: LoginView, meta: { guest: true } },
    { path: '/register', name: 'register', component: RegisterView, meta: { guest: true } },
    {
      path: '/', component: AppLayout, meta: { auth: true }, children: [
        { path: '/dashboard', name: 'dashboard', meta: { auth: true }, component: () => import('../views/DashboardView.vue') },
        { path: 'transactions', name: 'transactions', component: () => import('../views/TransactionsView.vue') },
        { path: 'categories', name: 'categories', component: () => import('../views/CategoriesView.vue') },
        { path: 'budgets', name: 'budgets', component: () => import('../views/BudgetsView.vue') },
      ]
    },
  ]
})
router.beforeEach((to) => { const auth = Boolean(localStorage.getItem('finance_token')); if (to.meta.auth && !auth) return { name: 'login' }; if (to.meta.guest && auth) return { name: 'dashboard' } })
export default router
