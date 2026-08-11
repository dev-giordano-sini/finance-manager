<script setup lang="ts">
import logo from "../assets/spendly_logo.png";
defineProps<{ open: boolean }>();
defineEmits<{ close: [] }>()
const links = [{ to: '/dashboard', icon: '⌂', label: 'Panoramica' },
{ to: '/transactions', icon: '↔', label: 'Movimenti' },
{ to: '/categories', icon: '◈', label: 'Categorie' },
{ to: '/budgets', icon: '◎', label: 'Budget' }]
</script>
<template>
    <aside class="sidebar" :class="{ open }">
        <div class="brand light"><span class="brand-avatar">
                <img :src="logo" alt="Spendly" class="brand-logo" />
            </span><strong>Spendly</strong>
            <button class="icon-btn mobile-only" @click="$emit('close')">×</button>
        </div>
        <nav>
            <RouterLink v-for="link in links" :key="link.to" :to="link.to" @click="$emit('close')"><span>{{ link.icon
            }}</span>{{ link.label }}</RouterLink>
        </nav>
        <div class="sidebar-card"><b>Controlla le tue finanze</b>
            <p>Piccoli progressi ogni giorno creano grandi risultati.</p>
        </div>
    </aside>
    <div v-if="open" class="overlay" @click="$emit('close')" />
</template>
