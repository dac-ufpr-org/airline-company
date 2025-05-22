<template>
  <div class="grid grid-cols-2 h-full w-full">
    <div class="bg-[#0a205d] flex items-center justify-center">
        <img src="@/assets/logo.png" alt="Logo" class="w-1/2 max-w-xs" />
      </div>
    <div class="flex justify-center items-center">
      <div class="mb-3 h-[60%] w-[70%] rounded rounded-lg p-6">
        <p class="mb-6 text-4xl font-semibold text-blue-800">Entrar</p>
        <Input
          title="Email"
          type="email"
          v-model="object.email"
          required
          :error="errors.email"
        />
        <Input
          title="Senha"
          type="password"
          v-model="object.password"
          required
          :error="errors.password"
        />
        <Button blue label="Entrar" @click="login()" />
        <div class="flex justify-end mt-6">
          <p
            class="cursor-pointer hover:text-blue-900 text-blue-800 font-semibold"
            @click="$router.push({ name: 'register' })"
          >
            Não tem conta? Cadastre-se
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '@/services/api';

export default {
  data: () => ({
    object: {},
    errors: {},
  }),
  methods: {
    async login() {
      this.errors = {};
      
      // Validação
      if (!this.object.email) this.errors.email = "Email é obrigatório";
      if (!this.object.password) this.errors.password = "Senha é obrigatória";

      if (Object.keys(this.errors).length === 0) {
        try {
          // 1. Chamada ao backend
          const response = await api.login(this.object.email, this.object.password);
          
          // 2. Armazenar token e role
          localStorage.setItem('token', response.token);
          localStorage.setItem('userRole', response.role); // Adicionado
          
          // 3. Redirecionar conforme o tipo de usuário
          const redirectRoute = response.role === 'FUNCIONARIO' 
            ? 'Dashboard do Funcionário' 
            : 'Dashboard do Cliente';
          
          this.$router.push({ name: redirectRoute });
          
        } catch (error) {
          // 4. Tratamento de erros mais robusto
          if (error.response?.status === 401) {
            this.errors.general = "Credenciais inválidas";
          } else {
            this.errors.general = "Erro ao conectar com o servidor";
            console.error("Erro no login:", error);
          }
        }
      }
    },
  },
};
</script>