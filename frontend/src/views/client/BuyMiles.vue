<template>
    <main class="p-6 max-w-xl mx-auto space-y-6">
      <Title label="Comprar Milhas" />
  
      <form @submit.prevent="confirmPurchase" class="space-y-4">
        <Card>
          <template #default>
            <Input
              title="Valor (R$)"
              type="text"
              placeholder="Ex: 150,00"
              icon="fa-money-bill"
              v-model="form.valor"
              @input="formatCurrency"
            />
            <Input
              title="Milhas Recebidas"
              type="text"
              icon="fa-star"
              :placeholder="milhasCalculadas || '0'"
              disabled
            />
            <Button
              type="submit"
              blue
              label="Confirmar Compra"
              icon="fa-check"
            />
          </template>
        </Card>
      </form>
  
      <Modal v-if="modalVisible" @close="modalVisible = false">
        <template #content>
          <h2 class="text-lg font-semibold mb-4">Compra Confirmada</h2>
          <p>Você comprou <strong>{{ milhasCalculadas }}</strong> milhas por <strong>R$ {{ form.valor }}</strong></p>
          <Button
            label="Fechar"
            blue
            class="mt-4"
            @click="modalVisible = false"
          />
        </template>
      </Modal>
    </main>
  </template>

  <script>
  export default {
    data() {
      return {
        form: {
          valor: ''
        },
        modalVisible: false,
        transacoes: []
      }
    },
    computed: {
      milhasCalculadas() {
        const valor = parseFloat(this.form.valor.replace(/\./g, '').replace(',', '.'));
        if (isNaN(valor)) return 0;
        return Math.floor(valor / 5);
      }
    },
    methods: {
      formatCurrency(event) {
        let value = event.target.value.replace(/\D/g, '');
        if (!value) {
          this.form.valor = '';
          return;
        }
        const numberValue = parseFloat(value) / 100;
        this.form.valor = numberValue.toLocaleString('pt-BR', {
          minimumFractionDigits: 2,
          maximumFractionDigits: 2
        });
      },
      confirmPurchase() {
        const valorNumerico = parseFloat(this.form.valor.replace(/\./g, '').replace(',', '.'));
        const milhas = this.milhasCalculadas;
  
        if (!valorNumerico || milhas <= 0) return;
  
        const novaTransacao = {
          performedDate: new Date().toISOString(),
          price: valorNumerico.toFixed(2),
          quantity: milhas,
          type: 'entry',
          description: 'COMPRA DE MILHAS'
        };
  
        this.transacoes.push(novaTransacao);
  
        //  salvar no backend 

  
        this.modalVisible = true;
      }
    }
  }
  </script>
  