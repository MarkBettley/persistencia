package com.ebac.modulo59.model;

import com.ebac.modulo59.dto.Direccion;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DireccionModel {

    private final EntityManager entityManager;

    public DireccionModel(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void guardar(Direccion direccion) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(direccion);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public void actualizar(Direccion direccion) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(direccion);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public Direccion obtenerPorId(int id) {
        return entityManager.find(Direccion.class, id);
    }

    public void eliminar(Direccion direccion) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.remove(direccion);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public List<Direccion> obtenerDirecciones() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Direccion> cq = cb.createQuery(Direccion.class);

        Root<Direccion> rootEntry = cq.from(Direccion.class);
        CriteriaQuery<Direccion> select = cq.select(rootEntry);

        TypedQuery<Direccion> query = entityManager.createQuery(select);
        return query.getResultList();
    }
}
