<template>
  <div class="overlay" @click.self="$emit('close')">
    <div class="modal">
      <h2 class="text-lg font-semibold mb-4">Detalhes da Reserva</h2>
      <p><strong>Origem:</strong> {{ viagem.origem }}</p>
      <p><strong>Destino:</strong> {{ viagem.destino }}</p>
      <p><strong>Data/Hora:</strong> {{ formatDateTime(viagem.dataHora) }}</p>
      <p><strong>Milhas:</strong> {{ viagem.milhas }}</p>
      <p><strong>Status:</strong> {{ viagem.status }}</p>

      <button @click="$emit('close')" class="mt-4 px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded">
        Fechar
      </button>
    </div>
  </div>
</template>

<script setup>
defineProps({
  viagem: Object
})

function formatDateTime(dateTime) {
  const options = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  }
  return new Date(dateTime).toLocaleString('pt-BR', options)
}
</script>

<style scoped>
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 50;
}
.modal {
  background: white;
  padding: 24px;
  border-radius: 10px;
  width: 100%;
  max-width: 400px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.2);
}
</style>
