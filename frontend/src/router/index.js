import { createRouter, createWebHistory } from 'vue-router'
import Register from '../views/Register.vue'
import Login from '../views/Login.vue'
import ClientDashboard from '../views/client/ClientDashboard.vue'
import ClientMilesExtract from '../views/client/ClientMilesExtract.vue'
import BuyMiles from '@/views/client/BuyMiles.vue'
import EmployeeDashboard from '@/views/employee/EmployeeDashboard.vue'
import NewReservation from '@/views/client/NewReservation.vue'
import ReservationConfirmation from '@/views/client/ReservationConfirmation.vue'
import ReservationReceipt from '@/views/client/ReservationReceipt.vue'
import Reservation from '@/views/client/Reservation.vue'
import Employee from '@/views/employee/Employee.vue'
import EmployeeRegistration from '@/views/employee/EmployeeRegistration.vue' // Nova rota de cadastro de funcionário
import FlightRegistration from '@/views/employee/FlightRegistration.vue'
const routes = [
  {
    path: '/register',
    name: 'register',
    component: Register,
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
  },
  //client routes
  {
    path: '/client/dashboard',
    name: 'Dashboard do Cliente',
    component: ClientDashboard,
  },

  {
    path: '/client/mileage-extract',
    name: 'Extrato de Milhas',
    component: ClientMilesExtract,
  },

  {
    path: '/client/buy-miles',
    name: 'Compra de Milhas',
    component: BuyMiles,
  },

  {
    path: '/client/reservas/nova',
    name: 'Nova Reserva',
    component: NewReservation,
  },
  {
    path: '/client/reservas/confirmar',
    name: 'Confirmar Reserva',
    component: ReservationConfirmation,
  },
  {
    path: '/client/reservas/comprovante',
    name: 'Reserva Recebida',
    component: ReservationReceipt,
  },
  {
    path: '/client/consultar-reservas',
    name: 'Consultar Reserva',
    component: Reservation,
  },
  //employee routes
  { path: '/employee/dashboard', name: 'Dashboard do Funcionário', component: EmployeeDashboard },
  { path: '/employee', name: 'Funcionários', component: Employee },
  { path: '/employee/employee-registration', name: 'Cadastro de Funcionário', component: EmployeeRegistration },
  { path: '/employee/flight-registration', name: 'Cadastro de Voo', component: FlightRegistration },

  {
    path: '/',
    redirect: '/login',
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router

