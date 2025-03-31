<template>
  <main>
    <h1 class="text-2xl font-bold text-blue-800 m-4">Reservar Novo Voo</h1>
    
    <FlightSearchForm @search="handleSearch" />
    
    <div v-if="loading" class="text-center py-8">
      <p>Buscando voos disponíveis...</p>
    </div>
    
    <div v-else>
      <div v-if="voos.length === 0 && searchPerformed" class="text-center py-8 text-gray-500">
        <p>Nenhum voo encontrado. Tente alterar seus critérios de busca.</p>
      </div>
      
      <div v-else-if="voos.length > 0" class="grid grid-cols-1 gap-4">
        <FlightSelectionCard 
          v-for="voo in voos" 
          :key="voo.id" 
          :voo="voo"
          @select="goToReservationDetails"
        />
      </div>

      <div v-else class="text-center py-8 text-gray-500">
        <p>Informe os critérios de busca para encontrar voos disponíveis.</p>
      </div>
    </div>
  </main>
  </template>
  
  <script>
  import FlightSearchForm from '@/components/FlightSearchForm.vue'
  import FlightSelectionCard from '@/components/FlightSelectionCard.vue'
  
  export default {
    components: { FlightSearchForm, FlightSelectionCard },
    data() {
      return {
        loading: false,
        voos: [],
        searchPerformed: false
      }
    },
    methods: {
      async handleSearch({ origem, destino }) {
        this.loading = true
        this.searchPerformed = true
        try {
          // Simulação de chamada API com filtro real
          const allFlights = await this.mockSearchFlights()
          this.voos = this.filterFlights(allFlights, origem, destino)
        } finally {
          this.loading = false
        }
      },
      
      mockSearchFlights() {
        // Mock de dados completo
        return new Promise(resolve => {
          setTimeout(() => {
            resolve([
              {
                id: 1,
                origem: 'GRU - Guarulhos',
                destino: 'GIG - Galeão',
                dataHora: '2023-12-25T14:30:00',
                preco: 450.90,
                milhasNecessarias: 1200
              },
              {
                id: 2,
                origem: 'CGH - Congonhas',
                destino: 'BSB - Brasília',
                dataHora: '2023-12-26T08:15:00',
                preco: 320.50,
                milhasNecessarias: 850
              },
              {
                id: 3,
                origem: 'GRU - Guarulhos',
                destino: 'SSA - Salvador',
                dataHora: '2023-12-27T11:20:00',
                preco: 520.75,
                milhasNecessarias: 1400
              }
            ])
          }, 800)
        })
      },
  
      filterFlights(flights, origem, destino) {
        if (!origem && !destino) return flights
        
        return flights.filter(flight => {
          const matchesOrigem = origem 
            ? flight.origem.toLowerCase().includes(origem.toLowerCase())
            : true
          
          const matchesDestino = destino
            ? flight.destino.toLowerCase().includes(destino.toLowerCase())
            : true
          
          return matchesOrigem && matchesDestino
        })
      },
  
      goToReservationDetails(voo) {
        this.$router.push({
          path: '/client/reservas/confirmar',
          query: { vooId: voo.id }
        })
      }
    }
  }
  </script>