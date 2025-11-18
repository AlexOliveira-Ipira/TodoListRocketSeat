package dev.oliveiratec.todolistrocketseat.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dev.oliveiratec.todolistrocketseat.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var serveletPath = request.getServletPath();

        if(serveletPath.contains("/tasks")){
            var autorizarion = request.getHeader("Authorization");

            var authEncoded = autorizarion.substring("Basic".length()).trim();

            byte[] autoDecode =  Base64.getDecoder().decode(authEncoded);

            var authString = new String(autoDecode);

            String[] credentials =  authString.split(":");
            String userName = credentials[0];
            String userPassword = credentials[1];

            // Validar Usuário
            var user  = userRepository.findByUsername(userName);
            if(user == null){
                response.sendError(401);
            }else {
                // Validar Senha
                var passwordVerify = BCrypt.verifyer().verify(userPassword.toCharArray() , user.get().getPassword());
                if(passwordVerify.verified){
                    request.setAttribute("idUser", user.get().getId());
                    filterChain.doFilter(request, response);
                }else{
                    response.sendError(401);
                }
            }
        }else {
            filterChain.doFilter(request, response);
        }
    }
}
