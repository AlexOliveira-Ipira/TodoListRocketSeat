package dev.oliveiratec.todolistrocketseat.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dev.oliveiratec.todolistrocketseat.dto.TokenDTO;
import dev.oliveiratec.todolistrocketseat.model.UserModel;
import dev.oliveiratec.todolistrocketseat.repository.UserRepository;
import dev.oliveiratec.todolistrocketseat.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String createdUser(UserModel user){
        String username = user.getUsername();
        if (userRepository.findByUsername(username).isPresent()) {
            return "Usuário já cadastrado";
        }
        String hashedPassword = BCrypt.withDefaults()
                .hashToString(12, user.getPassword().toCharArray());
        user.setPassword(hashedPassword);
        UserModel savedUser = userRepository.save(user);
        return savedUser.getUsername();
    }

    public TokenDTO userLogin(UserModel user){
        UserModel storedUser = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(user.getPassword(), storedUser.getPassword())){
            throw new RuntimeException("Usuário não autorizado!");
        }
        return TokenUtil.encode(storedUser);
    }

    public List<UserModel> allUser (){
        List<UserModel> listUser =  userRepository.findAll();
        return listUser;
    }

    public UserModel getUsername(String userModel){
        UserModel isUser = userRepository.findByUsername(userModel).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        if(isUser != null) return isUser;
        return null;
    }
}
