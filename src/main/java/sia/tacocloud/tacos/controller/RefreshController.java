package sia.tacocloud.tacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import sia.tacocloud.tacos.jwt.JWTUtil;

@RestController
public class RefreshController {
    private final JWTUtil jwtUtil;
    private Long ACCESS_TOKEN_EXPIRED_TIME;

    public RefreshController(JWTUtil jwtUtil,
            @Value("${spring.jwt.access-expired-time}") Long ACCESS_TOKEN_EXPIRED_TIME) {
        this.jwtUtil = jwtUtil;
        this.ACCESS_TOKEN_EXPIRED_TIME = ACCESS_TOKEN_EXPIRED_TIME;
    }


    @GetMapping(path = "/tokenRefresh", headers = "HEADER")
    public ResponseEntity<Object> tokenRefresh(HttpServletRequest request) {
        String refreshToken = request.getHeader("refresh");

        // 토큰이 없다면 badRequest
        if (refreshToken == null) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 토큰이 있다면 만료 여부 확인, 만료시 badRequest
        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.badRequest().body("refresh token expired");
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refreshToken);

        if (!category.equals("refresh")) {
            return ResponseEntity.badRequest().body("invalid refresh token");
        }

        // 위 과정을 모두 거쳤다면 AccessToken 발급
        String username = jwtUtil.getUsername(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        String access = jwtUtil.createJwt("access", username, role, ACCESS_TOKEN_EXPIRED_TIME);
        return ResponseEntity.ok().header("access", access).build();

    }
}
