// .price input안에 값이 변동하면 실행되는 스크립트 -> 천단위마다 ,찍어주는 스크립트임 + 숫자외에 기입 못하게 
	document.addEventListener('DOMContentLoaded', () => {
	    const priceInput = document.querySelector('.price');
	    priceInput.addEventListener('input', (e) => {
	        let value = e.target.value.replace(/\D/g, '');
	        e.target.value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
	    });
	});

// .light-product 에서 
document.addEventListener('DOMContentLoaded', () => {
    const lightInput = document.querySelector('.light-product');
    const consultingInput = document.querySelector('.ai-consulting');

    lightInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {  // 엔터 키 입력 감지
            e.preventDefault();    // form submit 방지
            const text = lightInput.value;

            fetch('/aiConsulting', {  // 컨트롤러 매핑 URL
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ inputText: text })
            })
            .then(res => res.text())
            .then(result => {
                consultingInput.value = result;  // 처리된 텍스트 넣기
            })
            .catch(err => console.error('처리 실패:', err));
        }
    });
});