DROP TABLE IF EXISTS Pessoa, Endereco, Discente, Professores, Disciplinas, Horarios, Professor_Curso, Matricula, Avaliacoes, Pagamentos, Departamentos, Cursos, Curso_Disciplina, Avaliacao_Professores CASCADE;

-- 1. Pessoa
CREATE TABLE Pessoa (
                        ID_pessoa SERIAL PRIMARY KEY,
                        Nome VARCHAR(100) NOT NULL,
                        Email VARCHAR(100),
                        Telefone VARCHAR(20),
                        CPF VARCHAR(14),
                        Data_nascimento DATE
);

-- 2. Endereço
CREATE TABLE Endereco (
                          ID_endereco SERIAL PRIMARY KEY,
                          ID_pessoa INT,
                          Rua VARCHAR(100),
                          Numero VARCHAR(10),
                          CEP VARCHAR(9),
                          Complemento VARCHAR(100),
                          Sigla_estado CHAR(2),
                          Cidade VARCHAR(50),
                          FOREIGN KEY (ID_pessoa) REFERENCES Pessoa(ID_pessoa)
                              ON DELETE CASCADE  -- Se a Pessoa for deletada, o Endereco também é deletado
                              ON UPDATE CASCADE  -- Se o ID_pessoa for alterado, o ID_pessoa na tabela Endereco é atualizado
);

-- 3. Discente
CREATE TABLE Discente (
                          ID_Discente SERIAL PRIMARY KEY,
                          ID_pessoa INT,
                          Registro_academico VARCHAR(20),
                          Data_ingresso DATE,
                          Status VARCHAR(20) CHECK (Status IN ('Ativa', 'Trancada', 'Concluída')),
                          FOREIGN KEY (ID_pessoa) REFERENCES Pessoa(ID_pessoa)
                              ON DELETE CASCADE  -- Se a Pessoa for deletada, o Discente também é deletado
                              ON UPDATE CASCADE  -- Se o ID_pessoa for alterado, o ID_pessoa na tabela Discente é atualizado
);

-- 4. Departamentos
CREATE TABLE Departamentos (
                               ID_departamento SERIAL PRIMARY KEY,
                               Nome_departamento VARCHAR(100)
);

-- 5. Professores
CREATE TABLE Professores (
                             ID_professor SERIAL PRIMARY KEY,
                             Pessoa_ID INT,
                             Departamento_ID INT,
                             Data_contratacao DATE,
                             FOREIGN KEY (Pessoa_ID)
                                 REFERENCES Pessoa(ID_pessoa)
                                 ON DELETE CASCADE -- Se a Pessoa for deletada, o Professor também é deletado
                                 ON UPDATE CASCADE, -- Se o ID_pessoa for alterado, o ID_pessoa na tabela Professores é atualizado
                             FOREIGN KEY (Departamento_ID)
                                 REFERENCES Departamentos(ID_departamento)
                                 ON DELETE SET NULL -- Se o Departamento for deletado, o Departamento_ID na tabela Professores é definido como NULL
                                 ON UPDATE CASCADE -- Se o ID_departamento for alterado, o Departamento_ID na tabela Professores é atualizado
);

-- 6. Disciplinas
CREATE TABLE Disciplinas (
                             ID_disciplina SERIAL PRIMARY KEY,
                             Nome_disciplina VARCHAR(100),
                             Carga_horaria INT,
                             Valor_mensal DECIMAL(10,2),
                             Professores_ID INT,
                             FOREIGN KEY (Professores_ID) REFERENCES Professores(ID_professor)
                                 ON DELETE SET NULL  -- Se o Professor for deletado, o Professores_ID na tabela Disciplinas é definido como NULL
                                 ON UPDATE CASCADE  -- Se o ID_professor for alterado, o Professores_ID na tabela Disciplinas é atualizado
);

-- 7. Horarios
CREATE TABLE Horarios (
                          ID_horario SERIAL PRIMARY KEY,
                          Disciplina_ID INT,
                          Dia_semana VARCHAR(20) CHECK (Dia_semana IN ('Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado', 'Domingo')),
                          Horario_inicio TIME,
                          Duracao INT,
                          Numero_sala VARCHAR(10),
                          FOREIGN KEY (Disciplina_ID) REFERENCES Disciplinas(ID_disciplina)
                              ON DELETE CASCADE  -- Se a Disciplina for deletada, o Horario também é deletado
                              ON UPDATE CASCADE  -- Se o ID_disciplina for alterado, o Disciplina_ID na tabela Horarios é atualizado
);

-- 8. Cursos
CREATE TABLE Cursos (
                        ID_curso SERIAL PRIMARY KEY,
                        Nome_curso VARCHAR(100),
                        ID_professor_coordenador INT,
                        FOREIGN KEY (ID_professor_coordenador) REFERENCES Professores(ID_professor)
                            ON DELETE SET NULL  -- Se o Professor for deletado, o ID_professor_coordenador é definido como NULL
                            ON UPDATE CASCADE  -- Se o ID_professor for alterado, o ID_professor_coordenador na tabela Cursos é atualizado
);

-- 9. Professor_Curso
CREATE TABLE Professor_Curso (
                                 ID_Professor INT,
                                 ID_Curso INT,
                                 Data_ingresso DATE,
                                 PRIMARY KEY (ID_Professor, ID_Curso),
                                 FOREIGN KEY (ID_Professor) REFERENCES Professores(ID_professor)
                                     ON DELETE CASCADE  -- Se o Professor for deletado, a relação é deletada
                                     ON UPDATE CASCADE,  -- Se o ID_professor for alterado, o ID_Professor na tabela Professor_Curso é atualizado
                                 FOREIGN KEY (ID_Curso) REFERENCES Cursos(ID_curso)
                                     ON DELETE CASCADE  -- Se o Curso for deletado, a relação é deletada
                                     ON UPDATE CASCADE  -- Se o ID_curso for alterado, o ID_Curso na tabela Professor_Curso é atualizado
);

-- 10. Matricula
CREATE TABLE Matricula (
                           ID_matricula SERIAL PRIMARY KEY,
                           ID_discente INT,
                           ID_disciplina INT,
                           Nota_final DECIMAL(5,2),
                           Ano_matricula INT,
                           Semestre INT,
                           FOREIGN KEY (ID_discente) REFERENCES Discente(ID_Discente)
                               ON DELETE CASCADE  -- Se o Discente for deletado, a Matricula também é deletada
                               ON UPDATE CASCADE,  -- Se o ID_Discente for alterado, o ID_discente na tabela Matricula é atualizado
                           FOREIGN KEY (ID_disciplina) REFERENCES Disciplinas(ID_disciplina)
                               ON DELETE CASCADE  -- Se a Disciplina for deletada, a Matricula também é deletada
                               ON UPDATE CASCADE  -- Se o ID_disciplina for alterado, o ID_disciplina na tabela Matricula é atualizado
);

-- 11. Avaliacoes
CREATE TABLE Avaliacoes (
                            ID_avaliacao SERIAL PRIMARY KEY,
                            ID_matricula INT,
                            Nota DECIMAL(5,2),
                            Peso DECIMAL(3,2),
                            FOREIGN KEY (ID_matricula) REFERENCES Matricula(ID_matricula)
                                ON DELETE CASCADE  -- Se a Matricula for deletada, a Avaliacao também é deletada
                                ON UPDATE CASCADE  -- Se o ID_matricula for alterado, o ID_matricula na tabela Avaliacoes é atualizado
);

-- 12. Pagamentos
CREATE TABLE Pagamentos (
                            ID_pagamento SERIAL PRIMARY KEY,
                            ID_discente INT,
                            Data_vencimento DATE,
                            Data_pagamento DATE,
                            Valor DECIMAL(10,2),
                            Status_pagamento VARCHAR(20) CHECK (Status_pagamento IN ('Pago', 'Pendente', 'Cancelado')),
                            FOREIGN KEY (ID_discente) REFERENCES Discente(ID_Discente)
                                ON DELETE CASCADE  -- Se o Discente for deletado, o Pagamento também é deletado
                                ON UPDATE CASCADE  -- Se o ID_Discente for alterado, o ID_discente na tabela Pagamentos é atualizado
);



-- 13. Curso_Disciplina
CREATE TABLE Curso_Disciplina (
                                  ID_curso INT,
                                  ID_disciplina INT,
                                  PRIMARY KEY (ID_curso, ID_disciplina),
                                  FOREIGN KEY (ID_curso) REFERENCES Cursos(ID_curso)
                                      ON DELETE CASCADE  -- Se o Curso for deletado, a relação é deletada
                                      ON UPDATE CASCADE,  -- Se o ID_curso for alterado, o ID_curso na tabela Curso_Disciplina é atualizado
                                  FOREIGN KEY (ID_disciplina) REFERENCES Disciplinas(ID_disciplina)
                                      ON DELETE CASCADE  -- Se a Disciplina for deletada, a relação é deletada
                                      ON UPDATE CASCADE  -- Se o ID_disciplina for alterado, o ID_disciplina na tabela Curso_Disciplina é atualizado
);

-- 14. Avaliação de Professores
CREATE TABLE Avaliacao_Professores (
                                       ID_avaliacao SERIAL PRIMARY KEY,
                                       ID_discente INT,
                                       ID_professor INT,
                                       Nota_ensino INT CHECK (Nota_ensino BETWEEN 1 AND 5),
                                       Comentario TEXT,
                                       Data_avaliacao DATE,
                                       FOREIGN KEY (ID_discente) REFERENCES Discente(ID_Discente)
                                           ON DELETE CASCADE  -- Se o Discente for deletado, a Avaliacao de Professor também é deletada
                                           ON UPDATE CASCADE,  -- Se o ID_discente for alterado, o ID_discente na tabela Avaliacao_Professores é atualizado
                                       FOREIGN KEY (ID_professor) REFERENCES Professores(ID_professor)
                                           ON DELETE CASCADE  -- Se o Professor for deletado, a Avaliacao de Professor também é deletada
                                           ON UPDATE CASCADE  -- Se o ID_professor for alterado, o ID_professor na tabela Avaliacao_Professores é atualizado
);
