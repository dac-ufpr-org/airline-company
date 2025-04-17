<template>
    <div class="container mx-auto max-w-md text-center mt-4">
      <Card>
        <template #default>
          <i class="fa-solid fa-check-circle text-green-500 text-6xl mb-4" />
          <Title label="Reserva Confirmada!" />
          <p class="text-gray-600 mb-6">Sua reserva foi efetuada com sucesso.</p>
          
          <div class="bg-blue-50 p-4 rounded-lg mb-6">
            <p class="font-mono text-2xl font-bold tracking-wider">{{ $route.query.codigo }}</p>
            <p class="text-sm text-gray-600 mt-2">Código da Reserva</p>
          </div>
          
          <Button 
            blue
            label="Voltar ao Dashboard"
            @click="goToDashboard"
            class="w-full"
            icon="fa-arrow-left"
          />
        </template>
      </Card>
    </div>
</template>

<script>

export default {
  methods: {
    goToDashboard() {
      this.$router.push('/client/dashboard');
    }
  }
}
</script>
