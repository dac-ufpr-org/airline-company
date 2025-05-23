<template>
  <main>
    <Title label="Reservar Novo Voo" />
    
    <SearchForm @search="handleSearch" :items="formItems" />

    <LoadingMessage v-if="loading" label="Buscando voos disponíveis..." />
    
    <div v-else>
      <div v-if="voos.length === 0 && searchPerformed" class="text-center py-8 text-gray-500">
        <p>Nenhum voo encontrado. Tente alterar seus critérios de busca.</p>
      </div>
      
      <div v-else-if="voos.length > 0">
        <Card 
          v-for="voo in voos" 
          :key="voo.id">
          <template #default>
            <div class="flex justify-between items-start">
              <div>
                <h3 class="font-bold text-blue-800">{{ voo.origem }} → {{ voo.destino }}</h3>
                <p class="text-gray-600">{{ $filters.formatDateTime(voo.dataHora) }}</p>
              </div>
              <span class="font-bold text-lg">{{ $filters.formatMoney(voo.preco) }}</span>
            </div>
            <div class="mt-4 flex justify-end">
              <Button 
                class="max-w-[90px]"
                blue
                label="Selecionar"
                @click="goToReservationDetails(voo)"
                size="text-sm"
              />
            </div>
          </template>
        </Card>
      </div>

      <div v-else class="text-center py-8 text-gray-500">
        <p>Informe os critérios de busca para encontrar voos disponíveis.</p>
      </div>
    </div>

    <LoadingDots v-if="loading" />
  </main>
  </template>
  
  <script>
  
  export default {
    data() {
      return {
        loading: false,
        voos: [],
        searchPerformed: false,
        formItems: [
          { key: 'origem', label: 'Aeroporto Origem', icon: 'fa-plane-departure' },
          { key: 'destino', label: 'Aeroporto Destino', icon: 'fa-plane-arrival' },
        ]
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