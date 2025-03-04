package enarleini.finance.Expense;

import enarleini.finance.Expense.exception.ExpenseNotFoundException;
import enarleini.finance.Expense.exception.InvalidExpenseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public void delete(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ExpenseNotFoundException("Expense not found with id " + id);
        }
        expenseRepository.deleteById(id);
    }

    public List<Expenses> findAllByUsername(String username) {
        return expenseRepository.findAllByUsername(username);
    }

    public Optional<Expenses> findById(Long id) {
        return Optional.ofNullable(expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found with id " + id)));
    }

    public void create(Expenses expense) {
        expenseRepository.save(expense);
    }

    public Expenses updateExpense(Long id, ExpensesDto updatedExpenseDto) {
        return expenseRepository.findById(id).map(expense -> {
            if (updatedExpenseDto.getAmount() != null) {
                expense.setAmount(updatedExpenseDto.getAmount());
            }
            if (updatedExpenseDto.getCategory() != null) {
                expense.setCategory(updatedExpenseDto.getCategory());
            }
            if (updatedExpenseDto.getDescription() != null) {
                expense.setDescription(updatedExpenseDto.getDescription());
            }
            if (updatedExpenseDto.getDate() != null) {
                try {
                    expense.setDate(LocalDate.parse(updatedExpenseDto.getDate()));
                } catch (DateTimeParseException e) {
                    throw new InvalidExpenseException("Invalid date format: " + updatedExpenseDto.getDate());
                }
            }
            return expenseRepository.save(expense);
        }).orElseThrow(() -> new ExpenseNotFoundException("Expense not found with id " + id));
    }
}