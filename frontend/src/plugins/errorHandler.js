export default {
    install(Vue) {
      Vue.config.errorHandler = (err) => {
        if (err.response?.status === 401) {
          localStorage.removeItem('token');
          router.push({ name: 'login' });
        }
        console.error("Erro global:", err);
      };
    },
  };