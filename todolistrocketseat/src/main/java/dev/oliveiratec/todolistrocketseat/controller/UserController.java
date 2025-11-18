package dev.oliveiratec.todolistrocketseat.controller;

import dev.oliveiratec.todolistrocketseat.dto.TokenDTO;
import dev.oliveiratec.todolistrocketseat.model.UserModel;
import dev.oliveiratec.todolistrocketseat.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserModel>> list(HttpServletRequest request){
        return ResponseEntity.ok().body(userService.allUser());
    }


    @GetMapping("/username/{Name}")
    public ResponseEntity<UserModel> getByUsername(@PathVariable String Name) {
        return ResponseEntity.ok().body(userService.getUsername(Name));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserModel userModel){
        try {
            return userService.createdUser(userModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserModel user){
        return ResponseEntity.ok(userService.userLogin(user));
    }
}