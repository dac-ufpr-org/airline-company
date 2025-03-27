// ✅ ClientDashboard.vue
<template>
  <div class="min-h-screen bg-gradient-to-br from-blue-50 to-blue-100">
    <HeaderComponent 
      :milhas="milhas" 
      pageTitle="Dashboard do Cliente"
      @logout="logout"
    />

    <div class="container mx-auto flex mt-6 p-4">
      <SidebarMenu 
        :active-tab="activeTab" 
        @tab-change="activeTab = $event"
      />

      <main class="flex-1 bg-white p-6 rounded-xl shadow-lg border border-blue-200">
        <FlightsTable 
          :items="currentItems" 
          :columns="tableColumns"
          :initial-search-term="searchTerm"
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
        </FlightsTable>

        <!-- Modal com detalhes da reserva -->
        <ModalDetalhesViagem
          v-if="mostrarModal"
          :viagem="reservaSelecionada"
          @close="mostrarModal = false"
        />
      </main>
    </div>
  </div>
</template>

<script>
import HeaderComponent from '../components/HeaderComponent.vue'
import SidebarMenu from '../components/SidebarMenu.vue'
import FlightStatusBadge from '../components/FlightStatusBadge.vue'
import FlightsTable from '../components/FlightsTable.vue'
import Button from '../components/Button.vue'
import ModalDetalhesViagem from '../components/ModalDetalhesViagem.vue'

export default {
  components: {
    HeaderComponent,
    SidebarMenu,
    FlightStatusBadge,
    FlightsTable,
    Button,
    ModalDetalhesViagem
  },
  data() {
    return {
      milhas: 3250,
      activeTab: 'reservas',
      searchTerm: '',
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
        { key: 'dataHora', label: 'Data/Hora', formatter: this.formatDateTime },
        { key: 'origem', label: 'Origem' },
        { key: 'destino', label: 'Destino' },
        { key: 'milhas', label: 'Milhas', formatter: (val) => val > 0 ? `${val} milhas` : '-' },
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
    formatDateTime(dateTime) {
      const options = { 
        year: 'numeric', 
        month: '2-digit', 
        day: '2-digit',
        hour: '2-digit', 
        minute: '2-digit' 
      }
      return new Date(dateTime).toLocaleString('pt-BR', options)
    },
    viewReservation(reservation) {
      this.reservaSelecionada = reservation
      this.mostrarModal = true
    },
    cancelReservation(reservation) {
      if (confirm(`Deseja realmente cancelar a reserva ${reservation.id} para ${reservation.destino}?`)) {
        console.log('Cancelar reserva:', reservation.id)
      }
    },
    rateFlight(flight) {
      console.log('Avaliar voo:', flight.id)
    },
    logout() {
      console.log('Logout realizado')
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
header {
  background-size: 150%;
  animation: gradientShift 8s ease infinite alternate;
}

@keyframes gradientShift {
  0% { background-position: 0% 50%; }
  100% { background-position: 100% 50%; }
}

tbody tr:hover {
  background-color: #f8fafc;
}
</style>