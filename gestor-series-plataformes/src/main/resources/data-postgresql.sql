-- Crear tablas
CREATE TABLE plataforma (
    id BIGINT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL
);

CREATE TABLE serie (
    id BIGSERIAL PRIMARY KEY,
    titol VARCHAR(255) NOT NULL,
    genere VARCHAR(100) NOT NULL,
    plataforma_id BIGINT NOT NULL,
    FOREIGN KEY (plataforma_id) REFERENCES plataforma (id)
);

-- Insertar datos
INSERT INTO plataforma (id, nom) VALUES (1, 'Netflix');

INSERT INTO plataforma (id, nom) VALUES (2, 'Disney+');

INSERT INTO plataforma (id, nom) VALUES (3, 'HBO');

INSERT INTO plataforma (id, nom) VALUES (4, 'Amazon Prime');

INSERT INTO plataforma (id, nom) VALUES (5, 'Apple TV+');

INSERT INTO plataforma (id, nom) VALUES (6, 'Hulu');

INSERT INTO plataforma (id, nom) VALUES (7, 'Max');

INSERT INTO plataforma (id, nom) VALUES (8, 'Paramount+');

INSERT INTO plataforma (id, nom) VALUES (9, 'Crunchyroll');

INSERT INTO plataforma (id, nom) VALUES (10, 'YouTube Premium');

-- Series Netflix
INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES (
        'Stranger Things',
        'Ciencia ficción',
        1
    );

INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES ('The Crown', 'Drama', 1);

INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES (
        'Black Mirror',
        'Ciencia ficción',
        1
    );

INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES ('Ozark', 'Thriller', 1);

-- Series Disney+
INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES ('Loki', 'Acción', 2);

INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES ('WandaVision', 'Acción', 2);

INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES (
        'The Mandalorian',
        'Ciencia ficción',
        2
    );

-- Series HBO
INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES (
        'Game of Thrones',
        'Fantasía',
        3
    );

INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES ('Chernobyl', 'Drama', 3);

INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES ('The Last of Us', 'Drama', 3);

-- Series Amazon Prime
INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES ('The Boys', 'Acción', 4);

INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES ('Reacher', 'Acción', 4);

-- Series Apple TV+
INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES ('Ted Lasso', 'Comedia', 5);

INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES (
        'The Morning Show',
        'Drama',
        5
    );

-- Series Hulu
INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES (
        'The Handmaid''s Tale',
        'Drama',
        6
    );

INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES ('Only Murders', 'Comedia', 6);

-- Series Max
INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES ('Succession', 'Drama', 7);

-- Series Paramount+
INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES ('Yellowstone', 'Western', 8);

-- Series Crunchyroll
INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES ('Attack on Titan', 'Anime', 9);

INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES ('Frieren', 'Anime', 9);

INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES ('Boku no hero', 'Anime', 9);

INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES ('Oshi No Ko', 'Anime', 9);

-- Series YouTube Premium
INSERT INTO
    serie (titol, genere, plataforma_id)
VALUES ('Cobra Kai', 'Acción', 10);