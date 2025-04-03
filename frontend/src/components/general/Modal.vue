<template>
  <div 
    class="fixed inset-0 bg-black/50 backdrop-blur-md flex items-center justify-center z-50"
    @click.self="$emit('close')"
  >
    <div class="bg-white p-6 rounded-lg w-full max-w-sm shadow-lg">
      <p class="font-bold mb-3 text-lg text-blue-800 border-b pb-3">{{ title }}</p>
      <div class="mb-3">
        <slot name="content"></slot>
      </div>
      <div class="flex justify-end">
        <Button red @click="$emit('close')" label="Fechar" />
      </div>
    </div>
  </div>
</template>

<script>
import Button from '../general/Button.vue'

export default {
  components: {
    Button
  },
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
