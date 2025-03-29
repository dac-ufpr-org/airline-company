<template>
  <div class="main flex flex-col min-h-screen bg-gradient-to-br from-blue-50 to-blue-100">
    <main class="flex-1">
      <HeaderComponent 
        class="sticky top-0 left-0 w-full bg-white shadow-md z-50" 
        :pageTitle="this.$route.name"
        @logout="this.$router.push('login')"
      >
        <template #default>
          <Button 
            v-if="this.group === 'client'"
            blue 
            label="3250 milhas" 
            @click="this.$router.push('/client/mileage-extract')" 
            icon="fa-star"
          />
          <Button 
            red 
            label="Sair" 
            @click="$emit('logout')" 
            icon="fa-sign-out-alt"
          />
        </template>
      </HeaderComponent>
      <router-view/>
    </main>
  </div>
</template>

<script>
import HeaderComponent from './components/HeaderComponent.vue'
import Button from './components/Button.vue'

export default {
  components: {
    HeaderComponent,
    Button
  },
  data: () => ({
    group: null
  }),
  mounted() {
    // logica de ver de qual grupo o usuário pertence
    // se for do grupo de cliente -> header x
    // se for do grupo de funcionario -> header y
    this.group = (window.location.pathname).includes('client') ? 'client' : 'employee'
  }
}
</script>


