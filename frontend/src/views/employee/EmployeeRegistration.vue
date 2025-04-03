<template>
    <div class="p-6 bg-white rounded-lg shadow-md">
      <h2 class="text-2xl font-semibold mb-6">Cadastrar Funcionário</h2>
  
      <form @submit.prevent="submitForm">
        <div class="mb-4">
          <label for="nome" class="block text-sm font-medium">Nome</label>
          <input
            v-model="form.nome"
            type="text"
            id="nome"
            class="w-full p-2 border border-gray-300 rounded-md"
            placeholder="Digite o nome completo"
            required
          />
        </div>
  
        <div class="mb-4">
          <label for="cpf" class="block text-sm font-medium">CPF</label>
          <input
            v-model="form.cpf"
            type="text"
            id="cpf"
            class="w-full p-2 border border-gray-300 rounded-md"
            placeholder="Digite o CPF"
            required
          />
        </div>
  
        <div class="mb-4">
          <label for="email" class="block text-sm font-medium">E-mail</label>
          <input
            v-model="form.email"
            type="email"
            id="email"
            class="w-full p-2 border border-gray-300 rounded-md"
            placeholder="Digite o e-mail"
            required
          />
        </div>
  
        <div class="mb-4">
          <label for="telefone" class="block text-sm font-medium">Telefone</label>
          <input
            v-model="form.telefone"
            type="text"
            id="telefone"
            class="w-full p-2 border border-gray-300 rounded-md"
            placeholder="Digite o telefone"
            required
          />
        </div>
  
        <div class="mt-6 flex justify-end">
          <Button
            blue
            label="Cadastrar"
            size="text-sm"
            icon="fa-save"
            type="submit"
          />
        </div>
      </form>
    </div>
  </template>
  
  <script>
  import Button from '@/components/general/Button.vue'
  
  export default {
    components: {
      Button
    },
    data() {
      return {
        form: {
          nome: '',
          cpf: '',
          email: '',
          telefone: ''
        }
      }
    },
    methods: {
      submitForm() {
        console.log('Funcionário cadastrado:', this.form)
        // Aqui você pode adicionar a lógica para enviar o formulário (API ou outro serviço)
      }
    }
  }
  </script>
  
  <style scoped>
  /* Estilos para a página de cadastro */
  </style>
  