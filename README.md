# DPANG USER SERVER

## 🌐 프로젝트 개요

이 프로젝트는 사용자 서비스를 지원하는 마이크로서비스로서, 사용자의 상세 정보 조회, 주소 변경, 탈퇴 및 관리자의 사용자 상세 정보 조회, 정보 목록 조회, 사용자 삭제 등의 기능을 제공합니다.

이를 통해 사용자 관련 작업을 효율적으로 관리하고, 사용자 경험을 향상시키는데 중점을 두고 있습니다.

## 🗃️ 데이터베이스 구조

마일리지 서비스에서 활용하는 데이터베이스(MySQL)는 다음과 같은 구조의 테이블을 가지고 있습니다.

```mermaid
erDiagram
    user {
        bigint user_id PK "사용자 ID"
        varchar(320) email "이메일 (ID)"
        enum status "활성화 상태"
    }
		user_detail {
        bigint user_detail_id PK "유저 디테일 ID"
        bigint user_id FK "사용자 ID"
				bigint employee_number "사원 번호"
				datetime(10) join_date "입사날짜"
				varchar(5) name "이름"
				varchar(15) phone_number "전화번호"
        varchar(5) zip_code "우편번호"
        varchar(128) address "주소"
        varchar(128) detail_address "상세 주소"
    }
    user_withdrawal {
        bigint user_id FK "사용자 ID"
        enum withdrawal_reason "탈퇴 사유"
        text message "남길 말씀"
        datetime(10) withdrawal_date "탈퇴 날짜"
    }
    cart {
        bigint cart_id PK "장바구니 ID"
        bigint user_id FK "사용자 ID"
    }
		cart_items {
				bitint cart_items_id PK "장바구니 아이템들 ID"
				bigint cart_id FK "장바구니 ID"
				bigint item_id "아이템 ID"
				int quantity "수량"
		}
    wishlist {
        bigint wishlist_item_id PK "위시리스트 아이템 ID"
        bigint user_id FK "사용자 ID"
    }
		wishlist_items {
				bitint wishlist_items_id PK "위시리스트 아이템들 ID"
				bigint wishlist_id FK "위시리스트 ID"
				bigint item_id "아이템 ID"		
		}
		    user ||--|| user_detail: ""
		    user ||--o| user_withdrawal: ""
				user ||--|| cart: ""
				cart ||--o{ cart_items: ""
		    user ||--|| wishlist: ""
		wishlist ||--o{ wishlist_items: ""
```

## ✅ 프로젝트 실행

해당 프로젝트를 추가로 개발 혹은 실행시켜보고 싶으신 경우 아래의 절차에 따라 진행해주세요

#### 1. `secret.yml` 생성

```commandline
cd ./src/main/resources
touch secret.yml
```

#### 2. `secret.yml` 작성

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://{YOUR_DB_HOST}:{YOUR_DB_PORT}/{YOUR_DB_NAME}
    username: { YOUR_DB_USERNAME }
    password: { YOUR_DB_PASSWORD }

  application:
    name: user-server

eureka:
  instance:
    prefer-ip-address: true

  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://{YOUR_EUREKA_SERVER_IP}:{YOUR_EUREKA_SERVER_PORT}/eureka/
```

#### 3. 프로젝트 실행

```commandline
./gradlew bootrun
```

**참고) 프로젝트가 실행 중인 환경에서 아래 URL을 통해 API 명세서를 확인할 수 있습니다**

```commandline
http://localhost:8080/swagger-ui/index.html
```
