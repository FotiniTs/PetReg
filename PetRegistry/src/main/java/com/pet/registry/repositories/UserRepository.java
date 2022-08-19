package com.pet.registry.repositories;

import com.pet.registry.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User,Integer> {
    boolean existsByUsername(String username);
    User findByUsername(String username);

    @Query(value="select * from users where username=:username and role='ROLE_ADMIN'", nativeQuery = true)
    User findAdminByUsername(@Param("username") String username);
}
