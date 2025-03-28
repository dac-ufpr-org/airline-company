import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './assets/base.css'
import '@fortawesome/fontawesome-free/css/all.min.css'; 
import * as Filtros from './plugins/filter'

const app = createApp(App)

app.config.globalProperties.$filters = {}

Object.keys(Filtros).forEach((filtro) => {
  app.config.globalProperties.$filters[filtro] = Filtros[filtro]
})

app.use(router).mount('#app')
