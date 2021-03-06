package UpdateServlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import Data.Repository;
import Entity.JsonResult;
import Entity.User;

@WebServlet(name = "UpdateUserContactsServletName", urlPatterns = "/updateContacts")
public class UpdateUserContactsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Gson gson;
	private Repository repository;

	public void init() throws ServletException {
		repository = Repository.getInstance();
		gson = new Gson();
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");

		StringBuilder sb = new StringBuilder();
		String s;
		while ((s = request.getReader().readLine()) != null) {
			sb.append(s);
		}

		User newUser = (User) gson.fromJson(sb.toString(), User.class);

		JsonResult result = new JsonResult();

		User user = repository.getById(newUser.getId()).updateUserContacts(newUser);


		if (user != null) {
			result.setMessage("Записа се ");
		} else {
			result.setMessage("Не се записа");
			HttpSession oldSession = request.getSession(false);
			User sessionUser = (oldSession != null) ? (User) oldSession.getAttribute("user") : null;
			result.setUser(repository.getById(sessionUser.getId()));

		}

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(gson.toJson(result));
		out.flush();
	}

}
