package com.example.app.Data.Repository;

import com.example.app.Data.Entity.UserEntity;
import com.example.app.Data.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {


     /**
      *
      * @param username
      * @return the username, string format
      */
     UserEntity findByUsername(String username);

     /**
      *
      * @param role
      * @return the role of user
      */
     UserEntity findByRoles(Role role);

     /**
      *
      *
      * @return list of all usernames from table
      */
     @Query("SELECT s.username FROM UserEntity s")
     List<String> getUsernames();
}
