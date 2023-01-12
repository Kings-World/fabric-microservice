FROM node:lts-alpine AS base
WORKDIR /app

FROM base AS deps
COPY package.json yarn.lock ./
RUN yarn install --frozen-lockfile

FROM base AS builder
COPY . .
COPY --from=deps /app/node_modules node_modules
RUN yarn build

FROM base as runner
ENV NODE_ENV production
COPY --from=builder /app/package.json /app/yarn.lock ./
COPY --from=builder /app/node_modules node_modules
COPY --from=builder /app/dist dist
CMD yarn start
