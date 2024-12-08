package com.bancoDeDados.service;

import com.bancoDeDados.model.AvaliacaoProfessor;
import com.bancoDeDados.model.Discente;
import com.bancoDeDados.model.Professor;
import com.bancoDeDados.model.dto.AvaliacaoProfessorForm;
import com.bancoDeDados.repository.AvaliacaoProfessorRepository;
import com.bancoDeDados.repository.dao.AvaliacaoProfessorDAO;
import com.bancoDeDados.repository.dao.DiscenteDAO;
import com.bancoDeDados.repository.dao.ProfessorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AvaliacaoProfessorService {

    private final AvaliacaoProfessorDAO avaliacaoProfessorDAO;
    private final AvaliacaoProfessorRepository repository;
    private final DiscenteDAO discenteDAO;
    private final ProfessorDAO professorDAO;

    @Autowired
    public AvaliacaoProfessorService(AvaliacaoProfessorDAO avaliacaoProfessorDAO, AvaliacaoProfessorRepository repository, DiscenteDAO discenteDAO, ProfessorDAO professorDAO) {
        this.avaliacaoProfessorDAO = avaliacaoProfessorDAO;
        this.repository = repository;
        this.discenteDAO = discenteDAO;
        this.professorDAO = professorDAO;
    }

    //(discente_ID, professor_ID, data_contratacao, nota_ensino, comentario)
    public void adicionar(AvaliacaoProfessorForm avaliacaoProfessorForm) {
        Discente discente = discenteDAO.buscarDiscenteCompletoPorId(avaliacaoProfessorForm.getDiscenteId());
        Professor professor = professorDAO.buscarPorId(avaliacaoProfessorForm.getProfessorId());

        AvaliacaoProfessor avaliacaoProfessor = AvaliacaoProfessor.builder()
                .idDiscente(avaliacaoProfessorForm.getDiscenteId())
                .idProfessor(avaliacaoProfessorForm.getProfessorId())
                .notaAvaliacao(avaliacaoProfessorForm.getNotaAvaliacao())
                .discente(discente)
                .professor(professor)
                .dataAvaliacao(LocalDate.now())
                .comentario(avaliacaoProfessorForm.getComentario())
                .build();

        avaliacaoProfessorDAO.inserir(avaliacaoProfessor);
    }

    public List<AvaliacaoProfessor> listar(){
        return repository.listar();
    }

    public List<AvaliacaoProfessor> listarPorProfessorId(Long idProfessor){
        return repository.listarPorIdProfessor(idProfessor);
    }

    public AvaliacaoProfessorForm converterAvaliacaoProfessorParaForm(AvaliacaoProfessor avaliacaoProfessor) {
        if (avaliacaoProfessor == null) {
            throw new IllegalArgumentException("Avaliação do professor não pode ser nula");
        }

        AvaliacaoProfessorForm form = new AvaliacaoProfessorForm();
        form.setProfessorId(avaliacaoProfessor.getIdProfessor());
        form.setNotaAvaliacao(avaliacaoProfessor.getNotaAvaliacao());
        form.setComentario(avaliacaoProfessor.getComentario());
        form.setDiscenteId(avaliacaoProfessor.getIdDiscente());

        return form;
    }

}
