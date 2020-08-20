package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.security.Key;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import beans.Account;
import beans.Address;
import beans.Amenity;
import beans.Apartment;
import beans.Comment;
import beans.Location;
import beans.Picture;
import beans.PricingCalendar;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import beans.Apartment;
import beans.DateCollection;
import beans.DateRange;
import beans.Reservation;
import beans.User;
import beans.enums.ApartmentType;
import beans.enums.DateStatus;
import beans.enums.DayOfWeek;
import beans.enums.Gender;
import beans.enums.ReservationStatus;
import beans.enums.UserType;
import dto.ApartmentDTO;
import dto.ApartmentEditDTO;
import dto.ApartmentFilterDTO;
import dto.BookingDatesDTO;
import dto.ErrorMessageDTO;
import dto.ReservationDTO;
import dto.TokenDTO;
import dto.UserDTO;
import dto.UserFilterDTO;
import exceptions.BadRequestException;
import exceptions.DatabaseException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidDateException;
import exceptions.InvalidPasswordException;
import exceptions.InvalidUserException;
import exceptions.NotUniqueException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import repository.AmenityRepository;
import repository.ApartmentRepository;
import repository.CommentRepository;
import repository.DateCollectionRepository;
import repository.ReservationRepository;
import repository.UserRepository;
import repository.csv.converter.AmenityCsvConverter;
import repository.csv.converter.ApartmentCsvConverter;
import repository.csv.converter.CommentCsvConverter;
import repository.csv.converter.DateCollectionCsvConverter;
import repository.csv.converter.PricingCalendarCsvConverter;
import repository.csv.converter.ReservationCsvConverter;
import repository.csv.converter.UserCsvConverter;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;
import service.UserService;
import spark.Request;
import spark.Session;
import spark.utils.IOUtils;
import specification.filterconverter.UserFilterConverter;
import utils.AppResources;
import ws.WsHandler;

public class SparkAppMain {

	private static Gson g;

	/**
	 * Kljuc za potpisivanje JWT tokena.
	 * Biblioteka: https://github.com/jwtk/jjwt
	 */
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	static AppResources resources;
	static UserCsvConverter userConverter = new UserCsvConverter();
	private static int minutesUntilTokenExpires = 60;

	public static void main(String[] args) throws IOException, DatabaseException {
		
		g = getGson();
		
		try {
			resources = new AppResources();
		} catch (DatabaseException e1) {
			e1.printStackTrace();
			System.out.println("Server resources failed to load");
			return;
		}
		
		
		port(8088);

		webSocket("/ws", WsHandler.class);

		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		get("/rest/vazduhbnb/test", (req, res) -> {
			return "Works";
		});
		
		
		post("/rest/vazduhbnb/login", (req, res) -> {
			//TODO: obavezno detaljnije pogledati
			
			res.type("application/json");
			String payload = req.body();
			UserDTO u = g.fromJson(payload, UserDTO.class);
			
			User loggedInUser = null;
			
			try{
				loggedInUser = resources.userService.login(u);
				
				String jws = getJwtToken(loggedInUser);
				
				System.out.println("User " + loggedInUser.getName() + " " + loggedInUser.getSurname() + " (" + loggedInUser.getUserType() + ") logged in.");
				System.out.println("Returned JWT: " + jws);
				
				return g.toJson(new TokenDTO(jws, loggedInUser.getAccount().getUsername(), loggedInUser.getUserType()), TokenDTO.class);
				
				//TODO: povratna vrednost kod exception-a
			}catch(InvalidUserException ex) {
				res.status(403); //forbidden (blocked user)
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}catch(BadRequestException ex) {
				res.status(400);
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}catch(EntityNotFoundException e) {
				res.status(404);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			} catch(DatabaseException ex) {
				res.status(500);
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}
			
		});
				
		
		
		post("/rest/vazduhbnb/register", (request, response) -> {
			
			response.type("application/json");
			String payload = request.body();
			
			UserDTO u = g.fromJson(payload, UserDTO.class);
			
			try {
				User registeredUser = resources.userService.register(u);
				response.status(200); // OK
				String jws = getJwtToken(registeredUser);
				System.out.println("Registered user: " + registeredUser.getAccount().getUsername() + " with JWTToken: " + jws);
				
				return g.toJson(new TokenDTO(jws, registeredUser.getAccount().getUsername(), registeredUser.getUserType()), TokenDTO.class);
				
			} catch(NotUniqueException ex) {
				response.status(409); // username is not unique
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			} catch(BadRequestException ex) {
				response.status(400); // data is invalid
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			} catch (DatabaseException ex) {
				response.status(500); // server-side error
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		get("/rest/vazduhbnb/apartments", (req, res) -> {
			res.type("application/json");
			
			System.out.println(req.body());
			
			String payload = req.queryParams("filter");
			ApartmentFilterDTO filter = g.fromJson(payload, ApartmentFilterDTO.class);
			User loggedInUser = getLoggedInUser(req);
			
			try {
				List<Apartment> apartments = resources.apartmentService.find(filter, loggedInUser);
				
				res.status(200);
				return g.toJson(apartments);
			}catch(BadRequestException e) {
				res.status(400);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			}catch(DatabaseException e) {
				res.status(500);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			}

		});
		
		get("/rest/vazduhbnb/filteredUsers", (request, response) -> {
			response.type("application/json");
			
			String payload = request.queryParams("filter");
			UserFilterDTO filter = g.fromJson(payload, UserFilterDTO.class);
			User loggedinUser = getLoggedInUser(request);
			
			try
			{
				List<User> users = resources.userService.find(filter, loggedinUser);
				
				return g.toJson(users);
			}
			catch(DatabaseException ex)
			{
				response.status(500);
				return g.toJson(new ErrorMessageDTO("Internal Server Error"), ErrorMessageDTO.class);
			}
			catch(InvalidUserException ex)
			{
				response.status(403);
				return g.toJson(new ErrorMessageDTO("User doesn't have permission."), ErrorMessageDTO.class);
			}
		});
		
		post("/rest/vazduhbnb/reservation", (req, res) -> {
			User loggedInUser = getLoggedInUser(req);
			if(loggedInUser == null) {
				res.status(401);
				return g.toJson(new ErrorMessageDTO("Unauthorized access."), ErrorMessageDTO.class);
			}
			
			ReservationDTO reservation = g.fromJson(req.body(), ReservationDTO.class);
			
			try {
				resources.reservationService.create(reservation, loggedInUser);
				res.status(200);
				return "OK";
			}catch(InvalidUserException e) {
				res.status(403);
				return g.toJson(new ErrorMessageDTO("Access denied."), ErrorMessageDTO.class);
			}catch(BadRequestException e) {
				res.status(400);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			}catch(DatabaseException e) {
				res.status(500);
				return g.toJson(new ErrorMessageDTO("Internal Server Error."), ErrorMessageDTO.class);
			}
		});
		
		post("/rest/vazduhbnb/apartment", (request, response) -> {
			response.type("application/json");
			
			User loggedInUser = getLoggedInUser(request);
			if(loggedInUser == null) {
				response.status(401);
				return g.toJson(new ErrorMessageDTO("Unauthorized access."), ErrorMessageDTO.class);
			}
			
			String payload = request.body();
			ApartmentDTO apartment = g.fromJson(payload, ApartmentDTO.class);
			
			try{
				resources.apartmentService.create(apartment, loggedInUser);
				response.status(200);
				return "OK";
			}catch(DatabaseException e){
				response.status(500);
				return g.toJson(new ErrorMessageDTO("Internal Server Error"), ErrorMessageDTO.class);
			}catch(InvalidUserException e) {
				response.status(403);
				return g.toJson(new ErrorMessageDTO("User doesn't have permission."), ErrorMessageDTO.class);
			}catch(BadRequestException e) {
				response.status(400);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		put("/rest/vazduhbnb/apartment", (req, res) -> {
			res.type("application/json");
			
			User loggedInUser = getLoggedInUser(req);
			if(loggedInUser == null) {
				res.status(401);
				return g.toJson(new ErrorMessageDTO("User not logged in."), ErrorMessageDTO.class);
			}
			
			ApartmentEditDTO apartmentEdit = g.fromJson(req.body(), ApartmentEditDTO.class);
			
			try {				
				resources.apartmentService.update(apartmentEdit.getApartment(), apartmentEdit.getDates(), loggedInUser);
				res.status(200);
				return "OK";
			}catch(InvalidUserException e) {
				res.status(403);
				return g.toJson(new ErrorMessageDTO("User doesn't have permission."), ErrorMessageDTO.class);
			}catch(BadRequestException e) {
				res.status(400);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			}catch(DatabaseException e) {
				res.status(500);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		put("/rest/vazduhbnb/approve", (req, res) ->{
			res.type("application/json");
			
			Comment comment = g.fromJson(req.body(), Comment.class);
			boolean toApprove = Boolean.valueOf(req.queryParams("approve"));
			long apartmentId = Long.valueOf(req.queryParams("apartment"));
			
			User loggedInUser = getLoggedInUser(req);
			
			if(loggedInUser == null) {
				res.status(401);
				return g.toJson(new ErrorMessageDTO("User not logged in."), ErrorMessageDTO.class);
			}
			
			try {
				
				resources.commentService.approveComment(comment, toApprove, loggedInUser, apartmentId);
				res.status(200);
				return toApprove ? "approved" : "disapproved";
				
			}catch(InvalidUserException e) {
				res.status(403);
				return g.toJson(new ErrorMessageDTO("Access denied."), ErrorMessageDTO.class);
			}catch(DatabaseException e) {
				res.status(500);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		put("/rest/vazduhbnb/activate", (req, res) ->{
			res.type("application/json");
			
			User loggedInUser = getLoggedInUser(req);
			if(loggedInUser == null) {
				res.status(401);
				return g.toJson(new ErrorMessageDTO("User not logged in."), ErrorMessageDTO.class);
			}
			
			boolean toActivate = Boolean.valueOf(req.queryParams("activate"));		
			Apartment apartment = g.fromJson(req.body(), Apartment.class);
			
			try {				
				resources.apartmentService.activateApartment(apartment, loggedInUser, toActivate);
				res.status(200);
				return "Apartment " + (toActivate ? "activated" : "deactivated") + ".";
			}catch(InvalidUserException e) {
				res.status(403);
				return g.toJson(new ErrorMessageDTO("Access denied."), ErrorMessageDTO.class);
			}catch(DatabaseException e) {
				res.status(500);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		delete("rest/vazduhbnb/apartment", (req, res) ->{
			
			User loggedInUser = getLoggedInUser(req);
			if(loggedInUser == null) {
				res.status(401);
				return g.toJson(new ErrorMessageDTO("User not logged in."), ErrorMessageDTO.class);
			}
			
			String payload = req.body();
			Apartment apartment = g.fromJson(payload, Apartment.class);
			
			try {				
				resources.apartmentService.delete(apartment, loggedInUser);
				res.status(200);
				return "Apartment deleted";
			}catch(InvalidUserException e) {
				res.status(403);
				return g.toJson(new ErrorMessageDTO("Access denied."), ErrorMessageDTO.class);
			}catch(BadRequestException e) {
				res.status(400);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			}catch(DatabaseException e) {
				res.status(500);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			}
			
		});
		
		get("/rest/vazduhbnb/availabledates", (req, res) -> {
			res.type("application/json");
			
			User loggedInUser = getLoggedInUser(req);
			if(loggedInUser == null) {
				res.status(401);
				return g.toJson(new ErrorMessageDTO("User not logged in."), ErrorMessageDTO.class);
			}
			
			long apartmentId = Long.valueOf(req.queryParams("apartment"));
			
			try {
				List<Date> dates = resources.reservationService.getAvailableDatesByApartment(apartmentId, loggedInUser);
				res.status(200);
				return g.toJson(dates);
			}catch(InvalidUserException e) {
				res.status(401);
				return g.toJson(new ErrorMessageDTO("User not logged in."), ErrorMessageDTO.class);
			}catch(DatabaseException e) {
				res.status(500);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		get("/rest/vazduhbnb/unavailabledates", (req, res) -> {
			res.type("application/json");
			
			User loggedInUser = getLoggedInUser(req);
			if(loggedInUser == null) {
				res.status(401);
				return g.toJson(new ErrorMessageDTO("User not logged in."), ErrorMessageDTO.class);
			}
			
			long apartmentId = Long.valueOf(req.queryParams("apartment"));
			
			try {
				List<Date> dates = resources.reservationService.getUnavailableDatesByApartment(apartmentId, loggedInUser);
				res.status(200);
				return g.toJson(dates);
			}catch(InvalidUserException e) {
				res.status(401);
				return g.toJson(new ErrorMessageDTO("User not logged in."), ErrorMessageDTO.class);
			}catch(DatabaseException e) {
				res.status(500);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		get("/rest/vazduhbnb/bookingdatesinfo", (req, res) -> {
			res.type("application/json");
			
			User loggedInUser = getLoggedInUser(req);
			if(loggedInUser == null) {
				res.status(401);
				return g.toJson(new ErrorMessageDTO("User not logged in."), ErrorMessageDTO.class);
			}
			
			long apartmentId = Long.valueOf(req.queryParams("apartment"));
			
			try {
				BookingDatesDTO dates = resources.reservationService.getBookingDatesInfo(apartmentId, loggedInUser);
				res.status(200);
				return g.toJson(dates, BookingDatesDTO.class);
			}catch(InvalidUserException e) {
				res.status(401);
				return g.toJson(new ErrorMessageDTO("User not logged in."), ErrorMessageDTO.class);
			}catch(DatabaseException e) {
				res.status(500);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		get("/rest/vazduhbnb/totalPrice", (req, res) -> {
			res.type("application/json");
			
			User loggedInUser = getLoggedInUser(req);
			if(loggedInUser == null) {
				res.status(401);
				return g.toJson(new ErrorMessageDTO("User not logged in."), ErrorMessageDTO.class);
			}
			
			long apartmentId = Long.valueOf(req.queryParams("apartment"));
			DateRange dateRange = g.fromJson(req.queryParams("dates"), DateRange.class);
			
			try {
				double totalPrice = resources.reservationService.getTotalPrice(loggedInUser, apartmentId, dateRange);
				res.status(200);
				return totalPrice;
			}catch(InvalidUserException e) {
				res.status(403);
				return g.toJson(new ErrorMessageDTO("Access denied."), ErrorMessageDTO.class);
			}catch(InvalidDateException e) {
				res.status(400);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			}catch(DatabaseException e) {
				res.status(500);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		get("/rest/vazduhbnb/profile", (request, response)->{
			User loggedinUser = getLoggedInUser(request);
			
			if(loggedinUser == null)
			{
				response.status(400);
				return g.toJson(new ErrorMessageDTO("User not logged in."), ErrorMessageDTO.class);
			}
			try{
				
				return g.toJson(resources.userService.getById(loggedinUser.getId()), User.class);
				
				//TODO: povratna vrednost kod exception-a
			} catch(DatabaseException ex) {
				response.status(500);
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}
			
		});
		
		put("/rest/vazduhbnb/updateUser", (request, response)->{
			response.type("application/json");
			String payload = request.body();
			
			UserDTO updatedUser = g.fromJson(payload, UserDTO.class);
			
			try {
				User user = resources.userService.update(updatedUser);
				return g.toJson(resources.userService.getById(user.getId()), User.class);
			}
			catch(DatabaseException ex)
			{
				response.status(500);
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class );
			}
			catch(InvalidPasswordException ex)
			{
				response.status(409);
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}
			catch(BadRequestException ex)
			{
				response.status(400);
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		put("/rest/vazduhbnb/updateAmenityName", (request, response)->{
			response.type("application/json");

			String payload = request.body();
			Amenity updatedAmenity = g.fromJson(payload, Amenity.class);
						
			try
			{
				resources.amenityService.update(updatedAmenity);
				return g.toJson(updatedAmenity);
			}
			catch(DatabaseException ex)
			{
				response.status(500);
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		put("/rest/vazduhbnb/deleteAmenity", (request, response)->{
			response.type("application/json");
			
			String payload = request.body();
			Amenity deletedAmenity = g.fromJson(payload, Amenity.class);
			
			try {
				resources.amenityService.delete(deletedAmenity);
				return g.toJson(deletedAmenity);
			}
			catch(DatabaseException ex)
			{
				response.status(500);
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		post("/rest/vazduhbnb/addNewAmenity", (request, response)->{
			response.type("application/json");
			
			String payload = request.body();
			
			String amenityName = g.fromJson(g.toJson(payload), String.class);
			
			System.out.println(amenityName);
			
			try {
				resources.amenityService.create(new Amenity(amenityName, false));
				return g.toJson(amenityName);
			}
			catch (DatabaseException ex) {
				response.status(500);
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		get("/rest/vazduhbnb/amenities", (request, response)->{
			response.type("application/json");
			
			try
			{
				List<Amenity> amenities = resources.amenityService.getAll();
				return g.toJson(amenities);
			}
			catch(DatabaseException ex)
			{
				response.status(500);
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		get("/rest/vazduhbnb/userType", (request, response)->{
			response.type("application/json");
			
			User loggedinUser = getLoggedInUser(request);
			response.status(200);
			if(loggedinUser != null)
				return g.toJson(loggedinUser.getUserType());
			return g.toJson(UserType.undefined);
		});
		
		get("/rest/vazduhbnb/apartmentsByUsertype", (request, response) -> {
			response.type("application/json");
			
			User loggedinUser = getLoggedInUser(request);
			if(loggedinUser == null)
			{
				response.status(403);
				return g.toJson("Forbidden");
			}
			
			List<Reservation> reservations = new ArrayList<Reservation>();
			List<Apartment> apartments = new ArrayList<Apartment>();
			try {
				if(loggedinUser.getUserType() == UserType.admin)
				{
					reservations = 	resources.reservationService.getAll();
				}
				else if(loggedinUser.getUserType() == UserType.guest)
				{
					reservations = resources.reservationService.getReservationByGuest(loggedinUser);
				}
				else if(loggedinUser.getUserType() == UserType.host)
				{
					reservations = resources.reservationService.getReservationByHost(loggedinUser, loggedinUser.getUserType());
				}
				
				for(Reservation res: reservations)
				{
					if(!apartments.contains(res.getApartment()))
						apartments.add(res.getApartment());
				}
				
				apartments = resources.apartmentService.getAll(UserType.admin);
				
				return g.toJson(apartments);
			}
			catch(DatabaseException ex)
			{
				response.status(500);
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}
			catch(InvalidUserException ex)
			{
				response.status(403);
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}			
		});
		
		get("/rest/vazduhbnb/getAllUsers", (request, response) ->{
			response.type("application/json");
			
			User user = getLoggedInUser(request);
			
			try
			{
				List<User> users = new ArrayList<User>();
				if(user.getUserType() == UserType.admin)
				{
					users = resources.userService.getAll();
				}
				else if(user.getUserType() == UserType.host)
				{
					users = resources.userService.getGuestsByHost(user, user.getUserType());
				}
				return g.toJson(users);
			}
			catch(DatabaseException ex)
			{
				response.status(500);
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		put("/rest/vazduhbnb/reservationsByApartment", (request, response) ->
		{
			response.type("application/json");
			
			String payload = request.body();
			
			Apartment apartment = g.fromJson(payload, Apartment.class);
			User loggedinUser = getLoggedInUser(request);
			
			try
			{
				List<Reservation> reservations = resources.reservationService.getReservationsByApartment(apartment.getId(), loggedinUser);
				return g.toJson(reservations);
			}
			catch(InvalidUserException ex)
			{
				response.status(403);
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}			
		});
		
		put("/rest/vazduhbnb/deleteUser", (request, response) ->{
			response.type("application/json");
			
			String payload = request.body();
			
			long id = g.fromJson(payload, long.class);
			
			if(id < 1)
			{
				response.status(400);
				return g.toJson("Index lower than minimal");
			}
			
			try
			{
				resources.userService.delete(id);
				return g.toJson(id);
			}
			catch(DatabaseException ex)
			{
				response.status(500);
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		put("/rest/vazduhbnb/blockUser", (request, response) ->{
			response.type("application/json");
			
			String payload = request.body();
			
			User user = g.fromJson(payload, User.class);
			
			if(user == null)
			{
				response.status(400);
				return g.toJson("User is null");
			}
			
			try
			{
				resources.userService.blockUser(user);
				return g.toJson(user);
			}
			catch(DatabaseException ex)
			{
				response.status(500);
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		put("/rest/vazduhbnb/unblockUser", (request, response) ->{
			response.type("application/json");
			
			String payload = request.body();
			
			User user = g.fromJson(payload, User.class);
			
			if(user == null)
			{
				response.status(400);
				return g.toJson("User is null");
			}
			
			try
			{
				User unblockedUser = resources.userService.unblockUser(user);
				return g.toJson(unblockedUser);
			}
			catch(DatabaseException ex)
			{
				response.status(500);
				return g.toJson(new ErrorMessageDTO(ex.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		get("/rest/vazduhbnb/pricingCalendar", (req, res) -> {
			res.type("application/json");
			
			User loggedInUser = getLoggedInUser(req);
			if(loggedInUser == null) {
				res.status(401);
				return g.toJson(new ErrorMessageDTO("User not logged in."), ErrorMessageDTO.class);
			}
			
			try {				
				PricingCalendar cal = resources.reservationService.getPricingCalendar(loggedInUser);
				res.status(200);
				return g.toJson(cal, PricingCalendar.class);
			}catch(InvalidUserException e) {
				res.status(403);
				return g.toJson(new ErrorMessageDTO("Access denied."), ErrorMessageDTO.class);
			}catch(DatabaseException e) {				
				res.status(500);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		put("/rest/vazduhbnb/pricingCalendar", (req, res) -> {
			res.type("application/json");
			
			User loggedInUser = getLoggedInUser(req);
			if(loggedInUser == null) {
				res.status(401);
				return g.toJson(new ErrorMessageDTO("User not logged in."), ErrorMessageDTO.class);
			}
			
			PricingCalendar pricingCalendar = g.fromJson(req.body(), PricingCalendar.class);
			
			try {
				
				resources.reservationService.updatePricingCalendar(pricingCalendar, loggedInUser);
				res.status(200);
				return "OK";
			}catch(InvalidUserException e) {
				res.status(403);
				return g.toJson(new ErrorMessageDTO("Access denied."), ErrorMessageDTO.class);
			}catch(DatabaseException e) {				
				res.status(500);
				return g.toJson(new ErrorMessageDTO(e.getMessage()), ErrorMessageDTO.class);
			}
		});
		
		get("/rest/vazduhbnb/getLoggedinUser", (request, response)->{
			response.type("application/json");
			
			User user = getLoggedInUser(request);
			
			return g.toJson(user);
		});
		
		get("/*", (request, response) -> {
			response.status(404);
			response.redirect("/404.html");
			return "";
		});
		
		get("/rest/vazduhbnb/testloginJWT", (req, res) -> {
			//TODO: obavezno detaljnije pogledati
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

	}

	private static Gson getGson() {
		// Creates the json object which will manage the information received 
		GsonBuilder builder = new GsonBuilder(); 

		// Register an adapter to manage the date types as long values 
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() { 
		   public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		      return new Date(json.getAsJsonPrimitive().getAsLong()); 
		   }
		});
		
		builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {

			@Override
			public JsonElement serialize(Date src, Type type, JsonSerializationContext context) {
				return src == null ? null : new JsonPrimitive(src.getTime());
			}
			
		});

		return builder.create();
	}
	
	private static User getLoggedInUser(Request request) {
		String auth = request.headers("Authorization");
		if ((auth != null) && (auth.contains("Bearer "))) {
			String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
			try {
			    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
			    // ako nije bacio izuzetak, onda je OK
				//System.out.println("User " + claims.getBody().getSubject() + " logged in.");
				return userConverter.fromCsv(claims.getBody().getSubject());
			} catch (Exception e) {
				//System.out.println(e.getMessage());
			}
		}
		return null;
	}
	

	private static String getJwtToken(User user) {
		
		JwtBuilder jwtBuilder = Jwts.builder();
		jwtBuilder.setSubject(userConverter.toCsv(user));
		
		// Token je validan 30 minuta!
		jwtBuilder.setExpiration(new Date(new Date().getTime() + 1000*minutesUntilTokenExpires*60L));
		jwtBuilder.setIssuedAt(new Date());
		
		
		return jwtBuilder.signWith(key).compact();
	}
/*
	private static void testRepositories() throws DatabaseException
	{
		//resources.dateCollectionRepository.getAll();
		//amenityRepoTest();
		//apartmentRepoTest();
		//dateCollectionTest();
		//commentRepoTest();
		//reservationRepoTest();
		//userRepoTest();
	}
	*/
	/*
	private static void dateCollectoinRepoTest() {
		DateCollectionRepository res = resources.availableDateCollectionRepository;
		
		//DateCollection dc = new DateCollection(new Apartment(), false, new ArrayList<DateRange>());
		
	}
	*/
	/*
	private static void amenityRepoTest()
	{
		AmenityRepository res = resources.amenityRepository;
		
		//RADE:
		 // - create
		 // - update
		 // - delete
		 // - getById
		 //- getAll
		 
		
		Amenity am1 = new Amenity("Klima", false);
		Amenity am2 = new Amenity("TV", false);
		Amenity am3 = new Amenity("Internet/Wi-Fi", false);
		Amenity am4 = new Amenity("Ves masina", false);
		Amenity am5 = new Amenity("Pegla", false);
		
		
		res.create(am1);
		res.create(am2);
		res.create(am3);
		res.create(am4);
		res.create(am5);
		
		
	}
	*/
	/*
	private static void apartmentRepoTest() throws DatabaseException
	{
		/RADE:
		 // - create
		 // - update
		 // - getById
		 // - delete
		 // - getAll
		 // - getEager		?
		 // - getAllEager	?
		 // - find			?
		 
		
		ApartmentRepository res = resources.apartmentRepository;
		UserRepository userRes = resources.userRepository;
		AmenityRepository amRes = resources.amenityRepository;
		CommentRepository comRes = resources.commentRepository;
		
		ApartmentCsvConverter conv = new ApartmentCsvConverter();
		
		Address add1 = new Address("Mare Ognjanovic", "12", "Novi Sad", "21000");
		Address add2 = new Address("Narnodnog frotna", "48", "Novi Sad", "21000");
		Address add3 = new Address("Blazakova", "28", "Novi Sad", "21000");
		Address add4 = new Address("Pere Popadica", "3/2", "Novi Sad", "21000");
		Address add5 = new Address("Safarikova", "33", "Novi Sad", "21000");
		
		Location loc1 = new Location(45.123456, 19.156936, add1);
		Location loc2 = new Location(45.156988, 19.159874, add2);
		Location loc3 = new Location(45.123456, 19.156936, add3);
		Location loc4 = new Location(45.789455, 19.369636, add4);
		Location loc5 = new Location(45.174896, 19.888855, add5);
		
		Picture pic1 = new Picture("glaata.jpg");
		Picture pic2 = new Picture("dadas.jpg");
		Picture pic3 = new Picture("ewqeqw.jpg");
		Picture pic4 = new Picture("dasdsa.jpg");
		Picture pic5 = new Picture("gffdg.jpg");
		Picture pic6 = new Picture("hfghfg.jpg");
		Picture pic7 = new Picture("kjhhjkhjk.jpg");
		Picture pic8 = new Picture("cxzcxz.jpg");
		Picture pic9 = new Picture("bcvbcvcb.jpg");
		Picture pic0 = new Picture("gmnbmbn.jpg");
		Picture pic11 = new Picture("dsaasdf.jpg");
		
		List<Picture> pictures1 = new ArrayList<Picture>();
		pictures1.add(pic11);
		pictures1.add(pic1);
		pictures1.add(pic2);
		
		List<Picture> pictures2 = new ArrayList<Picture>();
		pictures2.add(pic3);
		pictures2.add(pic4);
		pictures2.add(pic5);
		
		List<Picture> pictures3 = new ArrayList<Picture>();
		pictures3.add(pic6);
		pictures3.add(pic7);
		pictures3.add(pic8);
		
		List<Picture> pictures4 = new ArrayList<Picture>();
		pictures4.add(pic9);
		pictures4.add(pic0);
		pictures4.add(pic11);
		
		List<Picture> pictures5 = new ArrayList<Picture>();
		pictures5.add(pic1);
		pictures5.add(pic2);
		pictures5.add(pic3);
		
		List<Amenity> amenities1 = new ArrayList<Amenity>();
		amenities1.add(amRes.getById(1));
		amenities1.add(amRes.getById(3));
		amenities1.add(amRes.getById(4));
		
		List<Amenity> amenities2 = new ArrayList<Amenity>();
		amenities2.add(amRes.getById(2));
		amenities2.add(amRes.getById(4));
		amenities2.add(amRes.getById(5));
		
		List<Amenity> amenities3 = new ArrayList<Amenity>();
		amenities3.add(amRes.getById(1));
		amenities3.add(amRes.getById(2));
		amenities3.add(amRes.getById(3));
		
		List<Amenity> amenities4 = new ArrayList<Amenity>();
		amenities4.add(amRes.getById(3));
		amenities4.add(amRes.getById(4));
		amenities4.add(amRes.getById(5));
		
		List<Amenity> amenities5 = new ArrayList<Amenity>();
		amenities5.add(amRes.getById(5));
		amenities5.add(amRes.getById(2));
		amenities5.add(amRes.getById(3));
			
		
		List<Comment> comments1 = new ArrayList<Comment>();
		comments1.add(comRes.getById(1));
		comments1.add(comRes.getById(2));
		
		List<Comment> comments2 = new ArrayList<Comment>();
		comments2.add(comRes.getById(3));
		comments2.add(comRes.getById(4));
	
		List<Comment> comments3 = new ArrayList<Comment>();
		comments3.add(comRes.getById(1));
		comments3.add(comRes.getById(3));		
		
		
		Apartment ap1 = new Apartment(2, 10, 150.6, false, true, ApartmentType.fullApartment, loc1, userRes.getById(1), pictures1, amenities1, null);
		Apartment ap2 = new Apartment(1, 3, 150.6, false, true, ApartmentType.fullApartment, loc2, userRes.getById(2), pictures2, null, comments2);
		Apartment ap3 = new Apartment(1, 4, 150.6, false, true, ApartmentType.fullApartment, loc3, userRes.getById(2), null, amenities3, comments3);

					
		
		res.create(ap1);
		res.create(ap2);
		res.create(ap3);
		
		
		//res.delete(4);
		
		/*
		ap5 = res.getById(5);
		ap5.setComments(comments3);
		ap5.setAmenities(amenities2);
		ap5.setPictures(pictures2);
		res.update(ap5);
		*/
		
		/*
		ApartmentFilterDTO filter = new ApartmentFilterDTO("", 0, 11, null, new PriceRange(100, 200));
		List<Apartment> apartments = res.find(ApartmentFilterConverter.getSpecification(filter));
		
		
		*/
		
		/*
		List<Apartment> apartments = res.getAllEager();
		
		for(Apartment ap : apartments) {
			System.out.println(ap.getHost().getName() + " @" + ap.getHost().getAccount().getUsername() + ": " + (ap.getAmenities().isEmpty() ? "" : ap.getAmenities().get(0).getName()) + " > " + (ap.getComments().isEmpty() ? "" : ap.getComments().get(0).getUser().getName()));
			
		}
		
		
		//Apartment ap = res.getEager(2);
		//System.out.println(ap.getHost().getName() + " @" + ap.getHost().getAccount().getUsername() + ": " + (ap.getAmenities().isEmpty() ? "" : ap.getAmenities().get(0).getName()) + " > " + (ap.getComments().isEmpty() ? "" : ap.getComments().get(0).getUser().getName()));

	}
	*/
	/*
	private static void commentRepoTest() throws DatabaseException
	{
		CommentRepository res = resources.commentRepository;
		CommentCsvConverter conv = new CommentCsvConverter();
		UserRepository userRepo = resources.userRepository;
		
		Comment comment1 = new Comment("Vas apartamn je potpuno sranje.\nNe zelim nikome da ode tamo vise u zivotu", 1, false, false, userRepo.getById(8));
		Comment comment2 = new Comment("Najbolji apartman u gradu", 5, false, false, userRepo.getById(8));
		Comment comment3 = new Comment("Ooooo K.", 3, false, false, userRepo.getById(8));
		Comment comment4 = new Comment("Lorem Ipsum bla bla bla bla balab jhsuahbj", 8, false, false, userRepo.getById(8));
		
		
		res.create(comment1);
		res.create(comment2);
		res.create(comment3);
		res.create(comment4);
		
		
		//res.delete(3);
		
		//List<Comment> comments = res.getAll();
		
		//List<Comment> comments = res.getAllEager();
		
		//Comment com = res.getById(2);
		
		//com.setRating(4);
		//com.setText("Па и није нешто посебно");
		//res.update(com);
		
		//Comment comment = res.getEager(2);
		//System.out.println(comment.getUser().getName() + ": " + comment.getText());
		
		/*
		for(Comment comment : comments) {
			System.out.println(comment.getUser().getName() + ": " + comment.getText());
		}
		
		
	}
	*/
	/*
	private static void reservationRepoTest() throws DatabaseException
	{
		//RADE:
		 // - create
		 // - update
		 // - getById
		 // - delete
		 // - getAll
		 // - getAllEager
		 // - getEager
		 
		
		ReservationRepository res = resources.reservationRepository;
		ApartmentRepository apRes = resources.apartmentRepository;
		UserRepository userRes = resources.userRepository;
		
		ReservationCsvConverter conv = new ReservationCsvConverter();
		
		Reservation r1 = new Reservation(apRes.getById(1), userRes.getById(1), new GregorianCalendar(2020, 8-1, 1).getTime(), 5, 800.0, "Poruka1", false, ReservationStatus.accepted);
		Reservation r2 = new Reservation(apRes.getById(2), userRes.getById(2), new GregorianCalendar(2020, 10-1, 12).getTime(), 5, 525.0, "Poruka2", false, ReservationStatus.accepted);
		Reservation r3 = new Reservation(apRes.getById(3), userRes.getById(1), new GregorianCalendar(2020, 5-1, 23).getTime(), 5, 123.0, "Poruka3", false, ReservationStatus.accepted);
		Reservation r4 = new Reservation(apRes.getById(2), userRes.getById(4), new GregorianCalendar(2020, 1-1, 1).getTime(), 5, 421.0, "Poruka4", false, ReservationStatus.accepted);
		
		//res.create(r1);
		//res.create(r2);
		//res.create(r3);
		//res.create(r4);
		
		/*
		List<Reservation> reservations = res.getAll();
		for(Reservation re : reservations) {
			System.out.println(conv.toCsv(re));
		}
		*/
		/*
		List<Reservation> reservations = res.getAllEager();
		for(Reservation re : reservations) {
			System.out.println(re.getGuest().getName() + ": " + re.getApartment().getHost().getName());
		}*/
		
		/*
		Reservation r = res.getById(2);
		r.setMessage("Nova poruka");
		r.setNights(99);
		res.update(r);
		*/
		
		//res.delete(4);
		//res.getById(4);
		
		/*
		r2 = res.getById(1);
		r2.setReservationStatus(ReservationStatus.created);
		res.update(r2);
		
		Reservation r = res.getEager(res.getById(1).getId());
			System.out.println(conv.toCsv(r));
		
	}
	*/
	/*
	private static void userRepoTest() throws DatabaseException
	{
		UserRepository res = resources.userRepository;
		
		User user1 = new User(new Account("ggg", "huigf9wdbwhbd", false), "Igor", "Jovin", false, false, Gender.male, UserType.host);
		User user2 = new User(new Account("huihhjh", "rtdyGYUguryw7", false), "Marko", "Jovin", false, false, Gender.male, UserType.guest);
		User user3 = new User(new Account("73buiin", "rtdyGYUguryw7", false), "Nikola", "Jovin", false, false, Gender.male, UserType.admin);
		User user4 = new User(new Account("ioihhse", "rtdyGYUguryw7", false), "Jovan", "Jovin", false, false, Gender.male, UserType.guest);
		User user5 = new User(new Account("878hjn9ii", "rtdyGYUguryw7", false), "Dragan", "Jovin", false, false, Gender.male, UserType.guest);
		User user6 = new User(new Account("i8y7dfc", "rtdyGYUguryw7", false), "Milorad", "Jovin", false, false, Gender.male, UserType.guest);
		
		/*
		res.create(user1);
		res.create(user2);
		res.create(user3);
		res.create(user4);
		res.create(user5);
		res.create(user6);
		*/
		
		//res.delete(12);
		
		//UserCsvConverter conv = new UserCsvConverter();
		//AccountCsvConverter accConv = new AccountCsvConverter();
		/*
		List<User> users = res.getAll();
		
		for(User user : users) {
			System.out.println(conv.toCsv(user));
		}*/
		
		//User user = res.getById(4);
		//System.out.println(conv.toCsv(user));
		
		//User user = res.getEager(14);
		//System.out.println(conv.toCsv(user) + "\n" + accConv.toCsv(user.getAccount()));
		
		//UserFilterDTO filter = new UserFilterDTO("ggg", UserType.undefined, Gender.undefined);
		//List<User> users = res.find(UserFilterConverter.getSpecification(filter));
		
		//User user = res.getByUsername("huihhjh");
		//System.out.println(conv.toCsv(user));
		//System.out.println(user.getAccount().getPassword());
		
		//user.setGender(Gender.female);
		//user.setName("Marina");
		//res.update(user);
		
		/*
		for(User user : users) {
			System.out.println(conv.toCsv(user));
		}
		
	}
	*/
	/*

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


	private static void testDateCollection() throws DatabaseException {
		DateCollectionRepository repo = (new AppResources()).availableDateCollectionRepository;
		
		Map<Date, DateStatus> dates = new HashMap<Date, DateStatus>();
		dates.put(new Date(), DateStatus.free);
		dates.put(new GregorianCalendar(2015,3-1,15).getTime(), DateStatus.free);
		dates.put(new GregorianCalendar(2015,11-1,4).getTime(), DateStatus.free);
		dates.put(new GregorianCalendar(2015,8-1,4).getTime(), DateStatus.free);
		dates.put(new GregorianCalendar(2015,5-1,15).getTime(), DateStatus.free);
		dates.put(new GregorianCalendar(2015,7-1,11).getTime(), DateStatus.free);
		dates.put(new GregorianCalendar(2015,7-1,18).getTime(), DateStatus.free);
		dates.put(new GregorianCalendar(2015,6-1,7).getTime(), DateStatus.free);
		dates.put(new GregorianCalendar(2015,7-1,14).getTime(), DateStatus.free);
		dates.put(new GregorianCalendar(2018,9-1,13).getTime(), DateStatus.free);
		DateCollection dateCollection1 = new DateCollection(new Apartment(3), false, dates);
		
		/*
		List<Date> dates2 = new ArrayList<Date>();
		dates2.add(new Date());
		dates2.add(new GregorianCalendar(2015,6-1,4).getTime());
		dates2.add(new GregorianCalendar(2015,7-1,15).getTime());
		dates2.add(new GregorianCalendar(2018,9-1,23).getTime());
		DateCollection dateCollection2 = new DateCollection(new Apartment(5), false, dates2);
		*/
		/*
		DateCollectionCsvConverter converter = new DateCollectionCsvConverter();
		String str1 = converter.toCsv(dateCollection1);
		System.out.println("STR1   " + str1);
		String str2 = converter.toCsv(converter.fromCsv(str1));
		System.out.println("STR2   " + str2);
		System.out.println(str1.equals(str2));
		*/
		/*String dateCollectionString = converter.toCsv(dateCollection);
		System.out.println(dateCollectionString);
		String dateCollectionString2 = converter.toCsv(converter.fromCsv(dateCollectionString));
		System.out.println(dateCollectionString2);
		
		System.out.println(dateCollectionString.equals(dateCollectionString2));
		*/
		
		//repo.create(dateCollection1);
		//repo.create(dateCollection2);
		
		//List<DateCollection> dc = repo.getAll();
		//dc.forEach(c -> System.out.println(converter.toCsv(c)));
		
		/*
		List<DateCollection> eagerdc = repo.getAllEager();
		for(DateCollection c : eagerdc) {
			System.out.println(c.getApartment().getLocation().getAddress().getStreet());
		}
		*/
		/*
		DateCollection dc = repo.getEager(96);
		System.out.println(dc.getApartment().getLocation().getAddress().getStreet());
		*/
		/*
		DateCollection dc = repo.getByApartmentId(5);
		System.out.println(converter.toCsv(dc));
		dc.addDates(new Date());
		repo.update(dc);
		DateCollection newdc = repo.getByApartmentId(5);
		System.out.println(converter.toCsv(newdc));
		*/
		/*
		repo.delete(2);
		repo.getById(2);
		
	}
*/
	/*
	private static void testReservation() {
		
		Reservation reservation = new Reservation(7845, new Apartment(56), new User(new Long(55)), new Date(), 8, 568.75, "hdsuidskjuib udhsoudn udhwubwkdiuwhkj UINnd eijbe\njwdhwjdnk uidhwbdhwuhbUHUHGUY8wehkj idudu djbdbjhd i.", false, ReservationStatus.accepted);
		
		ReservationCsvConverter converter = new ReservationCsvConverter();
		
		String reservationString = converter.toCsv(reservation);
		System.out.println(reservationString);
		
		String drugiString = converter.toCsv(converter.fromCsv(reservationString));
		
		System.out.println(drugiString.equals(reservationString));
	}

	private static void testUser() {
		
		//User user = new User(567, "ushiy73", "rtdyGYUguryw7", "Igor", "Jovin", false, false, Gender.male, UserType.host);
		
		UserCsvConverter converter = new UserCsvConverter();
		
		//String userString = converter.toCsv(user);
		//System.out.println(userString);
		
		//String drugiString = converter.toCsv(converter.fromCsv(userString));
		
		//System.out.println(userString.equals(drugiString));
	}
	*/
}
