package org.sports.facility.center.repository;

import org.sports.facility.center.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Admin, Long> {
}
