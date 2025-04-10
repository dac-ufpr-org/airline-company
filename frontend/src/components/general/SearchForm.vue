<template>
  <div class="m-4">
    <div
      class="bg-white p-4 rounded-xl shadow-lg border mb-3"
      :class="error ? 'border-red-500' : 'border-blue-200'"
    >
      <div
        class="grid gap-4 items-end"
        style="grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));"
      >
        <div v-for="item in items" :key="item.key" class="flex flex-col">
          <label class="text-sm font-medium text-gray-700 mb-1">
            {{ item.label }}
          </label>
          <div class="relative">
            <input
              v-model="formData[item.key]"
              class="w-full p-2 border border-gray-300 rounded-md h-[48px] pl-10"
              :placeholder="`Digite ${item.label.toLowerCase()}`"
            />
            <i
              class="fas absolute left-3 top-4 text-gray-400"
              :class="item.icon ? item.icon : ''"
            ></i>
          </div>
        </div>

        <div class="flex flex-col">
          <Button
            @click="$emit('search', formData)"
            icon="fa-search"
            label="Buscar"
            blue
          />
        </div>
      </div>
    </div>
    <div class="flex justify-end">
      <p v-if="error" class="font-semibold text-red-500 text-sm">{{ error }}</p>
    </div>
  </div>
</template>

<script>
import { reactive, toRefs } from 'vue'
import Button from '../general/Button.vue'

export default {
  components: { Button },
  props: {
    items: {
      type: Array,
      default: () => []
    },
    error: {
      type: Boolean,
      default: false
    }
  },
  emits: ['search'],
  setup(props) {
    const formData = reactive({})

    props.items.forEach(item => {
      formData[item.key] = null
    })

    return {
      ...toRefs(formData),
      formData
    }
  }
}
</script>
