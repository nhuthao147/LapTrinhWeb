package com.edu.laptrinhweb.nhom4.repository;

import com.edu.laptrinhweb.nhom4.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
