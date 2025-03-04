package enarleini.finance.expenses;

import enarleini.finance.Expense.*;

import enarleini.finance.Expense.exception.ExpenseNotFoundException;
import enarleini.finance.Expense.exception.InvalidExpenseException;
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

class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteExpenseNotFound() {
        doThrow(new ExpenseNotFoundException("Expense not found with id 1")).when(expenseRepository).deleteById(1L);

        assertThrows(ExpenseNotFoundException.class, () -> expenseService.delete(1L));
    }

    @Test
    void testFindAllByUsername() {
        List<Expenses> expenses = Arrays.asList(new Expenses(), new Expenses());
        when(expenseRepository.findAllByUsername(anyString())).thenReturn(expenses);

        List<Expenses> result = expenseService.findAllByUsername("testuser");

        assertEquals(2, result.size());
        verify(expenseRepository, times(1)).findAllByUsername("testuser");
    }

    @Test
    void testFindById() {
        Expenses expense = new Expenses();
        when(expenseRepository.findById(anyLong())).thenReturn(Optional.of(expense));

        Optional<Expenses> result = expenseService.findById(1L);

        assertTrue(result.isPresent());
        verify(expenseRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdExpenseNotFound() {
        when(expenseRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ExpenseNotFoundException.class, () -> expenseService.findById(1L));
    }



    @Test
    void testCreate() {
        Expenses expense = new Expenses();
        when(expenseRepository.save(any(Expenses.class))).thenReturn(expense);

        expenseService.create(expense);

        verify(expenseRepository, times(1)).save(expense);
    }


    @Test
    void testUpdateExpense() {
        Expenses existingExpense = new Expenses();
        existingExpense.setId(1);
        existingExpense.setAmount(BigDecimal.valueOf(100));
        existingExpense.setCategory("Food");
        existingExpense.setDescription("Groceries");
        existingExpense.setDate(LocalDate.now());

        ExpensesDto updatedExpenseDto = new ExpensesDto();
        updatedExpenseDto.setAmount(BigDecimal.valueOf(200));
        updatedExpenseDto.setCategory("Entertainment");
        updatedExpenseDto.setDescription("Movies");
        updatedExpenseDto.setDate(LocalDate.now().toString());

        when(expenseRepository.findById(anyLong())).thenReturn(Optional.of(existingExpense));
        when(expenseRepository.save(any(Expenses.class))).thenReturn(existingExpense);

        Expenses updatedExpense = expenseService.updateExpense(1L, updatedExpenseDto);

        assertEquals(updatedExpenseDto.getAmount(), updatedExpense.getAmount());
        assertEquals(updatedExpenseDto.getCategory(), updatedExpense.getCategory());
        assertEquals(updatedExpenseDto.getDescription(), updatedExpense.getDescription());
        assertEquals(LocalDate.parse(updatedExpenseDto.getDate()), updatedExpense.getDate());
        verify(expenseRepository, times(1)).findById(1L);
        verify(expenseRepository, times(1)).save(existingExpense);
    }

    @Test
    void testUpdateExpenseNotFound() {
        ExpensesDto updatedExpenseDto = new ExpensesDto();
        updatedExpenseDto.setAmount(BigDecimal.valueOf(200));
        updatedExpenseDto.setCategory("Entertainment");
        updatedExpenseDto.setDescription("Movies");
        updatedExpenseDto.setDate(LocalDate.now().toString());

        when(expenseRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ExpenseNotFoundException.class, () -> expenseService.updateExpense(1L, updatedExpenseDto));
    }



}