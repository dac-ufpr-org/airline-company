<template>
    <main>
        <Title label="Consultar Reserva" />

        <SearchForm @search="handleSearch" :items="formItems" :error="errors.reservationId" />

        <Loading v-if="loading" label="Buscando reservas..." />

        <div v-else>
            <div v-if="reservations.length === 0 && searchPerformed" class="text-center py-8 text-gray-500">
                <p>Nenhuma reserva encontrada. Tente alterar seus critérios de busca.</p>
            </div>
            
            <div v-else-if="reservations.length > 0">
                <Card class="w-fit">
                    <template #default>
                        <div class="flex items-center justify-between pb-4 mb-4 border-b border-gray-200">
                            <p class="font-semibold text-blue-800 text-xl">
                                {{ `Reserva #${reservations[0].id}` }}
                            </p>
                        </div>
                        <div class="grid gap-3 mb-6">
                            <div
                                class="flex items-start gap-2"
                                v-for="(info, index) in reservationCardInfo"
                                :key="index"
                            >
                                <p class="font-medium text-bold min-w-[120px]">
                                {{ info.label }}:
                                </p>
                                <p class="text-gray-800">{{ reservations[0][info.key] }}</p>
                            </div>
                        </div>

                        <div :class="canDoCheckin ? 'gap-3' : ''" class="flex justify-end">
                            <Button class="max-w-[160px]" lightRed label="Cancelar Reserva" />
                            <Button
                                v-if="canDoCheckin"
                                class="max-w-[160px]"
                                lightBlue
                                label="Fazer Check-In"
                            />
                        </div>
                    </template>
                </Card>
            </div>

            <div v-else class="text-center py-8 text-gray-500">
                <p>Informe os critérios de busca para encontrar reservas existentes.</p>
            </div>
        </div>

        <LoadingDots v-if="loading" />
    </main>
</template>

<script>
import SearchForm from '../../components/general/SearchForm.vue'
import Title from '../../components/general/Title.vue'
import Card from '../../components/general/Card.vue'
import LoadingDots from '../../components/general/LoadingDots.vue'
import Button from '../../components/general/Button.vue'

export default {
    components: { SearchForm, Title, Card, LoadingDots, Button },
    data() {
        return {
            loading: false,
            reservations: [],
            searchPerformed: false,
            formItems: [
                { key: 'reservationId', label: 'Código Reserva', icon: 'fa-hashtag' }
            ],
            errors: {},
            reservationCardInfo: [
                { key: 'id', label: 'Código'  },
                { key: 'origem', label: 'Origem'  },
                { key: 'destino', label: 'Destino'  },
                { key: 'dataHora', label: 'Destino'  },
                { key: 'milhas', label: 'Milhas gastas'  },
                { key: 'status', label: 'Status'  }
            ]
        }
    },
    methods: {
        async handleSearch(filters) {
            if (!filters.reservationId) {
                this.errors.reservationId = 'Código da reserva é obrigatório'
                return 
            }
            this.errors = {}
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
                    dataHora: '2025-04-11T13:59:00',
                    milhas: 1200,
                    status: 'Confirmado'
                },
                {
                    id: 2,
                    origem: 'CGH - Congonhas',
                    destino: 'BSB - Brasília',
                    dataHora: '2023-12-26T08:15:00',
                    milhas: 850,
                    status: 'Feito Check-In'
                },
                {
                    id: 3,
                    origem: 'GRU - Guarulhos',
                    destino: 'SSA - Salvador',
                    dataHora: '2023-12-27T11:20:00',
                    milhas: 1400,
                    status: 'Cancelado'
                }
                ])
            }, 1000)
        })
        },
        filterReservations(reservations, filters) {
            if (filters.reservationId) {
                return reservations.filter(reservation => {
                    const idMatch = filters.reservationId
                    ? reservation.id.toString().includes(filters.reservationId.toString())
                    : true
    
                    return idMatch
                })
            }
            return []
        },

    },
    computed: {
        canDoCheckin() {
            if (this.reservations && this.reservations.length) {
                const actualDate = new Date()
                const checkinDate = new Date(this.reservations[0].dataHora)

                const diffInMs = checkinDate - actualDate
                const diffInHours = diffInMs / (1000 * 60 * 60)

                return diffInHours >= 0 && diffInHours <= 48
            }

            return false
        }
    }

}
</script>
