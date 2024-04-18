package br.edu.utfpr.iotapi.services;

import br.edu.utfpr.iotapi.core.PropertiesManager;
import br.edu.utfpr.iotapi.models.Pessoa;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Autowired
    private PropertiesManager propertiesManager;

    private String getSecret() {
        return propertiesManager.getConfigValue("jwt.secret");
    }

    private Instant getExpirationTime() {
        return LocalDateTime.now().plusMinutes(
                Long.parseLong(propertiesManager.getConfigValue("jwt.expiration-time-minutes")))
                .toInstant(ZoneOffset.of("-03:00"));
    }

    public String generateToken(Pessoa pessoa) {
        var algorithm = Algorithm.HMAC256(getSecret());

        return JWT.create()
                .withIssuer("iot-api")
                .withSubject(pessoa.getEmail())
                .withClaim("funcao", pessoa.getFuncao().name())
                .withExpiresAt(getExpirationTime())
                .sign(algorithm);
    }

    public String getEmailFromToken(String token) {
        var algorithm = Algorithm.HMAC256(getSecret());
        var verifier = JWT.require(algorithm).build();
        var decoded = verifier.verify(token);

        return decoded.getSubject();
    }
}
