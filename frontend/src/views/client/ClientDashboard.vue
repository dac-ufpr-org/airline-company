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
          class="col-span-3"
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
            v-if="activeTab === 'checkin'"
            lightGreen
            label="Fazer Check-in"
            @click="openCheckinModal(item)"
            size="text-sm"
            icon="fa-check"
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

    <Modal v-if="showCancelModal" title="Cancelar Reserva" @close="showCancelModal = false">
      <template #content>
        <div class="space-y-4">
          <div v-for="(item, index) in reservationDetails" :key="index" class="flex items-baseline">
            <p class="font-bold mr-2 w-32 text-gray-700">{{ item.label }}:</p>
            <span class="text-gray-900">{{ formattedValue(item) }}</span>
          </div>
          
          <div class="mt-6 pt-4 border-t border-gray-200">
            <p class="text-sm text-gray-600 mb-6">
              Ao cancelar esta reserva, <span class="font-bold text-gray-900">{{ reservaParaCancelar.milhas }} milhas</span> serão devolvidas à sua conta.
            </p>
            
            <div class="flex justify-center space-x-3">
              <Button 
                lightBlue
                label="Voltar" 
                @click="showCancelModal = false" 
                size="text-sm"
                class="px-4 py-2"
              />
              <Button 
                lightRed
                label="Confirmar Cancelamento" 
                @click="processCancelReservation" 
                size="text-sm"
                icon="fa-times"
                class="px-4 py-2"
              />
            </div>
          </div>
        </div>
      </template>
    </Modal>

    <Modal v-if="mostrarModal" title="Detalhes da Reserva" @close="mostrarModal = false">
      <template #content>
        <div class="flex" v-for="(item, index) in modalInfo" :key="index">
          <p class="font-bold mr-2">{{ `${item.label}: ` }}</p>
          {{ reservaSelecionada[item.key] }}
        </div>
      </template>
    </Modal>

    <Modal
      v-if="showCheckinModal"
      title="Confirmar Check-in"
      @close="showCheckinModal = false"
    >
  <template #content>

      <div class="space-y-4">
          <p>
            Você confirma que está ciente da data e hora do voo
            <span class="font-bold text-gray-900">{{ reservaParaCheckin?.id }}</span>?
          </p>
        
      <div class="flex justify-center space-x-3 pt-4">
        <Button
          lightBlue
          label="Voltar"
          @click="showCheckinModal = false"
          size="text-sm"
        />

        <Button
          lightGreen
          label="Confirmar Check-in"
          icon="fa-check"
          @click="confirmarCheckin"
          size="text-sm"
        />

      </div>
    </div>

    </template>
</Modal>

  </main>
</template>

<script>

export default {
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
      reservationDetails: [
        { label: 'Código', key: 'id' },
        { label: 'Origem', key: 'origem' },
        { label: 'Destino', key: 'destino' },
        { label: 'Data/Hora', key: 'dataHora', formatter: (val) => this.$filters.formatDateTime(val) },
        { label: 'Milhas utilizadas', key: 'milhas', formatter: (val) => `${val} milhas` },
        { label: 'Status', key: 'status' }
      ],

      activeTab: 'reservas',
      mostrarModal: false,
      showCancelModal: false,
      reservaSelecionada: null,
      reservaParaCancelar: null,
      showCheckinModal: false,
      reservaParaCheckin: null,

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
          dataHora: '2025-04-24T08:15:00',
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
      const now = new Date()
      const in48Hours = new Date(now.getTime() + 48 * 60 * 60 * 1000)
      switch (this.activeTab) {
        case 'reservas':
          return this.reservas.filter(i => i.status === 'Reservado' || i.status === 'Check-in')
        case 'voos':
          return this.voosRealizados
        case 'cancelados':
          return this.voosCancelados
        case 'checkin':
          return this.reservas.filter(i => {
            const dataHora = new Date(i.dataHora) 
            return (
              i.status === 'Reservado' &&
              dataHora > now &&
              dataHora <= in48Hours
            )
          })
        default: return []
      }
    }
  },
  methods: {
    viewReservation(reservation) {
      this.reservaSelecionada = reservation
      this.mostrarModal = true
    },
    formattedValue(item) {
      if (item.formatter) {
        return item.formatter(this.reservaParaCancelar[item.key])
      }
      return this.reservaParaCancelar[item.key]
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
    },
    
    openCheckinModal(reserva) {
      this.reservaParaCheckin = reserva
      this.showCheckinModal = true
    },

    confirmarCheckin() {
      if (this.reservaParaCheckin) {
      this.showCheckinModal = false 

      const index = this.reservas.findIndex(r => r.id === this.reservaParaCheckin.id)
      if (index !== -1) {
      this.reservas[index].status = 'Check-in'
      }

      this.$toast.success(`Check-in realizado para o voo ${this.reservaParaCheckin.id}`)
      }
    } 

  }
}
</script>