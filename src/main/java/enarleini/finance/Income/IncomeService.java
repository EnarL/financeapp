// IncomeService.java
package enarleini.finance.Income;

import enarleini.finance.Income.exception.IncomeNotFoundException;
import enarleini.finance.Income.exception.InvalidIncomeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public void delete(Long id) {
        if (!incomeRepository.existsById(id)) {
            throw new IncomeNotFoundException("Income not found with id " + id);
        }
        incomeRepository.deleteById(id);
    }

    public List<Incomes> findAllByUsername(String username) {
        return incomeRepository.findAllByUsername(username);
    }

    public Optional<Incomes> findById(Long id) {
        return Optional.ofNullable(incomeRepository.findById(id)
                .orElseThrow(() -> new IncomeNotFoundException("Income not found with id " + id)));
    }

    public Incomes updateIncome(Long id, IncomesDto updatedIncomeDto) {
        return incomeRepository.findById(id).map(income -> {
            if (updatedIncomeDto.getAmount() != null) {
                income.setAmount(updatedIncomeDto.getAmount());
            }
            if (updatedIncomeDto.getSource() != null) {
                income.setSource(updatedIncomeDto.getSource());
            }
            if (updatedIncomeDto.getDescription() != null) {
                income.setDescription(updatedIncomeDto.getDescription());
            }
            if (updatedIncomeDto.getDate() != null) {
                try {
                    income.setDate(LocalDate.parse(updatedIncomeDto.getDate()));
                } catch (DateTimeParseException e) {
                    throw new InvalidIncomeException("Invalid date format: " + updatedIncomeDto.getDate());
                }
            }
            return incomeRepository.save(income);
        }).orElseThrow(() -> new IncomeNotFoundException("Income not found with id " + id));
    }

    public void create(Incomes income) {
        incomeRepository.save(income);
    }
}