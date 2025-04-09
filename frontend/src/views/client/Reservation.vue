<template>
    <main>
        <Title label="Consultar Reserva" />

        <SearchForm @search="handleSearch" :items="formItems" />

        <Loading v-if="loading" label="Buscando reservas..." />

        <div v-else>
            <div v-if="reservations.length === 0 && searchPerformed" class="text-center py-8 text-gray-500">
                <p>Nenhuma reserva encontrada. Tente alterar seus critérios de busca.</p>
            </div>
            
            <div v-else-if="reservations.length > 0">
                <FlightSelectionCard 
                v-for="reservation in reservations" 
                :key="reservation.id" 
                :reservation="reservation"
                @select="goToReservationDetails"
                />
            </div>

            <div v-else class="text-center py-8 text-gray-500">
                <p>Informe os critérios de busca para encontrar reservas existentes.</p>
            </div>
        </div>

    </main>
</template>

<script>
import SearchForm from '../../components/general/SearchForm.vue'
import Title from '../../components/general/Title.vue'

export default {
    components: { SearchForm, Title },
    data: () => ({
        loading: false,
        reservations: [],
        searchPerformed: false,
        formItems: [
            { key: 'reservationId', label: 'Código Reserva', icon: 'fa-hashtag' }
        ]
    }),
    methods: {
        async handleSearch(filters) {
            this.loading = true
            this.searchPerformed = true
            try {
                const allReservations = await this.mockReservations()                
                this.reservations = this.filterReservations(allReservations, filters)
            } finally {
                this.loading = false
            }
        },
        mockReservations() {
            // Mock de dados completo
            return new Promise(resolve => {
            setTimeout(() => {
                resolve([
                {
                    id: 1,
                    origem: 'GRU - Guarulhos',
                    destino: 'GIG - Galeão',
                    dataHora: '2023-12-25T14:30:00',
                    preco: 450.90,
                    milhasNecessarias: 1200
                },
                {
                    id: 2,
                    origem: 'CGH - Congonhas',
                    destino: 'BSB - Brasília',
                    dataHora: '2023-12-26T08:15:00',
                    preco: 320.50,
                    milhasNecessarias: 850
                },
                {
                    id: 3,
                    origem: 'GRU - Guarulhos',
                    destino: 'SSA - Salvador',
                    dataHora: '2023-12-27T11:20:00',
                    preco: 520.75,
                    milhasNecessarias: 1400
                }
                ])
            }, 800)
        })
        },
        filterReservations(reservations, filters) {
            return reservations.filter(reservation => {
                const idMatch = filters.reservationId
                ? reservation.id.toString().includes(filters.reservationId.toString())
                : true

                return idMatch
            })
        },

    }

}
</script>
