package login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {

			UserBean user = new UserBean();
			user.setUserName(request.getParameter("loginUsername"));
			user.setPassword(request.getParameter("loginPassword"));
			
			System.out.println(">> User '"+user.getUsername() +"' is trying to log in");
			
			user = UserDAO.login(user);

			
			if (user.isValid()) {

				request.setAttribute("user", user);
				request.getRequestDispatcher("lobby").forward(request, response);
				//response.sendRedirect("lobby.jsp"); // logged-in page
			}

			else
				System.out.println(">> Login of user "+user.getUsername() +" failed");
				response.sendRedirect("loginFail.jsp"); // error page
			
		}

		catch (Throwable theException) {
			System.out.println(theException);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
