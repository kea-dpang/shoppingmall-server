package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.user.User;

import com.example.shoppingmallserver.entity.user.UserDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 사용자의 이메일을 기반으로 사용자를 조회합니다.
     *
     * @param email 조회할 사용자의 이메일
     * @return 이메일에 해당하는 User 엔티티. 해당 이메일을 가진 사용자가 없는 경우 빈 Optional을 반환
     */
    Optional<User> findByEmail(String email);

  
    Page<User> findByEmailContaining(String keyword, Pageable pageable);

    /**
     * 사용자 ID에 해당하는 사용자와 그에 연관된 사용자의 상세 정보를 함께 조회합니다.
     * 이 메서드는 사용자의 상세 정보를 함께 로딩하여 사용자의 상세 정보를 사용할 때 추가적인 쿼리를 방지합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 사용자 ID에 해당하는 사용자와 그에 연관된 사용자의 상세 정보
     */
    @Query("SELECT u FROM User u JOIN FETCH u.userDetail WHERE u.id = :userId")
    User findWithUserDetailById(@Param("userId") Long userId);
}
