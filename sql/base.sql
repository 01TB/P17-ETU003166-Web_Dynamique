CREATE DATABASE examWebDyn;

USE examWebDyn;

CREATE TABLE prevision(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(255) NOT NULL,
    montant DECIMAL(10,2) NOT NULL,
    date DATE NOT NULL
);

CREATE TABLE depense(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    id_prevision INTEGER NOT NULL,
    montant DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_prevision) REFERENCES prevision(id)
);