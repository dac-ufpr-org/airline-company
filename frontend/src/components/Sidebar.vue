<template>
  <div :class="{ '-translate-x-full': !isOpen }" class="flex min-h-screen bg-[#081A4A] w-64 text-white p-4 transition-all duration-300">
    <aside class="w-full transition-transform duration-300 ease-in-out">
      <div class="flex justify-between items-center mb-8">
        <h2 class="text-lg font-bold">Menu</h2>
        <button @click="$emit('toggle-sidebar')">
          <i class="fa-solid fa-bars cursor-pointer"></i>
        </button>
      </div>

      <nav>
        <ul>
          <li v-for="item in menuItems" :key="item.text" class="mb-4 cursor-pointer">
            <a @click="this.$router.push(item.route)" class="block px-4 py-2 rounded-lg hover:bg-[#0a205d]">
              {{ item.text }}
            </a>
          </li>
        </ul>
      </nav>
    </aside>
  </div>
</template>

<script>
export default {
  props: {
    isOpen: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      menuItems: [
        { text: 'Dashboard', route: '/client/dashboard' },
        { text: 'Reservas', route: '/client/reservas/nova' },
      ]
    };
  }
}
</script>

