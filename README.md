# 🛍️ AI 기반 쇼핑몰 웹 서비스

## 💡 프로젝트 주제 선정 이유
- 기존 쇼핑몰 서비스들은 상품 등록 과정이 수동적이고 번거로움  
- 상품 이미지/설명을 일일이 입력해야 해서 시간이 많이 소요됨  
- **AI를 활용하면 이미지에서 자동 태깅 → 설명 문구 자동 생성**이 가능하다고 판단  
- 따라서 **전자상거래 기본 기능 + AI 자동화 기능**을 결합한 쇼핑몰 서비스를 주제로 선정  

---

## 📌 프로젝트 개요
이 프로젝트는 **Spring Boot + Thymeleaf** 기반으로 구현된 **AI 활용 쇼핑몰 웹 서비스**입니다.  
기본적인 전자상거래 기능(회원가입/로그인, 상품 관리, 장바구니, 결제)에 더해  
**AI 이미지 태깅**과 **AI 문구 보정 기능**을 통해 상품 등록 과정을 자동화/지능화한 것이 특징입니다.  

---

## 🚀 주요 기능

### 👤 회원 관리
- 회원가입 / 로그인 / 로그아웃
- 이메일 인증 기반 회원가입 검증
- 이메일 중복 체크

### 🛒 장바구니
- 사용자별 장바구니 조회
- 상품 장바구니 추가 / 삭제 / 수량 변경
- Spring Security 기반 인증 사용자 관리

### 📦 상품 관리
- 상품 목록 조회 (`/main`)
- 상품 상세 조회 (댓글, 조회수 포함)
- 댓글 작성 (로그인 사용자만 가능)
- 상품 등록 (상품명, 가격, AI 태그, AI 문구, 이미지)
- 이미지 업로드 (Dropzone)
- 이미지 업로드 시 AI 자동 태깅
- 간단한 상품 설명 입력 시 AI 문구 보정

### 💳 결제
- KakaoPay 결제 연동
- 장바구니 전체 결제 및 단건 바로구매 지원
- 결제 승인 / 취소 / 실패 처리
- 결제 완료 시 장바구니 비우기 및 단건 구매 처리

---

## ⚙️ 기술 스택

### Backend
- **Java 17+**
- **Spring Boot** (MVC, Security, Validation)
- **JPA / Repository** (데이터 액세스)
- **Spring Scheduler** (유틸성 작업)
- **Lombok** (코드 간결화)

### Frontend
- **Thymeleaf** (서버 사이드 렌더링)
- **HTML/CSS/JavaScript**
- **Dropzone.js** (파일 업로드 UI)

### Database
- **RDBMS** (예: Oracle, MySQL, H2 등 — 추후 명시)
- **JPA 기반 Repository**

### AI 연동
- **HuggingFace API** (이미지 태깅)
- **Google Gemini API** (상품 설명 문구 보정)

### 결제
- **KakaoPay API** (결제 연동)

### Infra & Tooling
- **Gradle** (빌드 툴)
- **GitHub** (버전 관리)
- **IDE**: STS4 (Spring Tool Suite 4)

---

## 📑 프로젝트 구조
```
src/main/java/com/aiproject
 ├── ai                # AI 서비스 (이미지 태깅, 문구 보정)
 ├── cart              # 장바구니
 ├── mail              # 이메일 인증
 ├── member            # 회원 관리
 ├── pay               # 결제 (KakaoPay)
 ├── product           # 상품 관리
 ├── reply             # 댓글 관리
 ├── scheduler         # 스케줄러 / 유틸
 └── security          # Spring Security 설정
```

---

## 📖 API 명세
API 명세는 노션에 정리했습니다.  
👉 [API 명세 보러가기](https://www.notion.so/AI-API-27de75a00e2f808b9f63cbe06cf1756f)




---

## 👥 팀원별 구현 기능

### 👤 원규
- 회원 관리 (로그인, 회원가입, 이메일 인증, 로그아웃)
- 결제 연동 (KakaoPay API)
- AI 연동 (이미지 태깅, 상품 설명 문구 보정)
- Spring Security 인증/인가 로직

### 👤 팀원2 (예: 홍길동)
- 상품 관리 (상품 등록, 상세 조회, 목록 조회)
- 댓글 기능 (상품 댓글 CRUD)
- 장바구니 기능 (추가/조회/수정/삭제)
- 프론트엔드 화면 구성 (Thymeleaf 템플릿, UI)

---

## 🌱 팀원별 성장점

### 👤 원규
- Spring Security 기반 로그인/회원 관리 및 인증 메일 발송 로직 구현  
- AI API(HuggingFace, Gemini) 연동 경험을 통해 외부 API 활용 능력 향상  
- KakaoPay API 연동을 통한 결제 프로세스 이해  

### 👤 팀원2 (예: 홍길동)
- Thymeleaf 기반 화면 설계 및 상품/장바구니/댓글 기능 구현 경험  
- JPA Repository 활용하여 DB 연동 및 CRUD 처리에 대한 이해 강화  
- 협업 과정에서 GitHub 브랜치 전략, 코드 리뷰 경험  

---

## 🙌 팀원 및 기여
- 원규: 회원 관리, 결제 연동, AI 서비스 연동  
- 팀원2: 상품/댓글/장바구니 구현, 프론트엔드 화면 구성
