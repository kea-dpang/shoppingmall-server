package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.entity.user.UserDetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 사용자 상세 정보에 대한 데이터 접근을 관리하는 Repository 인터페이스입니다.
 * JpaRepository 인터페이스를 상속받아 기본적인 CRUD 메서드를 사용할 수 있습니다.
 */
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

    /**
     * 사용자의 사원 번호에 특정 키워드가 포함된 사용자의 상세 정보 목록을 페이지네이션하여 조회합니다.
     *
     * @param employeeNumber  사원 번호에서 검색할 키워드
     * @param pageable 페이지네이션 정보
     * @return 키워드가 포함된 사원 번호를 가진 사용자의 상세 정보 목록
     */
    Page<UserDetail> findByEmployeeNumber(Long employeeNumber, Pageable pageable);

    /**
     * 사용자의 이름에 특정 키워드가 포함된 사용자의 상세 정보 목록을 페이지네이션하여 조회합니다.
     *
     * @param keyword  사용자 이름에서 검색할 키워드
     * @param pageable 페이지네이션 정보
     * @return 키워드가 포함된 사용자 이름을 가진 사용자의 상세 정보 목록
     */
    Page<UserDetail> findByNameContaining(String keyword, Pageable pageable);

    /**
     * 사용자 ID에 해당하는 사용자의 상세 정보를 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 사용자 ID에 해당하는 사용자의 상세 정보
     */
    UserDetail findByUserId(Long userId);
}
