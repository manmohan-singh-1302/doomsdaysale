/* Repositories are constructed to add an abstraction layer between the database and application
* */
package com.doomsdaysale.repository;

import com.doomsdaysale.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
