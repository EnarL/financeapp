package enarleini.finance.Income;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Incomes, Long> {

    List<Incomes> findAllByUsername(String username);
    Optional<Incomes> findById(Long id);

}

