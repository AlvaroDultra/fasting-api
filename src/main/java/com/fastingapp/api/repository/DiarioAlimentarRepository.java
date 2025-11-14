package com.fastingapp.api.repository;

import com.fastingapp.api.model.entity.DiarioAlimentar;
import com.fastingapp.api.model.entity.Jejum;
import com.fastingapp.api.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiarioAlimentarRepository extends JpaRepository<DiarioAlimentar, Long> {

    Optional<DiarioAlimentar> findByJejum(Jejum jejum);

    @Query("SELECT d FROM DiarioAlimentar d WHERE d.jejum.usuario = :usuario ORDER BY d.registradoEm DESC")
    List<DiarioAlimentar> findByUsuario(@Param("usuario") Usuario usuario);
}