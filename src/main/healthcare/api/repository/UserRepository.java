package main.healthcare.api.repository;

import main.healthcare.api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    public Users findByUsername(String username);
    public Users findByUsernameAndPassword(String username, String password);
    public Boolean existsByUsername(String username);

}
