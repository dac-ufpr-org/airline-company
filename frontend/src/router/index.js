import { createRouter, createWebHistory } from "vue-router";
import Register from "../views/Register.vue";
import Login from "../views/Login.vue";
import ClientDashboard from '../views/ClientDashboard.vue';
import FuncionarioDashboard from "@/views/FuncionarioDashboard.vue";

const routes = [
  {
    path: "/register",
    name: "register",
    component: Register,
  },
  {
    path: "/login",
    name: "login",
    component: Login,
  },
  { path: '/client/dashboard', 
    name: "clientDashboard",
    component: ClientDashboard 
  },
  { path: '/funcionario/dashboard', 
    name: "funcionarioDashboard",
    component: FuncionarioDashboard
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
