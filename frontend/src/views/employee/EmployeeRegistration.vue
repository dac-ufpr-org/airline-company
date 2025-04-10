<template>
  <div class="p-6 max-w-3xl mx-auto space-y-6">
    <form
      @submit.prevent="submitForm"
      @input="formChanged = true"
      class="space-y-4"
    >
      <div class="grid grid-cols-2 gap-4">
        <Input
          title="Nome Completo"
          type="text"
          v-model="object.name"
          id="nome"
          placeholder="Digite o nome completo"
          required
          :error="errors.name"
        />

        <Input
          title="CPF"
          v-model="object.cpf"
          type="text"
          id="cpf"
          placeholder="Digite o CPF"
          required
          :error="errors.cpf"
        />
      </div>
      <div class="grid grid-cols-2 gap-4">
        <Input
          title="E-mail"
          v-model="object.email"
          type="email"
          id="email"
          placeholder="Digite o e-mail"
          required
          :error="errors.email"
        />

        <Input
          title="Telefone"
          v-model="object.phone"
          type="text"
          id="telefone"
          placeholder="Digite o telefone"
          required
          :error="errors.phone"
        />

        <Button
          blue
          label="Cadastrar Funcionário"
          size="text-sm"
          icon="fa-save"
          type="submit"
          class="flex justify-center items-center col-span-2"
        />
      </div>
    </form>
  </div>
</template>

<script>

export default {
  data() {
    return {
      object: {},
      errors: {},
    };
  },
  methods: {
    async register() {
      this.errors = {};

      // Validação básica
      if (!this.object.name) this.errors.name = "Nome é obrigatório";
      if (!this.object.cpf) this.errors.cpf = "CPF é obrigatório";
      if (!this.object.email) this.errors.email = "Email é obrigatório";
      if (!this.object.phone) this.errors.phone = "Telefone é obrigatório";

      if (Object.keys(this.errors).length === 0) {
        try {
          //Depois colocar aqui a chamada real do backend
          this.$router.push({ name: "employee" });
        } catch (error) {
          console.error("Erro ao cadastrar funcionário:", error);
          this.errors.general =
            "Erro ao cadastrar funcionário. Tente novamente.";
        }
      }
    },
  },
};
</script>
