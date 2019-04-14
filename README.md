카카오페이 경력 공채 과제(2019년 상반기)
====

### 과제명
* 주택금융 API개발

### 개발환경
* Java 8
* maven
* Spring Boot
* JPA



### Build
* 인메모리 DB 사용으로 테스트 Mthod 수행마다 데이터를 다시 생성하기 때문에 
테스트 허용 후 빌드시 많은 시간이 걸리기 때문에 테스트 없이 패키징 하세요.
~~~
mvn package -DskipTests=true
~~~
### Web Application Run
~~~
java -jar ${ProjectPath}/target/housingfinance-0.0.1-SNAPSHOT.jar
~~~

### 필수항목
1. 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 개발.
2. 주택금융 공급 금융기관(은행) 목록을 출력하는 API 를 개발하세요.
3. 년도별 각 금융기관의 지원금액 합계를 출력하는 API 를 개발하세요.
4. 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API를 개발
5. 전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API 개발
6. 특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측하는 API 개발

### 문제해결전략
* API 호출을 통해 파일을 업로드한 후 CSV 파일을 READ하여 DB에 저장
* 실행시 절차의 간편성을 위해 Spring boot의 내장 h2 DB를 사용
* 주어진 Data Set 분석
  * Entity 정의
    * Institute : 금융기관 이름, 금융기관 코드
    * Fund : 년, 월, 금융기관코드, 예산
* 금융기관 리스트는 AppConfig.class 파일 내의 ApplicationRunner를 선언하여 실행시 자동으로 insert
* 특정 은행의 특정 달에 대한 예측 API
  * 시계열 데이터 예측에 활용되는 ARIMA(Autoregressive integrated moving average)을 활용
* 예외처리
  * @RestControllerAdvice 활용하여 요청에 대한 오류를 자동으로 핸들링

### API 명세서
#### 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 개발.
>POST
http:{IP}:8080/files/fundCsvRead
~~~
Header
Content-Type: multipart/form-data

Request
{
	
}
~~~
#### 주택금융 공급 금융기관(은행) 목록을 출력하는 API
>GET
http:{IP}:8080/institutes
~~~
Header
Content-Type: application/json
Authorization : "Bearer {jwttoken}"

Request

Response
{
    "code": 200,
    "message": "정상 처리되었습니다.",
    "result": [
        {
            "name": "주택도시기금",
            "code": "bank001"
        },
        {
            "name": "국민은행",
            "code": "bank002"
        },
        ...
        {
            "name": "기타은행",
            "code": "bank009"
        }
    ]
}
~~~


#### 년도별 각 금융기관의 지원금액 합계를 출력하는 API
>GET
http:{IP}:8080/funds/stats/yearsum
~~~
Header
Content-Type: application/json
Authorization : "Bearer {jwttoken}"

Request

Response
{
    "code": 200,
    "message": "정상 처리되었습니다.",
    "result": [
        {
            "name": "주택금융 공급현황"
        },
        [
            {
                "year": "2005 년",
                "total_amount": 48016,
                "detail_amount": [
                    {
                        "주택도시기금": 22247,
                        "국민은행": 13231,
                        "우리은행": 2303,
                        "신한은행": 1815,
                        "한국시티은행": 704,
                        "하나은행": 3122,
                        "농협은행/수협은행": 1486,
                        "외환은행": 1732,
                        "기타은행": 1376
                    }
                ]
            },
            ...
            {
                "year": "2017 년",
                "total_amount": 295126,
                "detail_amount": [
                    {
                        "주택도시기금": 85409,
                        "국민은행": 31480,
                        "우리은행": 38846,
                        "신한은행": 40729,
                        "한국시티은행": 7,
                        "하나은행": 35629,
                        "농협은행/수협은행": 26969,
                        "외환은행": 0,
                        "기타은행": 36057
                    }
                ]
            }
        ]
    ]
}
~~~

#### 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API
>GET
http:{IP}:8080/funds/stats/yearsum/max
~~~
Header
Content-Type: application/json
Authorization : "Bearer {jwttoken}"

Request

Response
{
    "code": 200,
    "message": "정상 처리되었습니다.",
    "result": {
        "year": 2014,
        "bank": "주택도시기금"
    }
}
~~~

#### 전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API
>GET
http:{IP}:8080/funds/stats/yearavg/min-max
~~~
Header
Content-Type: application/json
Authorization : "Bearer {jwttoken}"

Request
{
  "institute_code":"bank008"
}

Response
{
    "code": 200,
    "message": "정상 처리되었습니다.",
    "result": {
        "bank": "외환은행",
        "support_amount": [
            {
                "year": 2015,
                "amount": 1702
            },
            {
                "year": 2017,
                "amount": 0
            }
        ]
    }
}
~~~


#### 특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측하는 API
>GET
http:{IP}:8080/predictions/fund/month
~~~
Header
Content-Type: application/json
Authorization : "Bearer {jwttoken}"

Request
{
	"year":2018,
	"bank":"우리은행",
	"month":7
}

Response
{
    "code": 200,
    "message": "정상 처리되었습니다.",
    "result": {
        "bank": "bank002",
        "year": 2018,
        "month": 2,
        "amount": 3703
    }
}
~~~

#### 회원가입 API
>POST
http:{IP}:8080/accounts/save
~~~
Header
Content-Type: application/json
Authorization : "Bearer {jwttoken}"

Request
{
	"username":"test002",
	"password":"test002"
}

Response
{
    "code": 200,
    "message": "정상 처리되었습니다.",
    "result": {token 값 생성됨.}
}
~~~

#### 로그인 API
>POST
http:{IP}:8080/accounts/token
~~~
Header
Content-Type: application/json
Authorization : "Bearer {jwttoken}"

Request
{
	"username":"test002",
	"password":"test002"
}

Response
{
    "code": 200,
    "message": "토큰이 발급되었습니다.",
    "result": {token 값 생성됨.}
}
~~~

#### 토큰 재발급 API
>GET
http:{IP}:8080/accounts/token
~~~
Header
Content-Type: application/json
Authorization : "Bearer {jwttoken}"

Request

Response
{
    "code": 200,
    "message": "정상 처리되었습니다.",
    "result": {token 값 생성됨.}
}
~~~
