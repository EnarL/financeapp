package enarleini.finance.Income;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api/incomes")

public class IncomeController {

    @Autowired
    private IncomeService service;

    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    @GetMapping("/findall")
    List<Incomes> findAll(@RequestParam String username) {
        return service.findAllByUsername(username);
    }
    @PreAuthorize("hasRole('ADMIN') or @userService.findClientById(#id).getUsername() == authentication.principal.username")
    @GetMapping("/{id}")
    Optional<Incomes> findById(@PathVariable Long id) {
        return service.findById(id);
    }
    @PreAuthorize("hasRole('ADMIN') or @userService.findClientById(#id).getUsername() == authentication.principal.username")
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Incomes updateIncome(@PathVariable Long id, @Valid @RequestBody IncomesDto updatedIncomesDto) {
        return service.updateIncome(id, updatedIncomesDto);
    }
    @PreAuthorize("hasRole('ADMIN') or @userService.findClientById(#id).getUsername() == authentication.principal.username")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    void create(@Valid @RequestBody Incomes income) {
        service.create(income);
    }
    @PreAuthorize("hasRole('ADMIN') or @userService.findClientById(#id).getUsername() == authentication.principal.username")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Long id) {
        service.delete(id);
    }
}