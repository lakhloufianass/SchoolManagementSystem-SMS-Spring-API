package com.lasm.sms.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.lasm.sms.entities.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long>{

	UserEntity findByEmail(String email);
}
