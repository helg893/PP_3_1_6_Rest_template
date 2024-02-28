package ru.kata.katapp316;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
//        responseEntity.getHeaders().get("Set-Cookie").stream().filter(s -> {})
//        responseEntity.getHeaders().get("Set-Cookie").stream().forEach(System.out::println);

        List<User> allUser = responseEntity.getBody();
        return allUser;
    }

//    public String getCookieSessionId() {
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class);
//        return responseEntity.getHeaders().getFirst("Set-Cookie");
////        return responseEntity.getHeaders().get("Set-Cookie").stream().filter(s -> s.startsWith("JSESSIONID=")).findAny().get();
//    }

    public String saveUser(User user) {

        long id = user.getId();
        if (id == 0) {
            ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
            return responseEntity.getBody();
        }

        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        return responseEntity.getBody();
    }

    public void deleteUser(User user) {

    }

    private HttpEntity<String> getHttpEntity() {
        if (sessionId == null || sessionId.isEmpty() || !sessionId.startsWith("JSESSIONID=")) {
            throw new RuntimeException("session id was not received! execute getAllUsers() first!");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", sessionId);
        return new HttpEntity<>(httpHeaders);

    }

}
