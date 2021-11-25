package by.naumenko.resttemplatedemo;

import by.naumenko.resttemplatedemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Communication {
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final String URL = "http://91.241.64.178:7081/api/users";


    @Autowired
    public Communication(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        this.httpHeaders.set("Cookie",
                String.join(";", restTemplate.headForHeaders(URL).get("Set-Cookie")));
    }

    public String getAnswer() {
        return addUser().getBody() + editUser().getBody() + removeUser().getBody();
    }

    //GET
    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<User>>() {
                });
        System.out.println(responseEntity.getHeaders());
        return responseEntity.getBody();
    }

    //POST
    private ResponseEntity<String> addUser() {
        User user = new User(3L, "James", "Brown", (byte) 25);
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);
        return restTemplate.postForEntity(URL, httpEntity, String.class);
    }

    //PUT
    private ResponseEntity<String> editUser() {
        User user = new User(3L, "Thomas", "Shelby", (byte) 5);
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);
        return restTemplate.exchange(URL, HttpMethod.PUT, httpEntity, String.class, 3);
    }

    //DELETE
    private ResponseEntity<String> removeUser() {
        Map<String, Long> uriVariables = new HashMap<>() {{
            put("id", 3L);
        }};
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        return restTemplate.exchange(URL + "/{id}", HttpMethod.DELETE, entity, String.class, uriVariables);
    }
}
