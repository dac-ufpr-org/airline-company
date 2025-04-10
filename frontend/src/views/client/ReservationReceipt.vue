<template>
  <div class="min-h-screen bg-gradient-to-br from-blue-50 to-blue-100 p-6">
    <div class="container mx-auto max-w-md text-center">
      <div class="bg-white p-8 rounded-xl shadow-lg border border-blue-200">
        <div class="text-green-500 text-6xl mb-4">
          <i class="fa-solid fa-check-circle"></i>
        </div>
        <h1 class="text-2xl font-bold text-blue-800 mb-2">Reserva Confirmada!</h1>
        <p class="text-gray-600 mb-6">Sua reserva foi efetuada com sucesso.</p>
        
        <div class="bg-blue-50 p-4 rounded-lg mb-6">
          <p class="font-mono text-2xl font-bold tracking-wider">{{ $route.query.codigo }}</p>
          <p class="text-sm text-gray-600 mt-2">Código da Reserva</p>
        </div>
        
        <!-- Botão para voltar ao Dashboard -->
        <Button 
          blue
          label="Voltar ao Dashboard"
          @click="goToDashboard"
          class="w-full"
          icon="fa-arrow-left"
        />
      </div>
    </div>
  </div>
</template>

<script>

export default {
  methods: {
    goToDashboard() {
      // Redireciona para a rota do dashboard do cliente
      this.$router.push('/client/dashboard');
      
      // Alternativa: se você quiser forçar recarregar a página
      // window.location.href = '/client/dashboard';
    }
  }
}
</script>

<style scoped>
/* Estilos adicionais (opcional) */
.button-container {
  transition: all 0.3s ease;
}
.button-container:hover {
  transform: translateY(-2px);
}
</style>