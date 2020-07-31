package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;

import java.io.File;
import java.security.Key;
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
import repository.csv.converter.DateCollectionCsvConverter;
import repository.csv.converter.ReservationCsvConverter;
import repository.csv.converter.UserCsvConverter;
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
		
		//testUser();
		//testReservation();
		//testDateCollection();
		Long idL = null;
		long id = idL.longValue();
		System.out.println(id);

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
