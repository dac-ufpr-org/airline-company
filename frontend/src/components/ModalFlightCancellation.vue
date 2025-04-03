<template>
    <div class="overlay" @click.self="$emit('close')">
      <div class="modal">
        <h2 class="text-lg font-semibold mb-4">Flight Cancellation</h2>
        <p>Você tem certeza que deseja cancelar este voo?</p>
        <p>Todas as reservas vinculadas serão canceladas.</p>
        <button class="mt-4 px-4 py-2 bg-red-600 hover:bg-red-700 text-white rounded w-full">
          Confirmar Cancelamento
        </button>
        <button @click="$emit('close')" class="mt-2 px-4 py-2 bg-gray-500 hover:bg-gray-600 text-white rounded w-full">
          Fechar
        </button>
      </div>
    </div>
  </template>
  
  <script setup>
  defineEmits(['close'])
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
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  }
  </style>
  