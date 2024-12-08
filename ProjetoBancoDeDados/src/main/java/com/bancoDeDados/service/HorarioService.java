package com.bancoDeDados.service;

import com.bancoDeDados.model.Horario;
import com.bancoDeDados.repository.dao.HorarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioService {

    @Autowired
    private HorarioDAO horarioDAO;

    public List<Horario> buscarPorDisciplina(Long disciplinaId) {
        return horarioDAO.buscarHorariosDeDisciplina(disciplinaId);
    }
}
