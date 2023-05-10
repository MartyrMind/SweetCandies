package com.example.course_work.Service;

import com.example.course_work.Entity.DTO.UserDto;
import com.example.course_work.Entity.User;
import com.example.course_work.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public User update(User old, User nw) {
        old.setFirstname(nw.getFirstname());
        old.setSecondname(nw.getSecondname());
        old.setBirthday(nw.getBirthday());
        old.setEmail(nw.getEmail());
        return save(old);
    }

    public List<User> getBirthdayUsers(int month, int day) {
        return userRepository.getBirthdayUsers(month, day);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
