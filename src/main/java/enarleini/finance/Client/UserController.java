package enarleini.finance.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/register")
    public Users register(@RequestBody Users user){
        return service.register(user);
    }
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Users user)   {
        return service.verify(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{username}/role")
    public void assignRoleToUser(@PathVariable String username, @RequestParam Roles role) {
        service.assignRoleToUser(username, role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/findall")
    public List<Users> findAll() {
        return service.findAllClients();
    }

    @PreAuthorize("hasRole('ADMIN') or @userService.findClientById(#id).getUsername() == authentication.principal.username")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/id/{id}")
    public Users findById(@PathVariable Long id){
        return service.findClientById(id);
    }
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/username/{username}")
    public Users findByUsername(@PathVariable String username){
        return service.findClientByUsername(username);
    }

    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/changepassword/{username}")
    public void changePassword(@PathVariable String username, @RequestParam String currentPassword, @RequestParam String newPassword) {
        service.changeClientPassword(username, currentPassword, newPassword);
    }
    @PreAuthorize("hasRole('ADMIN') or @userService.findClientById(#id).getUsername() == authentication.principal.username")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteClient(id);
    }

}