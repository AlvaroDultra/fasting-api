package com.fastingapp.api.repository;

import com.fastingapp.api.model.entity.Jejum;
import com.fastingapp.api.model.entity.Usuario;
import com.fastingapp.api.model.enums.StatusJejum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JejumRepository extends JpaRepository<Jejum, Long> {

    List<Jejum> findByUsuarioOrderByInicioDesc(Usuario usuario);

    List<Jejum> findByUsuarioAndStatus(Usuario usuario, StatusJejum status);

    @Query("SELECT j FROM Jejum j WHERE j.usuario = :usuario AND j.inicio >= :dataInicio AND j.inicio <= :dataFim ORDER BY j.inicio DESC")
    List<Jejum> findByUsuarioAndPeriodo(
            @Param("usuario") Usuario usuario,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );

    @Query("SELECT j FROM Jejum j WHERE j.usuario = :usuario AND j.status = 'ATIVO'")
    Optional<Jejum> findJejumAtivoByUsuario(@Param("usuario") Usuario usuario);

    @Query("SELECT COUNT(j) FROM Jejum j WHERE j.usuario = :usuario AND j.metaAtingida = true AND j.inicio >= :dataInicio")
    Long countJejunsConcluidos(
            @Param("usuario") Usuario usuario,
            @Param("dataInicio") LocalDateTime dataInicio
    );

    @Query("SELECT AVG(j.duracaoHoras) FROM Jejum j WHERE j.usuario = :usuario AND j.status = 'CONCLUIDO' AND j.inicio >= :dataInicio")
    Double calcularMediaHoras(
            @Param("usuario") Usuario usuario,
            @Param("dataInicio") LocalDateTime dataInicio
    );
}