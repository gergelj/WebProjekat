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
import dto.UserFilterDTO;
import exceptions.DatabaseException;
import exceptions.EntityNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import repository.AmenityRepository;
import repository.ApartmentRepository;
import repository.CommentRepository;
import repository.DateCollectionRepository;
import repository.ReservationRepository;
import repository.UserRepository;
import repository.abstractrepository.IUserRepository;
import repository.csv.converter.AmenityCsvConverter;
import repository.csv.converter.ApartmentCsvConverter;
import repository.csv.converter.CommentCsvConverter;
import repository.csv.converter.DateCollectionCsvConverter;
import repository.csv.converter.ReservationCsvConverter;
import repository.csv.converter.UserCsvConverter;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;
import spark.Session;
import specification.filterconverter.UserFilterConverter;
import utils.AppResources;
import ws.WsHandler;

public class SparkAppMain {

	private static Gson g = new Gson();

	/**
	 * Kljuc za potpisivanje JWT tokena.
	 * Biblioteka: https://github.com/jwtk/jjwt
	 */
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	AppResources res;

	public static void main(String[] args) throws Exception {
		
		//testRepositoryMethods();
		
		
		//amenityConverterTest();
		//apartmentConverterTest();
		//commentConverterTest();
		
		testRepositories();
	}
	
	
	private static void testRepositories() throws DatabaseException
	{
		amenityRepoTest();
		apartmentRepoTest();
		//TODO: dateCollectionTest();
		commentRepoTest();
		reservationRepoTest();
		userRepoTest();
	}
	
	private static void dateCollectoinRepoTest() {
		DateCollectionRepository res = AppResources.getInstance().availableDateCollectionRepository;
		
		DateCollection dc = new DateCollection(new Apartment(), false, new ArrayList<DateRange>());
		
	}
	
	private static void amenityRepoTest()
	{
		AmenityRepository res = AppResources.getInstance().amenityRepository;
		
		/*RADE:
		 * - create
		 * - update
		 * - delete
		 * - getById
		 * - getAll
		 */
		
	}
	
	private static void apartmentRepoTest() throws DatabaseException
	{
		/*RADE:
		 * - create
		 * - update
		 * - getById
		 * - delete
		 * - getAll
		 * - getEager		?
		 * - getAllEager	?
		 * - find			?
		 */
		
		ApartmentRepository res = AppResources.getInstance().apartmentRepository;
		UserRepository userRes = AppResources.getInstance().userRepository;
		AmenityRepository amRes = AppResources.getInstance().amenityRepository;
		CommentRepository comRes = AppResources.getInstance().commentRepository;
		
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
		
		
		Apartment ap1 = new Apartment(2, 10, 150.6, false, true, ApartmentType.fullApartment, loc1, userRes.getById(1), pictures1, amenities1, comments1);
		Apartment ap2 = new Apartment(1, 3, 150.6, false, true, ApartmentType.fullApartment, loc2, userRes.getById(2), pictures2, amenities2, comments2);
		Apartment ap3 = new Apartment(1, 4, 150.6, false, true, ApartmentType.fullApartment, loc3, userRes.getById(3), pictures3, amenities3, comments3);
		Apartment ap4 = new Apartment(1, 5, 150.6, false, true, ApartmentType.fullApartment, loc4, userRes.getById(4), pictures4, amenities4, comments2);
		Apartment ap5 = new Apartment(1, 3, 150.6, false, true, ApartmentType.fullApartment, loc5, userRes.getById(5), pictures5, amenities5, comments3);
					
		

		
		//TODO: find?
	}
	
	private static void commentRepoTest() throws DatabaseException
	{
		CommentRepository res = AppResources.getInstance().commentRepository;
		CommentCsvConverter conv = new CommentCsvConverter();
		UserRepository userRepo = AppResources.getInstance().userRepository;
		
		Comment comment1 = new Comment("Vas apartamn je potpuno sranje.\nNe zelim nikome da ode tamo vise u zivotu", 1, false, false, userRepo.getById(4));
		Comment comment2 = new Comment("Najbolji apartman u gradu", 5, false, false, userRepo.getById(5));
		Comment comment3 = new Comment("Ooooo K.", 3, false, false, userRepo.getById(6));
		Comment comment4 = new Comment("Lorem Ipsum bla bla bla bla balab jhsuahbj", 4, false, false, userRepo.getById(4));
		
		//res.create(comment1);
		//res.create(comment2);
		//res.create(comment3);
		//res.create(comment4);
		
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
		*/
		
	}
	
	private static void reservationRepoTest() throws DatabaseException
	{
		/*RADE:
		 * - create
		 * - update
		 * - getById
		 * - delete
		 * - getAll
		 * - getAllEager
		 * - getEager
		 */
		
		ReservationRepository res = AppResources.getInstance().reservationRepository;
		ApartmentRepository apRes = AppResources.getInstance().apartmentRepository;
		UserRepository userRes = AppResources.getInstance().userRepository;
		
		ReservationCsvConverter conv = new ReservationCsvConverter();
		
		Reservation r1 = new Reservation(apRes.getById(1), userRes.getById(1), new Date(2020, 8, 1), 5, 800.0, "Poruka1", false, ReservationStatus.accepted);
		Reservation r2= new Reservation(apRes.getById(2), userRes.getById(2), new Date(2020, 10, 12), 5, 525.0, "Poruka2", false, ReservationStatus.accepted);
		Reservation r3 = new Reservation(apRes.getById(3), userRes.getById(3), new Date(2020, 5, 23), 5, 123.0, "Poruka3", false, ReservationStatus.accepted);
		Reservation r4 = new Reservation(apRes.getById(4), userRes.getById(4), new Date(2020, 1, 1), 5, 421.0, "Poruka4", false, ReservationStatus.accepted);
		

		
		r2 = res.getById(1);
		r2.setReservationStatus(ReservationStatus.created);
		res.update(r2);
		
		Reservation r = res.getEager(res.getById(1).getId());
			System.out.println(conv.toCsv(r));
		
	}
	
	private static void userRepoTest() throws DatabaseException
	{
		UserRepository res = AppResources.getInstance().userRepository;
		
//		User user1 = new User("ushiy73", "rtdyGYUguryw7", "Igor", "Jovin", false, false, Gender.male, UserType.host);
//		User user2 = new User("huihhjh", "rtdyGYUguryw7", "Marko", "Jovin", false, false, Gender.male, UserType.guest);
//		User user3 = new User("8u88878", "rtdyGYUguryw7", "Nikola", "Jovin", false, false, Gender.male, UserType.admin);
//		User user4 = new User("ioihhse", "rtdyGYUguryw7", "Jovan", "Jovin", false, false, Gender.male, UserType.guest);
//		User user5 = new User("878hjn9ii", "rtdyGYUguryw7", "Dragan", "Jovin", false, false, Gender.male, UserType.guest);
//		User user6 = new User("i8y7dfc", "rtdyGYUguryw7", "Milorad", "Jovin", false, false, Gender.male, UserType.guest);
//		
		/*
		res.create(user1);
		res.create(user2);
		res.create(user3);
		res.create(user4);
		res.create(user5);
		res.create(user6);
		*/
		
		//res.delete(3);
		
		//UserCsvConverter conv = new UserCsvConverter();
		/*
		List<User> users = res.getAll();
		
		for(User user : users) {
			System.out.println(conv.toCsv(user));
		}*/
		
		//System.out.println(conv.toCsv(res.getById(3)));
		
		//UserFilterDTO filter = new UserFilterDTO("", UserType.guest, Gender.female);
		//List<User> users = res.find(UserFilterConverter.getSpecification(filter));
		
		//User user = res.getByUsername("ioihhse");
		//System.out.println(conv.toCsv(user));
		
		//user.setGender(Gender.female);
		//user.setName("Marina");
		//res.update(user);
		
		/*
		for(User user : users) {
			System.out.println(conv.toCsv(user));
		}
		*/
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
		
		//User user = new User(567, "ushiy73", "rtdyGYUguryw7", "Igor", "Jovin", false, false, Gender.male, UserType.host);
		
		UserCsvConverter converter = new UserCsvConverter();
		
		//String userString = converter.toCsv(user);
		//System.out.println(userString);
		
		//String drugiString = converter.toCsv(converter.fromCsv(userString));
		
		//System.out.println(userString.equals(drugiString));
	}
}
