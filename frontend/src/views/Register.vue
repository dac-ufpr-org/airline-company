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
export default {
  data: () => ({
      object: {},
      errors: {},
      inputs: [
          { title: 'Nome', type: 'text', model: 'name', isRequired: true, error: 'name' },
          { title: 'Email', type: 'email', model: 'email', isRequired: true, error: 'email' },
          { title: 'CPF', type: 'text', model: 'cpf', isRequired: true, error: 'cpf' },
          { title: 'CEP', type: 'text', model: 'cep', isRequired: true, error: 'cep' },
          { title: 'Rua', type: 'text', model: 'rua', isRequired: true, error: 'rua' },
          { title: 'Número', type: 'number', model: 'numero', isRequired: true, error: 'numero' },
          { title: 'Complemento', type: 'text', model: 'complemento', isRequired: false, error: 'complemento' },
          { title: 'Cidade', type: 'text', model: 'cidade', isRequired: false, error: 'cidade' },
          { title: 'Estado', type: 'text', model: 'estado', isRequired: false, error: 'estado' },
      ]
  }),
  methods: {
      async register() {
          if (this.object.email && this.object.name && this.object.password) {
              // const response = await this.register(this.object) //implementar depois no backend
              // if (!response?.data) {
              // }
              this.$router.push('login')
          }
          else {
              if (!this.object.name) this.errors.name = 'Nome é obrigatório'
              if (!this.object.email) this.errors.email = 'Email é obrigatório'
              if (!this.object.password) this.errors.password = 'Senha é obrigatória'
          }
      }
  }

}
</script>