package utils;

import beans.Account;
import beans.Amenity;
import beans.Apartment;
import beans.Comment;
import beans.DateCollection;
import beans.Reservation;
import beans.User;
import exceptions.DatabaseException;
import repository.AccountRepository;
import repository.AmenityRepository;
import repository.ApartmentRepository;
import repository.CommentRepository;
import repository.DateCollectionRepository;
import repository.ReservationRepository;
import repository.UserRepository;
import repository.abstractrepository.IUserRepository;
import repository.csv.converter.AccountCsvConverter;
import repository.csv.converter.AmenityCsvConverter;
import repository.csv.converter.ApartmentCsvConverter;
import repository.csv.converter.CommentCsvConverter;
import repository.csv.converter.DateCollectionCsvConverter;
import repository.csv.converter.ReservationCsvConverter;
import repository.csv.converter.UserCsvConverter;
import repository.csv.stream.CsvStream;
import repository.sequencer.LongSequencer;
import service.AmenityService;
import service.ApartmentService;
import service.CommentService;
import service.ReservationService;
import service.UserService;

public class AppResources {

	//private static AppResources instance = null;
	
	//Repositories
	private AmenityRepository amenityRepository;
	private ApartmentRepository apartmentRepository;
	private DateCollectionRepository dateCollectionRepository;
	private CommentRepository commentRepository;
	private ReservationRepository reservationRepository;
	private UserRepository userRepository;
	private AccountRepository accountRepository;
	
	//Services
	public AmenityService amenityService;
	public ApartmentService apartmentService;
	public CommentService commentService;
	public ReservationService reservationService;
	public UserService userService;
	
	public AppResources() throws DatabaseException
	{
		loadRespositories();
		loadServices();
	}
	
	private void loadRespositories() throws DatabaseException
	{
		accountRepository = new AccountRepository(new CsvStream<Account>("storage/accounts.dsv", new AccountCsvConverter()), new LongSequencer());
		
		userRepository = new UserRepository(new CsvStream<User>("storage/users.dsv", new UserCsvConverter()), new LongSequencer(), accountRepository);
		
		dateCollectionRepository = new DateCollectionRepository(new CsvStream<DateCollection>("storage/datecollection.dsv", new DateCollectionCsvConverter()), new LongSequencer());
		
		amenityRepository = new AmenityRepository(new CsvStream<Amenity>("storage/amenities.dsv", new AmenityCsvConverter()), new LongSequencer());
		
		commentRepository = new CommentRepository(new CsvStream<Comment>("storage/comments.dsv", new CommentCsvConverter()), new LongSequencer(), userRepository);
		
		apartmentRepository = new ApartmentRepository(new CsvStream<Apartment>("storage/apartments.dsv", new ApartmentCsvConverter()), new LongSequencer(), userRepository, amenityRepository, commentRepository, dateCollectionRepository);
		
		dateCollectionRepository.setApartmentRepository(apartmentRepository);
		
		reservationRepository = new ReservationRepository(new CsvStream<Reservation>("storage/reservations.dsv", new ReservationCsvConverter()), new LongSequencer(), apartmentRepository, userRepository);
		
	}
	
	private void loadServices()
	{
		amenityService = new AmenityService(amenityRepository);
		
		apartmentService = new ApartmentService(apartmentRepository, dateCollectionRepository);
		
		commentService = new CommentService(commentRepository, apartmentRepository);
		
		reservationService = new ReservationService(reservationRepository, dateCollectionRepository, apartmentRepository);
		
		userService = new UserService(userRepository, accountRepository, reservationService);
	}
}
