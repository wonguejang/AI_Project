document.addEventListener('DOMContentLoaded', () => {
    const urlParts = window.location.pathname.split('/');
    const productId = urlParts[urlParts.length - 1]; // 현재 페이지 마지막 숫자가 productIdx

    document.querySelector('.add-to-cart-btn').addEventListener('click', () => {
        fetch('/cart/add', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({productId: productId})
        })
        .then(res => res.json())
		.then(data => {
		    if(data.success) {
		        alert('장바구니에 추가되었습니다.');
		    } else {
		        if(data.message && data.message === "로그인 후 이용 가능합니다.") {
		            alert("로그인 후 이용 가능합니다.");
		            window.location.href = '/member/login'; // 로그인 페이지로 이동
		        } else {
		            alert('추가 실패');
		        }
		    }
		});
    });
});