# 상품 주문 프로그램
서비스의 주문 상품 재고 동시성 이슈를 처리하기 위한 연습 프로젝트


## 🖥️ 프로젝트 소개
Spring Boot + Spring Data JPA로 구현한 상품 주문 프로그램입니다.


### 브랜치 분리 예정
- main 브랜치: 주문 도메인 api 서버

## 🧑‍💻 개발 기간
- 23.05.23(화) - 23.06.02(금)


### ⚙️ 개발 환경
- `Java 17.0.7+8-LTS-224`
- `Jdk 17`
- **Framework** : Spring Boot(3.1.0)
- **Database** : H2 Database(2.1.214)



## 구현 방향 
동시 상품 주문 요청시 정상적으로 재고를 관리하기 위해 DB의 Lock을 활용 하였습니다.

또한 상품 주문으로 DB Lock이 걸린 상황에서도 상품 조회가 가능하게 하기 위해 상품과 상품 재고 테이블을 분리하였습니다.


이 과정에서 상품과 상품 재고 테이블은 1 대 1 관계인데

상품만 단독적으로 조회가 가능하고, 주문시 상품_재고 DB Lock과 함께 재고를 차감시킬수 있는 방식은 다음과 같이 두가지 방식이 있습니다.


단방향 상품_재고(주인) 상품(하인)

양방향 상품(주인) 상품_재고(하인) : Lazy 로딩


상품_재고 테이블을 조회후 재고를 차감시키고 상품_재고에서 상품의 재고 갯수 필드를 동기화 시켜서 상품 조회시에도 Lock 없이 재고까지 확인 할 수 있고,

상품을 등록(저장)할 때 상품에 재고를 할당하는 방식으로 코드를 작성가능한 두번째 방식을 선택하였습니다.
