package com.example.course_work.Repository;

import com.example.course_work.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where month(u.birthday) = ?1 and day(u.birthday) = ?2")
    List<User> getBirthdayUsers(int month, int day);
    User getUserByUsername(String username);
}

