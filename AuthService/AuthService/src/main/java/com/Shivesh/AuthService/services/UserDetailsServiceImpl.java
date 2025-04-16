package com.Shivesh.AuthService.services;

import java.util.HashSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Shivesh.AuthService.dto.UserInfoDto;
import com.Shivesh.AuthService.entities.UserInfo;
import com.Shivesh.AuthService.repo.UserInfoRepo;

import lombok.AllArgsConstructor;
import lombok.Data;


@Service
@AllArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService{
	
	
	@Autowired
	private UserInfoRepo userInfoRepo;
	
	
	@Autowired
	private final PasswordEncoder passwordEncoder ;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserInfo userFetched = userInfoRepo.findByUsername(username);
		if(userFetched==null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(userFetched);
	}
	
	public UserInfo checkIfAlreadyExists(String username) {
		return userInfoRepo.findByUsername(username);
	}
	
	public boolean signUp(UserInfoDto userInfoDto) {
		
		userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
		
		UserInfo userExist = this.checkIfAlreadyExists(userInfoDto.getUsername());
		if(userExist!=null) {
			return false;
		}
		String userId = UUID.randomUUID().toString();
		userInfoRepo.save(new UserInfo(userId,userInfoDto.getUsername(),userInfoDto.getPassword(),new HashSet<>()));
		return true;
	}

}
