<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'; import PageHeader from '../components/PageHeader.vue'; import AppModal from '../components/AppModal.vue'; import EmptyState from '../components/EmptyState.vue'; import { budgetsApi, categoriesApi, transactionsApi } from '../api/resources'; import type { Budget, Category, Transaction } from '../types'; import { currency, shortDate, today } from '../utils/format'; import { useApiError } from '../composables/useApiError'; defineEmits<{ menu: [] }>(); const items = ref<Budget[]>([]), categories = ref<Category[]>([]), transactions = ref<Transaction[]>([]), modal = ref(false), editing = ref<Budget | null>(null), loading = ref(true); const form = reactive({ categoryId: 0, amount: 0, startDate: today().slice(0, 8) + '01', endDate: today() }); const { error, capture } = useApiError(); async function load() { const [b, c, t] = await Promise.all([budgetsApi.list(), categoriesApi.list(), transactionsApi.list({ size: 100 })]); items.value = b.data; categories.value = c.data; transactions.value = t.data.content; loading.value = false } onMounted(load); function spent(b: Budget) { return transactions.value.filter(t => t.type === 'EXPENSE' && t.categoryId === b.categoryId && t.date >= b.startDate && t.date <= b.endDate).reduce((s, t) => s + Number(t.amount), 0) } function open(item?: Budget) { if (!categories.value.length) { error.value = 'Crea prima una categoria.'; return } editing.value = item || null; Object.assign(form, item ? { categoryId: item.categoryId, amount: Number(item.amount), startDate: item.startDate, endDate: item.endDate } : { categoryId: categories.value[0].id, amount: 0, startDate: today().slice(0, 8) + '01', endDate: today() }); error.value = ''; modal.value = true } async function save() { try { if (editing.value) await budgetsApi.update(editing.value.id, form); else await budgetsApi.create(form); modal.value = false; await load() } catch (e) { capture(e) } } async function remove(item: Budget) { if (confirm('Eliminare questo budget?')) { try { await budgetsApi.delete(item.id); await load() } catch (e) { capture(e) } } }
</script>
<template>
    <PageHeader title="Budget" subtitle="Stabilisci i limiti, raggiungi i tuoi obiettivi." @menu="$emit('menu')"><button
            class="btn primary" @click="open()">+ Nuovo budget</button></PageHeader>
    <div v-if="error && !modal" class="error">{{ error }}</div>
    <div v-if="loading" class="loading">Caricamento…</div>
    <section v-else-if="items.length" class="budget-grid">
        <article v-for="item in items" :key="item.id" class="panel budget-card">
            <div class="budget-title"><span class="color-preview"
                    :style="{ background: categories.find(c => c.id === item.categoryId)?.color }">◎</span>
                <div>
                    <h3>{{ item.categoryName }}</h3><small>{{ shortDate(item.startDate) }} –
                        {{ shortDate(item.endDate) }}</small>
                </div>
                <div class="row-actions"><button @click="open(item)">✎</button><button class="danger"
                        @click="remove(item)">×</button></div>
            </div>
            <div class="budget-numbers"><b>{{ currency(spent(item)) }}</b><span>di
                    {{ currency(Number(item.amount)) }}</span></div>
            <div class="progress"><i
                    :style="{ width: Math.min(100, spent(item) / Number(item.amount) * 100) + '%', background: spent(item) > Number(item.amount) ? '#ff7d6e' : categories.find(c => c.id === item.categoryId)?.color }"></i>
            </div><small>{{ Math.round(spent(item) / Number(item.amount) * 100) }}% utilizzato</small>
        </article>
    </section>
    <div v-else class="panel">
        <EmptyState title="Nessun budget" text="Imposta un limite di spesa per una categoria." />
    </div>
    <AppModal v-if="modal" :title="editing ? 'Modifica budget' : 'Nuovo budget'" @close="modal = false">
        <form class="modal-form" @submit.prevent="save">
            <div v-if="error" class="error">{{ error }}</div><label>Categoria<select v-model.number="form.categoryId">
                    <option v-for="c in categories" :key="c.id" :value="c.id">{{ c.name }}</option>
                </select></label><label>Importo massimo (€)<input v-model.number="form.amount" type="number" min="0.01"
                    step="0.01" required></label>
            <div class="form-row"><label>Dal<input v-model="form.startDate" type="date" required></label><label>Al<input
                        v-model="form.endDate" type="date" :min="form.startDate" required></label></div>
            <footer><button type="button" class="btn secondary" @click="modal = false">Annulla</button><button
                    class="btn primary">Salva</button></footer>
        </form>
    </AppModal>
</template>
