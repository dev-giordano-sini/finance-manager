import { ref } from 'vue'
import axios from 'axios'

export function useApiError() {
  const error = ref('')
  function capture(reason: unknown) {
    if (axios.isAxiosError(reason)) error.value = reason.response?.data?.detail || reason.response?.data?.message || 'Impossibile completare la richiesta.'
    else error.value = 'Si è verificato un errore inatteso.'
  }
  return { error, capture }
}
