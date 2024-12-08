package com.bancoDeDados.service;

import com.bancoDeDados.model.Matricula;
import com.bancoDeDados.repository.dao.MatriculaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaDAO matriculaDAO;

    public void matricularDiscenteEmDisciplina(Long idDiscente, Long idDisciplina) {

        LocalDate hoje = LocalDate.now();
        int anoAtual = hoje.getYear();
        int mesAtual = hoje.getMonthValue();
        int semestre = (mesAtual < 6) ? 1 : 2;

        Matricula matricula = Matricula.builder()
                .idDiscente(idDiscente)
                .idDisciplina(idDisciplina)
                .semestre(semestre)
                .anoMatricula(anoAtual)
                .build();
        matriculaDAO.adicionarMatricula(matricula);
    }
}
