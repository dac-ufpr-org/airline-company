<template>
  <div class="flex justify-center items-center mt-8">
    <div class="p-6 max-w-3xl w-full space-y-6">
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
        </div>

        <div class="flex justify-end">
          <Button
            blue
            label="Cadastrar Funcionário"
            size="text-sm"
            icon="fa-save"
            type="submit"
            class="w-auto px-4 py-2 text-sm"
          />
        </div>
      </form>

      <Modal
        v-if="mostrarModal"
        title="Funcionário cadastrado com sucesso!"
        @close="handleCloseModal"
      >
        <template #content>
          <div
            class="flex mb-2"
            v-for="(item, index) in modalInfo"
            :key="index"
          >
            <p class="font-bold mr-2">{{ `${item.label}:` }}</p>
            {{ object[item.key] }}
          </div>
          <div class="mt-4 flex justify-end">
            <Button blue label="Continuar" @click="handleCloseModal" />
          </div>
        </template>
      </Modal>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      object: {
        name: "",
        cpf: "",
        email: "",
        phone: "",
      },
      errors: {},
      mostrarModal: false,
      modalInfo: [
        { label: "Nome", key: "name" },
        { label: "CPF", key: "cpf" },
        { label: "Email", key: "email" },
        { label: "Telefone", key: "phone" },
      ],
    };
  },
  methods: {
    submitForm() {
      this.errors = {};

      if (!this.object.name) this.errors.name = "Nome é obrigatório";
      if (!this.object.cpf) this.errors.cpf = "CPF é obrigatório";
      if (!this.object.email) this.errors.email = "Email é obrigatório";
      if (!this.object.phone) this.errors.phone = "Telefone é obrigatório";

      if (Object.keys(this.errors).length === 0) {
        try {
          // Futuramente chamar API para cadastrar
          this.mostrarModal = true;
        } catch (error) {
          console.error("Erro ao cadastrar funcionário:", error);
          this.errors.general =
            "Erro ao cadastrar funcionário. Tente novamente.";
        }
      }
    },

    handleCloseModal() {
      this.mostrarModal = false;
      this.$router.push({ name: "employee" });
    },
  },
};
</script>
