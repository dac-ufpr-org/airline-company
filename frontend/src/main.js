import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import './assets/base.css'
import '@fortawesome/fontawesome-free/css/all.min.css'
import * as Filtros from './plugins/filter'

// Configuração global do Axios
axios.defaults.baseURL = 'http://localhost:8080' // API Gateway
axios.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

axios.interceptors.response.use(
  response => response,
  error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('userRole')
      router.push('/login')
    }
    return Promise.reject(error)
  }
)

const app = createApp(App)

// Adiciona axios como propriedade global
app.config.globalProperties.$http = axios

// Filtros (mantido igual)
app.config.globalProperties.$filters = {}
Object.keys(Filtros).forEach((filtro) => {
  app.config.globalProperties.$filters[filtro] = Filtros[filtro]
})

// Componentes globais (mantido igual)
const components = import.meta.glob('./components/**/*.vue', { eager: true })
for (const path in components) {
  const component = components[path].default
  const componentName = path.split('/').pop().replace(/\.\w+$/, '')
  app.component(componentName, component)
}

app.use(router).mount('#app')