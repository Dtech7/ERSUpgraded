import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import services.Service;
import user.User;
import user.Ticket;

public class Controller {
	
	private Service serv;
	private ObjectMapper objMap;
	
	
	public Controller(Service s) {
		this.serv = s;
		objMap = new ObjectMapper();
	}
/*----------------------------------------------------------------------------*/	
	//User handlers
	public Handler handleRegister = (ctx) -> {
		
		User newUser = objMap.readValue(ctx.body(), User.class);
		//User userCheck = serv.getUserByEmail(newUser.getEmail());
		//if(userCheck == null) {
			serv.register(newUser);
			ctx.status(201);
			ctx.result(objMap.writeValueAsString(newUser));	
		//}else {
			//ctx.status(400);
			//ctx.result("User Already Exist!");	
		//}

	};
	
	public Handler handleFired = (ctx) -> {
		@SuppressWarnings("unchecked")
		Map<String, String> body = objMap.readValue(ctx.body(), LinkedHashMap.class);
		String email = (String)ctx.req().getSession().getAttribute("user");
		String toFire = body.get("userName");
		serv.fired(email, toFire);
	};
	
	public Handler handleGetAllUsers = (ctx) ->{
		String email = (String)ctx.req().getSession().getAttribute("user");
		List<User> ulist = serv.getAllUsers(email);
		ctx.status(200);
		ctx.result(objMap.writeValueAsString(ulist));
	};
	
	public Handler handleLogin = (ctx) ->{
		
		@SuppressWarnings("unchecked")
		Map<String, String> body = objMap.readValue(ctx.body(), LinkedHashMap.class);
		User loggIn = serv.logIn(body.get("userName"), body.get("password"));
		if(loggIn == null) {
			ctx.status(401);
			ctx.result("User: " + body.get("userName")
			+ " failed to log in.");
			return;
		}
		
		ctx.req().getSession().setAttribute("user", loggIn.getEmail());
		ctx.status(200);
		ctx.result("User: " + loggIn.getEmail()+ " has successfully loged inn");
	};
	
	public Handler handleLogout = (ctx) ->{
		ctx.req().getSession().invalidate();
		ctx.status(200);
		ctx.result("Successfully logged out.");
	};
	
	
/*-----------------------------------------------------------------------------*/
	//Tickets handlers
	public Handler hadleCreate = (ctx) -> {
		Ticket t = objMap.readValue(ctx.body(), Ticket.class);
		String email = (String)ctx.req().getSession().getAttribute("user");
		User u =serv.getUserByEmail(email);
		Ticket nt = new Ticket(u, t.getInfo(), t.getAmount());
		serv.createTicket(nt);
		ctx.status(201);
		ctx.result("New ticket sent for manager approval.");
	};
	
	public Handler handleReview = (ctx) ->{
		
		@SuppressWarnings("unchecked")
		Map<String, Integer> body = objMap.readValue(ctx.body(), LinkedHashMap.class);
		int newStat = body.get("status");
		int tId = body.get("ticket_id");
		Ticket t = serv.getTicket(tId);
		
		String email = (String)ctx.req().getSession().getAttribute("user");
		User u =serv.getUserByEmail(email);
		
		serv.reviewTicket(u, newStat, tId);
		ctx.status(200);
		ctx.result("Ticket status changed user: " + t.getEmail()+ " will be notified.");
	};
	
	public Handler handleGetAllTickets = (ctx) ->{
		String email = (String)ctx.req().getSession().getAttribute("user");
		User u = serv.getUserByEmail(email);
		List<Ticket> tlist = serv.getAllTickets(u);
		//String s = new String();
		//s = "{ '1' : 'Pending', '2' : 'Approved', '3' : 'Denied' }";
		//ctx.result(objMap.writeValueAsString(s));
		ctx.status(200);
		ctx.result(objMap.writeValueAsString(tlist));
	};
}
