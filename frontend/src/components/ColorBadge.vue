<template>
    <Button 
      :lightBlue="blue"
      :lightGreen="green"
      :lightRed="red"
      :lightYellow="yellow"
      :label="label" 
      size="text-xs"
      disabled
      class="font-medium"
    />
  
</template>

<script>
import Button from '../components/Button.vue'

export default {
    components: { Button },
    props: {
        label: {
            type: String,
            default: ''
        },
        blue: {
            type: Boolean,
            default: true
        },
        green: {
            type: Boolean,
            default: false
        },
        red: {
            type: Boolean,
            default: false
        },
        yellow: {
            type: Boolean,
            default: false
        }
    }

}
</script>
