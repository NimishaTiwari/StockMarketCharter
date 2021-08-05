package com.stockmarketcharter.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stockmarketcharter.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


}
