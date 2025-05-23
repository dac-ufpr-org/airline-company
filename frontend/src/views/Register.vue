<template>
  <div class="grid grid-cols-2 h-screen">
    <div class="bg-[#0a205d] flex items-center justify-center">
      <img src="@/assets/logo.png" alt="Logo" class="w-1/2 max-w-xs" />
    </div>
    <div class="overflow-y-auto h-screen flex justify-center items-start">
      <div class="mt-10 mb-10 w-[70%] rounded-lg p-6">
        <p class="mb-6 text-4xl font-semibold text-blue-800">Cadastre-se</p>
        <Input
          v-for="(item, index) in inputs"
          :key="index"
          :title="item.title"
          :type="item.type"
          v-model="object[item.model]"
          :required="item.required"
          :error="errors[item.error]"
        />
        <Button blue label="Cadastrar" @click="register()" class="w-full" />
        <div class="flex justify-end mt-6">
          <p
            class="cursor-pointer hover:text-blue-900 text-blue-800 font-semibold"
            @click="this.$router.push('login')"
          >
            Já tem conta? Entrar
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '@/services/api'; // Importar o serviço

export default {
  data: () => ({
    object: {},
    errors: {},
    inputs: [
      { title: 'Nome', type: 'text', model: 'name', required: true, error: 'name' },
      { title: 'Email', type: 'email', model: 'email', required: true, error: 'email' },
      { title: 'CPF', type: 'text', model: 'cpf', required: true, error: 'cpf' },
      { title: 'CEP', type: 'text', model: 'cep', required: true, error: 'cep' },
      { title: 'Rua', type: 'text', model: 'rua', required: true, error: 'rua' },
      { title: 'Número', type: 'number', model: 'numero', required: true, error: 'numero' },
      // ... outros campos
    ],
  }),
  methods: {
    async register() {
      this.errors = {};
      let hasError = false;

      // Validação
      this.inputs.forEach((input) => {
        if (input.required && !this.object[input.model]) {
          this.errors[input.error] = `${input.title} é obrigatório`;
          hasError = true;
        }
      });

      if (!hasError) {
        try {
          await api.autocadastrarCliente(this.object);
          alert("Cadastro iniciado! Verifique seu e-mail para a senha temporária.");
          this.$router.push('login');
        } catch (error) {
          alert("Erro: " + error.response?.data?.message || "Falha no cadastro");
        }
      }
    },
    async buscarCep() {
        if (this.object.cep && this.object.cep.length === 8) {
            try {
                const response = await axios.get(`https://viacep.com.br/ws/${this.object.cep}/json/`);
                this.object.rua = response.data.logradouro;
                this.object.cidade = response.data.localidade;
                this.object.estado = response.data.uf;
            } catch (error) {
                console.error("Erro ao buscar CEP:", error);
            }
        }
    }
  },
  watch: {
      'object.cep': function(newVal) {
          if (newVal && newVal.length === 8) {
              this.buscarCep();
          }
      }
  }
};
</script>