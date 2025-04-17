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
            disabled
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
            label="Confirmar Edição"
            size="text-sm"
            icon="fa-check"
            type="submit"
            class="flex justify-center items-center col-span-2"
          />

          
        </div>
      </form>
  
      <Modal v-if="mostrarModal" title="Funcionário alterado com sucesso!" @close="handleCloseModal">
        <template #content>
          <div class="flex mb-2" v-for="(item, index) in modalInfo" :key="index">
            <p class="font-bold mr-2">{{ `${item.label}:` }}</p>
            {{ object[item.key] }}
          </div>
          <div class="mt-4 flex justify-end">
            <Button
              blue
              label="Continuar"
              @click="handleCloseModal"
            />
          </div>
        </template>
      </Modal>
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
    mounted() {
      const id = this.$route.query.id; 
      if (id) {
        this.loadEmployee(id);
      }
    },
    methods: {
      loadEmployee(cpf) {
        const mockData = [
          { name: 'Sabrina de Araújo', cpf: '123.456.789-00', email: 'sabrina@email.com', phone: '(11) 99999-9999' },
          { name: 'Lívia Araújo', cpf: '987.654.321-00', email: 'livia@email.com', phone: '(21) 98888-8888' },
          { name: 'Leonardo Nunes', cpf: '555.666.777-00', email: 'leonardo@emial.com', phone: '(31) 97777-7777' },
        ];
  
        const found = mockData.find(emp => emp.cpf === cpf);
        if (found) {
          this.object = { ...found };
        } else {
          console.warn("Funcionário não encontrado.");
        }
      },
  
      submitForm() {
        this.errors = {};
  
        if (!this.object.name) this.errors.name = "Nome é obrigatório";
        if (!this.object.email) this.errors.email = "Email é obrigatório";
        if (!this.object.phone) this.errors.phone = "Telefone é obrigatório";
  
        if (Object.keys(this.errors).length === 0) {
          try {
            // Futuramente chamar uma API para atualizar os dados
            this.mostrarModal = true;
          } catch (error) {
            console.error("Erro ao editar funcionário:", error);
            this.errors.general = "Erro ao editar funcionário. Tente novamente.";
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
  