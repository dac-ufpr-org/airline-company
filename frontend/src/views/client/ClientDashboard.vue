<template>
  <main>
    <Table 
      activeSidebar
      :items="currentItems" 
      :columns="tableColumns"
      :initial-search-term="searchTerm"
      @tab-change="updateTab"
    >
      <template #cell-status="{ item }">
        <ColorBadge :flightStatus="item.status" />
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
            v-if="item.status === 'Reservado' || item.status === 'Check-in'"
            lightBlue
            label="Ver Detalhes" 
            @click="viewReservation(item)" 
            size="text-sm"
            class="mr-2"
            icon="fa-eye"
          />
          <Button 
            v-if="item.status === 'Reservado' || item.status === 'Check-in'"
            lightRed
            label="Cancelar" 
            @click="openCancelModal(item)" 
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

    <Modal v-if="mostrarModal" title="Detalhes da Reserva" @close="mostrarModal = false">
      <template #content>
        <div class="flex" v-for="(item, index) in modalInfo" :key="index">
          <p class="font-bold mr-2">{{ `${item.label}: ` }}</p>
          {{ reservaSelecionada[item.key] }}
        </div>
      </template>
    </Modal>

    <ModalCancelReservation
      v-if="showCancelModal"
      :reservation="reservaParaCancelar"
      @close="showCancelModal = false"
      @confirm="processCancelReservation"
    />

  </main>
</template>

<script>
import Header from '../../components/general/Header.vue'
import ColorBadge from '../../components/general/ColorBadge.vue'
import Table from '../../components/general/Table.vue'
import Button from '../../components/general/Button.vue'
import Modal from '../../components/general/Modal.vue'
import Input from '../../components/general/Input.vue'
import ModalCancelReservation from '../../components/ModalCancelReservation.vue'

export default {
  components: {
    Header,
    ColorBadge,
    Table,
    Button,
    Modal,
    Input,
    ModalCancelReservation,
  },
  data() {
    return {
      modalInfo: [
        { label: 'Origem', key: 'origem' },
        { label: 'Destino', key: 'destino' },
        { label: 'Data/Hora', key: 'dataHora', filter: 'formatDateTime' },
        { label: 'Milhas', key: 'milhas' },
        { label: 'Status', key: 'status' }
      ],
      milhas: 3250,
      searchTerm: '',
      activeTab: 'reservas',
      mostrarModal: false,
      showCancelModal: false,
      reservaSelecionada: null,
      reservaParaCancelar: null,
      reservas: [
        {
          id: 'ABC123',
          dataHora: '2023-12-15T14:30:00',
          origem: 'GRU - Guarulhos',
          destino: 'GIG - Galeão',
          status: 'Reservado',
          milhas: 1200,
          valor: 600.00
        },
        {
          id: 'DEF456',
          dataHora: '2023-12-20T08:15:00',
          origem: 'CGH - Congonhas',
          destino: 'BSB - Brasília',
          status: 'Reservado',
          milhas: 850,
          valor: 425.00
        }
      ],
      voosRealizados: [
        {
          id: 'GHI789',
          dataHora: '2023-11-10T10:45:00',
          origem: 'GRU - Guarulhos',
          destino: 'SSA - Salvador',
          status: 'Realizado',
          milhas: 1500,
          valor: 750.00
        }
      ],
      voosCancelados: [
        {
          id: 'JKL012',
          dataHora: '2023-10-05T16:20:00',
          origem: 'CGH - Congonhas',
          destino: 'POA - Porto Alegre',
          status: 'Cancelado',
          milhas: 0,
          valor: 0.00
        }
      ],
      tableColumns: [
        { key: 'dataHora', label: 'Data/Hora', formatter: (val) => this.$filters.formatDateTime(val) ?? '-' },
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
        case 'reservas': return this.reservas.filter(i => i.status === 'Reservado' || i.status === 'Check-in')
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
    openCancelModal(reservation) {
      this.reservaParaCancelar = reservation
      this.showCancelModal = true
    },
    updateTab(newTab) {
      this.activeTab = newTab
    },
    async processCancelReservation(reservation) {
      try {
        // Simular chamada API
        await new Promise(resolve => setTimeout(resolve, 1000))
        
        // Atualizar status da reserva
        const index = this.reservas.findIndex(r => r.id === reservation.id)
        if (index !== -1) {
          this.reservas[index].status = 'Cancelado'
          
          // Devolver milhas
          this.milhas += reservation.milhas
          
          // Mover para lista de cancelados
          this.voosCancelados.unshift({
            ...this.reservas[index],
            dataHoraCancelamento: new Date().toISOString()
          })
          
          // Remover da lista de reservas
          this.reservas.splice(index, 1)
          
          // Registrar no extrato (simulado)
          this.registrarExtrato({
            data: new Date().toISOString(),
            codigoReserva: reservation.id,
            valor: 0,
            milhas: reservation.milhas,
            descricao: `${reservation.origem.split(' - ')[0]}->${reservation.destino.split(' - ')[0]}`,
            tipo: 'ENTRADA',
            motivo: 'CANCELAMENTO'
          })
          
          this.$toast.success('Reserva cancelada com sucesso! Milhas devolvidas.')
        }
        
        this.showCancelModal = false
      } catch (error) {
        this.$toast.error('Ocorreu um erro ao cancelar a reserva.')
        console.error('Erro ao cancelar reserva:', error)
      }
    },
    registrarExtrato(transacao) {
      // Aqui você faria a chamada API para registrar no extrato
      console.log('Registrando no extrato:', transacao)
    },
    rateFlight(flight) {
      console.log('Avaliar voo:', flight.id)
    }
  }
}
</script>