package com.example.app.Data.Login;

import com.example.app.Data.Entity.User;
import com.vaadin.flow.component.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.awt.print.Pageable;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoginService {

    private UserRepository repo;

    @Autowired
    public void UserService(UserRepository repository) {
        this.repo = repository;
    }

    public Optional<User> get(long id) {
        return repo.findById(id);
    }

    public User update(User entity) {
        return repo.save(entity);
    }

    public void delete(long id) {
        repo.deleteById(id);
    }

//    public Page<User> list(Pageable pageable) {
//        return repo.findAll(pageable);
//    }

    public int count() {
        return (int) repo.count();
    }
}
