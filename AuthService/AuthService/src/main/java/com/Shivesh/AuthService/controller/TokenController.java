package com.Shivesh.AuthService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Shivesh.AuthService.dto.AuthRequestDto;
import com.Shivesh.AuthService.dto.JwtResponseDto;
import com.Shivesh.AuthService.dto.RefreshTokenDto;
import com.Shivesh.AuthService.entities.RefreshToken;
import com.Shivesh.AuthService.services.JwtService;
import com.Shivesh.AuthService.services.RefreshTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class TokenController {

	 @Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private RefreshTokenService refreshTokenService;

	    @Autowired
	    private JwtService jwtService;

	    @PostMapping("auth/v1/login")
	    public ResponseEntity AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDTO){
	        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
	        if(authentication.isAuthenticated()){
	            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
	            return new ResponseEntity<>(JwtResponseDto.builder()
	                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))
	                    .token(refreshToken.getToken())
	                    .build(), HttpStatus.OK);

	        } else {
	            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	    @PostMapping("auth/v1/refreshToken")
	    public JwtResponseDto refreshToken(@RequestBody RefreshTokenDto refreshTokenRequestDTO){
	        return refreshTokenService.getRefreshToken(refreshTokenRequestDTO.getRefreshToken())
	                .map(refreshTokenService::verifyExpiration)
	                .map(RefreshToken::getUserInfo)
	                .map(userInfo -> {
	                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
	                    return JwtResponseDto.builder()
	                            .accessToken(accessToken)
	                            .token(refreshTokenRequestDTO.getRefreshToken()).build();
	                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
	    }

}
