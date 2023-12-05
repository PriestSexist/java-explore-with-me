package ru.practicum.ewmserver.compilation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmserver.compilation.dto.CompilationDto;
import ru.practicum.ewmserver.compilation.dto.NewCompilationDto;
import ru.practicum.ewmserver.compilation.dto.UpdateCompilationRequest;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

public interface AdminCompilationController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CompilationDto postCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto);

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCompilation(@PathVariable @PositiveOrZero int compId);

    @PatchMapping("/{compId}")
    CompilationDto patchCompilation(@RequestBody @Valid UpdateCompilationRequest updateCompilationRequest,
                                    @PathVariable @PositiveOrZero int compId);
}
