package com.example.app.Data.Repository;

import com.example.app.Data.Entity.StocksEntity;
import com.example.app.Data.Entity.UserEntity;
import com.example.app.Data.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


// gain access to the database with this interface
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

     // find username
     UserEntity findByUsername(String username);

     // find role
     UserEntity findByRoles(Role role);

     // Getting all USERNAMES from GPA table
     @Query("SELECT s.username FROM UserEntity s")
     List<String> getUsernames();
}
