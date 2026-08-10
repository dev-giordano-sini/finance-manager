export const currency = new Intl.NumberFormat('it-IT', { style: 'currency', currency: 'EUR' }).format
export const shortDate = (value: string) => new Intl.DateTimeFormat('it-IT', { day: '2-digit', month: 'short', year: 'numeric' }).format(new Date(`${value}T00:00:00`))
export const today = () => new Date().toISOString().slice(0, 10)
