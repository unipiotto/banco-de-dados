# Universidade Next Level

## Sumário

## Sobre o projeto

Projeto de criação de um banco de dados da disciplina Banco de Dados [DCE534] (A) (08) - 2024/2, ministrada pela professora Mariane Moreira de Souza.

### Membros do grupo

- Vinicius Boiago - 2023.1.08.024
- Rodrigo Rolucatelli - 2023.1.08.019
- Leonardo Bonardi - 2023.1.08.011
- Jose Olavo - 2023.1.08.009

## Banco de dados

### Descrição Inicial das tabelas

#### **1. Pessoa**
- **ID_pessoa** (PK)
- **Nome**
- **Email**
- **Telefone**
- **CPF**
- **Data_nascimento**

#### **2. Endereço**
- **ID_endereco** (PK)
- **ID_pessoa** (FK)
- **Rua**
- **Numero**
- **CEP**
- **Complemento**
- **Sigla_estado** (SP, RJ, ...)
- **Cidade**

#### **3. Discente**
- **ID_Discente** (PK)
- **ID_pessoa** (FK)
- **Registro_academico**
- **Data_ingresso**
- **Status** (Ativa, Trancada, Concluída)

#### **4. Professores**
- **ID_professor** (PK)
- **Pessoa_ID** (FK)
- **Departamento_ID** (FK)
- **Data_contratacao**

#### **5. Disciplinas**
- **ID_disciplina** (PK)
- **Nome_disciplina**
- **Carga_horaria**
- **Valor_mensal**
- **Professores_ID** (FK)

#### **6. Horarios**
- **ID_horario** (PK)
- **Disciplina_ID** (FK)
- **Dia_semana**
- **Horario_inicio**
- **Duracao**
- **Numero_sala**

#### **7. Professor_Curso**
- **ID_Professor** (PK)
- **ID_Curso** (PK)
- **Data_ingresso**

#### **8. Matricula**
- **ID_matricula** (PK)
- **ID_discente** (FK)
- **ID_disciplina** (FK)
- **Nota_final**
- **Ano_matricula**
- **Semestre**

#### **9. Avaliacoes**
- **ID_avaliacao** (PK)
- **ID_matricula** (FK)
- **Nota**
- **Peso**

#### **10. Pagamentos**
- **ID_pagamento** (PK)
- **ID_discente** (FK)
- **Data_vencimento**
- **Data_pagamento**
- **Valor**
- **Status_pagamento** (Pago, Pendente, Cancelado)

#### **11. Departamentos**
- **ID_departamento** (PK)
- **Nome_departamento**

#### **12. Cursos**
- **ID_curso** (PK)
- **Nome_curso**
- **ID_professor_coordenador**

#### **13. Curso_Disciplina**
- **ID_curso** (PK)
- **ID_disciplina** (PK)

#### **14. Avaliações de Professores**
- **ID_avaliacao** (PK)
- **ID_discente** (FK)
- **ID_professor** (FK)
- **Nota_ensino** (1 a 5)
- **Comentário**
- **Data_avaliacao**

### Modelo Entidade Relacionamento

## Como rodar a aplicação

### Conexão com o banco de dados
1. Rodar o docker compose presente na pasta local:
```
docker compose up -d
```
2. Estabelecer conexão com uma ferramenta de administração de BDs.
```
Host: localhost
Porta: 5432
Banco de dados: escola
Usuário: unifalmg
Senha: unifalmg123
```


### Pré-requisitos

- [Docker](https://docs.docker.com/desktop/setup/install/windows-install/)
- [DBeaver](https://dbeaver.io/) (recomendação)
