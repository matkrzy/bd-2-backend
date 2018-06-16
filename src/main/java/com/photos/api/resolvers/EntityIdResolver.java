package com.photos.api.resolvers;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;

import javax.persistence.EntityManager;

public class EntityIdResolver implements ObjectIdResolver {
    private EntityManager entityManager;

    public EntityIdResolver(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityIdResolver(){}

    @Override
    public void bindItem(ObjectIdGenerator.IdKey idKey, Object o) {

    }

    @Override
    public Object resolveId(ObjectIdGenerator.IdKey idKey) {
        return this.entityManager.find(idKey.scope, idKey.key);
    }

    @Override
    public ObjectIdResolver newForDeserialization(Object o) {
        return this;
    }

    @Override
    public boolean canUseFor(ObjectIdResolver objectIdResolver) {
        return false;
    }
}
