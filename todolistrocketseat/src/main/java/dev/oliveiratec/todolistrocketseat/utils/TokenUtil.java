package dev.oliveiratec.todolistrocketseat.utils;

import dev.oliveiratec.todolistrocketseat.dto.TokenDTO;
import dev.oliveiratec.todolistrocketseat.model.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Collections;
import java.util.Date;

public class TokenUtil {

    public static final String EMISSOR = "AlexOliveira";
    public static final long EXPIRATION = 60 * 60 * 1000;
    public static final String SECRET_KEY = "1234567890123456789012345678901234567891";



    public static TokenDTO encode(UserModel user){

        try {

            Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
            String jwtToken = Jwts.builder()
                    .subject(user.getUsername())
                    .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                    .issuer(EMISSOR)
                    .signWith(key)
                    .compact();
            return new TokenDTO(jwtToken);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static Authentication decode(HttpServletRequest request){

        try{
            String header = request.getHeader("Authorization");
            if(header != null){
                String token = header.replace("Bearer " , "");
                SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
                JwtParser parser = Jwts.parser().verifyWith(key).build();
                Claims claims = (Claims)parser.parse(token).getPayload();

                String subject = claims.getSubject();
                String issuer = claims.getIssuer();
                Date exp = claims.getExpiration();

                if(issuer.equalsIgnoreCase(EMISSOR) && subject.length() > 0 && exp.after(new Date(System.currentTimeMillis()))){
                    return new UsernamePasswordAuthenticationToken("Valido", null, Collections.emptyList());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
