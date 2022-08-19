package com.linkacademy.commerceapp.domain.repository;

import com.linkacademy.commerceapp.domain.entity.CommerceRole;
import com.linkacademy.commerceapp.domain.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<Role, UUID> {
    Optional<Role> findByCommerceRole(CommerceRole commerceRole);
}
