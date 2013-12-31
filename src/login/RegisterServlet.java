package login;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {

			UserBean user = new UserBean();
			
			String password = request.getParameter("regPassword");
			String username = request.getParameter("regUser");
			String email = request.getParameter("regEmail");
			String id = UUID.randomUUID().toString();
			
			user.setUserName(username);
			user.setPassword(password);
			user.setEmail(email);
			user.setId(id);
			
			System.out.println(username+password+email);
			
			user = UserDAO.register(user);

			if (user.isValid()) {
				request.getSession().setAttribute("message", "Sie wurden erfolgreich registriert!");
				response.sendRedirect("indexMsg.jsp");
			}

			else
				request.getSession().setAttribute("message", "Bei der Registrierung ist ein Fehler aufgetreten!");
				response.sendRedirect("indexMsg.jsp");
		}

		catch (Throwable theException) {
			System.out.println(theException);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
