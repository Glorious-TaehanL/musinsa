# musinsa# musinsa

안녕하세요,

이번에 사전과제테스트를 받게된 이태한이라고 합니다.
먼저 시간내서 코드확인해주셔서 감사드립니다.

과제의 구성은 아래와 같습니다.
musinsa / 백엔드   http://localhost:8080/  (따로 웹페이지가 구성된것은 아니니 참고부탁드립니다.)
react-api-musinsa / 프론트엔드  http://localhost:3000/
H2 디비 /  모니터링 설정해놓았습니다.    http://localhost:8080/h2-console  
        - Saved Settings: Generic H2 (Embedded)
        - Setting Name:	Generic H2 (Embedded)
        - Driver Class: org.h2.Driver
        - JDBC URL: (jdbc:h2:mem:realdb)
        - User Name: sa
        - Password: <설정안함>


musinsa #백엔드

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

구현된 api 입니다.
-/api/v1/product/health                         [   GET     ]    서버 상태체크
-/api/v1/product/getMinPriceByCategories        [   GET     ]    카테고리별로 최저가격의 브랜드와 상품을 조회하고 합을 확인합니다.
-/api/v1/product/getMinPriceByBrand             [   GET     ]    최저가격에 판매하는 브랜드의 모든상품의 합을 구합니다.
-/api/v1/product/getBothPriceByCategory         [   POST    ]    카테고리별로 최저가브랜드와 가격 최고가의 브랜드와 가격을 구합니다. 
-/api/v1/brand/create                           [   POST    ]    브랜드를 생성합니다.
-/api/v1/brand/addProduct                       [   POST    ]    브랜드에 상품을 추가하는 개념으로 브랜드하위에 추가하였습니다.
-/api/v1/brand/editProduct/{productId}          [   PUT     ]    상품 수정시, 전체 데이터가 변경되는경우 put으로 전체업데이트 됩니다.
-/api/v1/brand/editProduct/{productId}          [   PATCH   ]    상품 수정시, 일부 데이터가 변경되는 경우 patch로 부분 업데이트 됩니다.  
-/api/v1/brand/deleteProduct/{productId}        [   DELETE  ]    상품 삭제시 전달받은 id값의 상품을 삭제하고, 삭제테이블에 해당상품을 추가해 기록합니다.


포스트맨을 사용하여 테스트를 진행했고, 도큐먼트화 하여 링크 첨부했습니다.
https://documenter.getpostman.com/view/26410790/2sB2cRBPT5

디비의 구성은 3가지입니다.
product_entity          : 상품 테이블
brand_entity            : 브랜드 테이블
deleted_product_entity  : 유저가 삭제한 상품의 경우 product_entity에 column을 추가하여 표시하는 방법도 있으나, 추가적으로 디비를 구성하여 기록을 남기도록 설정하였습니다.


그리고 구성은 아래와같습니다.
구성 :: 
        kotlin
            com.devted.musinsa
                -controller
                -domain
                    -dto
                    -entities
                -exception      : 몇가지 익셉션을 핸들링하였습니다.
                -initializer    : 인메모리디비다보니 프로젝트가 리빌드될때 디비를 초기 셋업하는 용도로 사용하였습니다.
                -repository
                -services       : 인터페이스와 구현체로 분리하였습니다.
                    -impl 
        resources
            seedData
                -brad-data.json
                -product-data.json
