package com.Shivesh.AuthService.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Shivesh.AuthService.entities.RefreshToken;
import com.Shivesh.AuthService.entities.UserInfo;
import com.Shivesh.AuthService.repo.RefreshTokenRepo;
import com.Shivesh.AuthService.repo.UserInfoRepo;

@Service
public class RefreshTokenService {
    
	
	@Autowired
	private UserInfoRepo userInfoRepo;
	
	@Autowired
	private RefreshTokenRepo refreshTokenRepo;
	
	public RefreshToken createRefreshToken(String username) {
			UserInfo userExtracted = userInfoRepo.findByUsername(username);
			RefreshToken refreshToken = RefreshToken.builder()
					.userInfo(userExtracted)
					.token(UUID.randomUUID().toString())
					.expiryDate(Instant.now().plusMillis(600000))
					.build();
			return refreshTokenRepo.save(refreshToken);
	}
	
	
	public RefreshToken verifyExpiration(RefreshToken refreshToken) {
		
		if(refreshToken.getExpiryDate().compareTo(Instant.now())<0) {
			refreshTokenRepo.delete(refreshToken);
			throw new RuntimeException(refreshToken.getToken()+" Refresh Token is expired , please Sign In again");
			
		}
		return refreshToken;
		
	}
	
	public Optional<RefreshToken> getRefreshToken(String token) {
		return refreshTokenRepo.findByToken(token);
		
	}
	
}
