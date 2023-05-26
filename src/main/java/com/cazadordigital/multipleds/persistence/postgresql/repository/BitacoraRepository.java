package com.cazadordigital.multipleds.persistence.postgresql.repository;

import com.cazadordigital.multipleds.persistence.postgresql.entity.BitacoraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BitacoraRepository extends JpaRepository<BitacoraEntity, Long> {
}
