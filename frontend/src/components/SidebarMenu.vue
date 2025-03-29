<template>
    <aside class="my-4 ml-4 w-64 bg-white p-4 rounded-xl shadow-lg border border-blue-200">
      <nav>
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
    </aside>
  </template>
  
  <script>
  import Button from './Button.vue'
  
  export default {
    components: { Button },
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
          { id: 'cancelados', label: 'Voos Cancelados', icon: 'fa-ban' }
        ]
      }
    },
    emits: ['tab-change']
  }
  </script>