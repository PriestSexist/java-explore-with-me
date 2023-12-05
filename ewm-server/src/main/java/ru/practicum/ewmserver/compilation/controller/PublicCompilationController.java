package ru.practicum.ewmserver.compilation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewmserver.compilation.dto.CompilationDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

public interface PublicCompilationController {

    @GetMapping
    List<CompilationDto> getCompilation(@RequestParam boolean pinned,
                                        @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                        @RequestParam(defaultValue = "10") @Positive int size);

    @GetMapping("/{compId}")
    CompilationDto getCompilationById(@PathVariable @PositiveOrZero int compId);
}
