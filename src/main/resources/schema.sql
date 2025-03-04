
CREATE TABLE Client(
    userID SERIAL PRIMARY KEY,
    username varchar(250) NOT NULL,
    password varchar(250) NOT NULL,
    email varchar(250) NOT NULL,
    role varchar(250) NOT NULL
);

CREATE TABLE Expense(
    id SERIAL PRIMARY KEY,
    username varchar(250) NOT NULL,
    amount INT NOT NULL,
    category varchar(250) NOT NULL,
    description varchar(250) NOT NULL,
    date timestamp NOT NULL,
    version INT
);
DROP TABLE IF EXISTS INCOME;
CREATE TABLE Income(
    id SERIAL PRIMARY KEY,
    username varchar(250) NOT NULL,
    amount INT NOT NULL,
    source varchar(250) NOT NULL,
    date timestamp NOT NULL,
    description varchar(250) NOT NULL,
    version INT

);



