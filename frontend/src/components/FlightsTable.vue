<template>
    <div>
      <div class="mb-6">
        <Input 
          title="Buscar voos" 
          type="text" 
          placeholder="Digite para filtrar..." 
          search 
          v-model="searchTerm"
        />
      </div>
  
      <div class="overflow-x-auto">
        <table class="min-w-full divide-y divide-gray-200">
          <thead class="bg-gray-50">
            <tr>
              <th v-for="column in columns" :key="column.key" 
                  class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                {{ column.label }}
              </th>
            </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="(item, index) in filteredItems" :key="index">
              <td v-for="column in columns" :key="column.key" class="px-6 py-4 whitespace-nowrap">
                <slot :name="`cell-${column.key}`" :item="item">
                  {{ column.formatter ? column.formatter(item[column.key]) : item[column.key] }}
                </slot>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
  
      <div v-if="filteredItems.length === 0" class="text-center py-8 text-gray-500">
        Nenhum registro encontrado.
      </div>
    </div>
  </template>
  
  <script>
  import Input from './Input.vue'
  
  export default {
    components: { Input },
    props: {
      items: {
        type: Array,
        required: true
      },
      columns: {
        type: Array,
        required: true
      },
      initialSearchTerm: {
        type: String,
        default: ''
      }
    },
    data() {
      return {
        searchTerm: this.initialSearchTerm
      }
    },
    computed: {
      filteredItems() {
        if (!this.searchTerm) return this.items
        
        const term = this.searchTerm.toLowerCase()
        return this.items.filter(item => 
          this.columns.some(column => {
            const value = column.formatter 
              ? column.formatter(item[column.key]) 
              : item[column.key]
            return String(value).toLowerCase().includes(term)
          }) // Faltava este parêntese para fechar o some()
        ) // Faltava este parêntese para fechar o filter()
      }
    }
  }
  </script>