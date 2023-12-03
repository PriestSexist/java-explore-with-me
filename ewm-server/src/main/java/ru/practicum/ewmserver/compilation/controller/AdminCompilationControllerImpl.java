package ru.practicum.ewmserver.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmserver.compilation.dto.CompilationDto;
import ru.practicum.ewmserver.compilation.dto.NewCompilationDto;
import ru.practicum.ewmserver.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewmserver.compilation.service.AdminCompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/compilations")
@Validated
public class AdminCompilationControllerImpl implements AdminCompilationController {

    private final AdminCompilationService adminCompilationService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto postCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.debug("Вызван метод postCompilationAdmin");
        return adminCompilationService.postCompilation(newCompilationDto);
    }

    @Override
    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable @Positive int compId) {
        log.debug("Вызван метод deleteCompilationAdmin");
        adminCompilationService.deleteCompilation(compId);
    }

    @Override
    @PatchMapping("/{compId}")
    public CompilationDto patchCompilation(@RequestBody @Valid UpdateCompilationRequest updateCompilationRequest,
                                           @PathVariable @Positive int compId) {
        log.debug("Вызван метод patchCompilationAdmin");
        return adminCompilationService.patchCompilation(updateCompilationRequest, compId);
    }
}
