FROM node

WORKDIR /usr/src/app
COPY package*.json ./

RUN npm install

COPY . .

# RUN git clone https://github.com/muaz-khan/RTCMultiConnection-Server \
#     && cd RTCMultiConnection-Server \
#     && npm install

# CMD (cd RTCMultiConnection-Server && node server.js)
CMD ["node", "./server.js"]

EXPOSE 9001
