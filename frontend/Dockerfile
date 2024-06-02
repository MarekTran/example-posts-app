# Step 1: Official Node.js 18.20.2 Alpine runtime (maybe use non-alpine)
FROM node:18.20.2-alpine

WORKDIR /app

COPY package*.json ./

RUN npm install

# Copy code, .env supplied during runtime/compose
COPY . .

EXPOSE 3000

RUN npm run build

CMD [ "npm", "run", "start" ]