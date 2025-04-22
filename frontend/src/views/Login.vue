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

export default {
  data: () => ({
    object: {},
    errors: {},
  }),
  methods: {
    async login() {
      // Reset erros anteriores
      this.errors = {};

      // Validação básica
      if (!this.object.email) this.errors.email = "Email é obrigatório";
      if (!this.object.password) this.errors.password = "Senha é obrigatória";

      // Se não houver erros, procede com o login
      if (Object.keys(this.errors).length === 0) {
        try {
          // Implementar aqui a chamada real ao backend posteriormente
          // const response = await authService.login(this.object);
          // if (response.success) {
          //  this.$router.push({ name: 'clientDashboard' });
          // } else {
          //   this.errors.general = "Credenciais inválidas";
          // }

          // Por enquanto, apenas redireciona
          this.$router.push({ name: "clientDashboard" });
        } catch (error) {
          console.error("Erro no login:", error);
          this.errors.general = "Ocorreu um erro durante o login";
        }
      }
    },
  },
};
</script>