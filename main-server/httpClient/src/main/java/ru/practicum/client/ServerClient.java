/*
package ru.practicum.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.allDto.*;
import ru.practicum.allDto.HitDto;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ServerClient {

    private final RestTemplate rest;


    @Autowired
    public ServerClient(@Value("${statistics-server.url}") String serverUrl, RestTemplateBuilder builder) {
        rest = builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }

    public ResponseEntity<Object> create(HitDto body) {
        return makeAndSendRequest(HttpMethod.POST, "/hit", null, body);
    }

    public ResponseEntity<Object> getCompilations(String start, String end, List<String> uris, Boolean unique) {
        StringBuilder stringBuilder = new StringBuilder();
        if (uris != null) {
            for (String s : uris) {
                stringBuilder.append("uris=").append(s).append("&");
            }
        }
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "unique", unique);
        return makeAndSendRequest(
                HttpMethod.GET,
                "/stats?start={start}&end={end}&" + stringBuilder + "unique={unique}",
                parameters,
                null);
    }

    private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method, String path, @Nullable Map<String, Object> parameters, @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());

        ResponseEntity<Object> serverResponse;
        try {
            if (parameters != null) {
                serverResponse = rest.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                serverResponse = rest.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        log.info("Request for method {} on path {} completed", method, path);
        return prepareGatewayResponse(serverResponse);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }

    public ResponseEntity<Object> getCompilations(Integer from, Integer size, Boolean pinned) {
        return null;
    }

    public ResponseEntity<Object> getCompilationsById(Integer compId) {
        return null;
    }

    public ResponseEntity<Object> createCategory(Category category) {
        return null;
    }

    public ResponseEntity<Object> deleteCategory(Integer catId) {
        return null;
    }

    public ResponseEntity<Object> patchCategory(Integer catId, Category category) {
        return null;
    }

    public ResponseEntity<Object> getEventsUser(Integer userId, Integer from, Integer size) {
        return null;
    }

    public ResponseEntity<Object> createEventUser(Integer userId, Event event) {
        return null;
    }

    public ResponseEntity<Object> getEventUser(Integer userId, Integer eventId) {
        return null;
    }

    public ResponseEntity<Object> changeEventUser(Integer userId, Integer eventId) {
        return null;
    }

    public ResponseEntity<Object> getRequestsUserInEvent(Integer userId, Integer eventId) {
        return null;
    }

    public ResponseEntity<Object> changeStatusParticipationInEvent(Integer userId, Integer eventId, StatusEvents statusEvents) {
        return null;
    }

    public ResponseEntity<Object> getCategories(Integer from, Integer size) {
        return null;
    }

    public ResponseEntity<Object> getCategoryById(Integer catId) {
        return null;
    }

    public ResponseEntity<Object> searchEvents(List<Integer> users, List<Integer> states, List<Integer> categories, String rangeStart, String rangeEnd, Integer from, Integer size) {
        return null;
    }

    public ResponseEntity<Object> changeEventAndStatus(Integer eventId, Event event) {
        return null;
    }

    public ResponseEntity<Object> getEvents(String text, List<Integer> categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        return null;
    }

    public ResponseEntity<Object> getRequestUsersById(Integer userId) {
        return null;
    }

    public ResponseEntity<Object> createRequestParticipate(Integer userId, Integer eventId) {
        return null;
    }

    public ResponseEntity<Object> cancelingParticipate(Integer userId, Integer requestId) {
        return null;
    }

    public ResponseEntity<Object> getUsers(List<Integer> ids, Integer from, Integer size) {
        return null;
    }

    public ResponseEntity<Object> createUser(UserDto userDto) {
        return null;
    }

    public ResponseEntity<Object> deleteUser(Integer userId) {
        return null;
    }

    public ResponseEntity<Object> createCompilation(Compilation compilation) {
        return null;
    }

    public ResponseEntity<Object> deleteCompilation(Integer comId) {
        return null;
    }

    public ResponseEntity<Object> cangeCompilation(Integer comId, Compilation compilation) {
        return null;
    }
}


*/
