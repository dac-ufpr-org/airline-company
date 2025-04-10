<template>
    <Modal :title="title" @close="$emit('close')">
      <template #content>
        <div class="space-y-4">
          <div v-for="(item, index) in reservationDetails" :key="index" class="flex items-baseline">
            <p class="font-bold mr-2 w-32 text-gray-700">{{ item.label }}:</p>
            <span class="text-gray-900">{{ formattedValue(item) }}</span>
          </div>
          
          <div class="mt-6 pt-4 border-t border-gray-200">
            <p class="text-sm text-gray-600 mb-6">
              Ao cancelar esta reserva, <span class="font-bold text-gray-900">{{ reservation.milhas }} milhas</span> serão devolvidas à sua conta.
            </p>
            
            <div class="flex justify-center space-x-3">
              <Button 
                lightBlue
                label="Voltar" 
                @click="$emit('close')" 
                size="text-sm"
                class="px-4 py-2"
              />
              <Button 
                lightRed
                label="Confirmar Cancelamento" 
                @click="confirmCancel" 
                size="text-sm"
                icon="fa-times"
                class="px-4 py-2"
              />
            </div>
          </div>
        </div>
      </template>
    </Modal>
  </template>
  
  <script>
  
  export default {
    props: {
      reservation: {
        type: Object,
        required: true
      },
      title: {
        type: String,
        default: 'Cancelar Reserva'
      }
    },
    data() {
      return {
        reservationDetails: [
          { label: 'Código', key: 'id' },
          { label: 'Origem', key: 'origem' },
          { label: 'Destino', key: 'destino' },
          { label: 'Data/Hora', key: 'dataHora', formatter: (val) => this.$filters.formatDateTime(val) },
          { label: 'Milhas utilizadas', key: 'milhas', formatter: (val) => `${val} milhas` },
          { label: 'Status', key: 'status' }
        ]
      }
    },
    methods: {
      formattedValue(item) {
        if (item.formatter) {
          return item.formatter(this.reservation[item.key])
        }
        return this.reservation[item.key]
      },
      confirmCancel() {
        this.$emit('confirm', this.reservation)
      }
    }
  }
  </script>
  
  <style scoped>
  .flex > .font-bold {
    min-width: 120px;
  }
  </style>