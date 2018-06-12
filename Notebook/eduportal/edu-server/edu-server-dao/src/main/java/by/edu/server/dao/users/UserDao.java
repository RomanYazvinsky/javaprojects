package by.edu.server.dao.users;

import by.edu.server.beans.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {
    List<User> getByUsernameAndPassword(String username, String password);
    @Query("SELECT u from User u left join u.notes where u.id = (:userId)")
    List<User> getWithNotes(@Param("userId") Integer id);

    @Query("SELECT u from User u left join u.notes where u.username = (:uname) and u.password = (:pswrd)")
    List<User> getByUsernameAndPasswordWithNotes(@Param("uname") String username, @Param("pswrd") String password);
}
