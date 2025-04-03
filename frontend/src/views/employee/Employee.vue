<template>
  <div>
    <Table :items="filteredEmployees" :columns="columns">
      <template #table-actions>
        <Input
          title="Buscar funcionários"
          type="text"
          placeholder="Digite para filtrar..."
          search
          v-model="searchTerm" />
      </template>

      <template #cell-actions="{ item }">
        <div class="flex items-center space-x-3">
          <Button
            blue
            label="Editar"
            size="text-sm"
            icon="fa-pen-to-square"
            @click="$router.push(`/employee/employee-registration?id=${item.cpf}`)" />
          <Button red label="Remover" size="text-sm" icon="fa-trash" />
        </div>
      </template>
    </Table>
  </div>
</template>

<script>
import Table from '@/components/general/Table.vue'

export default {
  components: {
    Table,
  },
  data() {
    return {
      searchTerm: '',
      columns: [
        { key: 'nome', label: 'Nome' },
        { key: 'cpf', label: 'CPF' },
        { key: 'email', label: 'E-mail' },
        { key: 'telefone', label: 'Telefone' },
        { key: 'actions', label: 'Ações' }, // editar e remover colocar depois
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
}
</script>
