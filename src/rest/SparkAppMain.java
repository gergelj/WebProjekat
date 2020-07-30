package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;

import java.io.File;
import java.security.Key;
import java.util.Date;

import com.google.gson.Gson;

import beans.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import spark.Session;
import ws.WsHandler;

public class SparkAppMain {

	private static Gson g = new Gson();

	/**
	 * Ključ za potpisivanje JWT tokena.
	 * Biblioteka: https://github.com/jwtk/jjwt
	 */
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static void main(String[] args) throws Exception {
		/*
		port(8080);

		webSocket("/ws", WsHandler.class);

		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		get("/rest/demo/test", (req, res) -> {
			return "Works";
		});
		
		get("/rest/demo/book/:isbn", (req, res) -> {
			String isbn = req.params("isbn");
			return "/rest/demo/book received PathParam 'isbn': " + isbn;
		});

		get("/rest/demo/books", (req, res) -> {
			String num = req.queryParams("num");
			return "/rest/demo/book received QueryParam 'num': " + num;
		});
		
		get("/rest/demo/testheader", (req, res) -> {
			String cookie = req.headers("Cookie");
			return "/rest/demo/testheader received HeaderParam 'Cookie': " + cookie;
		});
		
		get("/rest/demo/testcookie", (req, res) -> {
			String cookie = req.cookie("pera");
			if (cookie == null) {
				res.cookie("pera", "Perin kolacic");
				return "/rest/demo/testcookie <b>created</b> CookieParam 'pera': 'Perin kolacic'";  
			} else {
				return "/rest/demo/testcookie <i><u>received</u></i> CookieParam 'pera': " + cookie;
			}
		});

		post("/rest/demo/forma", (req, res) -> {
			res.type("application/json");
			String ime = req.queryParams("ime");
			String prezime = req.queryParams("prezime");
			Student s = new Student(ime, prezime, null);
			return g.toJson(s);
		});

		post("/rest/demo/testjson", (req, res) -> {
			res.type("application/json");
			String payload = req.body();
			Student s = g.fromJson(payload, Student.class);
			s.setIme(s.getIme() + "2");
			s.setPrezime(s.getPrezime() + "2");
			return g.toJson(s);
		});

		post("/rest/demo/login", (req, res) -> {
			res.type("application/json");
			String payload = req.body();
			User u = g.fromJson(payload, User.class);
			Session ss = req.session(true);
			User user = ss.attribute("user");
			if (user == null) {
				user = u;
				ss.attribute("user", user);
			}
			return g.toJson(user);
		});

		get("/rest/demo/testlogin", (req, res) -> {
			Session ss = req.session(true);
			User user = ss.attribute("user");
			
			if (user == null) {
				return "No user logged in.";  
			} else {
				return "User " + user + " logged in.";
			}
		});

		get("/rest/demo/logout", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			User user = ss.attribute("user");
			
			if (user != null) {
				ss.invalidate();
			}
			return true;
		});
		
		post("/rest/demo/loginJWT", (req, res) -> {
			res.type("application/json");
			String payload = req.body();
			User u = g.fromJson(payload, User.class);
			// Token je validan 10 sekundi!
			String jws = Jwts.builder().setSubject(u.getUsername()).setExpiration(new Date(new Date().getTime() + 1000*10L)).setIssuedAt(new Date()).signWith(key).compact();
			u.setJWTToken(jws);
			System.out.println("Returned JWT: " + jws);
			return g.toJson(u);
		});

		get("/rest/demo/testloginJWT", (req, res) -> {
			String auth = req.headers("Authorization");
			System.out.println("Authorization: " + auth);
			if ((auth != null) && (auth.contains("Bearer "))) {
				String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
				try {
				    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
				    // ako nije bacio izuzetak, onda je OK
					return "User " + claims.getBody().getSubject() + " logged in.";
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			return "No user logged in.";
		});
		*/

	}
}
