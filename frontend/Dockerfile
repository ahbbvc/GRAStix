FROM node:10
COPY package.json ./
COPY package-lock.json ./
RUN npm install
COPY . ./
CMD [ "npm", "start" ]