<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { Doughnut, Line } from 'vue-chartjs'
import { ArcElement, CategoryScale, Chart as ChartJS, Filler, Legend, LinearScale, LineElement, PointElement, Tooltip } from 'chart.js'
import EmptyState from '../components/EmptyState.vue'
import PageHeader from '../components/PageHeader.vue'
import StatCard from '../components/StatCard.vue'
import { dashboardApi } from '../api/resources'
import { useApiError } from '../composables/useApiError'
import type { Dashboard } from '../types'
import { currency, shortDate } from '../utils/format'

ChartJS.register(ArcElement, CategoryScale, LinearScale, PointElement, LineElement, Tooltip, Legend, Filler)
defineEmits<{ menu: [] }>()
const loading = ref(true), dashboard = ref<Dashboard | null>(null), from = ref(''), to = ref('')
const { error, capture } = useApiError()
const commonOptions = { responsive: true, maintainAspectRatio: false, plugins: { legend: { display: false } } }
const doughnutOptions = { ...commonOptions, cutout: '72%' }
const lineData = computed(() => ({
  labels: dashboard.value?.dailyCashFlow.map(day => shortDate(day.date)) ?? [],
  datasets: [
    { label: 'Entrate', data: dashboard.value?.dailyCashFlow.map(day => +day.income) ?? [], borderColor: '#5bb89b', backgroundColor: '#5bb89b1c', fill: true, tension: .35 },
    { label: 'Uscite', data: dashboard.value?.dailyCashFlow.map(day => +day.expenses) ?? [], borderColor: '#ff7d6e', backgroundColor: '#ff7d6e14', fill: true, tension: .35 },
  ],
}))
const doughnutData = computed(() => ({
  labels: dashboard.value?.expensesByCategory.map(item => item.categoryName) ?? [],
  datasets: [{ data: dashboard.value?.expensesByCategory.map(item => +item.amount) ?? [], backgroundColor: dashboard.value?.expensesByCategory.map(item => item.categoryColor) ?? [], borderWidth: 0 }],
}))
async function loadDashboard() {
  loading.value = true; error.value = ''
  try {
    const { data } = await dashboardApi.get({ from: from.value || undefined, to: to.value || undefined })
    dashboard.value = data; from.value = data.from; to.value = data.to
  } catch (reason) { capture(reason) } finally { loading.value = false }
}
onMounted(loadDashboard)
</script>

<template>
  <PageHeader title="La tua panoramica" subtitle="Entrate, uscite e abitudini di spesa in un solo posto." @menu="$emit('menu')"><RouterLink class="btn primary" to="/transactions">+ Nuovo movimento</RouterLink></PageHeader>
  <form class="dashboard-filters" @submit.prevent="loadDashboard"><span>Periodo</span><label>Dal <input v-model="from" type="date" :max="to || undefined"></label><label>Al <input v-model="to" type="date" :min="from || undefined"></label><button class="btn secondary" :disabled="loading">Aggiorna</button></form>
  <div v-if="error" class="error dashboard-error">{{ error }} <button @click="loadDashboard">Riprova</button></div>
  <div v-if="loading" class="loading">Caricamento panoramica…</div>
  <template v-else-if="dashboard">
    <section class="stats">
      <StatCard label="Saldo del periodo" :value="currency(+dashboard.balance)" icon="€" tone="purple" :note="`${dashboard.transactionCount} movimenti`" />
      <StatCard label="Entrate" :value="currency(+dashboard.totalIncome)" icon="↗" tone="green" />
      <StatCard label="Uscite" :value="currency(+dashboard.totalExpenses)" icon="↘" tone="coral" />
      <StatCard label="Categorie di spesa" :value="String(dashboard.expensesByCategory.length)" icon="◈" tone="gold" />
    </section>
    <section class="dashboard-grid">
      <article class="panel"><div class="panel-title"><div><h3>Andamento finanziario</h3><p>Entrate e uscite giorno per giorno</p></div><div class="chart-key"><span>Entrate</span><span class="expense-key">Uscite</span></div></div><div class="chart"><Line v-if="dashboard.dailyCashFlow.length" :data="lineData" :options="commonOptions" /><EmptyState v-else title="Nessun dato" text="Aggiungi un movimento per vedere l'andamento." /></div></article>
      <article class="panel expense-panel"><div class="panel-title"><div><h3>Spese per categoria</h3><p>Distribuzione nel periodo</p></div></div><template v-if="dashboard.expensesByCategory.length"><div class="donut"><Doughnut :data="doughnutData" :options="doughnutOptions" /></div><ul class="category-legend"><li v-for="item in dashboard.expensesByCategory.slice(0, 5)" :key="item.categoryId"><i :style="{background:item.categoryColor}"></i><span>{{ item.categoryName }}</span><b>{{ (+item.percentage).toFixed(0) }}%</b></li></ul></template><EmptyState v-else title="Nessuna spesa" text="Le categorie appariranno qui." /></article>
    </section>
    <article class="panel recent"><div class="panel-title"><div><h3>Movimenti recenti</h3><p>Le ultime attività del periodo selezionato</p></div><RouterLink to="/transactions">Vedi tutti →</RouterLink></div><div v-if="dashboard.recentTransactions.length"><div v-for="item in dashboard.recentTransactions" :key="item.id" class="transaction-row"><span class="category-avatar" :class="item.type.toLowerCase()">{{ item.categoryName.charAt(0) }}</span><div><b>{{ item.description || item.categoryName }}</b><small>{{ item.categoryName }} · {{ shortDate(item.date) }}</small></div><strong :class="item.type==='INCOME'?'positive':'negative'">{{ item.type==='INCOME'?'+':'−' }} {{ currency(+item.amount) }}</strong></div></div><EmptyState v-else title="Nessun movimento" text="Registra la tua prima entrata o uscita." /></article>
  </template>
</template>
