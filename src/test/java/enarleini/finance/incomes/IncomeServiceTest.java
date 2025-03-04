package enarleini.finance.incomes;

import enarleini.finance.Income.*;
import enarleini.finance.Income.exception.IncomeNotFoundException;
import enarleini.finance.Income.exception.InvalidIncomeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class IncomeServiceTest {

    @Mock
    private IncomeRepository incomeRepository;

    @InjectMocks
    private IncomeService incomeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testDeleteIncomeNotFound() {
        doThrow(new IncomeNotFoundException("Income not found with id 1")).when(incomeRepository).deleteById(1L);

        assertThrows(IncomeNotFoundException.class, () -> incomeService.delete(1L));
    }

    @Test
    void testFindAllByUsername() {
        List<Incomes> incomes = Arrays.asList(new Incomes(), new Incomes());
        when(incomeRepository.findAllByUsername(anyString())).thenReturn(incomes);

        List<Incomes> result = incomeService.findAllByUsername("testuser");

        assertEquals(2, result.size());
        verify(incomeRepository, times(1)).findAllByUsername("testuser");
    }

    @Test
    void testFindById() {
        Incomes income = new Incomes();
        when(incomeRepository.findById(anyLong())).thenReturn(Optional.of(income));

        Optional<Incomes> result = incomeService.findById(1L);

        assertTrue(result.isPresent());
        verify(incomeRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdIncomeNotFound() {
        when(incomeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IncomeNotFoundException.class, () -> incomeService.findById(1L));
    }

    @Test
    void testCreate() {
        Incomes income = new Incomes();
        when(incomeRepository.save(any(Incomes.class))).thenReturn(income);

        incomeService.create(income);

        verify(incomeRepository, times(1)).save(income);
    }



    @Test
    void testUpdateIncome() {
        Incomes existingIncome = new Incomes();
        existingIncome.setId(1);
        existingIncome.setAmount(BigDecimal.valueOf(1000));
        existingIncome.setSource("Job");
        existingIncome.setDescription("Salary");
        existingIncome.setDate(LocalDate.now());

        IncomesDto updatedIncomeDto = new IncomesDto();
        updatedIncomeDto.setAmount(BigDecimal.valueOf(2000));
        updatedIncomeDto.setSource("Freelance");
        updatedIncomeDto.setDescription("Project");
        updatedIncomeDto.setDate(LocalDate.now().toString());

        when(incomeRepository.findById(anyLong())).thenReturn(Optional.of(existingIncome));
        when(incomeRepository.save(any(Incomes.class))).thenReturn(existingIncome);

        Incomes updatedIncome = incomeService.updateIncome(1L, updatedIncomeDto);

        assertEquals(updatedIncomeDto.getAmount(), updatedIncome.getAmount());
        assertEquals(updatedIncomeDto.getSource(), updatedIncome.getSource());
        assertEquals(updatedIncomeDto.getDescription(), updatedIncome.getDescription());
        assertEquals(LocalDate.parse(updatedIncomeDto.getDate()), updatedIncome.getDate());
        verify(incomeRepository, times(1)).findById(1L);
        verify(incomeRepository, times(1)).save(existingIncome);
    }

    @Test
    void testUpdateIncomeNotFound() {
        IncomesDto updatedIncomeDto = new IncomesDto();
        updatedIncomeDto.setAmount(BigDecimal.valueOf(2000));
        updatedIncomeDto.setSource("Freelance");
        updatedIncomeDto.setDescription("Project");
        updatedIncomeDto.setDate(LocalDate.now().toString());

        when(incomeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IncomeNotFoundException.class, () -> incomeService.updateIncome(1L, updatedIncomeDto));
    }


}