-- 1. Inserir dados na tabela Pessoa
INSERT INTO Pessoa (Nome, Email, Telefone, CPF, Data_nascimento)
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

-- 2. Inserir dados na tabela Endereco
INSERT INTO Endereco (ID_pessoa, Rua, Numero, CEP, Complemento, Sigla_estado, Cidade)
VALUES
    (1, 'Rua A', '100', '01001000', 'Apto 101', 'SP', 'São Paulo'),
    (2, 'Rua B', '200', '02002000', 'Apto 202', 'RJ', 'Rio de Janeiro'),
    (3, 'Rua C', '300', '03003000', 'Casa 303', 'MG', 'Belo Horizonte'),
    (4, 'Rua D', '400', '04004000', 'Casa 404', 'BA', 'Salvador'),
    (5, 'Rua E', '500', '05005000', 'Casa 505', 'PR', 'Curitiba'),
    (6, 'Rua F', '600', '06006000', 'Apto 606', 'RS', 'Porto Alegre'),
    (7, 'Rua G', '700', '07007000', 'Apto 707', 'SP', 'São Paulo'),
    (8, 'Rua H', '800', '08008000', 'Casa 808', 'ES', 'Vitória'),
    (9, 'Rua I', '900', '09009000', 'Casa 909', 'SC', 'Florianópolis'),
    (10, 'Rua J', '1000', '10010000', 'Apto 1010', 'DF', 'Brasília');

-- 3. Inserir dados na tabela Discente
INSERT INTO Discente (ID_pessoa, Registro_academico, Data_ingresso, Status)
VALUES
    (1, 'RA001', '2010-03-01', 'Ativa'),
    (2, 'RA002', '2011-04-15', 'Concluída'),
    (3, 'RA003', '2012-05-20', 'Ativa'),
    (4, 'RA004', '2013-06-25', 'Trancada'),
    (5, 'RA005', '2014-07-10', 'Concluída'),
    (6, 'RA006', '2015-08-15', 'Ativa'),
    (7, 'RA007', '2016-09-20', 'Trancada'),
    (8, 'RA008', '2017-10-25', 'Ativa'),
    (9, 'RA009', '2018-11-30', 'Concluída'),
    (10, 'RA010', '2019-12-05', 'Ativa');

-- 4. Inserir dados na tabela Departamentos
INSERT INTO Departamentos (Nome_departamento)
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

-- 5. Inserir dados na tabela Professores
INSERT INTO Professores (Pessoa_ID, Departamento_ID, Data_contratacao)
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

-- 6. Inserir dados na tabela Disciplinas
INSERT INTO Disciplinas (Nome_disciplina, Carga_horaria, Valor_mensal, Professores_ID)
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

-- 7. Inserir dados na tabela Horarios
INSERT INTO Horarios (Disciplina_ID, Dia_semana, Horario_inicio, Duracao, Numero_sala)
VALUES
    (1, 'Segunda', '08:00:00', 90, '101'),
    (2, 'Terça', '09:00:00', 90, '102'),
    (3, 'Quarta', '10:00:00', 90, '103'),
    (4, 'Quinta', '11:00:00', 90, '104'),
    (5, 'Sexta', '12:00:00', 90, '105'),
    (6, 'Sábado', '13:00:00', 90, '106'),
    (7, 'Domingo', '14:00:00', 90, '107'),
    (8, 'Segunda', '15:00:00', 90, '108'),
    (9, 'Terça', '16:00:00', 90, '109'),
    (10, 'Quarta', '17:00:00', 90, '110');

-- 8. Inserir dados na tabela Cursos
INSERT INTO Cursos (Nome_curso, ID_professor_coordenador)
VALUES
    ('Curso A', 1),
    ('Curso B', 2),
    ('Curso C', 3),
    ('Curso D', 4),
    ('Curso E', 5),
    ('Curso F', 6),
    ('Curso G', 7),
    ('Curso H', 8),
    ('Curso I', 9),
    ('Curso J', 10);

-- 9. Inserir dados na tabela Professor_Curso
INSERT INTO Professor_Curso (ID_Professor, ID_Curso, Data_ingresso)
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

-- 10. Inserir dados na tabela Matricula
INSERT INTO Matricula (ID_discente, ID_disciplina, Nota_final, Ano_matricula, Semestre)
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

-- 11. Inserir dados na tabela Avaliacoes
INSERT INTO Avaliacoes (ID_matricula, Nota, Peso)
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

-- 12. Inserir dados na tabela Pagamentos
INSERT INTO Pagamentos (ID_discente, Data_vencimento, Data_pagamento, Valor, Status_pagamento)
VALUES
    (1, '2023-05-10', '2023-05-09', 500.00, 'Pago'),
    (2, '2023-06-15', '2023-06-14', 550.00, 'Pago'),
    (3, '2023-07-20', '2023-07-19', 600.00, 'Pendente'),
    (4, '2023-08-25', '2023-08-24', 650.00, 'Cancelado'),
    (5, '2023-09-30', '2023-09-29', 700.00, 'Pago'),
    (6, '2023-10-15', '2023-10-14', 750.00, 'Pago'),
    (7, '2023-11-20', '2023-11-19', 800.00, 'Pendente'),
    (8, '2023-12-05', '2023-12-04', 850.00, 'Pago'),
    (9, '2023-01-10', '2023-01-09', 900.00, 'Pago'),
    (10, '2023-02-25', '2023-02-24', 950.00, 'Cancelado');
