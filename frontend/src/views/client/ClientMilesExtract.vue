<template>
  <div>
    <main>
      <Table 
        :items="items" 
        :columns="tableColumns"
        :initial-search-term="searchTerm"
        @tab-change="updateTab"
      >
        <template #cell-type="{ item }">
          <ColorBadge :label='this.labelName[item.type]' :green="item.type === 'entry'" :red="item.type === 'exit'" />
        </template>

        <template #table-actions>
            <Input 
                title="Buscar voos" 
                type="text" 
                placeholder="Digite para filtrar..." 
                search 
                v-model="searchTerm"
            />
        </template>

        <template #cell-actions="{ item }">
          <div class="flex items-center space-x-3">   
            <Button 
              v-if="item.status === 'Reservado'"
              lightBlue
              label="Ver Detalhes" 
              @click="viewReservation(item)" 
              size="text-sm"
              class="mr-2"
              icon="fa-eye"
            />
            <Button 
              v-if="item.status === 'Reservado'"
              lightRed
              label="Cancelar" 
              @click="cancelReservation(item)" 
              size="text-sm"
              icon="fa-times"
            />
            <Button 
              v-if="item.status === 'Realizado'"
              lightGreen
              label="Avaliar" 
              @click="rateFlight(item)" 
              size="text-sm"
              class="ml-2"
              icon="fa-star"
            />
          </div>
        </template>
      </Table>

      <ModalDetalhesViagem
        v-if="mostrarModal"
        :viagem="reservaSelecionada"
        @close="mostrarModal = false"
      />
    </main>
  </div>
</template>

<script>
import Header from '../../components/general/Header.vue'
import ColorBadge from '../../components/ColorBadge.vue'
import Table from '../../components/general/Table.vue'
import Button from '../../components/general/Button.vue'
import Input from '../../components/general/Input.vue'

export default {
  components: {
    Header,
    ColorBadge,
    Table,
    Button,
    Input
  },
  data() {
    return {
      searchTerm: '',
      labelName: {
        'entry': 'ENTRADA',
        'exit': 'SAÍDA'
      },
      items: [
          {
            id: 1,
            performedDate: '2023-12-20T08:15:00',
            reservationCode: null,
            price: '15.70',
            quantity: 3,
            type: 'entry',
            description: 'COMPRA DE MILHAS',
          },
          {
            id: 2,
            performedDate: '2023-12-25T07:15:00',
            reservationCode: '122',
            price: '1500.00',
            quantity: 300,
            type: 'exit',
            description: 'CWB->GRU',
          },
      ],
      tableColumns: [
        { key: 'performedDate', label: 'Data', formatter: (val) => this.$filters.formatDateTime(val) ?? '-'  },
        { key: 'reservationCode', label: 'Código Reserva' },
        { key: 'price', label: 'Valor', formatter: (val) => this.$filters.formatMoney(val) ?? '-' },
        { key: 'quantity', label: 'Quantidade', formatter: (val) => `${val} milhas` ?? '-' },
        { key: 'description', label: 'Descrição' },
        { key: 'type', label: 'Tipo', formatter: (val) => this.labelName[val] ?? '-' }
      ]
    }
  },
  methods: {
   
  }
}
</script>
