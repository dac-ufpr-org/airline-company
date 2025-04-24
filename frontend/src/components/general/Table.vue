<template>
  <main class="flex w-full h-full">
    <!-- Condicionalmente exibe a barra lateral de navegação -->
    <TableSidebar 
      v-if="activeSidebar"
      :active-tab="activeTab"
      @tab-change="handleTabChange"
    />

    <div :class="!activeSidebar ? 'ml-4' : ''" class="overflow-x-auto bg-white my-4 mr-4 w-full p-6 rounded-xl shadow-lg border border-blue-200">
      <!-- Espaço para ações de pesquisa -->
      <div class="flex gap-4"></div>

      <!-- Slot para ações personalizadas na tabela -->
      <slot name="table-actions"></slot>

      <!-- Contêiner da tabela -->
      <div>
        <!-- Tabela de dados -->
        <table class="min-w-full divide-y divide-gray-200">
          <!-- Cabeçalho da tabela -->
          <thead class="bg-gray-50">
            <tr>
              <!-- Loop sobre as colunas para renderizar os cabeçalhos -->
              <th v-for="column in columns" :key="column.key" 
                  class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                {{ column.label }}
              </th>
            </tr>
          </thead>

          <!-- Corpo da tabela -->
          <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="(item, index) in filteredItems" :key="index">
              <!-- Loop sobre as colunas para renderizar as células -->
              <td v-for="column in columns" :key="column.key" class="px-6 py-4 whitespace-nowrap">
                <slot :name="`cell-${column.key}`" :item="item">
                  <!-- Formatação dos dados das células com base no tipo de coluna -->
                  {{ column.formatter ? column.formatter(item[column.key]) : item[column.key] }}
                </slot>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Mensagem caso não haja itens filtrados -->
      <div v-if="filteredItems.length === 0" class="text-center py-8 text-gray-500">
        Nenhum registro encontrado.
      </div>
    </div>
  </main>
</template>

<script>
export default {
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
    // Computa os itens filtrados com base no termo de pesquisa
    filteredItems() {
      // Verifica se existem itens
      if (this.items.length) {
        // Se não houver termo de pesquisa, retorna todos os itens
        if (!this.searchTerm) return this.items

        const term = this.searchTerm.toLowerCase()

        // Filtra os itens com base nas colunas e no termo de pesquisa
        return this.items.filter(item => 
          this.columns.some(column => {
            const value = column.formatter 
              ? column.formatter(item[column.key]) 
              : item[column.key]
            // Verifica se o valor da célula contém o termo de pesquisa
            return String(value).toLowerCase().includes(term)
          }) 
        )
      }
      return []
    },

    // Condicionalmente adiciona a coluna de "Ações" dependendo da aba ativa
    columns() {
      const columns = [
        { key: 'dataHora', label: 'Data/Hora', formatter: (val) => this.$filters.formatDateTime(val) ?? '-' },
        { key: 'origem', label: 'Origem' },
        { key: 'destino', label: 'Destino' },
        { key: 'milhas', label: 'Milhas', formatter: (val) => `${val} milhas` ?? '-' },
        { key: 'status', label: 'Status' },
      ]

      // Adiciona a coluna "Ações" apenas se a aba ativa for "minhas reservas"
      if (this.activeTab === 'reservas') {
        columns.push({ key: 'actions', label: 'Ações', class: 'text-right' })
      }

      return columns;
    }
  },
  methods: {
    // Método para gerenciar a troca de abas
    handleTabChange(newTab){
      this.activeTab = newTab
      // Emite o evento de troca de aba
      this.$emit('tab-change', newTab)
    }
  }
}
</script>
