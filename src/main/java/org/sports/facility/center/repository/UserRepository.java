package org.sports.facility.center.repository;

import org.sports.facility.center.entity.User;
import org.sports.facility.center.enumconstant.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    User findByEmail(String email);

    List<User> findByRoles(UserType userType);
}
