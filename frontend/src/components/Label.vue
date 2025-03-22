<template>
    <div class="flex">
        <label for="default-input" class="block mb-2 text-sm font-medium text-gray-900">{{ title }}</label>
        <p v-if="required && title" class="text-red-500 font-bold">*</p>
    </div>
</template>

<script>
export default {
    props: {
        title: {
            type: String,
            default: ''
        },
        required: {
            type: Boolean,
            default: false
        },
    }
}
</script>
