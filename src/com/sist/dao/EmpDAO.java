package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cjw on 2016-03-09.
 */
public class EmpDAO {
    //연결객체 얻기
    private Connection connection;
    //문장 전송 객체
    private PreparedStatement preparedStatement;
    //디비 url
    private final String URL="jdbc:oracle:thin:@211.238.142.72:1521:ORCL";
    //오라클 디비 드라이버 클래스
    private final String DRIVER="oracle.jdbc.driver.OracleDriver";
    //유저 네임
    private final String USER="scott5";
    //패스워드
    private final String PWD="tiger5";


    //생성자(오라클 디비 드라이버 등록 작업)
    public EmpDAO(){
        try{
            Class.forName(DRIVER);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }


    //디비 연결 객체 획득하기
    public void getConnection(){
        try{
            connection= DriverManager.getConnection(URL,USER,PWD);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    //디비 연결 객체 해재하기
        public void disConnection(){
            try{
                if(preparedStatement!=null)
                    preparedStatement.close();
                if(connection!=null)
                    connection.close();
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }

    //기능들 추가하기
    //전체 리스트 얻어오기
    public List<EmpDTO> empAllData(int page){
        List<EmpDTO> empDTOList=new ArrayList<>();

        //어래이리스트 내용 채우기
        try{
            //디비 객체 연결
            this.getConnection();

            //가로행수
            int rowSize=5;
            //시작 행
            int start=(rowSize*page)-(rowSize-1);
            //마지막 행
            int end=rowSize*page;
            //rownum BETWEEN start AND end
            //                  1       5
            //                  6       10

            //쿼리문-조인과 서브쿼리 활용
            String sql="SELECT empno,ename,job,hiredate,deptno,num "
                        +"FROM (SELECT empno,ename,job,hiredate,deptno,rownum as num " +
                        "FROM(SELECT empno,ename,job,hiredate,deptno " +
                        "FROM emp ORDER BY empno DESC)) "
                        +"WHERE num BETWEEN " +start+ " AND " +end;

            preparedStatement=connection.prepareStatement(sql);

            //디비 연결 객체에 쿼리문 문장을 실행
            ResultSet resultSet=preparedStatement.executeQuery();

            //쿼리문 실행 결과를 임시로 저장해 놓은 리절트셋 내용을 클라이언트에게 출력해줌.
            while(resultSet.next()){
                //클라이언트에 나타날 각 행들은 EmpDTO(AC)객체들임.
                //디비 연결 객체에 쿼리하여 얻은 리절트셋을 해당 DTO에 셋팅함.
                EmpDTO empDTO=new EmpDTO();
                empDTO.setEmpno(resultSet.getInt(1));
                empDTO.setEname(resultSet.getString(2));
                empDTO.setJob(resultSet.getString(3));
                empDTO.setHiredate(resultSet.getDate(4));
                empDTO.setDeptno(resultSet.getInt(5));

                //한행(레코드)단위 셋을 했으므로, 이것을 어레이리스트에 추가함.
                empDTOList.add(empDTO);
            }
            resultSet.close();

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            //디비 객체 연결 해제
            this.disConnection();
        }

        return empDTOList;
    }



    //기능들 추가하기
    //emp 전체 페이지 얻기 메서드
    public int empTotalPage(){
        int total=0;
        try{
            //디비 객체 연결
            this.getConnection();

            //쿼리문
            String sql="SELECT CEIL(COUNT(*)/5) FROM emp";
            //쿼리 문장 전송 객체 획득하기.
            preparedStatement=connection.prepareStatement(sql);
            //전송객체 실행하기
            ResultSet resultSet=preparedStatement.executeQuery();

            //실행결과 리절트셋에 할당됨.
            resultSet.next();

            //해당내용 받기
           total= resultSet.getInt(1);
           resultSet.close();


        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            //디비 객체 연결 해제하기.
            disConnection();
        }
        return total;
    }


    //기능 추가
    //InsertServlet에서 직위목록 가져오기 함수
    public List<String> empGetJob(){
        //해당 목록 담아둘 컬렉션 생성
        List<String> jobList=new ArrayList<>();

        try{
            getConnection();
            //직위목록 가져오는 쿼리문 선언
            String sql="SELECT DISTINCT job FROM emp";
            //해당 스트링을 스트림 객체로 보내기
            preparedStatement=connection.prepareStatement(sql);
            //디비에 쿼리문 실행
            ResultSet resultSet=preparedStatement.executeQuery();

            //이제 리절트셋에  얻어온 직위 리스트를 출력함.
            while(resultSet.next()){
                jobList.add(resultSet.getString(1));
            }
            resultSet.close();
        }catch (Exception ex){
            System.out.println("empGetJob() : " +ex.getMessage());
        }finally {
            disConnection();
        }
        return jobList;
    }

    //기능 추가
    //디비에서 사수 리스트 얻기
    public List<Integer> empGetMgr(){
        //컬렉션으로 디비로부터 사수직원 사원번호 리스트 얻기.
        List<Integer> mgrList=new ArrayList<>();

        try{
            getConnection();
            //사수직원번호 쿼리문 선언
            String sql="SELECT DISTINCT mgr FROM emp WHERE mgr IS NOT NULL";
            //해당 쿼리문 스트림 객체통해 보내기
            preparedStatement=connection.prepareStatement(sql);
            //해당 쿼리문 실행하기
            ResultSet resultSet=preparedStatement.executeQuery();
            //실행된 사수직원번호를 컬랙션에 추가하기
            while(resultSet.next()){
                mgrList.add(resultSet.getInt(1));
            }
            resultSet.close();
        }catch (Exception ex){
            System.out.println("empGetMgr() : " +ex.getMessage());
        }finally {
            disConnection();
        }
        return mgrList;
    }


    /*

         EMPNO
         ENAME
         JOB
         MGR
         HIREDATE
         SAL
         COMM
         DEPTNO
     */
    //emp 테이블에 사원 등록하기 함수
    public void empInsert(EmpDTO empDTO){
        try{
            getConnection();
            //테이블에 사원데이터 등록 즉 입력하는 쿼리문 선언
            String sql="INSERT INTO emp VALUES("
                        +"(SELECT MAX(empno)+1 FROM emp),"
                        +"?,?,?,SYSDATE,?,?,?)";

/*            String sql="INSERT INTO emp (EMPNO, ENAME) VALUES("
                    +"(SELECT MAX(empno)+1 FROM emp),"
                    +"?)";*/
            //해당 쿼리문 스트림 객체로 보내기 위한 객체 획득.
            preparedStatement=connection.prepareStatement(sql);
            //해당 스트림 객체에 입력하는 각 값들을 각각의 필드에 설정하기
            //디비 필드 타입에 맞게 최종 셋팅함.
            preparedStatement.setString(1, empDTO.getEname());
            preparedStatement.setString(2, empDTO.getJob());
            preparedStatement.setInt(3, empDTO.getMgr());
            preparedStatement.setInt(4, empDTO.getSal());
            preparedStatement.setInt(5, empDTO.getComm());
            preparedStatement.setInt(6, empDTO.getDeptno());


            //해당 쿼리문 실행하기
            preparedStatement.executeUpdate();


        }catch (Exception ex){
            System.out.println("empInsert() : " +ex.getMessage());
        }finally {
            disConnection();
        }
    }

















}
















































