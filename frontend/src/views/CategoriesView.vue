<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'; import PageHeader from '../components/PageHeader.vue'; import AppModal from '../components/AppModal.vue'; import EmptyState from '../components/EmptyState.vue'; import { categoriesApi } from '../api/resources'; import type { Category } from '../types'; import { useApiError } from '../composables/useApiError'; defineEmits<{ menu: [] }>(); const items = ref<Category[]>([]), modal = ref(false), editing = ref<Category | null>(null), loading = ref(true); const form = reactive({ name: '', color: '#6956e8' }); const { error, capture } = useApiError(); async function load() { items.value = (await categoriesApi.list()).data; loading.value = false } onMounted(load); function open(item?: Category) { editing.value = item || null; form.name = item?.name || ''; form.color = item?.color || '#6956e8'; error.value = ''; modal.value = true } async function save() { try { if (editing.value) await categoriesApi.update(editing.value.id, form); else await categoriesApi.create(form); modal.value = false; await load() } catch (e) { capture(e) } } async function remove(item: Category) { if (confirm(`Eliminare la categoria “${item.name}”?`)) { try { await categoriesApi.delete(item.id); await load() } catch (e) { capture(e) } } }
</script>
<template>
    <PageHeader title="Categorie" subtitle="Organizza entrate e uscite a modo tuo." @menu="$emit('menu')"><button
            class="btn primary" @click="open()">+ Nuova categoria</button></PageHeader>
    <div v-if="error && !modal" class="error">{{ error }}</div>
    <div v-if="loading" class="loading">Caricamento…</div>
    <section v-else-if="items.length" class="card-grid">
        <article v-for="item in items" :key="item.id" class="category-card"><span class="color-preview"
                :style="{ background: item.color }">◇</span>
            <div>
                <h3>{{ item.name }}</h3>
                <p>{{ item.color }}</p>
            </div>
            <div class="row-actions"><button @click="open(item)">Modifica</button><button class="danger"
                    @click="remove(item)">Elimina</button></div>
        </article>
    </section>
    <div v-else class="panel">
        <EmptyState title="Crea le tue categorie" text="Usale per capire dove vanno i tuoi soldi." />
    </div>
    <AppModal v-if="modal" :title="editing ? 'Modifica categoria' : 'Nuova categoria'" @close="modal = false">
        <form class="modal-form" @submit.prevent="save">
            <div v-if="error" class="error">{{ error }}</div><label>Nome<input v-model="form.name" maxlength="80"
                    required></label><label>Colore<div class="color-input"><input v-model="form.color"
                        type="color"><input v-model="form.color" pattern="^#[0-9A-Fa-f]{6}$" required></div></label>
            <footer><button type="button" class="btn secondary" @click="modal = false">Annulla</button><button
                    class="btn primary">Salva</button></footer>
        </form>
    </AppModal>
</template>
