<template>
  <div 
    class="fixed inset-0 bg-black/50 backdrop-blur-md flex items-center justify-center z-50"
    @click.self="$emit('close')"
  >
    <div class="bg-white p-6 rounded-lg w-full max-w-sm shadow-lg relative">
      <button 
        @click="$emit('close')"
        class="absolute top-2 right-2 text-gray-500 hover:text-gray-700 transition-colors cursor-pointer"
      >
        <i class="fas fa-times text-lg"></i>
      </button>
      
      <p class="font-bold mb-3 text-lg text-black border-b pb-3 pr-6">{{ title }}</p>
      <div class="mb-3">
        <slot name="content"></slot>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  props: {
    item: {
      type: Object,
      default: {}
    },
    title: {
      type: String,
      default: ''
    }
  }
};
</script>