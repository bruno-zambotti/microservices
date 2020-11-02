package br.com.zambotti.customer.repository;

import br.com.zambotti.customer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findFirstByUsername(String username);
}
