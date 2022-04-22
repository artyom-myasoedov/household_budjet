package ru.vsu.hb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.hb.persistence.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserEmail(String userEmail);

    @Override
    User save(User user);

    void deleteByUserEmail(String userEmail);

}
