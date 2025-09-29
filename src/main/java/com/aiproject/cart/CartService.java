package com.aiproject.cart;

import java.time.LocalDateTime;
import java.util.Collections;
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
        Optional<Member> memberOpt = mRepo.findByMemberEmail(userEmail);
        if (!memberOpt.isPresent()) return Collections.emptyList();
        int memberIdx = memberOpt.get().getMemberIdx();
        return cRepo.findByMemberMemberIdx(memberIdx);
    }
    
    public void delCart(int orderIdx) {
    	cRepo.deleteById(orderIdx);
    }
    
    public void updateCart(int orderIdx, int quantity) {
    	cRepo.updateQuantity(orderIdx,quantity);
    }
}