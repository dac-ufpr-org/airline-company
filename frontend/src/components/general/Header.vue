<template>
    <header class="text-white p-4 shadow-lg" style="background-color: #0a205d;">
      <div class="items-center w-full flex justify-between">
        <div class="flex items-center space-x-3">
          <slot name="toggle-sidebar">
          </slot>
          <img 
            src="@/assets/logo.png" 
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
  
  export default {
    props: {
      pageTitle: {
        type: String,
        default: 'Dashboard do Cliente'
      }
    },
    emits: ['logout']
  }
  </script>