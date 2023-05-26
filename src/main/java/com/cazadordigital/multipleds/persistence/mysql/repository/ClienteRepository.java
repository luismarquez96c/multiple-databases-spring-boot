package com.cazadordigital.multipleds.persistence.mysql.repository;

import com.cazadordigital.multipleds.persistence.mysql.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
}
