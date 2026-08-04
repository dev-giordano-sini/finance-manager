export type TransactionType = 'INCOME' | 'EXPENSE'
export interface AuthResponse { accessToken: string; tokenType: string; expiresIn: number }
export interface Category { id: number; name: string; color: string; createdAt: string; updatedAt: string }
export interface CategoryInput { name: string; color: string }
export interface Transaction { id: number; categoryId: number; categoryName: string; type: TransactionType; amount: number; date: string; description?: string; createdAt: string; updatedAt: string }
export interface TransactionInput { categoryId: number; type: TransactionType; amount: number; date: string; description?: string }
export interface Budget { id: number; categoryId: number; categoryName: string; amount: number; startDate: string; endDate: string; createdAt: string; updatedAt: string }
export interface BudgetInput { categoryId: number; amount: number; startDate: string; endDate: string }
export interface Page<T> { content: T[]; totalElements: number; totalPages: number; number: number; size: number; first: boolean; last: boolean }
