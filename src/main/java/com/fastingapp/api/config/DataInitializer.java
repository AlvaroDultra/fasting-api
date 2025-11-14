package com.fastingapp.api.config;

import com.fastingapp.api.model.entity.Dica;
import com.fastingapp.api.model.enums.CategoriaDica;
import com.fastingapp.api.repository.DicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final DicaRepository dicaRepository;

    @Override
    public void run(String... args) {
        if (dicaRepository.count() == 0) {
            List<Dica> dicas = Arrays.asList(
                    // CONSISTÊNCIA
                    new Dica(null, "Mantenha um horário regular para iniciar seus jejuns. A consistência é a chave do sucesso!",
                            CategoriaDica.CONSISTENCIA, true, null),
                    new Dica(null, "Você completou 4 jejuns esta semana! Excelente consistência. Continue assim! 💪",
                            CategoriaDica.CONSISTENCIA, true, null),
                    new Dica(null, "Tente fazer pelo menos 5 jejuns por semana para obter melhores resultados.",
                            CategoriaDica.CONSISTENCIA, true, null),

                    // HIDRATAÇÃO
                    new Dica(null, "Lembre-se de beber água regularmente durante o jejum. Mantenha-se hidratado! 💧",
                            CategoriaDica.HIDRATACAO, true, null),
                    new Dica(null, "Água, chá e café sem açúcar são permitidos durante o jejum.",
                            CategoriaDica.HIDRATACAO, true, null),
                    new Dica(null, "Para jejuns longos, considere adicionar eletrólitos à sua água.",
                            CategoriaDica.HIDRATACAO, true, null),

                    // PROTOCOLO
                    new Dica(null, "Iniciantes devem começar com protocolos mais curtos como 12/12 ou 14/10.",
                            CategoriaDica.PROTOCOLO, true, null),
                    new Dica(null, "O protocolo 16/8 é o mais popular e sustentável para a maioria das pessoas.",
                            CategoriaDica.PROTOCOLO, true, null),
                    new Dica(null, "Se seus jejuns estão durando mais que 18h consistentemente, considere migrar para o 18/6.",
                            CategoriaDica.PROTOCOLO, true, null),

                    // HORÁRIO
                    new Dica(null, "A maioria das pessoas prefere iniciar o jejum após o jantar (20h-21h).",
                            CategoriaDica.HORARIO, true, null),
                    new Dica(null, "Ajuste o horário do jejum de acordo com sua rotina e sono.",
                            CategoriaDica.HORARIO, true, null),

                    // DESEMPENHO
                    new Dica(null, "Se está quebrando jejuns frequentemente, tente um protocolo mais leve primeiro.",
                            CategoriaDica.DESEMPENHO, true, null),
                    new Dica(null, "Foque na qualidade dos alimentos na sua janela de alimentação.",
                            CategoriaDica.DESEMPENHO, true, null),
                    new Dica(null, "Atividades leves como caminhadas podem ajudar durante o jejum.",
                            CategoriaDica.DESEMPENHO, true, null)
            );

            dicaRepository.saveAll(dicas);
            System.out.println("✅ Dicas de jejum inicializadas no banco de dados!");
        }
    }
}