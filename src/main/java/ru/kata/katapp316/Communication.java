package ru.kata.katapp316;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.kata.katapp316.model.User;

import java.util.List;

@Component
public class Communication {
    private final RestTemplate restTemplate;
    private final String URL = "http://94.198.50.185:7081/api/users";

    private String sessionId;

    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() { });

        sessionId = responseEntity.getHeaders().getFirst("Set-Cookie");

        return responseEntity.getBody();
    }

    public String createUser(User user) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST,
                new HttpEntity<>(user, getHttpHeaders()), String.class);
        return responseEntity.getBody();
    }

    public String updateUser(User user) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT,
                new HttpEntity<>(user, getHttpHeaders()), String.class);
        return responseEntity.getBody();
    }

    public String deleteUser(long id) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE,
                new HttpEntity<>(getHttpHeaders()), String.class);
        return responseEntity.getBody();
    }

    private HttpHeaders getHttpHeaders() {
        if (sessionId == null || sessionId.isEmpty() || !sessionId.startsWith("JSESSIONID=")) {
            throw new RuntimeException("session id was not received! execute getAllUsers() first!");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", sessionId);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

}
