<script setup lang="ts">defineProps<{ title: string; subtitle?: string }>(); defineEmits<{ menu: [] }>()</script>
<template>
    <header class="page-header"><button class="icon-btn mobile-only" @click="$emit('menu')">☰</button>
        <div>
            <h1>{{ title }}</h1>
            <p v-if="subtitle">{{ subtitle }}</p>
        </div>
        <slot />
    </header>
</template>
