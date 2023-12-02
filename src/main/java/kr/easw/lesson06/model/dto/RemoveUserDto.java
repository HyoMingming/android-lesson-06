package kr.easw.lesson06.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 사용자 삭제를 위한 DTO 클래스입니다.
 */
@Getter
public class RemoveUserDto {

    /** 사용자 아이디 */
    private String userId;

    /**
     * RemoveUserDto의 생성자로, 사용자 아이디를 받아 초기화합니다.
     *
     * @param userId 삭제할 사용자의 아이디
     */
    public RemoveUserDto(String userId) {
        this.userId = userId;
    }

    /**
     * 사용자 아이디를 반환하는 메서드입니다.
     *
     * @return 사용자 아이디
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * 사용자 아이디를 설정하는 메서드입니다.
     *
     * @param userId 설정할 사용자 아이디
     */
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * 기본 생성자입니다.
     */
    public RemoveUserDto() {
    }
}
