<template>
  <div>
    <div class="container mx-auto flex mt-6 p-4">
      <main
        class="flex-1 bg-white p-6 rounded-xl shadow-lg border border-blue-200"
      >
        <Table :items="voos" :columns="tableColumns">
          <template #cell-actions="{ item } ">
            <div class="flex items-center space-x-3">
              <Button
                blue
                label="Confirmar Embarque"
                size="text-sm"
                icon="fa-check"
                @click="openModal('boarding', item)"
              />

              <Button
                green
                label="Realizar Voo"
                size="text-sm"
                icon="fa-plane-departure"
                @click="openModal('completion', item)"
              />

              <Button 
              red 
              label="Cancelar Voo" 
              size="text-sm" 
              icon="fa-times"
              @click="openModal('cancellation', item)"
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

        <Modal v-if="mostrarModal" :title="modalTitle" @close="mostrarModal = false">
          <template #content>
            <p v-if="modalType === 'boarding'">
              Digite o código da reserva para confirmar o embarque do cliente.
            </p>
            <input
              v-if="modalType === 'boarding'"
              v-model="codigoReserva"
              type="text"
              placeholder="Código da reserva"
              class="border px-4 py-2 w-full rounded mt-2"
            />

            <p v-if="modalType === 'completion'">
              Confirme que este voo foi realizado com sucesso.
            </p>
            <p v-if="modalType === 'completion'">
              As reservas embarcadas serão atualizadas automaticamente.
            </p>

            <p v-if="modalType === 'cancellation'">
              Você tem certeza que deseja cancelar este voo?
            </p>
            <p v-if="modalType === 'cancellation'">
              Todas as reservas vinculadas serão canceladas.
            </p>
          </template>

          <template #footer>
            <Button
              v-if="modalType === 'boarding'"
              green
              @click="confirmBoarding"
              label="Confirmar"
            />
            
            <Button
              v-if="modalType === 'completion'"
              green
              @click="confirmCompletion"
              label="Confirmar"
            />
            
            <Button
              v-if="modalType === 'cancellation'"
              green
              @click="confirmCancellation"
              label="Cancelar Voo"
            />
            
            <Button red @click="mostrarModal = false" label="Fechar" />
          </template>
        </Modal>
        
      </main>
    </div>
  </div>
</template>

<script>
import Table from "../../components/general/Table.vue";
import Button from "../../components/general/Button.vue";
import Header from "../../components/general/Header.vue";
import Input from "../../components/general/Input.vue";
import Modal from '../../components/general/Modal.vue'

export default {
  components: {
    Header,
    Table,
    Button,
    Input,
    Modal,
  },
  data() {
    return {
      modalType: null,
      modalTitle: '',
      mostrarModal: false,
      codigoReserva: '',
      voos: [
        {
          id: 1,
          dataHora: "2023-12-15T14:30:00",
          origem: "GRU - Guarulhos",
          destino: "GIG - Galeão",
        },
        {
          id: 2,
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
      this.mostrarModal = true;
      this.modalTitle =
        type === 'boarding' ? 'Confirmação de Embarque' :
        type === 'completion' ? 'Realização de Voo' :
        type === 'cancellation' ? 'Cancelamento de Voo' : '';
    },
    closeModal() {               
      this.modalType = null;
      this.mostrarModal = false;
    },
    confirmBoarding() {
      if (!this.codigoReserva) {
        alert("Por favor, insira um código de reserva válido.");
        return;
      }
      console.log("Embarque confirmado para código de reserva:", this.codigoReserva);
      this.mostrarModal = false;
    },
    confirmCompletion() {
      console.log("Voo concluído com sucesso!");
      this.mostrarModal = false;
    },
    confirmCancellation() {
      console.log("Voo cancelado com sucesso!");
      this.mostrarModal = false;
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
