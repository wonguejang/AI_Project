document.addEventListener('DOMContentLoaded', () => {
    // 가격 input 숫자/천 단위 처리
    const priceInput = document.querySelector('.price');
    priceInput.addEventListener('input', (e) => {
        let value = e.target.value.replace(/\D/g, '');
        e.target.value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    });

    // 가벼운 설명 -> AI 컨설팅
	const lightInput = document.querySelector('.light-product');
	const consultingInput = document.querySelector('.ai-consulting');
	const loadingContainer = document.getElementById('loading-container');

	lightInput.addEventListener('keypress', (e) => {
	    if (e.key === 'Enter') {
	        e.preventDefault();
	        loadingContainer.style.display = 'flex'; // 로딩 표시
	        fetch('/aiConsulting', {
	            method: 'POST',
	            headers: { 'Content-Type': 'application/json' },
	            body: JSON.stringify({ inputText: lightInput.value })
	        })
	        .then(res => res.text())
	        .then(result => {
	            consultingInput.value = result; // 결과 표시
	        })
	        .catch(err => console.error('처리 실패:', err))
	        .finally(() => {
	            loadingContainer.style.display = 'none'; // 완료 후 숨김
	        });
	    }
	});

    // 폼 submit 전 hidden 값 채우기 + 필수 체크
    const form = document.querySelector('.product-info-group');
    form.addEventListener('submit', (e) => {
        const productName = document.querySelector('.product-name');
        const aiTags = document.querySelector('.ai-tags');
        const price = document.querySelector('.price');
        const aiConsultingHidden = document.getElementById('aiConsulting');
        const aiConsultingVal = document.querySelector('.description-2 input').value;
		const imageUrl = document.getElementById('imageUrl');
		
        // hidden 값 채우기
        aiConsultingHidden.value = aiConsultingVal;
		imageUrl.value = window.uploadUrl;

        // 필수 입력 체크
        if (!productName.value.trim()) {
            e.preventDefault();
            alert('상품 이름을 입력해주세요.');
            productName.focus();
            return;
        }
        if (!aiTags.value.trim()) {
            e.preventDefault();
            alert('AI 자동 태그를 입력해주세요.');
            aiTags.focus();
            return;
        }
        if (!price.value.trim()) {
            e.preventDefault();
            alert('가격을 입력해주세요.');
            price.focus();
            return;
        }
        if (!aiConsultingHidden.value.trim()) {
            e.preventDefault();
            alert('컨설팅 결과가 비어있습니다.');
            document.querySelector('.light-product').focus();
            return;
        }
		if (!window.uploadUrl || !window.uploadUrl.trim()) {
		    e.preventDefault();
		    alert('이미지 업로드가 필요합니다.');
		    return;
		}
    });
})