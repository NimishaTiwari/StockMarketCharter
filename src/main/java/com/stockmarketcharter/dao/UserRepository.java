package com.stockmarketcharter.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stockmarketcharter.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	public UserEntity findByUsername(String username);
	
	@Query(value="UPDATE User SET confirmed = true WHERE userId = ?1 ",nativeQuery = true)
	@Modifying
	public void enable(Integer id);

	@Query(value="SELECT * FROM User WHERE verification_code = ?1",nativeQuery = true)
	public UserEntity findByVerification_code(String code);
	
	@Query(value="SELECT * FROM User",nativeQuery = true)
	public Page<UserEntity> findallUser(Pageable pePageable);

}
