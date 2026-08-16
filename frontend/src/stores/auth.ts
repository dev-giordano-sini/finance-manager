import { computed, ref } from 'vue'
import { defineStore } from 'pinia'
import { authApi, userApi } from '../api/resources'
import type { CurrentUser } from '../types'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('finance_token'))
  const isAuthenticated = computed(() => Boolean(token.value))
  const user = ref<CurrentUser | null>(null);
  async function loadCurrentUser() {
    const { data } = await userApi.me();
    user.value = data;
  }
  function save(value: string) {
    token.value = value; localStorage.setItem('finance_token', value)
  }
  async function login(email: string, password: string) {
    const { data } = await authApi.login(email, password);
    save(data.accessToken);
    await loadCurrentUser();
  }
  async function register(name: string, surname: string, email: string, password: string) {
    const { data } = await authApi.register(name, surname, email, password);
    save(data.accessToken)
  }
  function logout() {
    token.value = null; localStorage.removeItem('finance_token')
  }
  return {
    token, user, isAuthenticated, login, register, logout
  }
})
