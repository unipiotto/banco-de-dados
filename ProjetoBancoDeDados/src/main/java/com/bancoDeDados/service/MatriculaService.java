package com.bancoDeDados.service;

import com.bancoDeDados.model.Disciplina;
import com.bancoDeDados.model.Matricula;
import com.bancoDeDados.model.Pagamento;
import com.bancoDeDados.repository.dao.DisciplinaDAO;
import com.bancoDeDados.repository.dao.MatriculaDAO;
import com.bancoDeDados.repository.dao.PagamentoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaDAO matriculaDAO;
    @Autowired
    private PagamentoDAO pagamentoDAO;
    @Autowired
    private DisciplinaDAO disciplinaDAO;

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

        Pagamento pagamento = pagamentoDAO.buscarPagamentoDoMes(mesAtual, anoAtual, idDiscente);

        Disciplina disciplina = disciplinaDAO.buscarDisciplinaPorId(idDisciplina);

        BigDecimal valorAntigo = pagamento.getValor();
        BigDecimal novoValor = valorAntigo.add(disciplina.getValorMensal());

        pagamentoDAO.atualizarValorPagamento(pagamento.getIdPagamento(), novoValor);
    }
}
