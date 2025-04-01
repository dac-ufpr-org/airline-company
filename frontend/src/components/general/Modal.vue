<template>
  <div 
    class="fixed inset-0 bg-black/50 backdrop-blur-md flex items-center justify-center z-50"
    @click.self="$emit('close')"
  >
    <div class="bg-white p-6 rounded-lg w-full max-w-sm shadow-lg">
      <p class="text-lg font-semibold mb-4">{{ title }}</p>
      <p v-for="(item, index) in item" :key="index" class="font-bold">
        <b>{{ item }}</b>
      </p>
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
