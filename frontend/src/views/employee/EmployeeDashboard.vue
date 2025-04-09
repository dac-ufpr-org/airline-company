<template>
  <main>
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
        <div class="grid grid-cols-4 flex items-end gap-3">
          <Input
            class="col-span-3"
            title="Buscar voos"
            type="text"
            placeholder="Digite para filtrar..."
            search
            v-model="searchTerm"
          />
          <Button
            class="mb-3"
            blue
            label="Cadastrar Voo"
            size="text-sm"
            icon="fa-plus"
            @click="$router.push('/employee/flight-registration')"
          />
        </div>
      </template>
    </Table>
    <Modal v-if="modalType === 'boarding'" @close="closeModal">
      <template #content>
        <h2 class="text-lg font-semibold mb-4">Boarding Confirmation</h2>
        <p>Digite o código da reserva para confirmar o embarque do cliente.</p>
        <input
          type="text"
          placeholder="Código da reserva"
          class="border px-4 py-2 w-full rounded mt-2"
        />
          <Button label="Confirmar" @click="closeModal" class="my-3" blue />
      </template>
    </Modal>
    <Modal v-if="modalType === 'cancellation'" @close="closeModal">
      <template #content>
        <h2 class="text-lg font-semibold mb-4">Flight Cancellation</h2>
        <p>Você tem certeza que deseja cancelar este voo?</p>
        <p class="font-semibold">Todas as reservas vinculadas serão canceladas.</p>
        <Button label="Confirmar" @click="closeModal" class="my-3" blue  />
      </template>
    </Modal>
    <Modal v-if="modalType === 'completion'" @close="closeModal">
      <template #content>
        <h2 class="text-lg font-semibold mb-4">Flight Completion</h2>
        <p>Confirme que este voo foi realizado com sucesso.</p>
        <p class="font-semibold">As reservas embarcadas serão atualizadas automaticamente.</p>
        <Button label="Confirmar Realização" @click="closeModal" blue class="my-3" />
      </template>
    </Modal>
  </main>
</template>

<script>
import Table from "../../components/general/Table.vue";
import Button from "../../components/general/Button.vue";
import Header from "../../components/general/Header.vue";
import Input from "../../components/general/Input.vue";
import Modal from "../../components/general/Modal.vue";

export default {
  components: {
    Header,
    Table,
    Button,
    Input,
    Modal
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
        { key: "dataHora", label: "Data/Hora", formatter: (val) => this.$filters.formatDateTime(val) ?? '-' },
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
  },
};
</script>

