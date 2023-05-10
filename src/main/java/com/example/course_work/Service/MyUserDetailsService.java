package com.example.course_work.Service;


import com.example.course_work.Entity.User;
import com.example.course_work.Repository.UserRepository;
import com.example.course_work.Utils.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        MyUserDetails details = new MyUserDetails(user);
        System.out.println(details.getAuthorities());
        return new MyUserDetails(user);
    }
}
