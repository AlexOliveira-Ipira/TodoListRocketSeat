package dev.oliveiratec.todolistrocketseat.controller;

import dev.oliveiratec.todolistrocketseat.dto.TokenDTO;
import dev.oliveiratec.todolistrocketseat.model.UserModel;
import dev.oliveiratec.todolistrocketseat.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<String> create(@RequestBody UserModel userModel){
      return ResponseEntity.ok(userService.createdUser(userModel));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserModel user){
        return ResponseEntity.ok(userService.userLogin(user));
    }
}