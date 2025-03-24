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
        
        <div class="flex items-center space-x-3"> <!-- Reduzi o space-x de 4 para 3 -->
          <div class="flex items-center bg-blue-600 px-3 py-1.5 rounded-lg shadow-inner"> <!-- Ajuste no padding -->
            <span class="font-semibold text-sm">{{ milhas }} milhas</span> <!-- Texto menor -->
            <svg class="w-4 h-4 ml-2 text-yellow-300" fill="currentColor" viewBox="0 0 20 20"> <!-- Ícone menor -->
              <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"/>
            </svg>
          </div>
          <Button 
            red 
            label="Sair" 
            @click="$emit('logout')" 
            class="px-3 py-1.5 text-sm hover:bg-red-700 transition-colors"
            icon="fa-sign-out-alt"
          />
        </div>
      </div>
    </header>
  </template>
  
  <script>
  import Button from './Button.vue'
  
  export default {
    components: { Button },
    props: {
      milhas: {
        type: Number,
        default: 0
      },
      pageTitle: {
        type: String,
        default: 'Dashboard do Cliente'
      }
    },
    emits: ['logout']
  }
  </script>