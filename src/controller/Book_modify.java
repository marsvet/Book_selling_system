package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import models.JDBCDao;

/**
 * Servlet implementation class Book_modify
 */
@WebServlet("/Book_modify")
public class Book_modify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Book_modify() {
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

		String option = request.getParameter("option");
		String ISBN = request.getParameter("ISBN");

		PrintWriter writer = response.getWriter();
		JDBCDao jdbcDao = new JDBCDao();

		switch (option) {
		case "1":
		case "2":
			int quantity = Integer.valueOf(request.getParameter("quantity"));
			String booksJsonString = null;
			try {
				booksJsonString = jdbcDao.search_books(new String[] { "INVENTORY" }, "ISBN", ISBN);
			} catch (ClassNotFoundException | SQLException e) {
				writer.write("0");
				writer.close();
				return;
			}
			JSONArray booksJsonArray = new JSONArray(booksJsonString);
			JSONObject booksJsonObject = booksJsonArray.getJSONObject(0);
			int inventory = Integer.valueOf(booksJsonObject.getString("INVENTORY"));
			try {
				jdbcDao.update_books(new String[] { "INVENTORY" },
						new String[] { String.valueOf(inventory + quantity) }, "ISBN", ISBN);
			} catch (ClassNotFoundException | SQLException e) {
				writer.write("0");
				writer.close();
				return;
			}
			break;
		case "3":
			String new_ISBN = request.getParameter("new_ISBN");
			String title = request.getParameter("title");
			String author = request.getParameter("author");
			String publisher = request.getParameter("publisher");

			String publisherJsonString = null;
			try {
				publisherJsonString = jdbcDao.search_publisher(new String[] { "PID" }, "PNAME", publisher);
			} catch (ClassNotFoundException | SQLException e) {
				writer.write("0");
				writer.close();
				return;
			}
			JSONArray publisherJsonArray = new JSONArray(publisherJsonString);
			JSONObject publisherJsonObject = publisherJsonArray.getJSONObject(0);
			String publisher_id = publisherJsonObject.getString("PID");
			try {
				jdbcDao.update_books(new String[] { "ISBN", "TITLE", "AUTHOR", "PUBLISHER_ID" },
						new String[] { new_ISBN, title, author, publisher_id }, "ISBN", ISBN);
			} catch (ClassNotFoundException | SQLException e) {
				writer.write("0");
				writer.close();
				return;
			}
			break;
		}

		writer.write("1");
		writer.close();
	}

}