package enarleini.finance.Expense;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expenses, Long> {

    List<Expenses> findAllByUsername(String username);
    Optional<Expenses> findById(Long id);


}
