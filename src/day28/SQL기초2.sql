# [주석]
/* 여러줄 주석 */
# 한줄 주석 
-- 한줄 주석 

# [명령어 실행]
# [1] ctrl+엔터 : 실행할 명령어에 커서를 두고 단위 실행 ( 번개 모양 I )
# [2] ctrl+shift : .sql 파일의 존재하는 모든 명령어 실행 ( 번개 모양 )

# [ SQL ]
/*
	[ DDL : data Definition Language ] : 데이터베이스 정의 언어 
		1. show databases;								: db server 내 모든 데이터베이스를 확인 
		2. show variables like 'datadir'				: db server local path 확인 	
		3. create database 데이터베이스명					: db server 내 db 생성 
		4. drop database 데이터베이스명						: db server 내 db 삭제 
			- drop database if exists 데이터베이스명 		: 만일 db가 존재하면 삭제 
		5. use 데이터베이스명								: db server 내 여러 db중에 사용 활성화
		6. create table 테이블명( 속성명 타입 , 속성명 타입 , 속성명 타입 ) : 활성화된 db에서 테이블/표 생성한다.
		7. drop table 테이블명							: 활성화된 db에서 테이블 삭제 
			- drop table if exists 테이블명		 		: 만일 테이블이 존재하면 삭제 
		8. show tables									: 활성화된 db내 모든 테이블 확인 
        
	[ 데이터 타입 ] : 테이블 생성시 필드/속성 의 데이터 타입 
		[정수]	TINYINT , SMALLINT , MEDIUMINT , INT , BIGINT
        [실수]	FLOAT , DOUBLE , DECIMAL(문자)
        [문자]	CHAR( ) , VARCHAR( )
        [날짜/시간] DATE , TIME , DATETIME
        [논리]	BOOL
*/
/* 
	[11/20, SQL과제] 키오스크 개발 - 필요한 데이터베이스 구축 하기 
	- 요구 사항 
		1. 여러개 카테고리 중에 하나의 카테고리를 선택하여 카테고리별 제품들을 출력
		2. 해당 제품을 선택하여 수량 입력받아 주문 처리 - 단) *한번 주문의 여러개 제품을 주문 할수 있다.*
		3. 주문번호를 받아서 대기한다.
	- 메모리 설계 필수 필드 속성 
		1. 카테고리명 2. 제품명 3.가격 4.주문수량 5.주문날짜 
	- 핵심 : 여러 테이블 구성 할때는 테이블간의 데이터 중복을 최소화 한다.
*/
# 생각순서 
# 1. 우리가 만들고자 하는 프로그램의 저장할 데이터를 생각 , 
	# 카테고리(커피,음료,주스,스무디) , 
    # 제품( 아메리카노1000 , 사과주스2000 , 메론스무디3000 , 콜라2500 , 모카치노3500 )
    # 주문( 102 아메리카노 2개 11/20 , 103 메론스무디 1개 콜라 1개 11/21 )

# 2. 데이터들을 어떻게 관리/구분/처리
	# 카페알바생 - 종이장부 
    # 손님1번째 이 아메리카노 1개 주문 했을때 , 어떻게 작성할지? 정답 없음
	# 손님2번째 이 아메리카노 1개 , 메론스무디 2개 주문 했을때 , 어떻게 작성할지? 
/*
종이장부
	주문번호	주문날짜		제품명		가격			개수	 제품구분
	102		11/20		아메리카노		1000		1	 커피
	103		11/21		아메리카노		1000		1	 커피
	103		11/21		메론스무디		3000		2	 스무디
*/ 
# 3. (정규화) 중복을 최소화 ,  왜? 메모리 를 효율성있게 아껴쓸려고 정규화를 한다.
create database mydb1121;
use mydb1121;
	# 카테고리테이블( 카테고리명 )
create table 카테고리( 카테고리명 char(10) ); 
    # 제품테이블( 제품명 , 가격 )
create table 제품( 제품명 varchar(50) , 가격 int );
    # 주문테이블( 주문날짜 )
create table 주문( 주문날짜 date );
    # 주문상세테이블( 주문수량 )
create table 주문상세(  주문수량 int );

# 4. (관계) , 관계형[서로 다른 테이블간의 종속] 데이터베이스 , 왜? 서로 다른 테이블간의 연결() 
	# 카테고리번호(카테고리의 식별) 가 필요한 이유 ?? 카테고리명을 식별 역할로 사용하지 않고 번호로 식별한다.
create table 카테고리( 카테고리번호PK int , 카테고리명 char(10) ); 		# 상위 테이블 
# create table 제품( 제품명 varchar(50) , 가격 int , 카테고리명 char(10) ); 				# 하위 테이블 
create table 제품( 제품번호PK int , 제품명 varchar(50) , 가격 int , 카테고리번호FK int ); 	# 하위 테이블 # 효율적이다.
create table 주문( 주문번호PK int , 주문날짜 date );										# 상위 테이블 
create table 주문상세( 주문상세번호PK int , 주문수량 int , 주문번호FK int , 제품번호FK int );	# 하위 테이블 
## 관례적으로 모든 테이블에는 1개이상의 식별데이터/번호 가 존재한다. (기본키-pk)
## pk가 다른 테이블 에서 사용/참조 한다. (참조키-fk)

# [ 제약 조건 ]
	# 1. not null : 해당 필드에 null 값을 포함하지 않는다.
    # 2. default 초기값 : 해당 필드에 레코드(값) 삽입시 비어 있는경우 초기값을 지정한다.
    # 3. unique : 해당 필드에 중복된 값을 허용 하지 않는다.[중복제거] 레코드 삽입시 데이터가 중복이면 오류발생 
	# 4. auto_increment : 해당 필드에 레코드 삽입시 순서대로 번호가 자동 삽입된다. ( oracle )
    # 5. primary key : 테이블의 식별필드 , (기본키-PK) , 식별 가능한 고유 값 가진 키 		, 참조 당하는 키 
		# primary key( pk필드명 )
    # 6. foreign key : 테이블의 참조필드 , (참조키-FK) , 다른 테이블내 기본키 를 참조 하는 키 	, 참조 하는 키
		# foreign key( fk필드명 ) references 참조테이블( pk필드명 )
    # 관례적으로 모든 테이블은 1개 이상의 pk필드 를 갖는다. 

# 회원제 게시판 ( 회원테이블 , 게시판테이블 = 종속관계 파악 , [O]회원(상위PK)이 게시물(하위FK) 작성 VS [X]게시물이 회원 작성  )    
create table 회원테이블( 
	회원번호PK int unsigned not null auto_increment ,	-- 회원번호는 일반적으로 가입순서대로 1 ~ 저장하기 위해서 int 사용했다.
		-- unsigned : [부호가없다뜻] int( +-21억 ) int unsigned ( 42억 )
    아이디 varchar(30) unique ,				-- 아이디는 최대 30글자만 입력받을 예정이라서 varchar(30) 사용했다.  
    비밀번호 varchar(20) ,						-- 비밀번호는 최대 20글자만 입력받을 예정이하서 varchar(20) 사용했다.
    연락처 char(13) ,							-- 010-1234-1234 형식으로 저장할 예정 이라서 char(13) 사용했다.
    광고전송여부 boolean default 0 ,			-- true , false 형식으로 저장할 예정 이라서 boolean 사용했다.
    가입일 datetime , 						-- 2024-11-21 20:25:11 형식으로 저장할 예정 이라서 datetime 사용했다.
    primary key( 회원번호PK )					-- 지정한 필드를 PK(기본키) 로 사용 하겠다. 
    -- 아이디 대신에 회원번호를 사용한 이유? 1.용량작다 2.중복배제( 유재석이 'QWE' 아이디 로 회원탈퇴 하고 5년후 강호동이 'QWE' 아이디로 가입했다. )
    -- 유재석과 강호동은 같은 회원인가?? 아니요.
); # 임의 테이블 생성  , create table 테이블명( 필드명 타입 , 필드명 타입 );

create table 게시물테이블( 
	게시물번호PK int unsigned not null auto_increment ,   -- 게시물번호는 일반적으로 작성순서대로 1~ 저장하기 때문에 int unsigned 했다.
    게시물제목 varchar(100) , 			-- 게시물제목은 적당하게 최대 100글자로 하기 위해서 varchar(100) 했다.
    게시물내용 longtext ,				-- 게시물내용은 많은 데이터를 저장하기 위해서 큰 용량인(4G) longtext 했다.
    게시물작성자회원번호FK int , 			-- 게시물작성자가 아이디가 아니고 회원번호인 이유는 : 회원번호를 통해 아이디 를 참조 할수 있다. 
		-- 예] 동사무소에 가면 주민등록번호(식별키) 만 말해도 내 신상정보를 공무원(직원) 이 볼수(참조) 있다.
	foreign key( 게시물작성자회원번호FK ) references 회원테이블( 회원번호PK ),
    primary key( 게시물번호PK )
);

/*
[ SQL과제2 ] 회원들만 구매 할 수 있는 '라면' 쇼핑몰 , 데이터베이스 구축 하기 
	- 요구사항
		1. 회원은 회원가입을 진행한다. (임의)
		2. 로그인된 상황에서 카테고리 를 선택한다. 
		3. 지정된 카테고리 에서 제품을 선택한다. 
		4. 지정한 제품을 장바구니에 담는다.
		--> 여러번 장바구니에 담았을때
		5. 만약에 장바구니에 담긴 '불닭볶음면' 2개 , 짜파게티 1개 를 주문 했다.
		6. 하나의 주문 번호를 발급 받았고 주문내역에서 지정한 주문상세를 클릭시 주문 상세 내역(정보) 나왔다.
	- 조건 
		1. 서로 다른 테이블간의 PK(기본키) 와 FK(외래키)를 적용하여 관계형 테이블 만드시오. 
		2. 적절한 필드명과 타입을 사용하시오.
		3. 적절한 제약조건을 사용하시오.
	- 제출 : itdanja@kakao.com  , 11/22 19:00 까지 제출 
*/


















