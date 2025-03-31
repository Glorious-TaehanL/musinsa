# musinsa

안녕하세요,

이번에 사전과제테스트를 받게된 이태한이라고 합니다.
먼저 시간내서 코드확인해주셔서 감사드립니다.

##### 과제의 구성은 아래와 같습니다.
musinsa / 백엔드   http://localhost:8080/  (따로 웹페이지가 구성된것은 아니니 참고부탁드립니다.)

react-api-musinsa / 프론트엔드  http://localhost:3000/

H2 디비 /  모니터링 설정해놓았습니다.    http://localhost:8080/h2-console  

        - Saved Settings: Generic H2 (Embedded)
        - Setting Name:	Generic H2 (Embedded)
        - Driver Class: org.h2.Driver
        - JDBC URL: (jdbc:h2:mem:realdb)
        - User Name: sa
        - Password: <설정안함>


#### musinsa #백엔드

-전체 테스트
./gradlew test
  --서비스별 테스트 
    (productService)
        ./gradlew test --tests com.devted.musinsa.services.impl.ProductServiceImplTest
    (brandService)
        ./gradlew test --tests com.devted.musinsa.services.impl.BrandServiceImplTest  

-빌드
./gradlew build
-실행
./gradlew run
-클린
./gradlew clean


react-api-musinsa # 프론트엔드
-- 모듈설치
npm install 
-- 실행
npm start


백엔드 동작시켜주시고 프론트엔드 동작이후 확인부탁드리겠습니다.

<hr>
구현된 api 입니다.

## 구현된 API 목록

| API | METHOD | DESCRIPTION |
| --- | --- | --- |
| /api/v1/product/health | [ GET ] | 서버 상태체크 |
| /api/v1/product/getMinPriceByCategories | [ GET ] | 카테고리별로 최저가격의 브랜드와 상품을 조회하고 합을 확인합니다. |
| /api/v1/product/getMinPriceByBrand | [ GET ] | 최저가격에 판매하는 브랜드의 모든상품의 합을 구합니다. |
| /api/v1/product/getBothPriceByCategory | [ POST ] | 카테고리별로 최저가브랜드와 가격 최고가의 브랜드와 가격을 구합니다. |
| /api/v1/brand/create | [ POST ] | 브랜드를 생성합니다. |
| /api/v1/brand/addProduct | [ POST ] | 브랜드에 상품을 추가하는 개념으로 접근하였고, 따라서 API 경로는 브랜드 하위에 추가하였습니다. 이후 새로운 브랜드가 `appProduct`와 들어온다면 새로운 브랜드를 생성하고, 상품을 추가하도록 작성하였습니다. |
| /api/v1/brand/editProduct/{productId} | [ PUT ] | 일반적으로 많은 업데이트도 디비에 부담이 될수 있다 생각합니다. 이에따라 상품 정보 수정시, 전체 데이터가 변경되는 경우 `PUT`으로 처리될수 있도록 구성하였습니다.. |
| /api/v1/brand/editProduct/{productId} | [ PATCH ] | 때에따라 일부분의 상품 정보만 수정시, 일부 데이터가 변경되는 경우 `PATCH`로 부분 업데이트 처리되도록 구성하였습니다. |
| /api/v1/brand/deleteProduct/{productId} | [ DELETE ] | 상품 삭제시 전달받은 `id`값의 상품을 삭제하고, 직접적인 delete를 사용하였기때문에 다른 테이블에 삭제가된 상품의 정보를 저장하는 기능을 구현하였습니다. |


포스트맨을 사용하여 테스트를 진행했고, 도큐먼트화 하여 링크 첨부했습니다. 
https://documenter.getpostman.com/view/26410790/2sB2cRBPNp

<hr>
디비의 구성은 3가지입니다. 
product_entity : 상품 테이블
brand_entity : 브랜드 테이블 
deleted_product_entity : 유저가 삭제한 상품의 경우 기록을 남기도록 설정하였습니다.

<hr>

구성 :: 

        kotlin
            com.devted.musinsa
                -controller
                -domain
                    -dto
                    -entities
                -exception
                -initializer :인메모리로 구성되다보니 프로젝트가 리빌드될때 또는 디비에 데이터가 없을때 초기 셋업하는 용도로 사용하였습니다
                -repository
                -services  : 인터페이스와 구현체로 분리하였습니다
                    -impl 
        resources
            seedData
                -brad-data.json
                -product-data.json
