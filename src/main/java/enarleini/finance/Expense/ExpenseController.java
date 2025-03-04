package enarleini.finance.Expense;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api/expenses")

public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    @GetMapping("/findall")
    @ResponseStatus(HttpStatus.OK)
    List<Expenses> findAll(@RequestParam String username) {
        return service.findAllByUsername(username);
    }
    @PreAuthorize("hasRole('ADMIN') or @userService.findClientById(#id).getUsername() == authentication.principal.username")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Optional<Expenses> findById(@PathVariable Long id){
        return service.findById(id);
    }
    @PreAuthorize("hasRole('ADMIN') or @userService.findClientById(#id).getUsername() == authentication.principal.username")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    void create(@Valid @RequestBody Expenses expense) {
        service.create(expense);
    }
    @PreAuthorize("hasRole('ADMIN') or @userService.findClientById(#id).getUsername() == authentication.principal.username")
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Expenses updateExpense(@PathVariable Long id, @Valid @RequestBody ExpensesDto updatedExpenseDto) {
        return service.updateExpense(id, updatedExpenseDto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Long id) {
        service.delete(id);
    }

}