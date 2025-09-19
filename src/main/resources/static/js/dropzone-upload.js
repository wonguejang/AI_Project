// 드롭존 라이브러리기능 커스텀 js임
// dropzone 라이브러리에서 id="myDropzone" 기준으로 옵션을 지정하는 방법임
// 여기서 myDropzone 은 <form id="my-dropzone" class="dropzone"></form>이랑 연결됨
Dropzone.options.myDropzone = {
    paramName: "file",            			// 파일 이름
    maxFilesize: 1,     					// 한번에 올릴수 있는 사진 갯수
    acceptedFiles: ".jpg,.png,.gif,.pdf", 	// 허용파일  	
    autoProcessQueue: true,					// 자동저장기능 아래 프리뷰템플릿은 <a>태그로 remove 나오던거 x로 바꾸고 버튼 태그로 바꿈 원래 이미지, 설명, 에러 등등의 정보를 전부 출력하는데 이미지랑 x표만 남김
	previewTemplate	:`
	      <div class="dz-preview dz-file-preview">
	        <div class="dz-image"><img data-dz-thumbnail /></div>
	        <button class="dz-remove" type="button" data-dz-remove>✕</button>
	      </div>
	    `,
    //드롭존 초기화 함수 (init : dropzone라이브러리에서 제공하는 초기화 호출함수임 addedfile, removedfile 호출이 가능함(저장,삭제일듯?))
    init: function() {
        const dz = this; // dz = Dropzone 객체

        // dz.on("addedfile"이(드롭존에 파일 드롭 혹은 선택시 발생함) 그때마다 실행되는 함수 function(file) 여기서 file이 방금 올라온 파일이라함) 함수내용 = if~ else까지 
        dz.on("addedfile", function(file) {
            // 메시지 숨기기
            dz.element.querySelector(".dz-message").style.display = "none";

            // 파일이면 그대로 업로드
            if (file.type) { 						// file.type = jpg,png 뭐 이딴거 말하는건데 url로 들어오면 문자열 객체로 들어와서 type이 없음
                const formData = new FormData();	// FormData를 만들어 서버 /upload에 POST
                formData.append("file", file);		

                fetch("/upload", { method: "POST", body: formData })
                    .then(res => res.text())				// 서버에서 보낸 response값 읽는거임
                    .then(url => {							// 이전 .then(res.text()) 결과가 url 매개변수로 들어오는건데 사실 필요는 없음 디버깅용임 나중에 코드 완성하면 지울?듯
                        console.log("서버 저장 경로:", url);	// 굳이 없어도 되는데 일단은 저장한 내용 확인할려고 넣어둠 alert 찍어두면 형님 불편하실까봐 숨겨놈
                        // DB에 저장 처리 가능
                    });
            } else if (file.dataURL && file.dataURL.startsWith("http")) {	// file.dataURL -> Dropzone이 전달한 파일 객체가 아니라 URL 문자열일때 + file.dataURL.startsWith("http") -> 그 문자열이 “http”로 시작하는지 확인
                // URL이면 /uploadByUrl 호출									// 이게 뭔소리냐면 file.dataURL : 파일 객체가 아님? + file.dataURL.startsWith("http") : 로컬 파일 아님? 
                fetch("/uploadByUrl", {										// = 들어온 값이 외부 파일이로구나! -> url저장
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },	// http 요청 헤더 지정 url 인코딩한 키 = 값 보낸다
                    body: "fileUrl=" + encodeURIComponent(file.dataURL)					// encodeURIComponent(file.dataURL) → URL이나 특수문자가 포함되어도 안전하게 전송		
                })
                .then(res => res.text())
                .then(savedUrl => {
                    console.log("URL 저장:", savedUrl);		// 파일일때랑 대충 비슷함
                });
            } else {
                console.warn("지원하지 않는 형식:", file);
            }

            // 사진 있으면 화면 가운데 사진올리기 삭제
            if (dz.files.length > 1) {
                dz.removeFile(dz.files[0]);
            }
        });
		// 사진 없으면 화면 드롭존 가운데 사진올리기 출력
        dz.on("removedfile", function() {
            if (dz.files.length === 0) {
                dz.element.querySelector(".dz-message").style.display = "flex";
            }
        });
    }
};