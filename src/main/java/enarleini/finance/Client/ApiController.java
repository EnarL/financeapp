package enarleini.finance.Client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("")
    public Map<String, String> getAllEndpoints() {
        Map<String, String> endpoints = new HashMap<>();

        // User endpoints
        endpoints.put("Register User", "POST /api/users/register");
        endpoints.put("Login User", "POST /api/users/login");
        endpoints.put("Assign Role to User (Admin)", "POST /api/users/{username}/role");
        endpoints.put("Find All Users (Admin)", "GET /api/users/findall");
        endpoints.put("Find User by ID", "GET /api/users/id/{id}");
        endpoints.put("Find User by Username", "GET /api/users/username/{username}");
        endpoints.put("Change User Password", "PUT /api/users/changepassword/{username}");
        endpoints.put("Delete User", "DELETE /api/users/{id}");

        // Expense endpoints
        endpoints.put("Add Expense", "POST /api/expenses");
        endpoints.put("Get All Expenses", "GET /api/expenses");
        endpoints.put("Get Expense by ID", "GET /api/expenses/{id}");
        endpoints.put("Update Expense", "PUT /api/expenses/{id}");
        endpoints.put("Delete Expense", "DELETE /api/expenses/{id}");

        // Income endpoints
        endpoints.put("Add Income", "POST /api/incomes");
        endpoints.put("Get All Incomes", "GET /api/incomes");
        endpoints.put("Get Income by ID", "GET /api/incomes/{id}");
        endpoints.put("Update Income", "PUT /api/incomes/{id}");
        endpoints.put("Delete Income", "DELETE /api/incomes/{id}");

        return endpoints;
    }
}