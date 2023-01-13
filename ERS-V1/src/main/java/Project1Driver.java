import io.javalin.Javalin;

import dao.TicketDao;
import dao.TicketDaoJDBC;
import dao.UserDao;
import dao.UserDaoJDBC;
import services.Service;

import exceptions.UserAlreadyExistException;
import exceptions.UserDoesNotExistException;
import exceptions.BadTicketException;
import exceptions.UnauthorizedException;
public class Project1Driver {

	public static void main(String[] args) {
		
		UserDao uDao = new UserDaoJDBC();
		TicketDao tDao = new TicketDaoJDBC();
		Service serv = new Service(uDao, tDao);
		Controller cntr = new Controller(serv);
		
		//Javelin set-up
		Javalin app = Javalin.create(config -> {config.plugins.enableCors(cors -> {cors.add(it -> {it.anyHost();});});});
		
		//Actions
		//for user
		app.post("/user/register", cntr.handleRegister);
		app.post("/user/login", cntr.handleLogin);
		app.get("/user/logout", cntr.handleLogout);
		app.get("/user/getAllUsers", cntr.handleGetAllUsers);
		app.delete("/user/fire", cntr.handleFired);
		
		//for tickets
		app.post("/ticket", cntr.hadleCreate);
		app.get("/ticket", cntr.handleGetAllTickets);
		app.put("/ticket", cntr.handleReview);
		
		//Exceptions
		app.exception(UserAlreadyExistException.class, (e, ctx)-> {
			ctx.status(409);
			ctx.result("Account already exist.");
		});
		
		app.exception(UserDoesNotExistException.class, (e, ctx)-> {
			ctx.status(401);
			ctx.result("Failed to Log In");
		});
		
		app.exception(BadTicketException.class, (e, ctx)-> {
			ctx.status(400);
			ctx.result("Ticket action failed.");
		});
		
		app.exception(UnauthorizedException.class, (e, ctx)-> {
			ctx.status(401);
			ctx.result("Not Authorized to complete this action!");
		});
		
		
		//start
		app.start(8000);
	}
}
