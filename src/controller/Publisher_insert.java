package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.PublisherDao;

/**
 * Servlet implementation class Publisher_insert
 */
@WebServlet("/Publisher_insert")
public class Publisher_insert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Publisher_insert() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");

		String pname = request.getParameter("pname");
		String plocation = request.getParameter("plocation");

		PrintWriter writer = response.getWriter();
		PublisherDao publisherDao = new PublisherDao();

		try {
			publisherDao.insert_into_publisher(pname, plocation);
		} catch (ClassNotFoundException | SQLException e) {
			writer.write("{\"message\":\"系统内部错误\"}");
			writer.close();
			return;
		}

		writer.write("{\"message\":\"success\"}");
		writer.close();
	}

}
