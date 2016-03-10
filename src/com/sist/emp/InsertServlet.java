package com.sist.emp;

import com.sist.dao.EmpDAO;
import com.sist.dao.EmpDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by cjw on 2016-03-09.
 */
public class InsertServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //응답타입 결정 : text/html, text/xml
        resp.setContentType("text/html;charset=EUC-KR");

        //사원등록 부분에서 필요한 데이터 db에서 받아오기.
        //DAO 객체 우선 생성
        EmpDAO empDAO=new EmpDAO();
        List<String> jobList=empDAO.empGetJob();        //직위목록 가져오기
        // 사수직원번호 가져오기
        List<Integer> mgrList=empDAO.empGetMgr();


        PrintWriter printWriter=resp.getWriter();

        //스트림 객체에 내용출력하기
        printWriter.println("<html>");
        printWriter.println("<body>");
        printWriter.println("<center>");
        printWriter.println("<h3>사원 등록하기</h3>");

        //이벤트 처리위해 폼추가하고 action,method 속성주기.
        printWriter.println("<form action=InsertServlet method=POST>");
            //테이블 생성하기
            printWriter.println("<table border=1 bordercolor=blue width=300 cellspacing=0>");
                printWriter.println("<tr>");
                    printWriter.println("<td width=25% align=right>성함</td>");
                    printWriter.println("<td width=75% align=left>");
                        printWriter.println("<input type=text size=12 name=ename>");
                    printWriter.println("</td>");
                printWriter.println("</tr>");




                //두번째 줄 -직위 추가
                printWriter.println("<tr>");
                    printWriter.println("<td width=25% align=right>직위</td>");
                    printWriter.println("<td width=75% align=left>");
                        printWriter.println("<select name=job>");
                            for(String jobStr : jobList){
                                printWriter.println("<option>");
                                printWriter.println(jobStr);
                                printWriter.println("</option>");
                            }

                        printWriter.println("</select>");
                    printWriter.println("</td>");
                printWriter.println("</tr>");



                //세번째 줄 -내선임 추가  (직원등록 페이지)
                printWriter.println("<tr>");
                    printWriter.println("<td width=25% align=right>내선임</td>");
                    printWriter.println("<td width=75% align=left>");
                        printWriter.println("<select name=mgr>");
                            for(int mgr : mgrList){
                                printWriter.println("<option>");
                                printWriter.println(mgr);
                                printWriter.println("</option>");
                            }
                        printWriter.println("</select>");
                    printWriter.println("</td>");
                printWriter.println("<tr>");



                //네번째 줄 -연봉 추가 (직원등록 페이지)
                printWriter.println("<tr>");
                    printWriter.println("<td width=25% align=right>연봉</td>");
                    printWriter.println("<td width=75% align=left>");
                        printWriter.println("<select name=sal>");
                            printWriter.println("<option>1000</option>");
                            printWriter.println("<option>2000</option>");
                            printWriter.println("<option>3000</option>");
                            printWriter.println("<option>4000</option>");
                        printWriter.println("</select>");
                    printWriter.println("<td>");
                printWriter.println("</tr>");




                //다섯번째 줄-성과급 추가(직원등록 페이지)
                printWriter.println("<tr>");
                    printWriter.println("<td width=25% align=right>성과급</td>");
                    printWriter.println("<td width=75% align=left>");
                        printWriter.println("<select name=comm>");
                            printWriter.println("<option>0</option>");
                            printWriter.println("<option>100</option>");
                            printWriter.println("<option>200</option>");
                            printWriter.println("<option>300</option>");
                        printWriter.println("</select>");
                    printWriter.println("</td>");
                printWriter.println("</tr>");


                //여섯번째 줄-부서리스트 추가(직원등록 페이지)
                printWriter.println("<tr>");
                    printWriter.println("<td width=25% align=right>부서</td>");
                    printWriter.println("<td width=75% align=left>");
                        printWriter.println("<select name=deptno>");
                            printWriter.println("<option>10</option>");
                            printWriter.println("<option>20</option>");
                            printWriter.println("<option>30</option>");
                            printWriter.println("<option>40</option>");
                        printWriter.println("</select>");
                    printWriter.println("<td>");
                printWriter.println("<tr>");



                //등록, 취소 버튼 추가하기
                printWriter.println("<tr>");
                    printWriter.println("<td colspan=2 align=center>");
                        printWriter.println("<input type=submit value=등록>");
                        printWriter.println("<input type=button value=취소 onclick=\"javascript:history.back() \"  >");
                    printWriter.println("</td>");
                printWriter.println("</tr>");

           printWriter.println("</table>");



        printWriter.println("</form>");



        printWriter.println("<center>");
        printWriter.println("</body>");
        printWriter.println("</html>");
    }


    //등록페이지에서 등록 이벤트 선택시 일어남.
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //사원 등록하기에서 등록되는 데이터 자체에 대한 설정
        req.setCharacterEncoding("EUC-KR");
        //테이블에서 입력되는 각 데이터에 대한 값을 저장하는 각 변수선언.
        //전부 String 타입 변수로 입력하는 사원정보를 받아줌.
        String ename=req.getParameter("ename");
        String job=req.getParameter("job");
        String mgr=req.getParameter("mgr");
        String sal=req.getParameter("sal");
        String comm=req.getParameter("comm");
        String deptno=req.getParameter("deptno");

        //입력된 값을 저장한 각 변수 콘솔출력
        System.out.println(ename);

        //위에 각 컬럼당 임시 저장된 데이터를 AO객체로 셋팅해줌.
        //우선 해당 AO 객체 생성
        EmpDTO empDTO=new EmpDTO();
        //해당 AO의 각 필드 항목을 입력된 값으로 셋팅하기
        empDTO.setEname(ename);
        empDTO.setJob(job);
        empDTO.setMgr(Integer.parseInt(mgr));
        empDTO.setSal(Integer.parseInt(sal));
        empDTO.setComm(Integer.parseInt(comm));
        empDTO.setDeptno(Integer.parseInt(deptno));


        //각 필드에 셋팅된 AO를 dao기능의 insert 함수호출하여 실제 디비에 데이터 입력하기
        //우선 dao 객체 생성
        EmpDAO empDAO=new EmpDAO();
        empDAO.empInsert(empDTO);

        //디비에 insert되고 나서 자동으로 페이지 이동하기
        resp.sendRedirect("EmpListServlet");
    }
}








































































