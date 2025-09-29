// 드롭존 라이브러리기능 커스텀 js
// dropzone 라이브러리에서 id="myDropzone" 기준으로 옵션을 지정하는 방법임
// 여기서 myDropzone 은 <form id="my-dropzone" class="dropzone"></form>과 연결됨
Dropzone.options.myDropzone = {
    paramName: "file",            			// 파일 이름
    maxFilesize: 1,     					// 한번에 올릴 수 있는 사진 용량(MB)
    acceptedFiles: ".jpg,.png,.gif,.pdf", 	// 허용 파일 형식
    autoProcessQueue: true,					// 자동 저장 기능 // 프리뷰 템플릿
    previewTemplate	:`                        
      <div class="dz-preview dz-file-preview">
        <div class="dz-image"><img data-dz-thumbnail /></div>
        <button class="dz-remove" type="button" data-dz-remove>✕</button>
      </div>
    `,
    // 드롭존 초기화 함수
    // init : dropzone 라이브러리에서 제공하는 초기화 호출 함수
    // addedfile, removedfile 호출 가능(파일 업로드/삭제 이벤트)
    init: function() {
        const dz = this; // dz = Dropzone 객체
        const tagLoading = document.getElementById("tag-loading"); // 태그 로딩창 div
        const aiTagsInput = document.querySelector(".ai-tags");   // 태그 input

        dz.on("addedfile", function(file) {
            // 드롭존 메시지 숨기기
            dz.element.querySelector(".dz-message").style.display = "none";

            // 파일이면 그대로 업로드
            if (file.type) { 						
                const formData = new FormData();	// FormData를 만들어 서버 /upload에 POST
                formData.append("file", file);		

                // 태그 로딩 표시, input 숨김
                aiTagsInput.style.display = "none";
                tagLoading.style.display = "flex";

                fetch("/upload", { method: "POST", body: formData })
                    .then(res => res.text())				
					.then(url => {							
					    console.log("서버 저장 경로:", url);	
						window.uploadUrl = url; //url 전역함수에 저장
					    return fetch("/aiTag", {
					        method: "POST",
					        headers: { "Content-Type": "application/json" },
					        body: JSON.stringify({ fileUrl: url }) // ← url 사용
					    });
					})
                    .then(res => res.text())
                    .then(tagString => {
						window.tag = tagString; //tag 전역함수에 저장
                        console.log("AI 태그:", tagString);
						
                        // 태그를 클래스 ai-tag input 박스에 넣기
                        aiTagsInput.value = tagString;
                    })
                    .catch(err => console.error("업로드/태그 처리 실패:", err))
                    .finally(() => {
                        // 태그 로딩 숨기고 input 다시 보이기
                        tagLoading.style.display = "none";
                        aiTagsInput.style.display = "block";
                    });
            } else if (file.dataURL && file.dataURL.startsWith("http")) {	
                // URL이면 /uploadByUrl 호출
                fetch("/uploadByUrl", {										
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },	
                    body: "fileUrl=" + encodeURIComponent(file.dataURL)					
                })
                .then(res => res.text())
                .then(savedUrl => {
                    console.log("URL 저장:", savedUrl);	
                });
            } else {
                console.warn("지원하지 않는 형식:", file);
            }

            // 사진이 여러 개면 첫 번째 파일 삭제
            if (dz.files.length > 1) {
                dz.removeFile(dz.files[0]);
            }
        });

        // 사진 없으면 드롭존 메시지 다시 표시
        dz.on("removedfile", function() {
            if (dz.files.length === 0) {
                dz.element.querySelector(".dz-message").style.display = "flex";
            }
        });
    }
};
