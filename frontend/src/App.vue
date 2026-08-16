<script setup lang="ts">
import { onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()
const expired = () => router.push({ name: 'login', query: { expired: '1' } })
onMounted(() => window.addEventListener('auth:expired', expired)); onUnmounted(() => window.removeEventListener('auth:expired', expired))
</script>
<template><RouterView /></template>
