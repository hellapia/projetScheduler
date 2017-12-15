DELETE FROM `category`;
DELETE FROM `tasks`;


INSERT INTO `category`(`id_category`,`name`,`color`) VALUES (1,'professionnelle','#111111');
INSERT INTO `category`(`id_category`,`name`,`color`) VALUES (2,'privee', '#222222');

INSERT INTO `tasks`(`id`, name, description, start_date, start_time, category, duration) VALUES (1, 'my title 1','first description', '2017-12-26', '22:30', 2 , '01:30');
INSERT INTO `tasks`(`id`, name, description, start_date, start_time, category, duration) VALUES (2, 'Rendre projet devWeb','rendre .zip sur devweb.chticod.eu', '2017-12-17', '20:30', 1 , '00:30');
INSERT INTO `tasks`(`id`, name, description, start_date, start_time, category, duration) VALUES (3, 'Envoyer CV JE','participer au concours Alten', '2017-11-20', '15:30', 1 , '01:00');
INSERT INTO `tasks`(`id`, name, description, start_date, start_time, category, duration) VALUES (4, 'Faire courses de Noel','Acheter des décorations et nourriture', '2017-12-22', '13:30', 2 , '02:30');
INSERT INTO `tasks`(`id`, name, description, start_date, start_time, category, duration) VALUES (5, 'Rdv client projet 100h','réunion à la clinique de HALLUIN pour parler de la maquette ', '2018-01-10', '15:00', 1 , '01:30');