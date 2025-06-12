import axios from 'axios';

// ATENÇÃO: Corrigido para a porta 8000, onde seu API Gateway está rodando.
const API_GATEWAY = 'http://localhost:8000'; // URL CORRETA do API Gateway

export default {
  // src/services/api.js
  async login(email, senha) {
    const response = await axios.post(`${API_GATEWAY}/api/auth/login`, { // Adicionado '/api' ao path
      email: email, // Alterado 'login' para 'email' para corresponder ao v-model e ao que o backend geralmente espera
      password: senha, // Alterado 'senha' para 'password' para corresponder ao que o backend geralmente espera
    });
    
    return {
      token: response.data.token,
      role: response.data.tipo // 'CLIENTE' ou 'FUNCIONARIO'
    };
  },

  // Autocadastro de cliente (SAGA)
  async autocadastrarCliente(dadosCliente) {
    const response = await axios.post(`${API_GATEWAY}/api/clientes/autocadastro`, { // Adicionado '/api' ao path
      cpf: dadosCliente.cpf,
      email: dadosCliente.email,
      name: dadosCliente.name,
      cep: dadosCliente.cep,
      rua: dadosCliente.rua,
      numero: dadosCliente.numero,
      complemento: dadosCliente.complemento,
      cidade: dadosCliente.cidade,
      estado: dadosCliente.estado,
    });
    return response.data;
  },
};