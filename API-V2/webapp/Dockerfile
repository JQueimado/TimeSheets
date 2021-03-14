FROM node:14.8.0-alpine3.10

WORKDIR /app

COPY package.json /app/
RUN npm install

RUN npm install -g react-scripts

CMD ["npm","start"]
