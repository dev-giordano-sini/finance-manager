<script setup lang="ts">
import { ref } from 'vue'; import { useRouter } from 'vue-router'; import { useAuthStore } from '../stores/auth'; import AppSidebar from '../components/AppSidebar.vue'
const menu = ref(false); const auth = useAuthStore(); const router = useRouter(); function logout() { auth.logout(); router.push('/login') }
</script>
<template>
    <div class="shell">
        <AppSidebar :open="menu" @close="menu = false" />
        <main class="main">
            <div class="top-actions"><button class="profile" @click="logout" title="Esci"><span>FM</span>
                    <div><b>Il mio conto</b><small>Esci</small></div>
                </button></div>
            <RouterView @menu="menu = true" />
        </main>
    </div>
</template>
