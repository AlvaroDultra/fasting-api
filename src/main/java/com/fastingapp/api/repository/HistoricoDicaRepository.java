package com.fastingapp.api.repository;

import com.fastingapp.api.model.entity.HistoricoDica;
import com.fastingapp.api.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoDicaRepository extends JpaRepository<HistoricoDica, Long> {

    List<HistoricoDica> findByUsuarioOrderByEnviadaEmDesc(Usuario usuario);
}