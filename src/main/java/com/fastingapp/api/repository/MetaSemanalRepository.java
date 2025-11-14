package com.fastingapp.api.repository;

import com.fastingapp.api.model.entity.MetaSemanal;
import com.fastingapp.api.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MetaSemanalRepository extends JpaRepository<MetaSemanal, Long> {

    List<MetaSemanal> findByUsuarioOrderBySemanaInicioDesc(Usuario usuario);

    @Query("SELECT m FROM MetaSemanal m WHERE m.usuario = :usuario AND :data BETWEEN m.semanaInicio AND m.semanaFim")
    Optional<MetaSemanal> findBySemanaAtual(
            @Param("usuario") Usuario usuario,
            @Param("data") LocalDate data
    );

    @Query("SELECT m FROM MetaSemanal m WHERE m.usuario = :usuario AND m.semanaInicio >= :dataInicio ORDER BY m.semanaInicio DESC")
    List<MetaSemanal> findMetasRecentes(
            @Param("usuario") Usuario usuario,
            @Param("dataInicio") LocalDate dataInicio
    );
}