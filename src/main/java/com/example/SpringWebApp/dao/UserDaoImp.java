package com.example.SpringWebApp.dao;

import com.example.SpringWebApp.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext(name = "TDPersistenceUnit")
    private EntityManager entityManager;

    public User findById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException("Пользователь с ID " + id + " не найден.");
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не может быть null.");
        }

        if (user.getId() == null || findById(user.getId()) == null) {
            throw new EntityNotFoundException("Пользователь с ID " + user.getId() + " не найден для обновления.");
        }
        entityManager.merge(user);
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        entityManager.remove(user);
    }


}
