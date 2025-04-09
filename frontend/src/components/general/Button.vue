<template>
  <button
    :disabled="disabled"
    @click="$emit('click')"
    :class="[ 
      { 'opacity-50 cursor-auto': disabled },
      { [blueClass]: blue && !outlined },
      { [blueBorderClass]: blue && outlined },
      { [greenClass]: green && !outlined },
      { [greenBorderClass]: green && outlined },
      { [redClass]: red && !outlined },
      { [redBorderClass]: red && outlined },
      { [lightBlueClass]: lightBlue && !outlined },
      { [lightGreenClass]: lightGreen && !outlined },
      { [lightRedClass]: lightRed && !outlined },
      { [lightYellowClass]: lightYellow && !outlined },
      { 'rounded-lg bg-gray-200 hover:bg-gray-300 border border-gray-300': gray },
      { 'rounded-lg border border-slate-200 hover:bg-gray-50': light },
      { 'cursor-pointer': !disabled }
    ]"
    class="py-1 w-full px-4 min-w-auto rounded-lg flex items-center justify-center transition-colors duration-200"
  >
    <div 
      :class="size === 'text-xs' || size === 'text-sm' ? 'px-2' : 'px-4'" 
      class="py-2 flex items-center justify-center whitespace-nowrap flex-nowrap"
    >
      <slot>
        <i 
          class="fa-solid" 
          :class="label ? `mr-2 ${icon}` : icon" 
          v-if="icon"
        ></i>
        <p :class="size ? size : ''">{{ label }}</p>
      </slot>
    </div>
  </button>
</template>

<script>
export default {
  props: {
    label: String,
    icon: String,
    disabled: {
      type: Boolean,
      default: false,
    },
    size: String,
    outlined: { type: Boolean, default: false },
    blue: [Boolean, Number],
    green: [Boolean, Number],
    red: [Boolean, Number],
    lightBlue: [Boolean, Number],
    lightGreen: [Boolean, Number],
    lightRed: [Boolean, Number],
    lightYellow: [Boolean, Number],
    gray: [Boolean, Number],
    light: [Boolean, Number],
  },
  computed: {
    blueClass() {
      return 'bg-blue-600 hover:bg-blue-700 text-white'
    },
    greenClass() {
      return 'bg-green-600 hover:bg-green-700 text-white'
    },
    redClass() {
      return 'bg-red-600 hover:bg-red-700 text-white'
    },
    lightBlueClass() {
      return 'bg-blue-100 hover:bg-blue-200 text-blue-800'
    },
    lightGreenClass() {
      return 'bg-green-100 hover:bg-green-200 text-green-800'
    },
    lightRedClass() {
      return 'bg-red-100 hover:bg-red-200 text-red-800'
    },
    lightYellowClass() {
      return 'bg-yellow-100 hover:bg-yellow-200 text-yellow-800'
    },
    blueBorderClass() {
      return 'border border-blue-600 hover:border-blue-700 text-blue-600 hover:bg-blue-50'
    },
    greenBorderClass() {
      return 'border border-green-600 hover:border-green-700 text-green-600 hover:bg-green-50'
    },
    redBorderClass() {
      return 'border border-red-600 hover:border-red-700 text-red-600 hover:bg-red-50'
    },
    yellowBorderClass() {
      return 'border border-yellow-600 hover:border-yellow-700 text-yellow-600 hover:bg-yellow-50'
    }
  }
}
</script>
