package com.Shivesh.AuthService.services;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Shivesh.AuthService.entities.UserInfo;
import com.Shivesh.AuthService.entities.UserRoles;

public class CustomUserDetails extends UserInfo implements UserDetails {
	
	private String username;
	private String password;
	Collection<? extends GrantedAuthority> authorities;
	
	
	public CustomUserDetails(UserInfo user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		List<GrantedAuthority> auths = new ArrayList<>();
		
		for(UserRoles role : user.getRoles()) {
			auths.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
		}
		this.authorities = auths;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}
	
	

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
