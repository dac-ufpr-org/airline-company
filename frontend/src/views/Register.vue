<template>
    <div class="grid grid-cols-2 h-[100vh] w-[100vw]">
        <div class="bg-red-500 bg-center bg-cover bg-[url(@/assets/airplane.png)]"></div>
        <div class="flex justify-center items-center">
            <div class="mb-3 h-[60%] w-[70%] rounded rounded-lg p-6">
                <p class="mb-6 text-4xl font-semibold text-blue-800">Cadastre-se</p>
                <Input title="Nome" v-model="object.name" required :error="this.errors.name" />
                <Input title="Email" type="email" v-model="object.email" required :error="errors.email" />
                <Input title="Senha" type="password" v-model="object.password" required :error="errors.password" />
                <Button blue label="Cadastrar" @click="register()" />
                <div class="flex justify-end mt-6">
                    <p class="cursor-pointer hover:text-blue-900 text-blue-800 font-semibold" @click="this.$router.push('login')">Já tem conta? Entrar</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Input from '../components/Input.vue'
import Button from '../components/Button.vue'

export default {
    components: {
        Input,
        Button
    },
    data: () => ({
        object: {},
        errors: {}
    }),
    methods: {
        async register() {
            if (this.object.email && this.object.name && this.object.password) {
                // const response = await this.register(this.object) //implementar depois no backend
                // if (!response?.data) {
                // }
                this.$router.push('login')
            }
            else {
                if (!this.object.name) this.errors.name = 'Nome é obrigatório'
                if (!this.object.email) this.errors.email = 'Email é obrigatório'
                if (!this.object.password) this.errors.password = 'Senha é obrigatória'
            }
        }
    }

}
</script>
