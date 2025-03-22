<template>
    <button
        :disabled="disabled"
        :class="[
        { 'opacity-50 cursor-auto': disabled },
        { [blueClass]: blue && !outlined },
        { [blueBorderClass]: blue && outlined },
        { [greenClass]: green && !outlined },
        { [greenBorderClass]: green && outlined },
        { [redClass]: red && !outlined },
        { [redBorderClass]: red && outlined },
        { [yellowClass]: yellow && !outlined },
        { [yellowBorderClass]: yellow && outlined },
        { 'rounded-lg bg-gray-200 hover:bg-gray-300 border border-gray-300': gray },
        { 'rounded-lg 0 border border-slate-20 hover:bg-gray-50': light },
        { 'cursor-pointer': !disabled }
        ]"
        class="py-1 w-full rounded-lg flex items-center justify-center"
    >
        <div :class="size === 'text-xs' || size === 'text-sm' ? 'px-2' : 'px-4'" class="py-2 flex items-center justify-center">
            <slot>
                <i class="fa-solid" :class="label ? `mr-2 ${this.icon}` : `${this.icon}`" v-if="icon"></i>
                <p :class="size ? size : ''">{{ label }}</p>
            </slot>
        </div>
    </button>
  
</template>

<script>
export default {
    props: {
    label: String,
    color: String,
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
    yellow: [Boolean, Number],
    dark: [Boolean, Number],
    gray: [Boolean, Number],
    light: [Boolean, Number],
  },
  computed: {
    blueClass() {
      return 'bg-blue-600 hover:bg-blue-700 text-white'
    },
    greenClass() {
      return 'bg-green-500 hover:bg-green-600'
    },
    redClass() {
      return 'bg-red-500 hover:bg-red-600'
    },
    yellowClass() {
      return 'bg-yellow-500 hover:bg-yellow-600'
    },
    blueBorderClass() {
      return 'border border-blue-600 hover:border-blue-700 text-blue-600'
    },
    greenBorderClass() {
      return 'border border-green-500 hover:border-green-600 text-green-500'
    },
    redBorderClass() {
      return 'border border-red-500 hover:border-red-600 text-red-500'
    },
    yellowBorderClass() {
      return ' border border-yellow-500 hover:border-yellow-600 text-yellow-500'
    },
  },
}
</script>
