FROM node:14.8.0-alpine3.10

WORKDIR /app

COPY package.json /app/

RUN npm install -g nodemon

RUN npm install

CMD ["nodemon","app.js"]
