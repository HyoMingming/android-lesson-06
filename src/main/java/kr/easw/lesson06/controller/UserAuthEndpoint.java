package kr.easw.lesson06.controller;

import kr.easw.lesson06.model.dto.ExceptionalResultDto;
import kr.easw.lesson06.model.dto.UserDataEntity;
import kr.easw.lesson06.service.UserDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthEndpoint {

    private final UserDataService userDataService;

    // 생성자
    public UserAuthEndpoint(final UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    // 로그인 엔드포인트
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDataEntity entity) {
        try {
            // 로그인 성공 시 토큰을 반환
            return ResponseEntity.ok(userDataService.createTokenWith(entity));
        } catch (Exception e) {
            // 로그인 실패 시 예외 처리
            return ResponseEntity.badRequest().body(new ExceptionalResultDto(e.getMessage()));
        }
    }

    // 회원가입 엔드포인트
    @PostMapping("/register")
    public void register(@RequestBody UserDataEntity entity) {

        if (userDataService.isUserExists(entity.getUserId())) {

            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        // 비밀번호 암호화
        String encodedPassword = new BCryptPasswordEncoder().encode(entity.getPassword());
        // 사용자 생성
        userDataService.createUser(new UserDataEntity(0L, entity.getUserId(), encodedPassword, false));
    }
}
