import axios from 'axios';

const API_GATEWAY = 'http://localhost:8080'; // URL do API Gateway

export default {
  // src/services/api.js
  async login(email, senha) {
    const response = await axios.post(`${API_GATEWAY}/auth/login`, {
      login: email,
      senha: senha,
    });
    
    return {
      token: response.data.token,
      role: response.data.tipo // 'CLIENTE' ou 'FUNCIONARIO'
    };
  },

  // Autocadastro de cliente (SAGA)
  async autocadastrarCliente(dadosCliente) {
    const response = await axios.post(`${API_GATEWAY}/clientes/autocadastro`, {
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