<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useAuthStore } from '../stores/auth';
import { useApiError } from '../composables/useApiError';
import logo from "../assets/spendly_logo.png";
const email = ref('');
const password = ref('');
const loading = ref(false);
const router = useRouter();
const route = useRoute();
const auth = useAuthStore();
const { error, capture } = useApiError();


async function submit() {
    loading.value = true;
    error.value = "";

    try {

        await auth.login(
            email.value,
            password.value
        );

        await router.replace("/dashboard");

    } catch (e) {
        capture(e);
    } finally {
        loading.value = false;
    }
}
</script>
<template>
    <main class="auth-page">
        <section class="auth-intro">
            <div class="brand light"><span class="brand-avatar">
                <img :src="logo" alt="Spendly" class="brand-logo" />
            </span><strong>Spendly</strong></div>
            <div><span class="eyebrow">FINANZE, SEMPLICI</span>
                <h1>Il tuo denaro.<br>Più <em>chiaro</em> che mai.</h1>
                <p>Una vista completa delle tue finanze per decidere meglio, ogni giorno.</p>
            </div><small>© 2026 Spendly</small>
        </section>
        <section class="auth-form">
            <form @submit.prevent="submit"><span class="mobile-brand">Spendly</span>
                <h2>Bentornato</h2>
                <p>Accedi per continuare verso i tuoi obiettivi.</p>
                <div v-if="route.query.expired" class="notice">Sessione scaduta. Accedi nuovamente.</div>
                <div v-if="error" class="error">{{ error }}</div><label>Email<input v-model="email" type="email"
                        placeholder="nome@esempio.it" required></label><label>Password<input v-model="password"
                        type="password" placeholder="Almeno 8 caratteri" required></label><button
                    class="btn primary full" :disabled="loading">{{ loading ? 'Accesso…' : 'Accedi' }}</button>
                <p class="auth-link">Non hai un account? <RouterLink to="/register">Registrati</RouterLink>
                </p>
            </form>
        </section>
    </main>
</template>
