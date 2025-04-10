<template>
  <div class="flex min-h-screen bg-gradient-to-br from-blue-50 to-blue-100 overflow-hidden">
    <Sidebar :class="{ 'hidden': isLoginRegister }" :isOpen="isOpen" @toggle-sidebar="toggleSidebar" :items="menuItems" />

    <main 
      class="flex-1 transition-all duration-300 overflow-auto"
      :class="{ 'ml-[-260px]': !isOpen, 'ml-0': isOpen }"
    >
      <Header 
       :class="{ 'hidden': isLoginRegister }"
        class="sticky top-0 left-0 w-full bg-white shadow-md z-50" 
        :pageTitle="$route.name"
        @logout="$router.push('login')"
      >
        <template #toggle-sidebar>
          <button 
            v-if="!isOpen"
            class="p-3 text-white rounded-lg cursor-pointer"
            @click="toggleSidebar"
          >
            <i class="fa-solid fa-bars"></i>
          </button>
        </template>
        <template #default>
          <Button 
            v-if="group === 'client'"
            blue 
            label="3250 milhas" 
            @click="$router.push('/client/mileage-extract')" 
            icon="fa-star"
          />
          <Button 
            red 
            label="Sair" 
            @click="logout()" 
            icon="fa-sign-out-alt"
          />
        </template>
      </Header>
      <router-view></router-view>
    </main>
  </div>
  
</template>

<script>

export default {
  data: () => ({
    isOpen: true,
    group: null,
    menuItems: [],
    clientMenuItems: [
      { id: 1, text: 'Dashboard', route: '/client/dashboard' },
      { id: 2, text: 'Reservas', route: '/client/consultar-reservas' },
    ],
    employeeMenuItems: [
      { text: 'Dashboard', route: '/employee/dashboard' },
    ],
  }),
  methods: {
    toggleSidebar() {
      this.isOpen = !this.isOpen
    },
    logout() {
      this.$emit('logout')
      this.$router.push('/login')
    },
  },
  computed: {
    isLoginRegister() {
      return this.$route.path.includes('login') || this.$route.path.includes('register')
    }
  },
  mounted() {
    // lógica de ver de qual grupo o usuário pertence
    this.group = (window.location.pathname).includes('client') ? 'client' : 'employee';
    this.menuItems = this.group === 'client' ? this.clientMenuItems : this.employeeMenuItems
  }
}
</script>

