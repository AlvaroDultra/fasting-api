package com.fastingapp.api.repository;

import com.fastingapp.api.model.entity.Dica;
import com.fastingapp.api.model.enums.CategoriaDica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DicaRepository extends JpaRepository<Dica, Long> {

    List<Dica> findByCategoria(CategoriaDica categoria);

    List<Dica> findByAtivaTrue();

    @Query("SELECT d FROM Dica d WHERE d.categoria = :categoria AND d.ativa = true ORDER BY RANDOM() LIMIT 1")
    Dica findDicaAleatoriaPorCategoria(@Param("categoria") CategoriaDica categoria);
}