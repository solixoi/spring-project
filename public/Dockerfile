FROM node:18 AS build
WORKDIR /app

COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 80

CMD ["npm", "run", "dev"]
