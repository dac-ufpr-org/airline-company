<template>
    <div class="min-h-screen bg-gradient-to-br from-blue-50 to-blue-100 p-4">
      <div class="container mx-auto max-w-3xl">
        <h1 class="text-2xl font-bold text-blue-800 mb-6">Confirmar Reserva</h1>
        
        <div class="bg-white p-6 rounded-xl shadow-lg border border-blue-200 mb-6">
          <h2 class="text-xl font-semibold mb-4">Detalhes do Voo</h2>
          <div class="grid grid-cols-2 gap-4 mb-4">
            <div>
              <p class="text-gray-600">Origem</p>
              <p class="font-medium">{{ voo.origem }}</p>
            </div>
            <div>
              <p class="text-gray-600">Destino</p>
              <p class="font-medium">{{ voo.destino }}</p>
            </div>
            <div>
              <p class="text-gray-600">Data/Hora</p>
              <p class="font-medium">{{ formatDateTime(voo.dataHora) }}</p>
            </div>
            <div>
              <p class="text-gray-600">Preço por assento</p>
              <p class="font-medium">{{ formatCurrency(voo.preco) }}</p>
            </div>
          </div>
        </div>
        
        <div class="bg-white p-6 rounded-xl shadow-lg border border-blue-200 mb-6">
          <h2 class="text-xl font-semibold mb-4">Sua Reserva</h2>
          
          <Input 
            title="Quantidade de Passagens" 
            type="number" 
            v-model="quantidade" 
            min="1"
            class="mb-4"
          />
          
          <div class="grid grid-cols-2 gap-4 mb-4">
            <div>
              <p class="text-gray-600">Total em Dinheiro</p>
              <p class="font-bold text-lg">{{ formatCurrency(totalDinheiro) }}</p>
            </div>
            <div>
              <p class="text-gray-600">Total em Milhas</p>
              <p class="font-bold text-lg">{{ totalMilhas }} milhas</p>
            </div>
          </div>
          
          <div class="mb-4">
            <p class="text-gray-600 mb-2">Usar minhas milhas (Saldo: {{ milhasDisponiveis }} milhas)</p>
            <Input 
              type="number" 
              v-model="milhasUsadas" 
              :max="Math.min(milhasDisponiveis, totalMilhas)"
              min="0"
            />
          </div>
          
          <div class="bg-blue-50 p-4 rounded-lg mb-6">
            <p class="font-bold text-blue-800">Total a Pagar: {{ formatCurrency(totalAPagar) }}</p>
            <p v-if="milhasUsadas > 0" class="text-sm">
              ({{ milhasUsadas }} milhas + {{ formatCurrency(totalAPagar) }} em dinheiro)
            </p>
          </div>
          
          <Button 
            green
            label="Confirmar Reserva"
            @click="confirmReservation"
            class="w-full"
            :disabled="!canConfirm"
          />
        </div>
      </div>
    </div>
  </template>
  
  <script>
  
  export default {
    data() {
      return {
        voo: {
          id: 1,
          origem: 'GRU - Guarulhos',
          destino: 'GIG - Galeão',
          dataHora: '2023-12-25T14:30:00',
          preco: 450.90,
          milhasNecessarias: 1200
        },
        quantidade: 1,
        milhasUsadas: 0,
        milhasDisponiveis: 3250,
        loading: false
      }
    },
    computed: {
      totalDinheiro() {
        return this.voo.preco * this.quantidade
      },
      totalMilhas() {
        return this.voo.milhasNecessarias * this.quantidade
      },
      totalAPagar() {
        const milhasEmDinheiro = (this.milhasUsadas / this.voo.milhasNecessarias) * this.voo.preco
        return this.totalDinheiro - milhasEmDinheiro
      },
      canConfirm() {
        return this.quantidade > 0 && this.totalAPagar >= 0
      }
    },
    methods: {
      formatDateTime(date) {
        return new Date(date).toLocaleString('pt-BR')
      },
      formatCurrency(value) {
        return 'R$ ' + value.toFixed(2).replace('.', ',')
      },
      async confirmReservation() {
        this.loading = true
        try {
          // Simulação de chamada API
          const reserva = await this.mockCreateReservation()
          this.$router.push({
            path: '/client/reservas/comprovante',
            query: { codigo: reserva.codigo }
          })
        } finally {
          this.loading = false
        }
      },
      mockCreateReservation() {
        return new Promise(resolve => {
          setTimeout(() => {
            resolve({
              codigo: this.generateReservationCode(),
              milhasUsadas: this.milhasUsadas,
              valorPago: this.totalAPagar
            })
          }, 1000)
        })
      },
      generateReservationCode() {
        const letters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'
        const numbers = '0123456789'
        let code = ''
        
        for (let i = 0; i < 3; i++) {
          code += letters.charAt(Math.floor(Math.random() * letters.length))
        }
        for (let i = 0; i < 3; i++) {
          code += numbers.charAt(Math.floor(Math.random() * numbers.length))
        }
        
        return code
      }
    },
    async created() {
      // Simular carregamento do voo selecionado
      // this.voo = await api.getFlight(this.$route.query.vooId)
    }
  }
  </script>