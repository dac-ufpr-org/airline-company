<template>
  <div>
    <div class="container mx-auto flex mt-6 p-4">
      <main class="flex-1 bg-white p-6 rounded-xl shadow-lg border border-blue-200">
        <FlightsTable 
          :items="voos" 
          :columns="tableColumns"
        >
          <template #cell-actions>
            <div class="flex items-center space-x-3">   
              <Button 
                blue
                label="Confirmar Embarque" 
                size="text-sm"
                icon="fa-check"
              />
              <Button 
                red
                label="Cancelar Voo" 
                size="text-sm"
                icon="fa-times"
              />
              <Button 
                green
                label="Realizar Voo" 
                size="text-sm"
                icon="fa-plane-departure"
              />
            </div>
          </template>
        </FlightsTable>
      </main>
    </div>
  </div>
  </template>
  
  <script>
  
  import FlightsTable from '../../components/FlightsTable.vue'
  import Button from '../../components/Button.vue'
  import HeaderComponent from '../../components/HeaderComponent.vue'
  
  export default {
    components: {
      HeaderComponent,
      FlightsTable,
      Button
    },
    data() {
      return {
        voos: [
          { dataHora: '2023-12-15T14:30:00', origem: 'GRU - Guarulhos', destino: 'GIG - Galeão' },
          { dataHora: '2023-12-20T08:15:00', origem: 'CGH - Congonhas', destino: 'BSB - Brasília' },
        ],
        tableColumns: [
          { key: 'dataHora', label: 'Data/Hora', formatter: this.formatDateTime },
          { key: 'origem', label: 'Origem' },
          { key: 'destino', label: 'Destino' },
          { key: 'actions', label: 'Ações', class: 'text-right' }
        ]
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
  