package ru.practicum.ewmserver.compilation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmserver.compilation.dto.CompilationDto;
import ru.practicum.ewmserver.compilation.dto.NewCompilationDto;
import ru.practicum.ewmserver.compilation.dto.UpdateCompilationRequest;

import javax.validation.constraints.Positive;

public interface AdminCompilationController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CompilationDto postCompilation(@RequestBody @Validated NewCompilationDto newCompilationDto);

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCompilation(@PathVariable @Positive int compId);

    @PatchMapping("/{compId}")
    CompilationDto patchCompilation(@RequestBody @Validated UpdateCompilationRequest updateCompilationRequest,
                                    @PathVariable @Positive int compId);
}