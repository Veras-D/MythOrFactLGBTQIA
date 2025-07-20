FROM node:18 AS builder

WORKDIR /app

COPY package*.json ./

RUN npm ci

COPY . .

RUN npm run build

FROM node:18-slim AS production

WORKDIR /app

ENV NODE_ENV=production
ENV VITE_API_URL=https://mythorfactlgbtqia.onrender.com/api

COPY --from=builder /app/dist ./dist
COPY --from=builder /app/package*.json ./
COPY --from=builder /app/node_modules ./node_modules

EXPOSE 5173

CMD ["npm", "run", "preview"]