FROM mongo:4.4.0-bionic

COPY user_setup.js /app/

VOLUME [ "./database-mdc/data:/data/db" ]

ENV MONGO_INITDB_ROOT_USERNAME=root
ENV MONGO_INITDB_ROOT_PASSWORD=rootpassword

EXPOSE 27017
