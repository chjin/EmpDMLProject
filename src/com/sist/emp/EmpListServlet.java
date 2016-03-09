package com.sist.emp;

import com.sist.dao.EmpDAO;
import com.sist.dao.EmpDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by cjw on 2016-03-09.
 */
public class EmpListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //응답 타입 결정 : text/html,  text/xml
        resp.setContentType("text/html;charset=EUC-KR");
        //HTML ==> 요청한 사람에 전송
        //TCP ==> Stream
        int curpage=0;
        int totalpage=0;

        // EmpListServlet?page=2&type=1
        String strPage=req.getParameter("page");

        //첫페이지 ==> 디폴트
        if(strPage==null)
            strPage="1";

        //데이터 출력 메소드에 넘겨줄 페이지 파라미터임.
        curpage=Integer.parseInt(strPage);

        //getOutputStream 에 쓰기
        PrintWriter printWriter=resp.getWriter();
        printWriter.println("<html>");
        printWriter.println("<body>");
        printWriter.println("<center>");
        printWriter.println("<h3>사원들 목록</h3>");
        printWriter.println("<table border=0 width=500>");
            printWriter.println("<tr>");
                printWriter.println("<td align=left>");
                    printWriter.println("<a href=InsertServlet>등록</a>");
                printWriter.println("</td>");
            printWriter.println("</tr>");
        printWriter.println("</table>");

        //두번째 테이블
        printWriter.println("<table border=0 width=500 cellspacing=0>");
        printWriter.println("<tr bgcolor=#ccccff>");
            printWriter.println("<th>사번</th>");
            printWriter.println("<th>이름</th>");
            printWriter.println("<th>직위</th>");
            printWriter.println("<th>입사일</th>");
            printWriter.println("<th>부서번호</th>");
        printWriter.println("</tr>");
        ///이 두번째 테이블에 실 데이터 출력하기
        EmpDAO empDAO=new EmpDAO();
        List<EmpDTO> empDTOList=empDAO.empAllData(curpage);
        totalpage=empDAO.empTotalPage();
        //해당 내용 출력
        for(EmpDTO empDTO : empDTOList){
            printWriter.println("<tr>");
                printWriter.println("<td align=center>" +empDTO.getEmpno()+"</td>");
                printWriter.println("<td align=center>" +empDTO.getEname()+ "</td>");
                printWriter.println("<td align=center>" +empDTO.getJob()+ "</td>");
                printWriter.println("<td align=center>" +empDTO.getHiredate()+ "</td>");
                printWriter.println("<td align=center>" +empDTO.getDeptno()+ "</td>");
            printWriter.println("</tr>");
        }



        printWriter.println("</table>");















        printWriter.println("</center>");
        printWriter.println("</body>");
        printWriter.println("</html>");
    }
}











































