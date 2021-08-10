package abc.wuyan.demo.service.impl;

import abc.wuyan.demo.model.User;
import abc.wuyan.demo.service.TokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public String getToken(User user) {
        String token="";
        token= JWT.create().withAudience(user.getName())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
