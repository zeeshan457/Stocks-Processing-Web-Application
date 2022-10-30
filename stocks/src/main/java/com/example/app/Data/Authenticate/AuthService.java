package com.example.app.Data.Authenticate;

import com.example.app.Data.Entity.UserEntity;
import com.example.app.Data.Repository.UserRepository;
import com.example.app.Data.Role;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class AuthService {

    // Injecting Repository in service class
    @Autowired
    public void UserService(UserRepository repository) {
        this.repo = repository;
    }



    public void Login(String username, String password) throws AuthException {
        UserEntity user = repo.findByUsername(username);
        if (user != null) {
            VaadinSession.getCurrent().setAttribute(UserEntity.class, user);
        } else {
            throw new AuthException();
        }
    }

    // calling abstract method from the abst
    public void Register(String username, String password) throws AuthException {
        UserEntity user = new UserEntity(username, password, Role.USER);
        repo.save(user);
    }

    
    // Implementing abstract methods from UserRepository
    private UserRepository repo = new UserRepository() {
        @Override
        public List<UserEntity> findAll() {
            return null;
        }
        @Override
        public List<UserEntity> findAll(Sort sort) {
            return null;
        }
        @Override
        public Page<UserEntity> findAll(Pageable pageable) {
            return null;
        }
        @Override
        public List<UserEntity> findAllById(Iterable<Integer> integers) {
            return null;
        }
        @Override
        public long count() {
            return 0;
        }
        @Override
        public void deleteById(Integer integer) {
        }
        @Override
        public void delete(UserEntity entity) {
        }
        @Override
        public void deleteAllById(Iterable<? extends Integer> integers) {
        }
        @Override
        public void deleteAll(Iterable<? extends UserEntity> entities) {
        }
        @Override
        public void deleteAll() {
        }
        @Override
        public <S extends UserEntity> S save(String username, String password) {
            return null;
        }
        @Override
        public <S extends UserEntity> S save(S entity) {
            return null;
        }
        @Override
        public <S extends UserEntity> List<S> saveAll(Iterable<S> entities) {
            return null;
        }
        @Override
        public Optional<UserEntity> findById(Integer integer) {
            return Optional.empty();
        }
        @Override
        public boolean existsById(Integer integer) {
            return false;
        }
        @Override
        public void flush() {

        }
        @Override
        public <S extends UserEntity> S saveAndFlush(S entity) {
            return null;
        }
        @Override
        public <S extends UserEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
            return null;
        }
        @Override
        public void deleteAllInBatch(Iterable<UserEntity> entities) {

        }
        @Override
        public void deleteAllByIdInBatch(Iterable<Integer> integers) {

        }
        @Override
        public void deleteAllInBatch() {
        }
        @Override
        public UserEntity getOne(Integer integer) {
            return null;
        }
        @Override
        public UserEntity getById(Integer integer) {
            return null;
        }
        @Override
        public UserEntity getReferenceById(Integer integer) {
            return null;
        }
        @Override
        public <S extends UserEntity> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }
        @Override
        public <S extends UserEntity> List<S> findAll(Example<S> example) {
            return null;
        }
        @Override
        public <S extends UserEntity> List<S> findAll(Example<S> example, Sort sort) {
            return null;
        }
        @Override
        public <S extends UserEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
            return null;
        }
        @Override
        public <S extends UserEntity> long count(Example<S> example) {
            return 0;
        }
        @Override
        public <S extends UserEntity> boolean exists(Example<S> example) {
            return false;
        }
        @Override
        public <S extends UserEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
            return null;
        }
        @Override
        public UserEntity findByUsername(String username) {
            return null;
        }
    };
}
