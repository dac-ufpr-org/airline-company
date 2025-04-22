<template>
  <aside class="my-4 ml-4 w-64 bg-white p-4 rounded-xl shadow-lg border border-blue-200 flex flex-col h-[calc(100vh-8rem)]">
    <!-- Conteúdo principal do menu -->
    <nav class="flex-1 overflow-y-auto">
      <ul class="space-y-3">
        <li v-for="item in menuItems" :key="item.id">
          <Button 
            :lightBlue="activeTab === item.id"
            :light="activeTab !== item.id"
            :label="item.label"
            @click="$emit('tab-change', item.id)"
            :icon="item.icon"
            class="w-full text-left transition-all"
            :class="{'shadow-md': activeTab === item.id}"
          />
        </li>
      </ul>
    </nav>

    <!-- Área fixa inferior -->
    <div class="mt-auto pt-2 sticky bottom-0 bg-white">
      <Button 
        green
        label="Efetuar Reserva"
        @click="handleNewReservation"
        icon="fa-plus-circle"
        class="w-full transition-all"
      />
    </div>
  </aside>
</template>

<script>
import { useRouter } from 'vue-router'

export default {
  props: {
    activeTab: {
      type: String,
      default: 'reservas'
    },
    menuItems: {
      type: Array,
      default: () => [
        { id: 'reservas', label: 'Minhas Reservas', icon: 'fa-calendar-check' },
        { id: 'voos', label: 'Voos Realizados', icon: 'fa-plane' },
        { id: 'cancelados', label: 'Voos Cancelados', icon: 'fa-ban' },
        { id: 'checkin', label: 'Fazer Check-in', icon: 'fa-clock' }
      ]
    }
  },
  emits: ['tab-change'],
  setup() {
    const router = useRouter()

    const handleNewReservation = () => {
      router.push('/client/reservas/nova')
    }

    return { handleNewReservation }
  }
}
</script>

<style scoped>
/* Garante que o sidebar não ultrapasse a altura da tela */
aside {
  max-height: calc(100vh - 8rem);
}
</style>