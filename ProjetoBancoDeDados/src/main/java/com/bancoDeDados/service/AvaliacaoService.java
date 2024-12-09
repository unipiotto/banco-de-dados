package com.bancoDeDados.service;

import com.bancoDeDados.model.Avaliacao;
import com.bancoDeDados.model.dto.AvaliacaoForm;
import com.bancoDeDados.repository.AvaliacaoRepository;
import com.bancoDeDados.repository.dao.AvaliacaoDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AvaliacaoDAO avaliacaoDAO;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, AvaliacaoDAO avaliacaoDAO) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.avaliacaoDAO = avaliacaoDAO;
    }

    public void adicionar(AvaliacaoForm avaliacaoForm) {

        Avaliacao avaliacao = Avaliacao.builder()
                .nota(avaliacaoForm.getNota())
                .peso(avaliacaoForm.getPeso())
                .idMatricula(avaliacaoForm.getIdMatricula())
                .build();

        avaliacaoRepository.inserir(avaliacao);
    }

    public List<Avaliacao> listarNotasDiscente(Long id) {
        return avaliacaoRepository.listarNotasDiscente(id);

    }

    public List<Avaliacao> listarNotasDiscentePorDisciplina(Long idDiscente, Long idDisciplina) {
        return avaliacaoRepository.listarNotasDiscentePorDisciplina(idDiscente, idDisciplina);
    }

    public void atualizar(AvaliacaoForm avaliacaoForm, Long id) {
        Avaliacao avaliacao = Avaliacao.builder()
                .idAvaliacao(id)
                .nota(avaliacaoForm.getNota())
                .peso(avaliacaoForm.getPeso())
                .idMatricula(avaliacaoForm.getIdMatricula())
                .build();

        avaliacaoRepository.atualizar(avaliacao);
    }

    public Avaliacao buscarPorId(Long id) {
        return avaliacaoDAO.buscarPorId(id);
    }

    public AvaliacaoForm converterAvaliacaoForm(Avaliacao avaliacao) {
        AvaliacaoForm avaliacaoForm = new AvaliacaoForm();
        avaliacaoForm.setNota(avaliacao.getNota());
        avaliacaoForm.setPeso(avaliacao.getPeso());
        avaliacaoForm.setIdMatricula(avaliacao.getIdMatricula());
        return avaliacaoForm;
    }

    public Long buscarIdDiscentePorIdAvaliacao(Long idAvaliacao) {
        return avaliacaoRepository.buscarIdDiscentePorIdAvaliacao(idAvaliacao);
    }
}
