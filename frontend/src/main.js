import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './assets/base.css'
import '@fortawesome/fontawesome-free/css/all.min.css'
import * as Filtros from './plugins/filter'

const app = createApp(App)

app.config.globalProperties.$filters = {}
Object.keys(Filtros).forEach((filtro) => {
  app.config.globalProperties.$filters[filtro] = Filtros[filtro]
})

const components = import.meta.glob('./components/**/*.vue', { eager: true })

for (const path in components) {
  const component = components[path].default
  const componentName = path
    .split('/')
    .pop()
    .replace(/\.\w+$/, '')

  app.component(componentName, component)
}

app.use(router).mount('#app')
