/*package br.com.cursosEBS.gateway.UtilJWT;

import br.com.cursosEBS.gateway.infra.exception.ValidationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${api.security.token.secret}")
    private String secretKey;

    public String getSubject(String tokenJWT) {

        try {

            var algoritmo = Algorithm.HMAC256(secretKey);

            return JWT.require(algoritmo)
                    .withIssuer("User API")
                    .build()
                    //verifica se esse token que está chegando como parametro está valido de acordo com esse algoritmo e com esse Issuer na hora de verificar o token
                    .verify(tokenJWT)
                    .getToken();

        } catch (JWTVerificationException exception){
            throw new ValidationException("JWT invalido");
        }

    }

}*/

package br.com.cursosEBS.gateway.UtilJWT;

import br.com.cursosEBS.gateway.infra.exception.ValidationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUtil {

    @Value("${api.security.token.secret}")
    private String secretKey;

    public Optional<UserDetailsCustom> validateToken(String tokenJWT) {

        try {

            // Cria o algoritmo para validar o token
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            // Configura o verificador do token JWT
            JWTVerifier verifier = JWT.require(algorithm).build();

            // Decodifica e verifica o token
            DecodedJWT decodedJWT = verifier.verify(tokenJWT);

            return Optional.of(new UserDetailsCustom(decodedJWT));

        } catch (JWTVerificationException exception){
            throw new ValidationException("JWT invalido");
        }

    }

}

