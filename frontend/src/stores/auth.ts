import { computed, ref } from 'vue'
import { defineStore } from 'pinia'
import { authApi } from '../api/resources'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('finance_token'))
  const isAuthenticated = computed(() => Boolean(token.value))
  function save(value: string) { token.value = value; localStorage.setItem('finance_token', value) }
  async function login(email: string, password: string) { const { data } = await authApi.login(email, password); save(data.accessToken) }
  async function register(name: string, email: string, password: string) { const { data } = await authApi.register(name, email, password); save(data.accessToken) }
  function logout() { token.value = null; localStorage.removeItem('finance_token') }
  return { token, isAuthenticated, login, register, logout }
})
