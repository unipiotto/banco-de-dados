-- 1. Pessoa
CREATE TABLE Pessoa (
    ID_pessoa INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(100) NOT NULL,
    Email VARCHAR(100),
    Telefone VARCHAR(20),
    CPF VARCHAR(14),
    Data_nascimento DATE
);

-- 2. Endereço
CREATE TABLE Endereco (
    ID_endereco INT PRIMARY KEY AUTO_INCREMENT,
    ID_pessoa INT,
    Rua VARCHAR(100),
    Numero VARCHAR(10),
    CEP VARCHAR(9),
    Complemento VARCHAR(100),
    Sigla_estado CHAR(2),
    Cidade VARCHAR(50),
    FOREIGN KEY (ID_pessoa) REFERENCES Pessoa(ID_pessoa)
);

-- 3. Discente
CREATE TABLE Discente (
    ID_Discente INT PRIMARY KEY AUTO_INCREMENT,
    ID_pessoa INT,
    Registro_academico VARCHAR(20),
    Data_ingresso DATE,
    Status ENUM('Ativa', 'Trancada', 'Concluída'),
    FOREIGN KEY (ID_pessoa) REFERENCES Pessoa(ID_pessoa)
);

-- 4. Professores
CREATE TABLE Professores (
    ID_professor INT PRIMARY KEY AUTO_INCREMENT,
    Pessoa_ID INT,
    Departamento_ID INT,
    Data_contratacao DATE,
    FOREIGN KEY (Pessoa_ID) REFERENCES Pessoa(ID_pessoa),
    FOREIGN KEY (Departamento_ID) REFERENCES Departamentos(ID_departamento)
);

-- 5. Disciplinas
CREATE TABLE Disciplinas (
    ID_disciplina INT PRIMARY KEY AUTO_INCREMENT,
    Nome_disciplina VARCHAR(100),
    Carga_horaria INT,
    Valor_mensal DECIMAL(10,2),
    Professores_ID INT,
    FOREIGN KEY (Professores_ID) REFERENCES Professores(ID_professor)
);

-- 6. Horarios
CREATE TABLE Horarios (
    ID_horario INT PRIMARY KEY AUTO_INCREMENT,
    Disciplina_ID INT,
    Dia_semana ENUM('Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado', 'Domingo'),
    Horario_inicio TIME,
    Duracao INT,
    Numero_sala VARCHAR(10),
    FOREIGN KEY (Disciplina_ID) REFERENCES Disciplinas(ID_disciplina)
);

-- 7. Professor_Curso
CREATE TABLE Professor_Curso (
    ID_Professor INT,
    ID_Curso INT,
    Data_ingresso DATE,
    PRIMARY KEY (ID_Professor, ID_Curso),
    FOREIGN KEY (ID_Professor) REFERENCES Professores(ID_professor),
    FOREIGN KEY (ID_Curso) REFERENCES Cursos(ID_curso)
);

-- 8. Matricula
CREATE TABLE Matricula (
    ID_matricula INT PRIMARY KEY AUTO_INCREMENT,
    ID_discente INT,
    ID_disciplina INT,
    Nota_final DECIMAL(5,2),
    Ano_matricula INT,
    Semestre INT,
    FOREIGN KEY (ID_discente) REFERENCES Discente(ID_Discente),
    FOREIGN KEY (ID_disciplina) REFERENCES Disciplinas(ID_disciplina)
);

-- 9. Avaliacoes
CREATE TABLE Avaliacoes (
    ID_avaliacao INT PRIMARY KEY AUTO_INCREMENT,
    ID_matricula INT,
    Nota DECIMAL(5,2),
    Peso DECIMAL(3,2),
    FOREIGN KEY (ID_matricula) REFERENCES Matricula(ID_matricula),
    CHECK (Nota BETWEEN 0 AND 100),
    CHECK (Peso BETWEEN 0 AND 1)
);

-- 10. Pagamentos
CREATE TABLE Pagamentos (
    ID_pagamento INT PRIMARY KEY AUTO_INCREMENT,
    ID_discente INT,
    Data_vencimento DATE,
    Data_pagamento DATE,
    Valor DECIMAL(10,2),
    Status_pagamento ENUM('Pago', 'Pendente', 'Cancelado'),
    FOREIGN KEY (ID_discente) REFERENCES Discente(ID_Discente)
);

-- 11. Departamentos
CREATE TABLE Departamentos (
    ID_departamento INT PRIMARY KEY AUTO_INCREMENT,
    Nome_departamento VARCHAR(100),
    ID_professor_chefe INT,
    FOREIGN KEY (ID_professor_chefe) REFERENCES Professores(ID_professor)
);

-- 12. Cursos
CREATE TABLE Cursos (
    ID_curso INT PRIMARY KEY AUTO_INCREMENT,
    Nome_curso VARCHAR(100),
    ID_professor_coordenador INT,
    FOREIGN KEY (ID_professor_coordenador) REFERENCES Professores(ID_professor)
);

-- 13. Curso_Disciplina
CREATE TABLE Curso_Disciplina (
    ID_curso INT,
    ID_disciplina INT,
    PRIMARY KEY (ID_curso, ID_disciplina),
    FOREIGN KEY (ID_curso) REFERENCES Cursos(ID_curso),
    FOREIGN KEY (ID_disciplina) REFERENCES Disciplinas(ID_disciplina)
);

-- 14. Avaliação de Professores
CREATE TABLE Avaliacao_Professores (
    ID_avaliacao INT PRIMARY KEY AUTO_INCREMENT,
    ID_discente INT,
    ID_professor INT,
    Nota_ensino INT,
    Comentario TEXT,
    Data_avaliacao DATE,
    FOREIGN KEY (ID_discente) REFERENCES Discente(ID_Discente),
    FOREIGN KEY (ID_professor) REFERENCES Professores(ID_professor),
    CHECK (Nota_ensino BETWEEN 1 AND 5)
);
