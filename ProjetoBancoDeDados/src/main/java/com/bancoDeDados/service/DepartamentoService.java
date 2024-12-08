package com.bancoDeDados.service;

import com.bancoDeDados.model.Departamento;
import com.bancoDeDados.repository.DepartamentoRepository;
import com.bancoDeDados.repository.dao.DepartamentoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;
    private final DepartamentoDAO departamentoDAO;

    @Autowired
    public DepartamentoService(DepartamentoRepository departamentoRepository, DepartamentoDAO departamentoDAO) {
        this.departamentoRepository = departamentoRepository;
        this.departamentoDAO = departamentoDAO;
    }

    public List<Departamento> listarTodos() {
        List<Departamento> departamentos = departamentoRepository.listarTodos();
        return departamentos;
    }

    public Departamento buscarDepartamentoId(Long id) {
        return departamentoDAO.buscarDepartamentoPorId(id);
    }
}