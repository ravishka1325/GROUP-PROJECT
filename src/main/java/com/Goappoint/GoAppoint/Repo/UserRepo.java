package com.Goappoint.GoAppoint.Repo;

import com.Goappoint.GoAppoint.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByUserEmail(String userEmail);
}
