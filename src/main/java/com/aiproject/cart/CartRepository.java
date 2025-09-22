package com.aiproject.cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	@Query("SELECT SUM(c.quantity * p.price) FROM Cart c JOIN c.product p WHERE c.member.memberEmail = :email")
	Integer findTotalPriceByEmail(@Param("email") String email);
	

	@Transactional
	@Modifying
	@Query("DELETE FROM Cart c WHERE c.member.memberIdx = :memberIdx")
	void cartRemoveAll(@Param("memberIdx") int memberIdx);
	List<Cart> findByMemberMemberIdx(Integer memberIdx);
	void deleteById(Integer orderIdx);
	
	@Transactional
	@Modifying
	@Query("UPDATE Cart c SET c.quantity = :quantity WHERE c.orderIdx = :orderIdx")
	int updateQuantity(@Param("orderIdx") int orderIdx, @Param("quantity") int quantity);
}