<template>
    <h1 class="text-2xl font-bold text-blue-800 m-4">{{ label }}</h1>
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
