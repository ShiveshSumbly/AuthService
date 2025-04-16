package com.Shivesh.AuthService.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Shivesh.AuthService.entities.RefreshToken;

@Repository
public interface RefreshTokenRepo extends CrudRepository<RefreshToken, Integer> {
	Optional<RefreshToken> findByToken(String token);
}
