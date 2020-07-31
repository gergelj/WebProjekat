package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;

import java.io.File;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import beans.Address;
import beans.Amenity;
import beans.Apartment;
import beans.ApartmentType;
import beans.Comment;
import beans.Location;
import beans.Picture;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringJoiner;

import com.google.gson.Gson;

import beans.Apartment;
import beans.DateCollection;
import beans.DateRange;
import beans.Gender;
import beans.Reservation;
import beans.ReservationStatus;
import beans.User;
import beans.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import repository.csv.converter.AmenityCsvConverter;
import repository.csv.converter.ApartmentCsvConverter;
import repository.csv.converter.CommentCsvConverter;
import repository.csv.converter.DateCollectionCsvConverter;
import repository.csv.converter.ReservationCsvConverter;
import repository.csv.converter.UserCsvConverter;

import spark.Session;
import ws.WsHandler;

public class SparkAppMain {

	private static Gson g = new Gson();

	/**
	 * Kljuc za potpisivanje JWT tokena.
	 * Biblioteka: https://github.com/jwtk/jjwt
	 */
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static void main(String[] args) throws Exception {

		/*
		port(8080);

		
		//testUser();
		//testReservation();
		//testDateCollection();
		Long idL = null;
		long id = idL.longValue();
		System.out.println(id);

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
		
		amenityConverterTest();
		apartmentConverterTest();
		commentConverterTest();
	}
	
	private static void commentConverterTest()
	{
		CommentCsvConverter ccnv = new CommentCsvConverter();
		
		User user = new User(15);
		
		Comment commentTest = new Comment(15, "Vas apartamn je potpuno sranje.\nNe zelim nikome da ode tamo vise u zivotu", 1, false, true, user);
		
		String test = ccnv.toCsv(commentTest);
		
		System.out.println("");
		System.out.println(test);
		System.out.println(ccnv.toCsv(ccnv.fromCsv(test)));
		System.out.println(test.equals(ccnv.toCsv(ccnv.fromCsv(test))));
		
	}
	
	private static void amenityConverterTest()
	{
		AmenityCsvConverter acnv = new AmenityCsvConverter();
		
		Amenity testAmen = new Amenity(12, "Klima", false);
		
		String test = acnv.toCsv(testAmen);
		
		System.out.println(test);
		System.out.println(acnv.toCsv(acnv.fromCsv(test)));
		
		System.out.println(test.equals(acnv.toCsv(acnv.fromCsv(test))));
		
	}
	
	private static void apartmentConverterTest()
	{
		ApartmentCsvConverter acnv = new ApartmentCsvConverter();
		
		Location loc = new Location(44.708181,21.604299, new Address("Knez Mihajlova", "bb", "Vinci", "dsada"));
		User host = new User(15);
		List<Picture> pictures = new ArrayList<Picture>();
		pictures.add(new Picture("glavata.jpg"));
		pictures.add(new Picture("majmuncina.jpg"));
		pictures.add(new Picture("liman.jpg"));
		
		List<Amenity> amenities = new ArrayList<Amenity>();
		amenities.add(new Amenity(69));
		amenities.add(new Amenity(15));
		amenities.add(new Amenity(6));
		amenities.add(new Amenity(5));
		amenities.add(new Amenity(59));
		amenities.add(new Amenity(1899));
		amenities.add(new Amenity(1969));
		amenities.add(new Amenity(235));
		amenities.add(new Amenity(1));
		
		List<Comment> comments = new ArrayList<Comment>();
		comments.add(new Comment(55));
		comments.add(new Comment(58));
		comments.add(new Comment(79));
		comments.add(new Comment(11));
		comments.add(new Comment(56));
		
		
		Apartment testApartment =  new Apartment(123, 2, 5, 75.5, false, true, 15, 12, ApartmentType.fullApartment, loc, host, pictures, amenities, comments);
		
		String test = acnv.toCsv(testApartment);
		
		System.out.println("");
		System.out.println(test);
		System.out.println(acnv.toCsv(acnv.fromCsv(test)));
		System.out.println(test.equals(acnv.toCsv(acnv.fromCsv(test))));
	}


	private static void testDateCollection() {
		
		List<DateRange> dates = new ArrayList<DateRange>();
		dates.add(new DateRange(new Date(), new Date()));
		dates.add(new DateRange(new GregorianCalendar(2015,6-1,4).getTime(), new GregorianCalendar(2015,7-1,15).getTime()));
		DateCollection dateCollection = new DateCollection(458, new Apartment(96), false, dates);
		
		DateCollectionCsvConverter converter = new DateCollectionCsvConverter();
		
		String dateCollectionString = converter.toCsv(dateCollection);
		System.out.println(dateCollectionString);
		String dateCollectionString2 = converter.toCsv(converter.fromCsv(dateCollectionString));
		System.out.println(dateCollectionString2);
		
		System.out.println(dateCollectionString.equals(dateCollectionString2));
		
	}

	private static void testReservation() {
		
		Reservation reservation = new Reservation(7845, new Apartment(56), new User(new Long(55)), new Date(), 8, 568.75, "hdsuidskjuib udhsoudn udhwubwkdiuwhkj UINnd eijbe\njwdhwjdnk uidhwbdhwuhbUHUHGUY8wehkj idudu djbdbjhd i.", false, ReservationStatus.accepted);
		
		ReservationCsvConverter converter = new ReservationCsvConverter();
		
		String reservationString = converter.toCsv(reservation);
		System.out.println(reservationString);
		
		String drugiString = converter.toCsv(converter.fromCsv(reservationString));
		
		System.out.println(drugiString.equals(reservationString));
	}

	private static void testUser() {
		
		User user = new User(567, "ushiy73", "rtdyGYUguryw7", "Igor", "Jovin", false, false, Gender.male, UserType.host);
		
		UserCsvConverter converter = new UserCsvConverter();
		
		String userString = converter.toCsv(user);
		System.out.println(userString);
		
		String drugiString = converter.toCsv(converter.fromCsv(userString));
		
		System.out.println(userString.equals(drugiString));
	}
}
