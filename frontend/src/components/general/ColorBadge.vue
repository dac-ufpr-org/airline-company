<template>
    <Button 
      :lightBlue="flightStatus === 'Reservado' || blue"
      :lightGreen="flightStatus === 'Realizado' || green"
      :lightRed="flightStatus === 'Cancelado' || red"
      :lightYellow="yellow"
      :label="flightStatus ? flightStatus : label" 
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
        },
        flightStatus: {
            type: String, 
            default: '',
            validator: value => ['Reservado', 'Realizado', 'Cancelado'].includes(value)
        }
    }

}
</script>
