package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

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
import java.util.Comparator;
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
import exceptions.EntityNotFoundException;
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
		
		testRepositoryMethods();
		
		//amenityConverterTest();
		//apartmentConverterTest();
		//commentConverterTest();
	}
	
	private static void testRepositoryMethods() throws EntityNotFoundException {
		   List<User> users = new ArrayList<User>();
			users.add(new User(567, "ushiy73", "rtdyGYUguryw7", "Igor", "Jovin", false, false, Gender.male, UserType.host));
			users.add(new User(128, "ushiy73", "rtdyGYUguryw7", "Milos", "Jovin", false, false, Gender.male, UserType.host));
			users.add(new User(755, "ushiy73", "rtdyGYUguryw7", "Marko", "Jovin", false, false, Gender.male, UserType.host));
			users.add(new User(56, "ushiy73", "rtdyGYUguryw7", "Jovan", "Jovin", false, false, Gender.male, UserType.host));
			//users.add(new User(755, "ushiy73", "rtdyGYUguryw7", "Nikola", "Jovin", false, false, Gender.male, UserType.host));
			users.add(new User(8, "ushiy73", "rtdyGYUguryw7", "Dragan", "Jovin", false, false, Gender.male, UserType.host));
			
			User notInList = new User(888, "ushiy73", "rtdyGYUguryw7", "Dragan", "Jovin", false, false, Gender.male, UserType.host);
			
			System.out.println(users.size());
			User us = users.stream().filter(user -> user.getId() == 755).findFirst().get();
			System.out.println(users.size());
			
			
			//long maxId = users.stream().max(Comparator.comparing(User::getId)).get().getId();
			//System.out.println(maxId);
			
			//User user = getById(users, 7);
			//System.out.println(user.getName());
			
			/*
			System.out.println("Before");
			User user = getById(users, 567);
			System.out.println(user.isDeleted());
			delete(users, user);
			System.out.println("After");
			User updatedUser = getById(users, 567);
			System.out.println(updatedUser.isDeleted());
			 */
			
			//write(users);
			//addUser(notInList);
			//List<User> list = read();
			
	}
	
	private static List<User> read(){
		List<User> list = new ArrayList<User>();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(new FileInputStream("storage/users.dsv"), "UTF8"));
			
			UserCsvConverter conv = new UserCsvConverter();
			String line = null;
			
			while((line=rd.readLine()) != null) {
				list.add(conv.fromCsv(line));
			}
			
			rd.close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	private static void write(List<User> users) {
		try {
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("storage/users.dsv"), "UTF8"));
			
			UserCsvConverter conv = new UserCsvConverter();
			
			for(User user : users) {
				wr.write(conv.toCsv(user));
				wr.newLine();
			}
			
			wr.close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void addUser(User user) {
		try {
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("storage/users.dsv", true), "UTF8"));
			
			UserCsvConverter conv = new UserCsvConverter();
			
			wr.append(conv.toCsv(user));
			wr.newLine();
			
			wr.close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void delete(List<User> users, User entity) throws EntityNotFoundException {
		   entity.delete();
		   update(users, entity);
	   }
	   
	   private static List<User> update(List<User> entities, User entity) throws EntityNotFoundException {
		   int index = entities.indexOf(entity);
		   
		   if(index == -1)
			   throw new EntityNotFoundException("not found");
		   
		   entities.set(index, entity);
		   return entities;
	   }
	
	private static User getById(List<User> users, int id) throws EntityNotFoundException {
		   try {
			   
			   return users.stream().filter(entity -> entity.getId() == id).findFirst().get();
		   }
		   catch(NoSuchElementException e) {
			   throw new EntityNotFoundException("not found", e);
		   }
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
