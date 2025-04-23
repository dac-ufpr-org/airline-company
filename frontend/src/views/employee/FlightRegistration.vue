<template>
    <main class="p-6 max-w-3xl mx-auto space-y-6">
      <form
        @submit.prevent="submitForm"
        @input="formChanged = true"
        class="space-y-4"
      >

        <div class="grid grid-cols-2 gap-4">
          <Input
            title="Data do Voo"
            type="date"
            icon="fa-calendar"
            v-model="form.data"
            placeholder="Selecione a data"
          />
          <Input
            title="Hora do Voo"
            type="time"
            icon="fa-clock"
            v-model="form.hora"
            placeholder="Selecione a hora"
          />
        </div>
  
        <div class="grid grid-cols-2 gap-4">
          <Input
            title="Aeroporto de Origem"
            type="text"
            icon="fa-plane-departure"
            v-model="form.origem"
            placeholder="Ex: CWB - Curitiba"
          />
          <Input
            title="Aeroporto de Destino"
            type="text"
            icon="fa-plane-arrival"
            v-model="form.destino"
            placeholder="Ex: POA - Porto Alegre"
          />
        </div>
  
       <!-- Aqui nas milhas eu não coloqei nenhuma conversão, só coloquei um input disabled -->
        <div class="grid grid-cols-2 gap-4">
        <Input
            title="Valor da Passagem (R$)"
            type="text"
            icon="fa-money-bill"
            v-model="form.valor"
            placeholder="Ex: 350,00"
            @input="formatCurrency"
        />
        
        <Input
            title="Equivalente em Milhas"
            type="text"
            icon="fa-star"
            :placeholder="milhasCalculadas || '0'"
            disabled
        />
        </div>

        <Input
          title="Quantidade de Poltronas"
          type="number"
          icon="fa-couch"
          v-model.number="form.poltronas"
          placeholder="Ex: 180"
          min="1"
        />

        <p
        v-if="poltronasError"
        class="text-red-600 
        text-sm 
        font-medium"
        >
        A quantidade de poltronas deve ser maior que 0.
        </p>
      
        <div
          v-if="formError"
          class="text-red-600 font-medium"
        >
          Por favor, preencha todos os campos obrigatórios.
        </div>
  
        <div class="flex justify-end space-x-4 pt-4">
          <Button
            type="submit"
            label="Cadastrar Voo"
            blue
            icon="fa-check"
          />
        </div>
      </form>
  

      <Modal
        v-if="modalVisible"
        @close="modalVisible = false"
      >
        <template #content>
          <h2 class="text-lg font-semibold mb-4">Voo Cadastrado</h2>
          <p>Código do voo gerado: <strong>{{ vooCodigo }}</strong></p>
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
import formatCurrency from '@/utils/formatMoney';

  export default {
    data() {
      return {
        form: {
          data: "",
          hora: "",
          origem: "",
          destino: "",
          valor: "", // string porfavor
          poltronas: null,
        },
        modalVisible: false,
        vooCodigo: "",
        formChanged: false,
        formError: false,
        poltronasError: false,
      };
    },
    
    computed: {
      milhasCalculadas() {
        const valor = parseFloat(this.form.valor.replace(/\./g, '').replace(',', '.'));
        if (isNaN(valor)) return 0;
        return Math.floor(valor / 5);
      },
    },

    methods: {
      submitForm() {
        const {
          data,
          hora,
          origem,
          destino,
          valor,
          poltronas,
        } = this.form;
  
        if (!data || !hora || !origem || !destino || !valor || poltronas == null) {
          this.formError = true;
          return;
        }

        if (poltronas <= 0){
          this.poltronasError = true;
          return;
        }
  
        this.vooCodigo = "TADS" + Math.floor(1000 + Math.random() * 9000);
        this.modalVisible = true;
        this.formChanged = false;
        this.formError = false;
      },

      formatCurrency(event) {
        let value = event.target.value;

        value = value.replace(/\D/g, '');

        if (!value) {
          this.form.valor = '';
          return;
        }

        const numberValue = parseFloat(value) / 100;

        this.form.valor = numberValue.toLocaleString('pt-BR', {
          minimumFractionDigits: 2,
          maximumFractionDigits: 2,
        });
      },
    },
    beforeRouteLeave(to, from, next) {
      if (this.formChanged) {
        const answer = window.confirm("Tem certeza que deseja sair? Alterações não serão salvas.");
        if (answer) {
          next();
        } else {
          next(false);
        }
      } else {
        next();
      }
    },
  };
  </script>
  