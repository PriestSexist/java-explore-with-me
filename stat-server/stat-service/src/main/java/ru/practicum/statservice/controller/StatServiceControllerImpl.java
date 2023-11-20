package ru.practicum.statservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statdto.dto.EndpointHitDto;
import ru.practicum.statdto.dto.ViewStatsDto;
import ru.practicum.statservice.service.StatServiceService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatServiceControllerImpl implements StatServiceController {
    private final StatServiceService statServiceService;
    private static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hit")
    @Override
    public EndpointHitDto postEndpoint(@RequestBody EndpointHitDto endpointHitDto) {
        log.debug("Вызван метод postEndpoint");
        return statServiceService.postEndpointHit(endpointHitDto);
    }

    @GetMapping("/stats")
    @Override
    public List<ViewStatsDto> getStat(@RequestParam @DateTimeFormat(pattern = FORMATTER) LocalDateTime start,
                                      @RequestParam @DateTimeFormat(pattern = FORMATTER) LocalDateTime end,
                                      @RequestParam List<String> uris,
                                      @RequestParam(defaultValue = "false") Boolean unique) {
        log.debug("Вызван метод getStat");
        return statServiceService.getStat(start, end, uris, unique);
    }
}
