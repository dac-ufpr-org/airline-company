<template>
  <div class="text-center py-8">
      <p>{{ label }}</p>
    </div>
</template>

<script>
export default {
    props: {
        label: {
            type: String,
            default: ''
        }
    }

}
</script>
