FROM node:18-alpine

WORKDIR /app

# Instala dependências (incluindo devDependencies temporariamente)
COPY package*.json ./
RUN npm install

# Instala curl e dependências básicas
RUN apk add --no-cache curl

# Copia o código fonte
COPY . .

# Configurações de segurança
RUN chown -R node:node /app
USER node

ENV NODE_ENV=production
ENV PORT=8000

EXPOSE 8000

CMD ["npm", "start"]