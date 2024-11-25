/* 여러줄 주석*/
# 한줄 주석
-- 한줄 주석

-- 명령어 ( ; ) 단위 실행 : 커서 두고 ctrl - enter
-- 전체 명령어 실행 

# 1. 데이버베이스생성 -> 데이터 (자료) 들의 모임 , 데이터저장 하기 위해서 사용.
create database mydb1122;
# 2. 모든 데이터베이스 목록 확인
show databases;
# 3. 데이터베이스 활성화 (여러 데이터베이스 중에 사용)
use mydb1122;
# 4. 활성화된 데이터베이스에 테이블/표 생성
create table board(
	bno int unsigned auto_increment  , 
    -- bno 게시물번호 -> 숫자 -> 숫자중에 적젛란 타입 -> int
    -- unsigned -> 부호가 없다 -> int ( += 21억 ) -> 총 42억정도
    -- auto_increment -> 레코드/행/표 삽입시 자동으로 번호가 순차적으로 할당 해주는 키워드
	bcontent longtext not null ,
    -- longtext -> 게시물의 내용 많이들어가면 좋을것 같아서 4G 선택
    -- not null -> 게시물 등록시 게시물 내용은 빈내용이 들어가면 안될것같아서 차단
    bwriter varchar(100) not null,
    -- varchar -> 작성자는 적절하게 100글자까지 입력 받을수 있게 선택
    bpwd int not null ,
    -- 비밀번호는 숫자타입으로 int 사용
    primary key( bno )
    -- primary key란 ? 기본(식별)키 , 여러개의 필드중에서 식별 가능한 필드 , 중복X , 
    -- 게시물 번호를 pk로 사용하는 이유? 게시물내용은/작성자/비밀번호는 충분히 중복 데이터가 존재할 경우가 크다.
    -- 관례적(무조건x일반적o)으로 테이블마다 1개 이상의 pk(기본키)가 필수 , (주민등록번호 o - 사람이믊 x ) / (학번 o / 학생이름 x)
);

# 5. DML : 데이터 조작어
# 1. select
select * from board;
-- select * from 테이블명;									: 지정한 테이블의 모든( * ) 레코드 조회/검색 
select bcontent , bwriter from board;
-- select 필드명1, 필드명2 from 테이블명;							: 지정한 테이블의 특정 필드의 레코드 조회/검색

# 2. insert : 레코드 삽입/추가 SQL
insert into board values(1, '안녕하세요','유재석',1234); 
# 전체필드에 값 삽입시 삽입할 필드명을 생략해도 된다.
-- insert into 테이블명 values(값1, 값2, 값3);
-- board 테이블에 필드선언 순서대로 'bno'에는 1, 'bcontent'에는 안녕하세요 , 'bwriter'에는 유재석 , 'bpwd'에는 1234

insert into board(bcontent,bwriter,bpwd) values('그래 안녕' , '강호동1' , 4567); 

##insert into board values('그래 안녕' , '강호동' , 4567); # 오류 # 필드의 개수와 데이터 개수가 일치하지 않는다.##
insert into board(bcontent,bwriter,bpwd) values('그래 안녕' , '강호동' , 4567); 
# 특정 필드에 값을 삽입시 삽입할 필드명을 명시 해야한다.
-- insert into 테이블명( 필드명1, 필드명2, 필드명3) values ( 값1, 값2, 값3);

# 3. update : 특정 레코드의 필드값 수정 SQL
update board set bwriter = '신동엽' where bno = 1;
# update 테이블명 set 수정할필드명 = 새로운값 where 조건필드 = 조건값;
# update 테이블명 set 수정할필드명 = 새로운값 -- 조건이없으면 전체 수정


# 4. delete : 특정 레코드 ( 한줄 ) 삭제 SQL 
# delete from 테이블명; 						지정한 테이블 전체 삭제
# delete from 테이블명 where 조건필드 = 조간값; 	지정한 테이블의 특정 조건을 충족하는 레코드만 삭제
delete from board where bno = 3;

# [실습]
/*
[문제1]: 테이블이 존재한다는 가정하에 products 테이블에 새로운 제품을 추가하세요. 제품의 정보는 다음과 같습니다 ,
   필드명 : 값
   product_id: 201
   product_name: 'Wireless Mouse'
   category: 'Electronics'
   price: 29.99
   stock_quantity: 150
   insert into products values(201, 'Wireless Mouse' , 'Electronics' , 29.99 , 150 )
 [문제2]: 테이블이 존재한다는 가정하에 employees 테이블에서 모든 직원의 first_name과 email을 조회하세요.
 [문제3]: 테이블이 존재한다는 가정하에 products 테이블에서 price가 1000 인 제품의 product_name과 price를 조회하세요.
 [문제4]: 테이블이 존재한다는 가정하에 employees 테이블에서 employee_id가 101인 직원의 email을 'john.doe@company.com'으로 업데이트하세요.
 [문제5]: 테이블이 존재한다는 가정하에 products 테이블에서 product_id가 201인 제품의 price를 24.99로 업데이트하세요.
 [문제6]: 테이블이 존재한다는 가정하에 employees 테이블에서 employee_id가 101인 직원을 삭제하세요.
 [문제7]: 테이블이 존재한다는 가정하에 products 테이블에서 product_id가 201인 제품을 삭제하세요.
*/
insert into products(product_id,product_name,category,price,stock_quantity)
 values(201, 'Wireless Mouse' , 'Electronics' , 29.99 , 150 );
select first_name, email from employees;
select product_name, price from products where price = 1000;

update employees set email = 'john.doe@company.com' where employee_id = 101;
update products set price를 = 24.99 where product_id = 201;
delete from employees where employee_id = 101;
delete from products where product_id = 201;
