<template>
  <div>
    <div class="container mx-auto flex mt-6 p-4">
      <main
        class="flex-1 bg-white p-6 rounded-xl shadow-lg border border-blue-200"
      >
        <Table :items="voos" :columns="tableColumns">
          <template #cell-actions>
            <div class="flex items-center space-x-3">
              <Button
                blue
                label="Confirmar Embarque"
                size="text-sm"
                icon="fa-check"
                @click="openModal('boarding')"
              />

              <Button
                green
                label="Realizar Voo"
                size="text-sm"
                icon="fa-plane-departure"
                @click="openModal('completion')"
              />

              <Button 
              red 
              label="Cancelar Voo" 
              size="text-sm" 
              icon="fa-times"
              @click="openModal('cancellation')"
               />
            </div>
          </template>

          <template #table-actions>
            <Input
              title="Buscar voos"
              type="text"
              placeholder="Digite para filtrar..."
              search
              v-model="searchTerm"
            />
            <Button
                blue
                label="Cadastrar Voo"
                size="text-sm"
                icon="fa-plus"
                @click="$router.push('/employee/flight-registration')"
              />
          </template>
        </Table>
      </main>
    </div>
    <ModalBoardingConfirmation v-if="modalType === 'boarding'" @close="closeModal" />
    <ModalFlightCancellation v-if="modalType === 'cancellation'" @close="closeModal" />
    <ModalFlightCompletion v-if="modalType === 'completion'" @close="closeModal" />
  </div>
</template>

<script>
import Table from "../../components/general/Table.vue";
import Button from "../../components/general/Button.vue";
import Header from "../../components/general/Header.vue";
import Input from "../../components/general/Input.vue";
import ModalBoardingConfirmation from "../../components/ModalBoardingConfirmation.vue";
import ModalFlightCancellation from "../../components/ModalFlightCancellation.vue";
import ModalFlightCompletion from "../../components/ModalFlightCompletion.vue";

export default {
  components: {
    Header,
    Table,
    Button,
    Input,
    ModalBoardingConfirmation,
    ModalFlightCancellation,
    ModalFlightCompletion,
  },
  data() {
    return {
      modalType: null,
      voos: [
        {
          dataHora: "2023-12-15T14:30:00",
          origem: "GRU - Guarulhos",
          destino: "GIG - Galeão",
        },
        {
          dataHora: "2023-12-20T08:15:00",
          origem: "CGH - Congonhas",
          destino: "BSB - Brasília",
        },
      ],
      tableColumns: [
        { key: "dataHora", label: "Data/Hora", formatter: this.formatDateTime },
        { key: "origem", label: "Origem" },
        { key: "destino", label: "Destino" },
        { key: "actions", label: "Ações", class: "text-right" },
      ],
    };
  },
  methods: {
    openModal(type) {            
      this.modalType = type;
    },
    closeModal() {               
      this.modalType = null;
    },
    formatDateTime(dateTime) {
      const options = {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
        hour: "2-digit",
        minute: "2-digit",
      };
      return new Date(dateTime).toLocaleString("pt-BR", options);
    },
    logout() {
      console.log("Logout realizado");
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
header {
  background-size: 150%;
  animation: gradientShift 8s ease infinite alternate;
}

@keyframes gradientShift {
  0% {
    background-position: 0% 50%;
  }
  100% {
    background-position: 100% 50%;
  }
}

tbody tr:hover {
  background-color: #f8fafc;
}
</style>
