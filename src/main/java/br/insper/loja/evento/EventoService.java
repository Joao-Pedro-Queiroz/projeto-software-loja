package br.insper.loja.evento;

import br.insper.loja.usuario.Usuario;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventoService {

    public void salvarEvento(String usuario, String acao, String token) {
        Evento evento = new Evento();
        evento.setEmail(usuario);
        evento.setAcao(acao);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        HttpEntity<Evento> entity = new HttpEntity<>(evento, headers);

        try {
            ResponseEntity<Evento> response = restTemplate.exchange(
                    "http://usuario:8080/api/evento",
                    HttpMethod.POST,
                    entity,
                    Evento.class
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
