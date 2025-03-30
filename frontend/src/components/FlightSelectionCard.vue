<template>
    <div class="bg-white p-4 rounded-lg shadow-md border border-blue-100 hover:border-blue-300 transition-colors">
      <div class="flex justify-between items-start">
        <div>
          <h3 class="font-bold text-blue-800">{{ voo.origem }} → {{ voo.destino }}</h3>
          <p class="text-gray-600">{{ formatDateTime(voo.dataHora) }}</p>
        </div>
        <span class="font-bold text-lg">{{ formatCurrency(voo.preco) }}</span>
      </div>
      <div class="mt-4 flex justify-end">
        <Button 
          blue
          label="Selecionar"
          @click="$emit('select', voo)"
          size="text-sm"
        />
      </div>
    </div>
  </template>
  
  <script>
  import Button from './Button.vue'
  
  export default {
    components: { Button },
    props: {
      voo: {
        type: Object,
        required: true
      }
    },
    methods: {
      formatDateTime(date) {
        return new Date(date).toLocaleString('pt-BR')
      },
      formatCurrency(value) {
        return 'R$ ' + value.toFixed(2).replace('.', ',')
      }
    }
  }
  </script>