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

    <Modal v-if="modalType === 'boarding'" title="Confirmação de Embarque" @close="closeModal">
      <template #content>
        <div class="mb-4">
          <p>Digite o código da reserva para confirmar o embarque do cliente.</p>
        </div>
        <input
          type="text"
          placeholder="Código da reserva"
          class="border px-4 py-2 w-full rounded mt-2"
        />
          <Button label="Confirmar" @click="closeModal" class="my-3" blue />
      </template>
    </Modal>
    
    <Modal v-if="modalType === 'cancellation'" title="Cancelamento de Voo" @close="closeModal">
      <template #content>
        <div class="mb-4">
          <p>Você tem certeza que deseja cancelar este voo?</p>
          <p class="font-semibold">Todas as reservas vinculadas serão canceladas.</p>
        </div>
        <Button label="Confirmar" @click="closeModal" class="my-3" blue  />
      </template>
    </Modal>

    <Modal v-if="modalType === 'completion'" title="Confirmação de Voo Realizado" @close="closeModal">
      <template #content>
        <div class="mb-4">
          <p>Confirme que este voo foi realizado com sucesso.</p>
          <p class="font-semibold">As reservas embarcadas serão atualizadas automaticamente.</p>
        </div>
        <Button label="Confirmar Realização" @click="closeModal" blue class="my-3" />
      </template>
    </Modal>
  </main>
</template>

<script>

export default {
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

