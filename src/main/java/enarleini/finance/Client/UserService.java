package enarleini.finance.Client;

import enarleini.finance.Client.exception.UserNotFoundException;
import enarleini.finance.Client.exception.InvalidUserException;
import enarleini.finance.config.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        if (repository.findByUsername(user.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Roles.USER);
        return repository.save(user);
    }

    public Map<String, String> verify(Users user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        String token = "";
        if (authentication.isAuthenticated()) {
            token = jwtService.generateToken(user.getUsername());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials");
        }
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }

    public List<Users> findAllClients() {
        return repository.findAll();
    }

    public Users findClientById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("Client not found with id " + id));
    }

    public Users findClientByUsername(String username) {
        Users user = repository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("Client not found with username " + username);
        }
        return user;
    }

    public void deleteClient(Long id) {
        if (!repository.existsById(id)) {
            throw new UserNotFoundException("Client not found with id " + id);
        }
        repository.deleteById(id);
    }

    public void changeClientPassword(String username, String currentPassword, String newPassword) {
        Users existingClient = findClientByUsername(username);
        if (encoder.matches(currentPassword, existingClient.getPassword())) {
            existingClient.setPassword(encoder.encode(newPassword));
            repository.save(existingClient);
        } else {
            throw new InvalidUserException("Current password is incorrect");
        }
    }

    public void assignRoleToUser(String username, Roles role) {
        Users user = repository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found with username " + username);
        }
        user.setRole(role);
        repository.save(user);
    }
}