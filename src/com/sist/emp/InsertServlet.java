package com.sist.emp;

import com.sist.dao.EmpDAO;

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
        List<String> jobList=empDAO.empGetJob();

        PrintWriter printWriter=resp.getWriter();

        //스트림 객체에 내용출력하기
        printWriter.println("<html>");
        printWriter.println("<body>");
        printWriter.println("<center>");
        printWriter.println("<h3>사원 등록하기</h3>");

        printWriter.println("<form>");
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
                


           printWriter.println("</table>");



        printWriter.println("</form>");



        printWriter.println("<center>");
        printWriter.println("</body>");
        printWriter.println("</html>");
    }
}








































































