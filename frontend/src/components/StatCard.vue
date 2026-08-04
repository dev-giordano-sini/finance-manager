<script setup lang="ts">defineProps<{ label: string; value: string; icon: string; tone?: string; note?: string }>()</script>
<template><article class="stat-card"><div class="stat-icon" :class="tone">{{ icon }}</div><div><span>{{ label }}</span><h2>{{ value }}</h2><small v-if="note">{{ note }}</small></div></article></template>
