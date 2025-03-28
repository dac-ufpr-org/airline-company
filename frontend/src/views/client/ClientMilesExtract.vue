<template>
    <div>
        <FlightsTable 
          :items="milhas" 
          :columns="tableColumns"
          :initial-search-term="searchTerm"
        >
          <template #cell-status="{ item }">
            <FlightStatusBadge :status="item.status" />
          </template>

          <template #cell-actions="{ item }">
            <div class="flex items-center space-x-3">   
              <Button 
                v-if="item.status === 'Reservado'"
                lightBlue
                label="Ver Detalhes" 
                @click="viewReservation(item)" 
                size="text-sm"
                class="mr-2"
                icon="fa-eye"
              />
              <Button 
                v-if="item.status === 'Reservado'"
                lightRed
                label="Cancelar" 
                @click="cancelReservation(item)" 
                size="text-sm"
                icon="fa-times"
              />
              <Button 
                v-if="item.status === 'Realizado'"
                lightGreen
                label="Avaliar" 
                @click="rateFlight(item)" 
                size="text-sm"
                class="ml-2"
                icon="fa-star"
              />
            </div>
          </template>
        </FlightsTable>
    </div>
</template>

<script>
import FlightsTable from '../../components/FlightsTable.vue'

export default {
    components: {
        FlightsTable
    },
    data: () => ({
        milhas: [
            {
                id: 1,
                performedDate: '2023-12-20T08:15:00',
                reservationCode: '124',
                price: '15.70',
                quantity: 3,
                type: 'entry',
                description: 'COMPRA DE MILHAS',
            },
            {
                id: 2,
                performedDate: '2023-12-25T07:15:00',
                reservationCode: '122',
                price: '1500.00',
                quantity: 300,
                type: 'exit',
                description: 'CWB->GRU',
            },
        ],
        tableColumns: [
            { key: 'performedDate', label: 'Data', formatter: (val) => this.$filters.formatDateTime(val) ?? '-'  },
            { key: 'price', label: 'Valor', formatter: (val) => this.$filters.formatMoney(val) ?? '-'  },
        ]
    })
}
</script>
