INSERT INTO plataforma (id, nom) VALUES (1, 'Netflix');
INSERT INTO plataforma (id, nom) VALUES (2, 'Disney+');
INSERT INTO plataforma (id, nom) VALUES (3, 'HBO');

-- Series Netflix
INSERT INTO serie (titol, genere, plataforma_id) VALUES ('Stranger Things', 'Ciencia ficción', 1);
INSERT INTO serie (titol, genere, plataforma_id) VALUES ('The Crown', 'Drama', 1);
INSERT INTO serie (titol, genere, plataforma_id) VALUES ('Black Mirror', 'Ciencia ficción', 1);
INSERT INTO serie (titol, genere, plataforma_id) VALUES ('Ozark', 'Thriller', 1);

-- Series Disney+
INSERT INTO serie (titol, genere, plataforma_id) VALUES ('Loki', 'Acción', 2);
INSERT INTO serie (titol, genere, plataforma_id) VALUES ('WandaVision', 'Acción', 2);
INSERT INTO serie (titol, genere, plataforma_id) VALUES ('The Mandalorian', 'Ciencia ficción', 2);

-- Series HBO
INSERT INTO serie (titol, genere, plataforma_id) VALUES ('Game of Thrones', 'Fantasía', 3);
INSERT INTO serie (titol, genere, plataforma_id) VALUES ('Chernobyl', 'Drama', 3);
INSERT INTO serie (titol, genere, plataforma_id) VALUES ('The Last of Us', 'Drama', 3);