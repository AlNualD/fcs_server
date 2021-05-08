package ru.devegang.fcs_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.devegang.fcs_server.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    User findUserByName(String name);
    Optional<User> findByLogin(String login);
    boolean existsUserByLogin(String login);
}
