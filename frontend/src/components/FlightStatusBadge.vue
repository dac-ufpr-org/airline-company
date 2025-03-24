<template>
    <Button 
      :lightBlue="status === 'Reservado'"
      :lightGreen="status === 'Realizado'"
      :lightRed="status === 'Cancelado'"
      :label="status" 
      size="text-xs"
      disabled
      class="font-medium"
    />
  </template>
  
  <script>
  import Button from './Button.vue'
  
  export default {
    components: { Button },
    props: {
      status: {
        type: String,
        required: true,
        validator: value => ['Reservado', 'Realizado', 'Cancelado'].includes(value)
      }
    }
  }
  </script>