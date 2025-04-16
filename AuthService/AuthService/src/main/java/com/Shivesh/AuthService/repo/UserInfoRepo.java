package com.Shivesh.AuthService.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Shivesh.AuthService.entities.UserInfo;

@Repository
public interface UserInfoRepo extends CrudRepository<UserInfo,String> {
  public UserInfo findByUsername(String username);
}
