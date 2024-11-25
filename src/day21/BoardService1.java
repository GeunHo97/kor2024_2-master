/*
    BoardService1
        - 내용 과 작성자 로 구성된 게시물을 최대 3개 까지 저장하는 서비스 구축
        - 조건 : main함수 1개 와 변수 6개와 반복문 사용
        - 구축 : 게시물 쓰기 와 게시물 출력 기능 구현
*/
package day21;

import java.util.Scanner;

public class BoardService1 {
    public static void main(String[] args) { // main start // 번역된 코드들을 실행하는 기능(main스레드) 포함
        Scanner scan = new Scanner(System.in); // - 입력 객체
        // - 저장소 설계 , 게시물1개당 내용과 작성자( 내용 2개 ) , 게시물3개면 내용 6개
            // 변수란? 하나의 데이터/주소값 를 저장하는 공간/메모리
            // 타입이란? 변수의 저장될 데이터/주소의 타입 , 데이터 분류 , 기본타입(7:int~double) VS 참조타입( 클래스,인터페이스,배열 )
        String content1 = null;   String writer1 = null;        // 첫번째 게시물 내용물들
        String content2 = null;   String writer2 = null;        // 두번째 게시물 내용물들
        String content3 = null;   String writer3 = null;        // 세번째 게시물 내용물들

        // if : 다중 if는 다중 조건의 다중 결과를 충족할수 있다. ,
        // if~else if : 다중 조건의 무조건 1개 결과를 충족한다.
        while( true ) { // 무한루프
            System.out.print("1.게시물쓰기 2.게시물출력 선택 : ");// 출력
            int choose = scan.nextInt(); // 입력
            if( choose == 1 ) { // 만약에 입력한 값이 1 이면 '게시물 쓰기' 구현
                // 게시물을 저장할 내용 입력 받기
                System.out.print("새로운 게시물 내용: "); String content = scan.next();
                System.out.print("새로운 게시물 작성자: "); String writer = scan.next();
                // 게시물을 작성할 공간이 있는지 체크 , 임의로 게시물이 존재하지 않는 뜻  null(객체없다뜻) 또는 ""
                if( content1 == null ){ // 만약에 첫번째 게시물의 정보가 비어있으면
                    content1 = content;   writer1 = writer; // 입력받은 값들을 첫번째 게시물 변수에 저장
                } // if end
                else if( content2 == null ) { // 만약에 두번째 게시물의 정보가 비어 있으면
                    content2 = content; writer2 = writer;
                } // else if end
                else if ( content3 == null ) {
                    content3 = content; writer3 = writer;
                }else{
                    System.out.println("게시물 쓰기 실패 : 빈공간이 없습니다.");
                } // else end
            } // if end
            else if ( choose == 2 ) { // 만약에 입력한 값이 2 이면 '게시물 출력' 구현

                if( content1 != null ) {// 만약에 첫번째 게시물의 존재하면
                    System.out.printf("작성자 : %s , 내용 : %s \n" , writer1 , content1 );
                }
                if( content2 != null ) {// 만약에 두번째 게시물의 존재하면
                    System.out.printf("작성자 : %s , 내용 : %s \n" , writer2 , content2 );
                }
                if( content3 != null ) {// 만약에 세번째 게시물의 존재하면
                    System.out.printf("작성자 : %s , 내용 : %s \n" , writer3 , content3 );
                }

            } // if end
        } // while end
    } // main end
} // class end






