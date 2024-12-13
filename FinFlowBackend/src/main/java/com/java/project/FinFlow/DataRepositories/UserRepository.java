package com.java.project.FinFlow.DataRepositories;

import com.java.project.FinFlow.Tables.User;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@ComponentScan(basePackages={"com.java.project.FinFlow.DateRepositories"})

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	
	@Query("SELECT u.budget \n"
			+ "FROM User u \n"
			+ "WHERE u.userId = :userId")
	Float getBudgetByUserId(String userId);
}
