<template>
    <div class="bg-white p-4 rounded-xl shadow-lg border border-blue-200 m-4">
      <div class="grid grid-cols-1 md:grid-cols-3 gap-4 items-end"> <!-- items-end para alinhar pela base -->
        <div class="flex flex-col">
          <label class="text-sm font-medium text-gray-700 mb-1">Aeroporto Origem</label>
          <div class="relative">
            <input 
              v-model="form.origem"
              class="w-full p-2 border border-gray-300 rounded-md h-10 pl-10"
              placeholder="Digite a origem"
            >
            <i class="fas fa-plane-departure absolute left-3 top-3 text-gray-400"></i>
          </div>
        </div>
  
        <div class="flex flex-col">
          <label class="text-sm font-medium text-gray-700 mb-1">Aeroporto Destino</label>
          <div class="relative">
            <input 
              v-model="form.destino"
              class="w-full p-2 border border-gray-300 rounded-md h-10 pl-10"
              placeholder="Digite o destino"
            >
            <i class="fas fa-plane-arrival absolute left-3 top-3 text-gray-400"></i>
          </div>
        </div>
  
        <div class="flex flex-col">
          <label class="invisible text-sm font-medium mb-1">Buscar</label> <!-- Espaçador invisível -->
          <button
            @click="$emit('search', form)"
            class="bg-blue-600 text-white py-2 px-4 rounded-md h-10 hover:bg-blue-700 transition-colors flex items-center justify-center"
          >
            <i class="fas fa-search mr-2"></i>
            Buscar Voos
          </button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        form: {
          origem: '',
          destino: ''
        }
      }
    }
  }
  </script>