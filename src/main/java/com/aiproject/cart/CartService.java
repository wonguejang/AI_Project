package com.aiproject.cart;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aiproject.member.Member;
import com.aiproject.member.MemberRepository;
import com.aiproject.product.Product;
import com.aiproject.product.ProductRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository cRepo;
    
    @Autowired
    private MemberRepository mRepo;
    
    @Autowired
    private ProductRepository pRepo;
    
    public int TotalPrice(String userId) {
        Integer totalprice = cRepo.findTotalPriceByEmail(userId);
        return totalprice != null ? totalprice : 0;
    }
    
    public void cartRemoveAll(String userId) {
    	Optional<Member> member =  mRepo.findByMemberEmail(userId);
    	int memberIdx = member.get().getMemberIdx(); 
    	cRepo.cartRemoveAll(memberIdx);
    }
    
    public void addCart(String userEmail, int productIdx) {
        Member member = mRepo.findByMemberEmail(userEmail)
                             .orElseThrow(() -> new RuntimeException("회원 정보가 없습니다."));
        Product product = pRepo.findById(productIdx)
                               .orElseThrow(() -> new RuntimeException("상품 정보가 없습니다."));

        Cart cart = new Cart();
        cart.setMember(member);
        cart.setProduct(product);
        cart.setQuantity(1);
        cart.setOrderDate(LocalDateTime.now());

        cRepo.save(cart);
    }
    
    public List<Cart> getCartList(String userEmail) {
    	List<Cart> list = new ArrayList<>();
    	Optional<Member> member =  mRepo.findByMemberEmail(userEmail);
    	int memberIdx = member.get().getMemberIdx(); 
    	list = cRepo.findByMemberMemberIdx(memberIdx);
    	
    	return list;
    }
}