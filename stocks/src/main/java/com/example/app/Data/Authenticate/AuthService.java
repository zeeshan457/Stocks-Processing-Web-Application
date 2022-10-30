package com.example.app.Data.Authenticate;

import com.example.app.Data.Entity.UserEntity;
import com.example.app.Data.Login.UserRepository;
import com.example.app.Data.Role;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.util.Optional;

@Service
public class AuthService {

    private UserRepository repo;
    private UserEntity user;

    @Autowired
    public void UserService(UserRepository repository) {
        this.repo = repository;
    }

    public Optional<UserEntity> get(int id) {
        return repo.findById(id);
    }

    public void Login(String username, String password) throws AuthException {
        UserEntity user = repo.findByUsername(username);
        if (user != null) {
            VaadinSession.getCurrent().setAttribute(UserEntity.class, user);
        } else {
            throw new AuthException();
        }
    }

    public void Register(String username, String password) {
        UserEntity user = repo.save(new UserEntity(1, username, password, "USER"));
    }

    public void delete(int id) {
        repo.deleteById(id);
    }



//    public Page<User> list(Pageable pageable) {
//        return repo.findAll(pageable);
//    }

    public int count() {
        return (int) repo.count();
    }
}
