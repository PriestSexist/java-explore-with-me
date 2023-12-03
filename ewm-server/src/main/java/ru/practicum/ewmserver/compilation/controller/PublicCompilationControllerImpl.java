package ru.practicum.ewmserver.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmserver.compilation.dto.CompilationDto;
import ru.practicum.ewmserver.compilation.service.PublicCompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Validated
public class PublicCompilationControllerImpl implements PublicCompilationController {

    private final PublicCompilationService publicCompilationService;

    @Override
    @GetMapping
    public List<CompilationDto> getCompilation(@RequestParam boolean pinned,
                                               @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                               @RequestParam(defaultValue = "10") @Positive int size) {
        log.debug("Вызван метод getCompilation");
        return publicCompilationService.getCompilation(pinned, from, size);
    }

    @Override
    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable @Positive int compId) {
        log.debug("Вызван метод getCompilationById");
        return publicCompilationService.getCompilationById(compId);
    }
}
