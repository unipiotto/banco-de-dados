DROP TABLE IF EXISTS pessoa, endereco, discente, professores, disciplinas, horarios, professor_curso, matricula, avaliacoes, pagamentos, departamentos, cursos, curso_disciplina, avaliacao_professores CASCADE;

-- 1. Pessoa
CREATE TABLE pessoa (
                        ID_pessoa SERIAL PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        telefone VARCHAR(20) NOT NULL,
                        cpf VARCHAR(14) NOT NULL UNIQUE,
                        data_nascimento DATE NOT NULL
);

-- 2. Endereço
CREATE TABLE endereco (
                          ID_endereco SERIAL PRIMARY KEY,
                          pessoa_ID INT NOT NULL,
                          rua VARCHAR(100) NOT NULL,
                          numero VARCHAR(10) NOT NULL,
                          cep VARCHAR(9) NOT NULL,
                          complemento VARCHAR(100),
                          sigla_estado CHAR(2) NOT NULL,
                          cidade VARCHAR(50) NOT NULL,
                          FOREIGN KEY (pessoa_ID) REFERENCES pessoa(ID_pessoa)
                              ON DELETE CASCADE
                              ON UPDATE CASCADE
);

-- 4. Departamentos
CREATE TABLE departamentos (
                               ID_departamento SERIAL PRIMARY KEY,
                               nome_departamento VARCHAR(100) NOT NULL
);



-- 5. Professores
CREATE TABLE professores (
                             ID_professor SERIAL PRIMARY KEY,
                             pessoa_ID INT,
                             departamento_ID INT,
                             data_contratacao DATE NOT NULL,
                             FOREIGN KEY (pessoa_ID) REFERENCES pessoa(ID_pessoa)
                                 ON DELETE CASCADE
                                 ON UPDATE CASCADE,
                             FOREIGN KEY (departamento_ID) REFERENCES departamentos(ID_departamento)
                                 ON DELETE SET NULL
                                 ON UPDATE CASCADE
);

-- 8. Cursos
CREATE TABLE cursos (
                        ID_curso SERIAL PRIMARY KEY,
                        nome_curso VARCHAR(100) NOT NULL,
                        professor_coordenador_ID INT,
                        departamento_ID INT,
                        FOREIGN KEY (professor_coordenador_ID) REFERENCES professores(ID_professor)
                            ON DELETE SET NULL
                            ON UPDATE CASCADE,
                        FOREIGN KEY (departamento_ID) REFERENCES departamentos(ID_departamento)
);

-- 3. Discente
CREATE TABLE discente (
                          ID_discente SERIAL PRIMARY KEY,
                          pessoa_ID INT NOT NULL,
                          registro_academico VARCHAR(20) NOT NULL,
                          data_ingresso DATE NULL,
                          curso_ID INT NOT NULL,
                          status VARCHAR(20) CHECK (status IN ('Ativa', 'Trancada', 'Concluida')),
                          FOREIGN KEY (pessoa_ID) REFERENCES pessoa(ID_pessoa)
                              ON DELETE CASCADE
                              ON UPDATE CASCADE,
                          FOREIGN KEY (curso_ID) REFERENCES cursos(ID_curso)
                              ON DELETE CASCADE
                              ON UPDATE CASCADE
);

-- 6. Disciplinas
CREATE TABLE disciplinas (
                             ID_disciplina SERIAL PRIMARY KEY,
                             nome_disciplina VARCHAR(100) NOT NULL,
                             carga_horaria INT NOT NULL,
                             valor_mensal DECIMAL(10,2) NOT NULL,
                             professor_ID INT,
                             FOREIGN KEY (professor_ID) REFERENCES professores(ID_professor)
                                 ON DELETE SET NULL
                                 ON UPDATE CASCADE
);

-- 7. Horários
CREATE TABLE horarios (
                          ID_horario SERIAL PRIMARY KEY,
                          disciplina_ID INT,
                          dia_semana VARCHAR(20) CHECK (dia_semana IN ('segunda', 'terça', 'quarta', 'quinta', 'sexta', 'sábado', 'domingo')),
                          horario_inicio TIME,
                          duracao INT NOT NULL,
                          numero_sala VARCHAR(10),
                          FOREIGN KEY (disciplina_ID) REFERENCES disciplinas(ID_disciplina)
                              ON DELETE CASCADE
                              ON UPDATE CASCADE
);

-- 9. Professor_Curso
CREATE TABLE professor_curso (
                                 professor_ID INT,
                                 curso_ID INT,
                                 data_ingresso DATE,
                                 PRIMARY KEY (professor_ID, curso_ID),
                                 FOREIGN KEY (professor_ID) REFERENCES professores(ID_professor)
                                     ON DELETE CASCADE
                                     ON UPDATE CASCADE,
                                 FOREIGN KEY (curso_ID) REFERENCES cursos(ID_curso)
                                     ON DELETE CASCADE
                                     ON UPDATE CASCADE
);

-- 10. Matrícula
CREATE TABLE matricula (
                           ID_matricula SERIAL PRIMARY KEY,
                           discente_ID INT,
                           disciplina_ID INT,
                           nota_final DECIMAL(5,2),
                           ano_matricula INT NOT NULL,
                           semestre INT NOT NULL,
                           FOREIGN KEY (discente_ID) REFERENCES discente(ID_discente)
                               ON DELETE CASCADE
                               ON UPDATE CASCADE,
                           FOREIGN KEY (disciplina_ID) REFERENCES disciplinas(ID_disciplina)
                               ON DELETE CASCADE
                               ON UPDATE CASCADE
);

-- 11. Avaliações
CREATE TABLE avaliacoes (
                            ID_avaliacao SERIAL PRIMARY KEY,
                            matricula_ID INT,
                            nota DECIMAL(5,2) NOT NULL,
                            peso DECIMAL(3,2) NOT NULL,
                            FOREIGN KEY (matricula_ID) REFERENCES matricula(ID_matricula)
                                ON DELETE CASCADE
                                ON UPDATE CASCADE
);

-- 12. Pagamentos
CREATE TABLE pagamentos (
                            ID_pagamento SERIAL PRIMARY KEY,
                            discente_ID INT,
                            data_vencimento DATE NOT NULL,
                            data_pagamento DATE,
                            valor DECIMAL(10,2) NOT NULL,
                            status_pagamento VARCHAR(20) CHECK (status_pagamento IN ('pago', 'pendente')),
                            FOREIGN KEY (discente_ID) REFERENCES discente(ID_discente)
                                ON DELETE CASCADE
                                ON UPDATE CASCADE
);

-- 13. Curso_Disciplina
CREATE TABLE curso_disciplina (
                                  curso_ID INT,
                                  disciplina_ID INT,
                                  PRIMARY KEY (curso_ID, disciplina_ID),
                                  FOREIGN KEY (curso_ID) REFERENCES cursos(ID_curso)
                                      ON DELETE CASCADE
                                      ON UPDATE CASCADE,
                                  FOREIGN KEY (disciplina_ID) REFERENCES disciplinas(ID_disciplina)
                                      ON DELETE CASCADE
                                      ON UPDATE CASCADE
);

-- 14. Avaliação de Professores
CREATE TABLE avaliacao_professores (
                                       ID_avaliacao SERIAL PRIMARY KEY,
                                       discente_ID INT,
                                       professor_ID INT,
                                       nota_ensino INT CHECK (nota_ensino BETWEEN 1 AND 5),
                                       comentario TEXT NOT NULL,
                                       data_avaliacao DATE NOT NULL,
                                       FOREIGN KEY (discente_ID) REFERENCES discente(ID_discente)
                                           ON DELETE CASCADE
                                           ON UPDATE CASCADE,
                                       FOREIGN KEY (professor_ID) REFERENCES professores(ID_professor)
                                           ON DELETE CASCADE
                                           ON UPDATE CASCADE
);
