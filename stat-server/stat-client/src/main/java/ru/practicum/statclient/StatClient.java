package ru.practicum.statclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.statdto.dto.Constants;
import ru.practicum.statdto.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

@Service
public class StatClient extends BaseClient {
    private static final String START = "start";
    private static final String END = "end";

    @Autowired
    public StatClient(@Value("${stat-service.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> postEndpointHit(EndpointHitDto endpointHitDto) {
        return post("/hit", endpointHitDto);
    }

    public ResponseEntity<Object> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {

        String stringStart = start.format(Constants.FORMATTER);
        String stringEnd = end.format(Constants.FORMATTER);


        Map<String, Object> parameters = Map.of(
                START, stringStart,
                END, stringEnd
        );

        StringJoiner pathBuilder = new StringJoiner("&", "/stats?start={start}&end={end}", "");
        if (Objects.nonNull(uris) && !uris.isEmpty()) {
            uris.forEach(uri -> pathBuilder.add("&uris=" + uri));
        }
        if (Objects.nonNull(unique)) {
            pathBuilder.add("&unique=" + unique);
        }
        String path = pathBuilder.toString();
        return makeAndSendRequest(HttpMethod.GET, path, null, parameters, null);
    }
}
