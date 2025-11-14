package com.fastingapp.api.repository;

import com.fastingapp.api.model.entity.Notificacao;
import com.fastingapp.api.model.entity.Usuario;
import com.fastingapp.api.model.enums.TipoNotificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    List<Notificacao> findByUsuarioOrderByDataDisparoDesc(Usuario usuario);

    List<Notificacao> findByUsuarioAndLidaFalseOrderByDataDisparoDesc(Usuario usuario);

    @Query("SELECT n FROM Notificacao n WHERE n.enviada = false AND n.dataDisparo <= :agora")
    List<Notificacao> findNotificacoesPendentes(@Param("agora") LocalDateTime agora);

    List<Notificacao> findByUsuarioAndTipo(Usuario usuario, TipoNotificacao tipo);
}