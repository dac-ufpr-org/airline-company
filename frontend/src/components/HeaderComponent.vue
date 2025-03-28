<template>
    <header class="text-white p-4 shadow-lg" style="background-color: #0a205d;">
      <div class="container mx-auto flex justify-between items-center">
        <div class="flex items-center space-x-3">
          <img 
            src="@/assets/airplaneIcon.png" 
            alt="Ícone Avião"
            class="w-8 h-8 object-contain"
          >
          <h1 class="text-2xl font-bold">{{ pageTitle }}</h1>
        </div>
        
        <div class="flex items-center gap-x-3">
          <slot id="default"></slot>
        </div>
      </div>
    </header>
  </template>
  
  <script>
  import Button from './Button.vue'
  
  export default {
    components: { Button },
    props: {
      pageTitle: {
        type: String,
        default: 'Dashboard do Cliente'
      }
    },
    emits: ['logout']
  }
  </script>