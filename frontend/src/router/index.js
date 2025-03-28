import { createRouter, createWebHistory } from "vue-router";
import Register from "../views/Register.vue";
import Login from "../views/Login.vue";
import ClientDashboard from "../views/client/ClientDashboard.vue";
import ClientMilesExtract from "../views/client/ClientMilesExtract.vue";
import EmployeeDashboard from "@/views/employee/EmployeeDashboard.vue";

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
  //client routes
  { path: '/client/dashboard', 
    name: "Dashboard do Cliente",
    component: ClientDashboard 
  },
  { path: '/client/miles-extract', 
    name: "Extrato de Milhas",
    component: ClientMilesExtract 
  },

  //employee routes
  { path: '/employee/dashboard', 
    name: "Dashboard do Funcionário",
    component: EmployeeDashboard
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
