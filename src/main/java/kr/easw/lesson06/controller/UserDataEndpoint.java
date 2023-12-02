package kr.easw.lesson06.controller;

import java.util.List;
import java.util.stream.Collectors;
import kr.easw.lesson06.model.dto.RemoveUserDto;
import kr.easw.lesson06.model.dto.UserDataEntity;
import kr.easw.lesson06.service.UserDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/v1/user"})
//사용자 데이터 서비스 주입
public class UserDataEndpoint {
    private final UserDataService userDataService;

    //모든 사용자의 ID 목록을 반환하는 엔드포인트
    @GetMapping({"/list"})
    public List<String> listUsers() {
        return (List)this.userDataService.getAllUsers().stream().map(UserDataEntity::getUserId).collect(Collectors.toList());
    }

    //사용자 삭제 엔드포인트
    @PostMapping({"/remove"})
    public ResponseEntity<String> removeUser(@RequestBody RemoveUserDto removeUserDto) {
        boolean isRemoved = this.userDataService.removeUser(removeUserDto.getUserId());
        return isRemoved ? ResponseEntity.ok("User removed successfully") : ResponseEntity.badRequest().body("Failed to remove user");
    }

    public UserDataEndpoint(final UserDataService userDataService) {
        this.userDataService = userDataService;
    }
}
