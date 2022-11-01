package com.example.app.Data.Repository;

import com.example.app.Data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// gain access to the database with this interface
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
     UserEntity findByUsername(String username);
}
