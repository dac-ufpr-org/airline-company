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
import EmployeeRegistration from '@/views/employee/EmployeeRegistration.vue'  // Nova rota de cadastro de funcionário
import EmployeeEdit from '@/views/employee/EmployeeEdit.vue'
import FlightRegistration from '@/views/employee/FlightRegistration.vue'

const routes = [
  {
    path: '/register',
    name: 'register',
    component: Register,
    meta: { public: true }
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
    meta: { public: true }
  },
  //client routes
  {
    path: '/client/dashboard',
    name: 'Dashboard do Cliente',
    component: ClientDashboard,
    meta: { role: 'CLIENTE' } 
  },

  {
    path: '/client/mileage-extract',
    name: 'Extrato de Milhas',
    component: ClientMilesExtract,
    meta: { role: 'CLIENTE' } 
  },

  {
    path: '/client/buy-miles',
    name: 'Compra de Milhas',
    component: BuyMiles,
    meta: { role: 'CLIENTE' } 
  },

  {
    path: '/client/reservas/nova',
    name: 'Nova Reserva',
    component: NewReservation,
    meta: { role: 'CLIENTE' } 
  },
  {
    path: '/client/reservas/confirmar',
    name: 'Confirmar Reserva',
    component: ReservationConfirmation,
    meta: { role: 'CLIENTE' } 
  },
  {
    path: '/client/reservas/comprovante',
    name: 'Reserva Recebida',
    component: ReservationReceipt,
    meta: { role: 'CLIENTE' } 
  },
  {
    path: '/client/consultar-reservas',
    name: 'Consultar Reserva',
    component: Reservation,
    meta: { role: 'CLIENTE' } 
  },
  //employee routes
  { 
    path: '/employee/dashboard', 
    name: 'Dashboard do Funcionário', 
    component: EmployeeDashboard,
    meta: { role: 'FUNCIONARIO' }
  },
  { 
    path: '/employee', 
    name: 'Funcionários', 
    component: Employee,
    meta: { role: 'FUNCIONARIO' }
  },
  { 
    path: '/employee/employee-registration', 
    name: 'Cadastro de Funcionário', 
    component: EmployeeRegistration,
    meta: { role: 'FUNCIONARIO' }
  },
  { 
    path: '/employee/flight-registration', 
    name: 'Cadastro de Voo', 
    component: FlightRegistration,
    meta: { role: 'FUNCIONARIO' }
  },
  { 
    path: '/employee/employee-edit', 
    name: 'Edição de Funcionário', 
    component: EmployeeEdit,
    meta: { role: 'FUNCIONARIO' }
  },
  {
    path: '/',
    redirect: '/login',
  }  
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

// Middleware de autenticação
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const userRole = localStorage.getItem('userRole') // Armazenado durante o login

  // Se a rota não é pública e não tem token, redireciona para login
  if (!to.meta.public && !token) {
    return next('/login')
  }

  // Se tem token mas a rota requer role específica
  if (token && to.meta.role && to.meta.role !== userRole) {
    // Redireciona para dashboard conforme role
    return next(userRole === 'CLIENTE' ? '/client/dashboard' : '/employee/dashboard')
  }

  next()
})

export default router

