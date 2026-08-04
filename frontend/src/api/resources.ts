import { api } from './client'
import type { AuthResponse, Budget, BudgetInput, Category, CategoryInput, Page, Transaction, TransactionInput } from '../types'

export const authApi = {
  login: (email: string, password: string) => api.post<AuthResponse>('/auth/login', { email, password }),
  register: (name: string, email: string, password: string) => api.post<AuthResponse>('/auth/register', { name, email, password }),
}
export const categoriesApi = {
  list: () => api.get<Category[]>('/categories'), create: (data: CategoryInput) => api.post<Category>('/categories', data),
  update: (id: number, data: CategoryInput) => api.put<Category>(`/categories/${id}`, data), delete: (id: number) => api.delete(`/categories/${id}`),
}
export const transactionsApi = {
  list: (params: Record<string, unknown> = {}) => api.get<Page<Transaction>>('/transactions', { params }), create: (data: TransactionInput) => api.post<Transaction>('/transactions', data),
  update: (id: number, data: TransactionInput) => api.put<Transaction>(`/transactions/${id}`, data), delete: (id: number) => api.delete(`/transactions/${id}`),
}
export const budgetsApi = {
  list: () => api.get<Budget[]>('/budgets'), create: (data: BudgetInput) => api.post<Budget>('/budgets', data),
  update: (id: number, data: BudgetInput) => api.put<Budget>(`/budgets/${id}`, data), delete: (id: number) => api.delete(`/budgets/${id}`),
}
