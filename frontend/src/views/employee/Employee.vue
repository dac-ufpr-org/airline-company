<template>
  <div>
    <Table :items="filteredEmployees" :columns="columns">
      <template #table-actions>
        <div class="grid grid-cols-4 flex items-end gap-3">
          <Input
              class="col-span-3"
              title="Buscar funcionários"
              type="text"
              placeholder="Digite para filtrar..."
              search
              v-model="searchTerm" 
            />
            <Button
                class="mb-3"
                blue
                label="Cadastrar Funcionário"
                size="text-sm"
                icon="fa-plus"
                @click="$router.push('/employee/employee-registration')"
            />
        </div>
      </template>

      <template #cell-actions="{ item }">
        <div class="flex items-center space-x-3">
          <Button
            lightBlue
            label="Editar"
            size="text-sm"
            icon="fa-pen-to-square"
            @click="$router.push(`/employee/employee-edit?id=${item.cpf}`)" 
          />
          <Button 
            lightRed 
            label="Remover Funcionário" 
            size="text-sm" 
            icon="fa-times"
            @click="openModal('remove')"
          />
        </div>
      </template>
    </Table>
    
    <Modal v-if="modalType === 'remove'" title="Remover Funcionário" @close="closeModal">
      <template #content>
        <div class="mb-4">
          <p class="mb-2">Você tem certeza que deseja remover este funcionário?</p>
          <p class="font-semibold text-red-600">Esse funcionário será removido permanentemente.</p>
        </div>
        <Button label="Confirmar" @click="closeModal" class="my-3" blue />
      </template>
    </Modal>

  </div>
</template>

<script>
export default {
  data() {
    return {
      modalType: null,
      searchTerm: '',
      columns: [
        { key: 'nome', label: 'Nome' },
        { key: 'cpf', label: 'CPF' },
        { key: 'email', label: 'E-mail' },
        { key: 'telefone', label: 'Telefone' },
        { key: 'actions', label: 'Ações' },
      ],
      employees: [
        { nome: 'Sabrina de Araújo', cpf: '123.456.789-00', email: 'sabrina@email.com', telefone: '(11) 99999-9999' },
        { nome: 'Lívia Araújo', cpf: '987.654.321-00', email: 'livia@email.com', telefone: '(21) 98888-8888' },
        { nome: 'Leonardo Nunes', cpf: '555.666.777-00', email: 'leonardo@emial.com', telefone: '(31) 97777-7777' },
      ],
    }
  },
  computed: {
    filteredEmployees() {
      return this.employees.filter((employee) =>
        Object.values(employee).some((value) => value.toLowerCase().includes(this.searchTerm.toLowerCase()))
      )
    },
  },
  methods: {
    openModal(type) {            
      this.modalType = type;
    },
    closeModal() {               
      this.modalType = null;
    },
  },
}
</script>
