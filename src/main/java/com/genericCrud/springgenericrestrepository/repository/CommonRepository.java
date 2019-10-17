package com.genericCrud.springgenericrestrepository.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * *
 *
 * @author : Dushman Nalin
 * @version : 1.0
 * @date : September 25, 2019
 */
@Repository
public class CommonRepository implements Serializable {

    @PersistenceContext
    EntityManager entityManager;

    public <E> E insert(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    public <S> List<S> saveList(Iterable<S> entities) {
        List<S> result = new ArrayList<S>();
        if (entities == null) {
            return result;
        }
        for (S entity : entities) {
            result.add(entityManager.merge(entity));
        }
        return result;
    }

    public <S> List<S> update(Iterable<S> entities) {
        List<S> result = new ArrayList<S>();
        if (entities == null) {
            return result;
        }
        for (S entity : entities) {
            result.add(entityManager.merge(entity));
        }
        return result;
    }

    public <S> void delete(Iterable<S> entities) {

        for (S entity : entities) {
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        }

    }

    public <T> T select(Class<T> entity, Object primaryKey) {
        return entityManager.find(entity, primaryKey);
    }

    public <T> T selectAll(Class<T> entity, Object primaryKey) {
        return entityManager.find(entity, primaryKey);
    }


}