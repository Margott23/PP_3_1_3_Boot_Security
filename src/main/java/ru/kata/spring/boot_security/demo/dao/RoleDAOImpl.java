package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Role findByRole(String roleName) {
        if (roleName.isEmpty()) {
            roleName = "ROLE_USER";
        }
        Query query = entityManager.createQuery("SELECT r FROM Role r WHERE r.role = :roleName");
        query.setParameter("roleName", roleName);
        return (Role) query.getSingleResult();
    }

    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role findById(int id) {
        return entityManager.find(Role.class, id);
    }
}
