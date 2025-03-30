<template>
  <main class="flex w-full">
    <ClientSidebarMenu 
      v-if="activeSidebar"
      :active-tab="activeTab"
      @tab-change="handleTabChange"
    />
    <div :class="!activeSidebar ? 'ml-4' : ''" class="overflow-x-auto bg-white my-4 mr-4 w-full p-6 rounded-xl shadow-lg border border-blue-200">
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
  </main>
  </template>
  
  <script>
  import Input from './Input.vue'
  import ClientSidebarMenu from './ClientSidebarMenu.vue'
  
  export default {
    components: { Input, ClientSidebarMenu },
    props: {
      items: {
        type: Array,
        default: [],
        required: true
      },
      columns: {
        type: Array,
        required: true
      },
      initialSearchTerm: {
        type: String,
        default: ''
      },
      activeSidebar: {
        type: Boolean,
        default: false
      },
    },
    data() {
      return {
        searchTerm: this.initialSearchTerm,
        activeTab: 'reservas'
      }
    },
    computed: {
      filteredItems() {
        if (this.items.length) {
          if (!this.searchTerm) return this.items
          
          const term = this.searchTerm.toLowerCase()
          return this.items.filter(item => 
            this.columns.some(column => {
              const value = column.formatter 
                ? column.formatter(item[column.key]) 
                : item[column.key]
              return String(value).toLowerCase().includes(term)
            }) 
          )
        }
        return []
      }
    },
    methods: {
      handleTabChange(newTab){
        this.activeTab = newTab
        this.$emit('tab-change', newTab)
      }
    }
  }
  </script>