// ✅ ClientDashboard.vue
<template>
  <div>
    <main>
      <Table 
        activeSidebar
        :items="currentItems" 
        :columns="tableColumns"
        :initial-search-term="searchTerm"
        @tab-change="updateTab"
      >
        <template #cell-status="{ item }">
          <FlightStatusBadge :status="item.status" />
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
import HeaderComponent from '../../components/HeaderComponent.vue'
import FlightStatusBadge from '../../components/FlightStatusBadge.vue'
import Table from '../../components/Table.vue'
import Button from '../../components/Button.vue'
import ModalDetalhesViagem from '../../components/ModalDetalhesViagem.vue'

export default {
  components: {
    HeaderComponent,
    FlightStatusBadge,
    Table,
    Button,
    ModalDetalhesViagem
  },
  data() {
    return {
      milhas: 3250,
      searchTerm: '',
      activeTab: 'reservas',
      mostrarModal: false,
      reservaSelecionada: null,
      reservas: [
        {
          id: 1,
          dataHora: '2023-12-15T14:30:00',
          origem: 'GRU - Guarulhos',
          destino: 'GIG - Galeão',
          status: 'Reservado',
          milhas: 1200
        },
        {
          id: 2,
          dataHora: '2023-12-20T08:15:00',
          origem: 'CGH - Congonhas',
          destino: 'BSB - Brasília',
          status: 'Reservado',
          milhas: 850
        }
      ],
      voosRealizados: [
        {
          id: 3,
          dataHora: '2023-11-10T10:45:00',
          origem: 'GRU - Guarulhos',
          destino: 'SSA - Salvador',
          status: 'Realizado',
          milhas: 1500
        }
      ],
      voosCancelados: [
        {
          id: 4,
          dataHora: '2023-10-05T16:20:00',
          origem: 'CGH - Congonhas',
          destino: 'POA - Porto Alegre',
          status: 'Cancelado',
          milhas: 0
        }
      ],
      tableColumns: [
        { key: 'dataHora', label: 'Data/Hora', formatter: (val) => this.$filters.formatDateTime(val) ?? '-'  },
        { key: 'origem', label: 'Origem' },
        { key: 'destino', label: 'Destino' },
        { key: 'milhas', label: 'Milhas', formatter: (val) => `${val} milhas` ?? '-' },
        { key: 'status', label: 'Status' },
        { key: 'actions', label: 'Ações', class: 'text-right' }
      ]
    }
  },
  computed: {
    currentItems() {
      switch (this.activeTab) {
        case 'reservas': return this.reservas.filter(i => i.status === 'Reservado')
        case 'voos': return this.voosRealizados
        case 'cancelados': return this.voosCancelados
        default: return []
      }
    }
  },
  methods: {
    viewReservation(reservation) {
      this.reservaSelecionada = reservation
      this.mostrarModal = true
    },
    updateTab(newTab) {
      this.activeTab = newTab
    },
    cancelReservation(reservation) {
      if (confirm(`Deseja realmente cancelar a reserva ${reservation.id} para ${reservation.destino}?`)) {
        console.log('Cancelar reserva:', reservation.id)
      }
    },
    rateFlight(flight) {
      console.log('Avaliar voo:', flight.id)
    },
  }
}
</script>
