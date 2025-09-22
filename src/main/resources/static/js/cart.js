$(document).ready(function() {

    // 총 금액 계산 함수
    function updateCartTotals() {
        let totalPrice = 0;
        $('.cart-item').each(function() {
            let price = parseInt($(this).find('.item-price').text().replace(/,/g,'').replace('원',''));
            let quantity = parseInt($(this).find('input[type="number"]').val());
            let itemTotal = price * quantity;
            $(this).find('.item-total-price').text(itemTotal.toLocaleString() + '원');
            totalPrice += itemTotal;
        });

        let deliveryFee = totalPrice > 0 ? 3000 : 0; // 예시 배송비
        let finalPrice = totalPrice + deliveryFee;

        $('.summary-value').eq(0).text(totalPrice.toLocaleString() + '원');
        $('.summary-value').eq(1).text(deliveryFee.toLocaleString() + '원');
        $('.final-price').text(finalPrice.toLocaleString() + '원');
    }

    // 초기 로딩 시 총액 계산
    updateCartTotals();

    // 수량 변경 이벤트 (+/-)
    $(document).on('change', '.quantity-control input', function() {
        let cartItem = $(this).closest('.cart-item');
        let orderIdx = cartItem.data('order-idx');
        let quantity = $(this).val();

        // 서버에 수량 업데이트
        $.post('/cart/update', { orderIdx: orderIdx, quantity: quantity })
            .done(function(res){
                console.log('수량 변경 완료');
            })
            .fail(function(err){
                console.error('수량 변경 실패', err);
            });

        // 클라이언트에서 바로 계산
        updateCartTotals();
    });

    // 삭제 버튼 이벤트
    $(document).on('click', '.item-remove', function() {
        let cartItem = $(this).closest('.cart-item');
        let orderIdx = cartItem.data('order-idx');

        $.ajax({
            url: '/cart/delete',
            method: 'POST',
            data: { orderIdx: orderIdx },
            success: function() {
                cartItem.remove();
                updateCartTotals();
                console.log('삭제 완료');
            },
            error: function(err) {
                console.error('삭제 실패', err);
            }
        });
    });
	
	$(document).on('click', '.checkout-btn', function(){
		location.href = '/payment/ready';
	})
	
});
