<template>
    <div class="mb-3">
        <Label :title="title" :required="required"/>
        <div class="flex">
            <input 
                :type="type" 
                @input="updateValue"
                class="border border-gray-300 bg-slate-100 text-gray-900 text-sm rounded-l-lg focus:ring-blue-500 focus:border-blue-500 block w-full focus:ring-0 p-3"
                :class="[{ 'bg-slate-100': disabled, 'border-red-500': error }]"
                v-model="inputValue"
                :disabled="disabled"
                :placeholder="placeholder"
                :error="error"
            >
            <i 
                :class="this.icon ? `fa ${this.icon} text-gray-400` : ''"
                class="p-3 border border-gray-300 bg-slate-100 rounded-r-lg cursor-pointer"
                v-if="icon"
                @click="search ? $emit('search') : null"
            />
        </div>
        <div class="text-red-400 text-xs p-2 mt-3" v-if="error">
            <b>{{ error }}</b>
        </div>
    </div>
</template>

<script>

export default {
    data: () => ({
        inputValue: ''
    }),
    props: {
        title: {
            type: String, 
            default: ''
        },
        search: {
            type: Boolean, 
            default: false
        },
        disabled: {
            type: Boolean, 
            default: false
        },
        placeholder: {
            type: String, 
            default: ''
        },
        required: {
            type: Boolean, 
            default: false
        },
        error: {
            type: Boolean, 
            default: false
        },
        type: {
            type: String,
            default: 'text'
        },
        modelValue: [String, Number]
    },
    methods: {
        handleInputType() {
            if (this.search) {
                this.icon = 'fa-search'
            }
            else {
                if (this.type && this.type === 'email') {
                    this.icon = 'fa-at'
                }
                else if (this.type && this.type === 'password') {
                    this.icon = 'fa-unlock'
                }
            }
        },
        updateValue() {
            this.$emit('update:modelValue', this.inputValue)
        },
    },
    watch: {
        modelValue(newVal){
            this.inputValue = newVal
        }
    },
    mounted() {
        this.inputValue = this.modelValue
        this.handleInputType()
    }
}
</script>
