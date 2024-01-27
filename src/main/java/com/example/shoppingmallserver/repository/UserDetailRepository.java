package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.user.UserDetail;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 상세 정보에 대한 데이터 접근을 관리하는 Repository 인터페이스입니다.
 * JpaRepository 인터페이스를 상속받아 기본적인 CRUD 메서드를 사용할 수 있습니다.
 */
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

    /**
     * 사용자 이름에 특정 키워드가 포함된 사용자의 상세 정보 목록을 조회합니다.
     * 이 메서드는 주로 관리자가 사용자 정보를 검색할 때 사용됩니다.
     *
     * @param keyword 사용자 이름에서 검색할 키워드
     * @return 키워드가 포함된 사용자 이름을 가진 사용자의 상세 정보 목록
     */
    List<UserDetail> findByNameContaining(String keyword);


    UserDetail findByUserId(Long userId);
}
