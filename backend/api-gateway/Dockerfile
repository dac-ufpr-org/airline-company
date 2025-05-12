# Imagem base
FROM node:18

# Diretório de trabalho dentro do container
WORKDIR /usr/src/app

# Copia os arquivos de dependência
COPY package*.json ./

# Instala as dependências
RUN npm install --production

# Copia todo o restante do código
COPY . .

# Expõe a porta
EXPOSE 8000

# Comando de execução
CMD [ "node", "app.js" ]
