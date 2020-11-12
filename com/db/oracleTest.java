package com.db;

import java.sql.Connection;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.util.*;

/*import java.sql.Timestamp;
import java.time.LocalDateTime;*/
import com.db.model.*;

public class oracleTest 
{
    static Scanner sc = new Scanner(System.in);
    public static void main(String args[]) throws Exception
    {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        DAO.connectDB();
        while(true)
        {
            try
            {
                System.out.print("1. 관리자 모드 \n2. 일반사용자 모드\n3. 종료\n입력 : ");
                int mode = sc.nextInt();
                sc.nextLine();
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                switch(mode)
                {
                    case 1:
                    {
                        System.out.println("1. 영화관 등록");
                        System.out.println("2. 영화관 수정");
                        System.out.println("3. 영화관 제거");
                        System.out.println("4. 영화관 출력");
                        System.out.println("5. 상영관 등록");
                        System.out.println("6. 상영관 수정");
                        System.out.println("7. 상영관 제거");
                        System.out.println("8. 상영관 출력");
                        System.out.println("9. 영화 추가");
                        System.out.println("10. 영화 수정");
                        System.out.println("11. 영화 제거");
                        System.out.println("12. 영화 출력");
                        System.out.println("13. 영화관 별 예매정보 등록");
                        System.out.println("14. 영화관 별 예매정보 수정");
                        System.out.println("15. 영화관 별 예매정보 제거");
                        System.out.println("16. 영화관 별 예매정보 출력");
                        System.out.println("17. 통계정보 열람");
                        System.out.println("18. 수입 계좌 관리");
                        System.out.println("19. 관리자 계정 등록");
                        System.out.println("20. 타입별 가격 수정");
                        System.out.println("21. 돌아가기");
                        System.out.print("입력 : ");
                        int menu = sc.nextInt();
                        sc.nextLine();
                        
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                        switch(menu)
                        {
                            case 1 :    //영화관 등록
                            {
                                TheaterDAO tDao = new TheaterDAO();
                                ArrayList<TheaterDTO> list = tDao.getTheaterList();
                                displayTheater(list);
                                tDao.addTheater(insertInfoTheater(DTO.EMPTY_ID));
                                break;
                            }
                            case 2 :    //영화관 수정
                            {
                                TheaterDAO tDao = new TheaterDAO();
                                ArrayList<TheaterDTO> list = tDao.getTheaterList();
                                displayTheater(list);
                                System.out.print("수정할 영화관을 선택해주세요");
                                TheaterDTO temp = list.get(sc.nextInt());
                                sc.nextLine();

                                tDao.changeTheater(insertInfoTheater(temp.getId()));

                                
                                break;
                            }
                            case 3 :    //영화관 삭제
                            {
                                TheaterDAO tDao = new TheaterDAO();
                                ArrayList<TheaterDTO> list = tDao.getTheaterList();
                                displayTheater(list);
                                System.out.print("삭제할 영화관을 선택해주세요");
                                TheaterDTO temp = list.get(sc.nextInt());
                                sc.nextLine();

                                tDao.removeTheater(temp);
                                
                                break;
                            }
                            case 4 :    //영화관 출력
                            {
                                TheaterDAO tDao = new TheaterDAO();
                                ArrayList<TheaterDTO> list = tDao.getTheaterList();
                                displayTheater(list);
                                
                                break;
                            }
                            case 5 :    //상영관 등록
                            {
                                TheaterDAO tDao = new TheaterDAO();
                                ArrayList<TheaterDTO> list = tDao.getTheaterList();
                                displayTheater(list);
                                System.out.print("영화관을 선택해주세요");
                                TheaterDTO theaterElem = list.get(sc.nextInt());
                                sc.nextLine();
                                System.out.println("선택한 영화관 : " + theaterElem.getName());
                                
                                ScreenDAO sDao = new ScreenDAO();
                                sDao.addScreen(insertInfoScreen(DTO.EMPTY_ID, theaterElem.getId()));
                                
                                break;
                            }
                            case 6 :    //상영관 수정
                            {
                                TheaterDAO tDao = new TheaterDAO();
                                ArrayList<TheaterDTO> tlist = tDao.getTheaterList();
                                displayTheater(tlist);
                                System.out.print("영화관을 선택해주세요");
                                TheaterDTO theaterElem = tlist.get(sc.nextInt());
                                sc.nextLine();
                                

                                ScreenDAO sDao = new ScreenDAO();
                                ArrayList<ScreenDTO> slist = sDao.getScreenList(theaterElem.getId());
                                displayScreen(slist);
                                System.out.print("상영관을 선택해주세요");
                                ScreenDTO screenElem = slist.get(sc.nextInt());
                                sc.nextLine();

                                System.out.println("선택한 영화관 : " + theaterElem.getName());
                                System.out.println("선택한 상영관 : " + screenElem.getName());

                                sDao.changeScreen(insertInfoScreen(screenElem.getId(), screenElem.getTheaterId()));
                                
                                break;
                            }
                            case 7 :    //상영관 삭제
                            {
                                TheaterDAO tDao = new TheaterDAO();
                                ArrayList<TheaterDTO> tlist = tDao.getTheaterList();
                                displayTheater(tlist);
                                System.out.print("영화관을 선택해주세요");
                                TheaterDTO theaterElem = tlist.get(sc.nextInt());
                                sc.nextLine();
                                
                                ScreenDAO sDao = new ScreenDAO();
                                ArrayList<ScreenDTO> slist = sDao.getScreenList(theaterElem.getId());
                                displayScreen(slist);
                                System.out.print("상영관을 선택해주세요");
                                ScreenDTO screenElem = slist.get(sc.nextInt());
                                sc.nextLine();

                                sDao.removeScreen(screenElem);
                                
                                break;
                            }
                            case 8 :    //상영관 출력
                            {
                                TheaterDAO tDao = new TheaterDAO();
                                ArrayList<TheaterDTO> tlist = tDao.getTheaterList();
                                displayTheater(tlist);
                                System.out.print("영화관을 선택해주세요");
                                TheaterDTO theaterElem = tlist.get(sc.nextInt());
                                sc.nextLine();

                                ScreenDAO sDao = new ScreenDAO();
                                ArrayList<ScreenDTO> list = sDao.getScreenList(theaterElem.getId());
                                displayScreen(list);
                                
                                break;
                            }
                            case 9 :    //영화 추가
                            {
                                MovieDAO mDao = new MovieDAO();
                                mDao.addMovie(insertInfoMovie(DTO.EMPTY_ID));
                                
                                break;
                            }
                            case 10 :   //영화 수정
                            {  
                                MovieDAO mDao = new MovieDAO();
                                ArrayList<MovieDTO> mList = mDao.getMovieList(insertConditionMovie());
                                displayMovie(mList);
                                System.out.print("영화를 선택해주세요");
                                MovieDTO movieElem = mList.get(sc.nextInt());
                                sc.nextLine();

                                mDao.changeMovie(insertInfoMovie(movieElem.getId()));
                                
                                break;
                            }
                            case 11 :   //영화 삭제
                            {
                                MovieDAO mDao = new MovieDAO();
                                ArrayList<MovieDTO> mList = mDao.getMovieList(insertConditionMovie());
                                displayMovie(mList);
                                System.out.print("영화를 선택해주세요");
                                MovieDTO movieElem = mList.get(sc.nextInt());
                                sc.nextLine();

                                mDao.removeMovie(movieElem);

                                break;
                            }
                            case 12 :   //영화 출력
                            {
                                MovieDAO mDao = new MovieDAO();
                                ArrayList<MovieDTO> mList = mDao.getMovieList(insertConditionMovie());
                                displayMovie(mList);
                                
                                break;
                            }
                            case 13 :   //영화관 별 예매정보 등록
                            {
                                TheaterDAO tDao = new TheaterDAO();
                                ArrayList<TheaterDTO> tlist = tDao.getTheaterList();
                                displayTheater(tlist);
                                System.out.print("영화관을 선택해주세요");
                                TheaterDTO theaterElem = tlist.get(sc.nextInt());
                                sc.nextLine();

                                ScreenDAO sDao = new ScreenDAO();
                                ArrayList<ScreenDTO> slist = sDao.getScreenList(theaterElem.getId());
                                displayScreen(slist);
                                System.out.print("상영관을 선택해주세요");
                                ScreenDTO screenElem = slist.get(sc.nextInt());
                                sc.nextLine();

                                MovieDAO mDao = new MovieDAO();
                                ArrayList<MovieDTO> mList = mDao.getMovieList(insertConditionMovie());
                                displayMovie(mList);
                                System.out.print("영화를 선택해주세요");
                                MovieDTO movieElem = mList.get(sc.nextInt());
                                sc.nextLine();

                                TimeTableDAO ttDao = new TimeTableDAO();
                                ttDao.addTimeTable(insertInfoTimeTable(movieElem.getId(), screenElem.getId(), DTO.EMPTY_ID));

                                break;
                            }
                            case 14 :   //영화관 별 예매정보 수정
                            {
                                TheaterDAO tDao = new TheaterDAO();
                                ArrayList<TheaterDTO> tlist = tDao.getTheaterList();
                                displayTheater(tlist);
                                System.out.print("영화관을 선택해주세요");
                                TheaterDTO theaterElem = tlist.get(sc.nextInt());
                                sc.nextLine();

                                ScreenDAO sDao = new ScreenDAO();
                                ArrayList<ScreenDTO> slist = sDao.getScreenList(theaterElem.getId());
                                displayScreen(slist);
                                System.out.print("상영관을 선택해주세요");
                                ScreenDTO screenElem = slist.get(sc.nextInt());
                                sc.nextLine();

                                MovieDAO mDao = new MovieDAO();
                                ArrayList<MovieDTO> mList = mDao.getMovieList(insertConditionMovie());
                                displayMovie(mList);
                                System.out.print("영화를 선택해주세요");
                                MovieDTO movieElem = mList.get(sc.nextInt());
                                sc.nextLine();

                                TimeTableDAO ttDao = new TimeTableDAO();
                                ArrayList<TimeTableDTO> ttlist = ttDao.getTimeTableList(insertInfoTimeTable(movieElem.getId(), screenElem.getId(), DTO.EMPTY_ID));
                                displayTimeTable(ttlist);
                                System.out.print("상영시간표를 선택해주세요");
                                TimeTableDTO ttElem = ttlist.get(sc.nextInt());

                                sc.nextLine();

                                ttDao.changeTimeTable(insertInfoTimeTable(movieElem.getId(), screenElem.getId(), ttElem.getId()));

                                break;
                            }
                            case 15 :   //영화관 별 예매정보 제거
                            {
                                TheaterDAO tDao = new TheaterDAO();
                                ArrayList<TheaterDTO> tlist = tDao.getTheaterList();
                                displayTheater(tlist);
                                System.out.print("영화관을 선택해주세요");
                                TheaterDTO theaterElem = tlist.get(sc.nextInt());
                                sc.nextLine();

                                ScreenDAO sDao = new ScreenDAO();
                                ArrayList<ScreenDTO> slist = sDao.getScreenList(theaterElem.getId());
                                displayScreen(slist);
                                System.out.print("상영관을 선택해주세요");
                                ScreenDTO screenElem = slist.get(sc.nextInt());
                                sc.nextLine();

                                MovieDAO mDao = new MovieDAO();
                                ArrayList<MovieDTO> mList = mDao.getMovieList(insertConditionMovie());
                                displayMovie(mList);
                                System.out.print("영화를 선택해주세요");
                                MovieDTO movieElem = mList.get(sc.nextInt());
                                sc.nextLine();

                                TimeTableDAO ttDao = new TimeTableDAO();
                                ArrayList<TimeTableDTO> ttlist = ttDao.getTimeTableList(insertInfoTimeTable(movieElem.getId(), screenElem.getId(), DTO.EMPTY_ID));
                                displayTimeTable(ttlist);
                                System.out.print("상영시간표를 선택해주세요");
                                TimeTableDTO ttElem = ttlist.get(sc.nextInt());
                                sc.nextLine();

                                ttDao.removeTimeTable(ttElem);

                                break;
                            }
                            case 16 :   //영화관 별 예매정보 출력
                            {
                                TheaterDAO tDao = new TheaterDAO();
                                ArrayList<TheaterDTO> tlist = tDao.getTheaterList();
                                displayTheater(tlist);
                                System.out.print("영화관을 선택해주세요");
                                TheaterDTO theaterElem = tlist.get(sc.nextInt());
                                sc.nextLine();

                                ScreenDAO sDao = new ScreenDAO();
                                ArrayList<ScreenDTO> slist = sDao.getScreenList(theaterElem.getId());
                                displayScreen(slist);
                                System.out.print("상영관을 선택해주세요");
                                ScreenDTO screenElem = slist.get(sc.nextInt());
                                sc.nextLine();

                                MovieDAO mDao = new MovieDAO();
                                ArrayList<MovieDTO> mList = mDao.getMovieList(insertConditionMovie());
                                displayMovie(mList);
                                System.out.print("영화를 선택해주세요");
                                MovieDTO movieElem = mList.get(sc.nextInt());
                                
                                sc.nextLine();

                                TimeTableDAO ttDao = new TimeTableDAO();
                                ArrayList<TimeTableDTO> ttlist = ttDao.getTimeTableList(insertInfoTimeTable(movieElem.getId(), screenElem.getId(), DTO.EMPTY_ID));
                                displayTimeTable(ttlist);

                                break;
                            }
                            case 18:    //수입계좌 관리
                            {
                                MemberDAO mDAO = new MemberDAO();
                                MemberDTO member = getMember();

                                if(member.getRole().equals("2"))
                                {
                                    System.out.println("관리자 계정이 아닙니다!");
                                    break;
                                }
                                System.out.printf("현재 관리자 계좌 : %s\n", member.getAccount());
                                System.out.print("수정 할 관리자 계좌 : ");
                                member.setAccount(sc.nextLine());
                                mDAO.changeMemberInfo(member);
                                break;
                            }
                            case 19:    //관리자 계정 등록
                            {
                                System.out.println("[관리자 계정 등록]");
                                MemberDAO mDAO = new MemberDAO();
                                mDAO.addMember(insertInfoAdmin("1"));
                                break;
                            }
                            case 20:
                            {
                                ChargeDAO cDao = new ChargeDAO();
                                ArrayList<ChargeDTO> cList = cDao.getChargeList();
                                for(int i = 0; i < cList.size(); i++)
                                {
                                    ChargeDTO temp = cList.get(i);
                                    System.out.printf("[%d] %s | %d \n", i, temp.getType(), temp.getPrice());
                                }
                                System.out.print("수정할 타입을 선택해주세요 : ");
                                ChargeDTO cDto = cList.get(sc.nextInt());
                                sc.nextLine();

                                System.out.print("수정할 가격을 입력해주세요 : ");
                                cDto.setPrice(sc.nextInt());
                                sc.nextLine();

                                cDao.changeCharge(cDto);
                                break;
                            }
                            case 21:
                            {
                                break;
                            }
                        }
                        break;
                    }
                    case 2 :
                    {
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                        System.out.println("1. 영화관 조회");
                        System.out.println("2. 현재 상영작 정보 조회");
                        System.out.println("3. 상영 예정작 정보 조회");
                        System.out.println("4. 상영 시간표 조회");
                        System.out.println("5. 영화 예매");
                        System.out.println("6. 영화 취소");
                        System.out.println("7. 예매 현황 조회");
                        System.out.println("8. 평점 및 리뷰 등록");
                        System.out.println("9. 평점 및 리뷰 수정");
                        System.out.println("10. 사용자 등록");
                        System.out.println("11. 사용자 수정");
                        System.out.println("12. 돌아가기");
                        System.out.print("입력 : ");
                        int menu = sc.nextInt();
                        sc.nextLine();
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                        switch(menu)
                        {
                            case 1 :    //영화관 조회 
                            {
                                TheaterDAO tDao = new TheaterDAO();
                                ArrayList<TheaterDTO> tlist = tDao.getTheaterList();
                                displayTheater(tlist);
                                System.out.print("영화관을 선택해주세요");
                                TheaterDTO theaterElem = tlist.get(sc.nextInt());
                                sc.nextLine();

                                ScreenDAO sDao = new ScreenDAO();
                                ArrayList<ScreenDTO> slist = sDao.getScreenList(theaterElem.getId());
                                displayScreen(slist);
                                
                                break;
                            }
                            case 2 :    //현재 상영작 정보 조회 
                            {
                                MovieDAO mDao = new MovieDAO();
                                ArrayList<MovieDTO> mList = mDao.getMovieList(currentAndScheduleMovie("1"));
                                displayMovie(mList);
                                
                                break;
                            }
                            case 3 :    //상영 예정작 정보 조회
                            {
                                MovieDAO mDao = new MovieDAO();
                                ArrayList<MovieDTO> mList = mDao.getMovieList(currentAndScheduleMovie("2"));
                                displayMovie(mList);
                                
                                break;
                            }
                            case 4 :    //상영 시간표 조회
                            {
                                TheaterDAO tDao = new TheaterDAO();
                                ArrayList<TheaterDTO> tlist = tDao.getTheaterList();
                                displayTheater(tlist);
                                System.out.print("영화관을 선택해주세요");
                                TheaterDTO theaterElem = tlist.get(sc.nextInt());
                                sc.nextLine();
                                
                                sc.nextLine();

                                TimeTableDAO ttDao = new TimeTableDAO();
                                ArrayList<TimeTableDTO> ttlist = ttDao.getTimeTableList(insertCurTimeTable("%", "%", DTO.EMPTY_ID), theaterElem.getId());
                                userDisTimeTable(ttlist);   //모든 영화, 영화 아이디 입력 가능
                                break;
                            }
                            case 5 :    //영화 예매
                            {
                                Connection conn = DAO.getConn();
                                conn.setAutoCommit(false);
                                Savepoint sp = conn.setSavepoint();
                                //상영 시간표 중에서 선택
                                //결제 시도(결제 시 사용자의 계좌에는 돈이 빠지고 관리자의 계좌에는 돈이 추가됨)
                                //상영관 최대 행, 열 구해야함
                                //예매 테이블 중 에 행과 열이 겹치는 지 판단해야함
                                try
                                {
                                    MemberDTO member = getMember();

                                    TheaterDAO tDao = new TheaterDAO();
                                    ArrayList<TheaterDTO> tlist = tDao.getTheaterList();
                                    displayTheater(tlist);
                                    System.out.print("영화관을 선택해주세요");
                                    TheaterDTO theaterElem = tlist.get(sc.nextInt());
                                    sc.nextLine();
    
                                    TimeTableDAO ttDao = new TimeTableDAO();
                                    ArrayList<TimeTableDTO> ttlist = ttDao.getTimeTableList(insertCurTimeTable("%", "%", DTO.EMPTY_ID), theaterElem.getId());
                                    userDisTimeTable(ttlist);
                                    System.out.print("상영 시간표를 선택해주세요");
                                    TimeTableDTO ttElem = ttlist.get(sc.nextInt());
                                    sc.nextLine();
                                    int current_rsv = ttElem.getCurrentRsv();
    
                                    //ttElem의 id로 상영관 id를 얻어서 최대 행, 최대 열, 총 수용 가능 인원을 받아와야 함.
    
                                    ScreenDAO sDao = new ScreenDAO();
                                    ScreenDTO screenElem = sDao.getScreenElem(ttElem.getScreenId());
                                    int total_capacity = screenElem.getTotalCapacity();
                                    int row = screenElem.getMaxRow();
                                    int col = screenElem.getMaxCol();
    
                                    if(current_rsv == total_capacity)   //자리 꽉 차면 예외 처리 발생
                                    {
                                        System.out.println("예약이 불가능 합니다(좌석이 부족합니다)");
                                    }
    
                                    //현재 상영시간표의 id에 해당하는 모든 row, col 집합이 필요함
                                    
                                    ReservationDAO rDao = new ReservationDAO();
                                    System.out.printf("[최대 행 = %d, 최대 열 = %d]\n", row, col);
                                    displayCurrentRsvSeat(rDao.getRsvListFromTT(ttElem.getId()));

                                    ReservationDTO rsvInfo = insertInfoRsv(member.getId(), ttElem.getId());

                                    System.out.println("계좌 비밀번호를 입력해주세요 : ");
                                    String account_passwd = sc.nextLine();
    
                                    rDao.addReservation(rsvInfo, account_passwd);
                                    
                                    conn.commit();
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                    conn.rollback(sp);
                                }
                                conn.setAutoCommit(true);           
                                break;
                            }
                            case 6 :    //예매 취소
                            {
                                //사용자의 계좌에 돈 입급(예매 테이블 가격 참조)
                                //예매 테이블의 정보 삭제
                                //상영시간표의 현재 예매인원 감소
                                Connection conn = DAO.getConn();
                                conn.setAutoCommit(false);
                                Savepoint sp = conn.setSavepoint();

                                try
                                {
                                    MemberDTO member = getMember();

                                    ReservationDAO rDao = new ReservationDAO();
                                    ArrayList<ReservationDTO> rsvList = rDao.getRsvListFromMem(member.getId());

                                    displayCurrentRsv(rsvList);
                                    System.out.print("예약 내역을 선택해주세요 : ");
                                    ReservationDTO rsvElem = rsvList.get(sc.nextInt());
                                    sc.nextLine();
                                    rDao.cancelRsv(rsvElem.getId());
                                    
                                    conn.commit();
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                    conn.rollback(sp);
                                }
                                conn.setAutoCommit(true);           
                                break;
                            }
                            case 7 :    //예매 현황 조회
                            {
                                MemberDTO member = getMember();

                                ReservationDAO rDao = new ReservationDAO();
                                ArrayList<ReservationDTO> rsvList = rDao.getRsvListFromMem(member.getId());

                                displayCurrentRsv(rsvList);

                                break;
                            }
                            case 8 :    //평점 및 리뷰 등록
                            {
                                MemberDTO member = getMember();

                                //사용자가 시청한 영화 불러오기
                                MovieDAO movDao = new MovieDAO();
                                ArrayList<MovieDTO> movList = movDao.getMemberMovie(member.getId());
                                displayMovie(movList);
                                System.out.print("리뷰를 쓸 영화를 선택해주세요 : ");
                                MovieDTO movElem = movList.get(sc.nextInt());
                                sc.nextLine();

                                ReviewDAO rDao = new ReviewDAO();
                                rDao.addReview(insertInfoReview(DTO.EMPTY_ID, member.getId(), movElem.getId()));
                                break;
                            }
                            case 9 :    //평점 및 리뷰 수정
                            {
                                MemberDTO member = getMember();

                                //사용자가 시청한 영화 불러오기
                                MovieDAO movDao = new MovieDAO();
                                ArrayList<MovieDTO> movList = movDao.getMemberMovie(member.getId());
                                displayMovie(movList);
                                System.out.print("리뷰를 쓴 영화를 선택해주세요 : ");
                                MovieDTO movElem = movList.get(sc.nextInt());
                                sc.nextLine();

                                ReviewDAO rDao = new ReviewDAO();
                                ArrayList<ReviewDTO> rvList = rDao.getRvList(member.getId(), movElem.getId());
                                displayReview(rvList);
                                System.out.print("리뷰를 선택해주세요 : ");
                                ReviewDTO rvElem = rvList.get(sc.nextInt());
                                sc.nextLine();

                                rDao.changeReview(insertInfoReview(rvElem.getId(), rvElem.getMemberId(), rvElem.getMovieId()));

                                break;
                            }
                            case 10 :    //사용자 등록
                            {
                                System.out.println("[회원가입]");
                                MemberDAO mDAO = new MemberDAO();
                                mDAO.addMember(insertInfoMem("2"));
                                break;
                            }
                            case 11 :   //사용자 수정
                            {
                                System.out.println("[정보 수정]");
                                MemberDAO mDAO = new MemberDAO();
                                mDAO.changeMemberInfo(insertInfoMem("2"));
                                break;
                            }
                            case 12:
                            {
                                break;
                            }
                        }
                        break;
                    }
                    case 3 :
                    {
                        DAO.closeDB();
                        sc.close();
                        return;
                    }
                }
            
            }
            catch(Exception e)
            {
                sc.nextLine();
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    public static TheaterDTO insertInfoTheater(String id)
    {
        System.out.print("영화관 이름 : ");
        String name = sc.nextLine();
        System.out.print("영화관 주소 : ");
        String address = sc.nextLine();
        System.out.print("총 상영관 수 : ");
        int screen = sc.nextInt();
        sc.nextLine();
        System.out.print("총 좌석 수 : ");
        int seat = sc.nextInt();
        sc.nextLine();

        return new TheaterDTO(id, name, address, screen, seat);
    }

    public static void displayTheater(ArrayList<TheaterDTO> list)
    {
        for(int i = 0; i < list.size(); i++)
        {
            TheaterDTO temp = (TheaterDTO) list.get(i);
            System.out.printf("[%d] %s | %s | %d | %d \n", i, temp.getName(), temp.getAddress(), temp.getTotalScreen(), temp.getTotalSeats());
        }
    }

    public static ScreenDTO insertInfoScreen(String id, String theater_id)
    {
        System.out.print("상영관 이름 : ");
        String name = sc.nextLine();
        System.out.print("상영관 좌석 수 : ");
        int capacity = sc.nextInt();
        sc.nextLine();
        System.out.print("상영관 행 : ");
        int row = sc.nextInt();
        sc.nextLine();
        System.out.print("상영관 열 : ");
        int col = sc.nextInt();
        sc.nextLine();

        return new ScreenDTO(id, theater_id, name, capacity, row, col);
    }

    public static void displayScreen(ArrayList<ScreenDTO> list)
    {
        for(int i = 0; i < list.size(); i++)
        {
            ScreenDTO temp = list.get(i);
            System.out.printf("[%d] %s | %s | %d | %d | %d \n", i, temp.getTheaterId(), temp.getName(), temp.getTotalCapacity(), temp.getMaxRow(), temp.getMaxCol());
        }
    }

    public static MovieDTO insertInfoMovie(String id)
    {
        System.out.print("영화 타이틀 : ");
        String title = sc.nextLine();
        System.out.print("영화 개봉일(YYYY-MM-DD) : ");
        String release_date = sc.nextLine();
        System.out.print("상영 여부(상영종료 : 0, 현재상영 : 1, 상영예정 : 2) : ");
        String is_current = sc.nextLine();
        System.out.print("줄거리 : ");
        String plot = sc.nextLine();
        System.out.print("포스터 경로 : ");
        String poster = sc.nextLine();
        System.out.print("스틸컷 경로 : ");
        String stillcut = sc.nextLine();
        System.out.print("트레일러 경로 : ");
        String trailer = sc.nextLine();
        System.out.print("감독 : ");
        String director = sc.nextLine();
        System.out.print("배우 : ");
        String actor = sc.nextLine();
        System.out.print("상영 시간 :");
        int min = sc.nextInt();
        sc.nextLine();

        return new MovieDTO(id, title, release_date, is_current, plot, poster, stillcut, trailer, director, actor, min);
    }

    public static HashMap<String, String> insertConditionMovie()
    {
        HashMap<String, String> info = new HashMap<String, String>();

        System.out.print("영화 타이틀(모를 땐 % 입력) : ");
        info.put("title", sc.nextLine());
        System.out.print("시작 날짜(YYYY-MM-DD) : ");
        info.put("start_date", sc.nextLine());
        System.out.print("마지막 날짜(YYYY-MM-DD) : ");
        info.put("end_date", sc.nextLine());
        System.out.print("상영 여부 선택(상영종료 : 0, 현재상영 : 1, 상영예정 : 2) : ");
        info.put("is_current", sc.nextLine());
        System.out.print("감독(모를 땐 % 입력) : ");
        info.put("director", sc.nextLine());
        System.out.print("배우(모를 땐 % 입력) : ");
        info.put("actor", sc.nextLine());

        return info;
    }

    public static HashMap<String, String> currentAndScheduleMovie(String elem)
    {
        HashMap<String, String> info = new HashMap<String, String>();

        info.put("title", "%");
        info.put("start_date", "1000-01-01");
        info.put("end_date", "2999-01-01");
        info.put("is_current", elem);
        info.put("director", "%");
        info.put("actor", "%");

        return info;
    }

    public static void displayMovie(ArrayList<MovieDTO> list)
    {
        for(int i = 0; i < list.size(); i++)
        {
            MovieDTO temp = (MovieDTO) list.get(i);
            System.out.printf("[%d] %s | %s | %s | %s | %s | %s | %s | %s | %s | %d \n", i, temp.getTitle(), temp.getReleaseDate(), 
            temp.getIsCurrent(), temp.getPlot(), temp.getPosterPath(), temp.getStillCutPath(), temp.getTrailerPath(), temp.getDirector(), temp.getActor(), temp.getMin());
        }
    }

    public static TimeTableDTO insertInfoTimeTable(String movie_id, String screen_id, String id) //
    {
        System.out.print("시작 시간 입력(YYYY-MM-DD HH:MM:SS.S의 형태로 입력) : ");
        String start_time = sc.nextLine();
        System.out.print("종료 시간 입력(YYYY-MM-DD HH:MM:SS.S의 형태로 입력) : ");
        String end_time = sc.nextLine();

        return new TimeTableDTO(id, movie_id, screen_id, start_time, end_time, 0);
    }

    public static TimeTableDTO insertCurTimeTable(String movie_id, String screen_id, String id)
    {
        Timestamp start_time = new Timestamp(System.currentTimeMillis());

        return new TimeTableDTO(id, movie_id, screen_id, start_time, 0);
    }

    public static void displayTimeTable(ArrayList<TimeTableDTO> list) throws Exception
    {
        for(int i = 0; i < list.size(); i++)
        {
            TimeTableDTO temp = list.get(i);
            System.out.printf("[%d] %s | %s | %s | %s | %s \n", i, temp.getMovieId(), temp.getScreenId(), 
            temp.getStartTime(), temp.getEndTime(), temp.getType());
        }
    }

    public static void userDisTimeTable(ArrayList<TimeTableDTO> list) throws Exception
    {
        for(int i = 0; i < list.size(); i++)
        {
            TimeTableDTO temp = list.get(i);
            System.out.printf("[%d] %s | %s | %s | %s | %s \n", i, temp.getMovieId(), temp.getScreenId(), 
            temp.getStartTime(), temp.getEndTime(), temp.getType());
        }
    }

    public static MemberDTO getMember() throws Exception
    {
        MemberDAO mDao = new MemberDAO();
        System.out.print("아이디를 입력해주세요 : ");
        String id = sc.nextLine();
        System.out.print("패스워드를 입력해주세요 : ");
        String passwd = sc.nextLine();
        return mDao.getMember(id, passwd);
    }

    public static void displayCurrentRsvSeat(ArrayList<ReservationDTO> rsvList)
    {
        System.out.println("[현재 예약된 좌석 좌표]");

        for(int i = 0; i < rsvList.size(); i++)
        {
            ReservationDTO r_temp = rsvList.get(i);
            System.out.printf("====(%d, %d)====\n", r_temp.getScreenRow(), r_temp.getScreenCol());
        }
    }

    public static void displayCurrentRsv(ArrayList<ReservationDTO> rsvList)
    {
        System.out.println("[예매 목록]");
        for(int i = 0; i < rsvList.size(); i++)
        {
            ReservationDTO temp = rsvList.get(i);
            System.out.printf("[%d] %s | %s | %d | %d | %d | %s \n", i, temp.getMemberId(), temp.getTimeTableId(), 
            temp.getScreenRow(), temp.getScreenCol(), temp.getPrice(), temp.getCancel());
        }
    }

    public static ReservationDTO insertInfoRsv(String member_id, String tt_id)
    {
        ReservationDTO empty = new ReservationDTO();
        empty.setId("-1");
        System.out.print("행을 선택해주세요 : ");
        empty.setScreenRow(sc.nextInt());
        sc.nextLine();
        System.out.print("열을 선택해주세요 : ");
        empty.setScreenCol(sc.nextInt());
        sc.nextLine();
        empty.setMemberId(member_id);
        empty.setTimeTableId(tt_id);
        empty.setCancel("0");

        return empty;
    }

    public static ReviewDTO insertInfoReview(String id, String mem_id, String mov_id)
    {
        ReviewDTO empty = new ReviewDTO();
        empty.setId(id);
        empty.setMemberId(mem_id);
        empty.setMovieId(mov_id);
        System.out.print("별점을 입력해주세요(0~10) : ");
        empty.setStar(sc.nextInt());
        sc.nextLine();
        System.out.print("리뷰 내용을 입력해주세요 : ");
        empty.setText(sc.nextLine());

        return empty;
    }

    public static void displayReview(ArrayList<ReviewDTO> rvList)
    {
        for(int i = 0; i < rvList.size(); i++)
        {
            ReviewDTO temp = rvList.get(i);
            System.out.printf("[%d] %s | %s | %d | %s  \n", i, temp.getMemberId(), temp.getMovieId(), temp.getStar(), temp.getText());
        }
    }

    public static MemberDTO insertInfoMem(String role)
    {
        MemberDTO mem = new MemberDTO();
                                
        System.out.print("아이디 : ");
        mem.setid(sc.nextLine());
        mem.setRole(role);
        System.out.print("패스워드 : ");
        mem.setPassword(sc.nextLine());
        System.out.print("계좌 : ");
        mem.setAccount(sc.nextLine());
        System.out.print("이름 : ");
        mem.setName(sc.nextLine());
        System.out.print("휴대폰 번호 : ");
        mem.setPhoneNumber(sc.nextLine());
        System.out.print("생일 : ");
        mem.setBirth(sc.nextLine());
        System.out.print("성별(남자:1, 여자:0) : ");
        mem.setGender(sc.nextLine());

        return mem;
    }

    public static MemberDTO insertInfoAdmin(String role)
    {
        MemberDTO mem = new MemberDTO();
                                
        System.out.print("아이디 : ");
        mem.setid(sc.nextLine());
        mem.setRole(role);
        System.out.print("패스워드 : ");
        mem.setPassword(sc.nextLine());
        System.out.print("계좌 : ");
        mem.setAccount(sc.nextLine());

        return mem;
    }
}