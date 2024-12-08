-- 1. Inserir dados na tabela pessoa
INSERT INTO pessoa (nome, email, telefone, cpf, data_nascimento)
VALUES
    ('João Silva', 'joao.silva@example.com', '11999999999', '12345678901', '1990-01-15'),
    ('Maria Oliveira', 'maria.oliveira@example.com', '11988888888', '98765432100', '1985-06-20'),
    ('Carlos Pereira', 'carlos.pereira@example.com', '11977777777', '10293847561', '1992-09-10'),
    ('Ana Santos', 'ana.santos@example.com', '11966666666', '56473829101', '1991-02-15'),
    ('Lucas Lima', 'lucas.lima@example.com', '11955555555', '35482617120', '1989-12-05'),
    ('Paula Costa', 'paula.costa@example.com', '11944444444', '12121212123', '1993-08-25'),
    ('Roberto Almeida', 'roberto.almeida@example.com', '11933333333', '21436587901', '1987-03-10'),
    ('Fernanda Silva', 'fernanda.silva@example.com', '11922222222', '33221144556', '1994-04-30'),
    ('Eduardo Rocha', 'eduardo.rocha@example.com', '11911111111', '55667788900', '1988-11-22'),
    ('Juliana Souza', 'juliana.souza@example.com', '11900000000', '77665544332', '1995-07-17');

-- 2. Inserir dados na tabela endereco
INSERT INTO endereco (pessoa_id, rua, numero, cep, complemento, sigla_estado, cidade)
VALUES
    (3, 'Rua C', '300', '03003000', 'Casa 303', 'MG', 'Belo Horizonte'),
    (4, 'Rua D', '400', '04004000', 'Casa 404', 'BA', 'Salvador'),
    (5, 'Rua E', '500', '05005000', 'Casa 505', 'PR', 'Curitiba'),
    (8, 'Rua H', '800', '08008000', 'Casa 808', 'ES', 'Vitória'),
    (9, 'Rua I', '900', '09009000', 'Casa 909', 'SC', 'Florianópolis'),
    (10, 'Rua J', '1000', '10010000', 'Apto 1010', 'DF', 'Brasília');

-- 2.1 Inserir dados na tabela endereco sem complemento
INSERT INTO endereco (pessoa_id, rua, numero, cep, sigla_estado, cidade)
VALUES
    (1, 'Rua A', '100', '01001000', 'SP', 'São Paulo'),
    (2, 'Rua B', '200', '02002000',  'RJ', 'Rio de Janeiro'),
    (6, 'Rua F', '600', '06006000', 'RS', 'Porto Alegre'),
    (7, 'Rua G', '700', '07007000',  'SP', 'São Paulo');

-- 3. Inserir dados na tabela discente
INSERT INTO discente (pessoa_id, registro_academico, data_ingresso, status)
VALUES
    (1, '2010.1.08.001', '2010-02-10', 'Ativa'),
    (2, '2011.1.08.002', '2011-02-15', 'Concluida'),
    (3, '2012.1.08.003', '2012-02-12', 'Ativa'),
    (4, '2013.1.08.004', '2013-06-05', 'Trancada'),
    (5, '2014.1.08.005', '2014-02-10', 'Concluida'),
    (6, '2015.1.08.006', '2015-06-15', 'Ativa'),
    (7, '2016.1.08.007', '2016-06-20', 'Trancada'),
    (8, '2017.1.08.008', '2017-06-10', 'Ativa'),
    (9, '2018.1.08.009', '2018-02-02', 'Concluida'),
    (10, '2019.1.08.010', '2019-02-05', 'Ativa');

-- 4. Inserir dados na tabela departamentos
INSERT INTO departamentos (nome_departamento)
VALUES
    ('Departamento A'),
    ('Departamento B'),
    ('Departamento C'),
    ('Departamento D'),
    ('Departamento E'),
    ('Departamento F'),
    ('Departamento G'),
    ('Departamento H'),
    ('Departamento I'),
    ('Departamento J');

-- 5. Inserir dados na tabela professores
INSERT INTO professores (pessoa_id, departamento_id, data_contratacao)
VALUES
    (1, 1, '2010-03-01'),
    (2, 2, '2011-04-15'),
    (3, 3, '2012-05-20'),
    (4, 4, '2013-06-25'),
    (5, 5, '2014-07-10'),
    (6, 6, '2015-08-15'),
    (7, 7, '2016-09-20'),
    (8, 8, '2017-10-25'),
    (9, 9, '2018-11-30'),
    (10, 10, '2019-12-05');

-- 6. Inserir dados na tabela disciplinas
INSERT INTO disciplinas (nome_disciplina, carga_horaria, valor_mensal, professor_id)
VALUES
    ('Matemática', 60, 500.00, 1),
    ('Física', 60, 550.00, 2),
    ('Química', 60, 600.00, 3),
    ('Biologia', 60, 650.00, 4),
    ('História', 60, 700.00, 5),
    ('Geografia', 60, 750.00, 6),
    ('Literatura', 60, 800.00, 7),
    ('Filosofia', 60, 850.00, 8),
    ('Sociologia', 60, 900.00, 9),
    ('Inglês', 60, 950.00, 10);

-- 7. Inserir dados na tabela horarios
INSERT INTO horarios (disciplina_id, dia_semana, horario_inicio, duracao, numero_sala)
VALUES
    (1, 'segunda', '08:00:00', 90, '101'),
    (1, 'terça', '09:00:00', 90, '102'),
    (1, 'quarta', '10:00:00', 90, '103'),
    (4, 'quinta', '11:00:00', 90, '104'),
    (5, 'sexta', '12:00:00', 90, '105'),
    (7, 'domingo', '14:00:00', 90, '107'),
    (8, 'segunda', '15:00:00', 90, '108'),
    (9, 'terça', '16:00:00', 90, '109'),
    (10, 'quarta', '17:00:00', 90, '110');

-- 8. Inserir dados na tabela cursos
INSERT INTO cursos (nome_curso, professor_coordenador_id, departamento_id)
VALUES
    ('Curso A', 1, 1),
    ('Curso B', 2, 2),
    ('Curso C', 3, 3),
    ('Curso D', 4, 4),
    ('Curso E', 5, 5),
    ('Curso F', 6, 6),
    ('Curso G', 7, 7),
    ('Curso H', 8, 8),
    ('Curso I', 9, 9),
    ('Curso J', 10, 10);

-- 9. Inserir dados na tabela professor_curso
INSERT INTO professor_curso (professor_id, curso_id, data_ingresso)
VALUES
    (1, 1, '2010-03-01'),
    (2, 2, '2011-04-15'),
    (3, 3, '2012-05-20'),
    (4, 4, '2013-06-25'),
    (5, 5, '2014-07-10'),
    (6, 6, '2015-08-15'),
    (7, 7, '2016-09-20'),
    (8, 8, '2017-10-25'),
    (9, 9, '2018-11-30'),
    (10, 10, '2019-12-05');

-- 10. Inserir dados na tabela matricula
INSERT INTO matricula (discente_id, disciplina_id, nota_final, ano_matricula, semestre)
VALUES
    (1, 1, 8.5, 2020, 1),
    (2, 2, 9.0, 2020, 2),
    (3, 3, 7.5, 2020, 1),
    (4, 4, 6.0, 2020, 2),
    (5, 5, 8.0, 2021, 1),
    (6, 6, 9.5, 2021, 2),
    (7, 7, 7.0, 2021, 1),
    (8, 8, 9.0, 2021, 2),
    (9, 9, 6.5, 2021, 1),
    (10, 10, 8.0, 2021, 2);

-- 11. Inserir dados na tabela avaliacoes
INSERT INTO avaliacoes (matricula_id, nota, peso)
VALUES
    (1, 8.5, 0.8),
    (2, 9.0, 0.9),
    (3, 7.5, 0.85),
    (4, 6.0, 0.75),
    (5, 8.0, 0.8),
    (6, 9.5, 0.95),
    (7, 7.0, 0.7),
    (8, 9.0, 0.9),
    (9, 6.5, 0.65),
    (10, 8.0, 0.8);

-- 12. Inserir dados na tabela pagamentos
INSERT INTO pagamentos (discente_id, data_vencimento, data_pagamento, valor, status_pagamento)
VALUES
    (1, '2023-07-20', '2023-07-19', 600.00, 'pago'),
    (1, '2023-08-25', '2023-08-24', 650.00, 'pago'),
    (1, '2023-09-30', '2023-09-29', 700.00, 'pago'),
    (1, '2023-10-15', '2023-10-14', 750.00, 'pago'),
    (1, '2023-11-20', '2023-11-19', 800.00, 'pago'),
    (1, '2023-12-05', '2023-12-04', 850.00, 'pago'),
    (1, '2023-01-10', '2023-01-09', 900.00, 'pago'),
    (10, '2023-02-25', '2023-02-24', 950.00, 'pago');

INSERT INTO pagamentos (discente_id, data_vencimento, valor, status_pagamento)
VALUES
    (1, '2024-12-31', 550.00, 'pendente'),
    (1, '2023-05-10', 550.00, 'pendente');

-- 13. Inserir dados na tabela curso_disciplina
INSERT INTO curso_disciplina (curso_id, disciplina_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 6),
    (7, 7),
    (8, 8),
    (9, 9),
    (10, 10);

-- 14. Inserir dados na tabela avaliacao_professores
INSERT INTO avaliacao_professores (discente_id, professor_id, nota_ensino, comentario, data_avaliacao)
VALUES
    (1, 1, 5, 'Ótimo professor', '2023-05-01'),
    (2, 2, 4, 'Bom professor', '2023-06-01'),
    (3, 3, 3, 'Regular', '2023-07-01'),
    (4, 4, 2, 'Precisava melhorar', '2023-08-01'),
    (5, 5, 1, 'Não gostei', '2023-09-01'),
    (6, 6, 5, 'Excelente', '2023-10-01'),
    (7, 7, 4, 'Muito bom', '2023-11-01'),
    (8, 8, 3, 'Bom, mas pode melhorar', '2023-12-01'),
    (9, 9, 2, 'Difícil de acompanhar', '2024-01-01'),
    (10, 10, 5, 'Fantástico', '2024-02-01');
