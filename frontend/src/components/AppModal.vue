<script setup lang="ts">defineProps<{ title: string }>(); defineEmits<{ close: [] }>()</script>
<template><Teleport to="body"><div class="modal-backdrop" @click.self="$emit('close')"><section class="modal"><header><h2>{{ title }}</h2><button class="icon-btn" @click="$emit('close')">×</button></header><slot /></section></div></Teleport></template>
