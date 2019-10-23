CREATE DATABASE phones_stock ENCODING 'UTF-8';

CREATE TABLE IF NOT EXISTS roles (
                                     id   SERIAL PRIMARY KEY,
                                     role VARCHAR(5) NOT NULL
);

INSERT INTO roles (id, role) VALUES (DEFAULT, 'admin');
INSERT INTO roles (id, role) VALUES (DEFAULT, 'user');


CREATE TABLE IF NOT EXISTS users (
                                     id       SERIAL PRIMARY KEY,
                                     login    VARCHAR(10) UNIQUE NOT NULL,
                                     password VARCHAR(10) UNIQUE NOT NULL,
                                     role     INTEGER     NOT NULL,
                                     FOREIGN KEY (role) REFERENCES roles (id)
);


INSERT INTO users (id, login, password, role) VALUES (DEFAULT, 'Andrew', 'an123', 1);
INSERT INTO users (id, login, password, role)VALUES (DEFAULT, 'Roman', 'rom123', 2);



CREATE TABLE IF NOT EXISTS phone_models (
                                            id   SERIAL PRIMARY KEY,
                                            name VARCHAR(15) UNIQUE NOT NULL
);

INSERT INTO phone_models (id, name) VALUES (DEFAULT, 'samsung')
    RETURNING id;

INSERT INTO phone_models (id, name) VALUES (DEFAULT, 'iphone')
    RETURNING id;

INSERT INTO phone_models (id, name) VALUES (DEFAULT, 'xaomi')
    RETURNING id;

INSERT INTO phone_models (id, name) VALUES (DEFAULT, 'huawey')
    RETURNING id;