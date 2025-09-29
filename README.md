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
API 명세는 노션에 정리했습니다.(권한 설정 예정)  
👉 [API 명세 보러가기](https://www.notion.so/AI-API-27de75a00e2f808b9f63cbe06cf1756f)



---

## 👥 팀원별 구현 기능

### 👤 원규 — 구현 기능 (상세)

#### 1) 회원 관리: 이메일 회원가입 + **JavaMail 기반 이메일 인증**
- 회원가입 시 입력값 검증 및 비밀번호 확인 로직 구현  
- 이메일 중복 체크 API 제공  
- 가입 시 인증 토큰을 포함한 메일 발송 → 메일 링크 클릭 시 계정 활성화  
- Spring Security `Authentication`/`Principal`을 활용해 로그인 여부 처리  

#### 2) AI 연동: **이미지 태깅(HuggingFace)** / **문구 보정(Gemini)**
- HuggingFace API 연동으로 이미지 기반 상품 카테고리 자동 태깅  
- Gemini API 연동으로 입력된 상품 설명을 자연스러운 마케팅 문구로 보정  
- 업로드/보정 결과를 상품 등록 화면과 연동  

#### 3) 프로젝트 전체 구조 설계
- Controller → Service → Repository 구조로 레이어드 아키텍처 설계  
- 기능 단위 패키지 구조 (`ai`, `cart`, `member`, `pay`, `product`, `reply` 등)  
- DTO 분리를 통해 엔티티 직접 노출 방지  
- 세션을 활용한 조회수 중복 방지 로직 구현  

#### 4) 엔티티 설계 및 구현
- **Member, Product, Reply, Cart** 등 핵심 엔티티 설계  
- 회원–댓글, 상품–댓글, 회원–장바구니, 상품–장바구니 등 주요 연관관계 매핑  
- 조회/댓글/장바구니 기능 구현 시 JPA 기반으로 CRUD 처리  



### 👤 팀원2 (예: 홍길동)
- 

---

## 🌱 팀원별 성장점

### 👤 원규
- Spring Security 기반 로그인/회원 관리 및 **JavaMail 기반 이메일 인증** 로직 구현  
- **AI API(HuggingFace, Gemini)** 연동 경험을 통해 외부 API 활용 능력 향상  
- 프로젝트 전체 구조 설계 및 엔티티 설계/구현 경험
- Thymeleaf를 활용한 서버 사이드 렌더링 이해 및 적용 경험 

### 👤 팀원2 (예: 홍길동)
 

---
