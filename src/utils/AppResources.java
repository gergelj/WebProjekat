package utils;

import beans.Amenity;
import beans.Apartment;
import beans.Comment;
import beans.Reservation;
import beans.User;
import repository.AmenityRepository;
import repository.ApartmentRepository;
import repository.AvailableDateCollectionRepository;
import repository.CommentRepository;
import repository.ReservationRepository;
import repository.UserRepository;
import repository.abstractrepository.IUserRepository;
import repository.csv.converter.AmenityCsvConverter;
import repository.csv.converter.ApartmentCsvConverter;
import repository.csv.converter.CommentCsvConverter;
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

	private static AppResources instance = null;
	
	
	//Repositories
	public AmenityRepository amenityRepository;
	public ApartmentRepository apartmentRepository;
	//TODO: DateCollectionRepository
	public CommentRepository commentRepository;
	public ReservationRepository reservationRepository;
	public UserRepository userRepository;
	
	//Services
	public AmenityService amenityService;
	public ApartmentService apartmentService;
	public CommentService commentService;
	public ReservationService reservationService;
	public UserService userService;
	
	private AppResources()
	{
		loadRespositories();
		loadServices();
	}
	
	public static AppResources getInstance()
	{
		if(instance == null)
		{
			instance = new AppResources();
		}
		
		return instance;
	}
	
	private void loadRespositories()
	{
		userRepository = new UserRepository(new CsvStream<User>("storage/users.dsv", new UserCsvConverter()), new LongSequencer());
		
		amenityRepository = new AmenityRepository(new CsvStream<Amenity>("storage/amenities.dsv", new AmenityCsvConverter()), new LongSequencer());
		
		commentRepository = new CommentRepository(new CsvStream<Comment>("storage/comments.dsv", new CommentCsvConverter()), new LongSequencer(), userRepository);
		
		apartmentRepository = new ApartmentRepository(new CsvStream<Apartment>("storage/apartments.dsv", new ApartmentCsvConverter()), new LongSequencer(), userRepository, amenityRepository, commentRepository);
		
		reservationRepository = new ReservationRepository(new CsvStream<Reservation>("storage/reservations.dsv", new ReservationCsvConverter()), new LongSequencer());
		
		//TODO: dateCollectionRepositorty
	}
	
	private void loadServices()
	{
		amenityService = new AmenityService(amenityRepository);
		
		//TODO: treba dateCollection x2   apartmentService = new ApartmentService(apartmentRepository, availableDateCollectionRepository, bookingDateCollectionRepository);
		
		commentService = new CommentService(commentRepository);
		
		//TODO: treba dateCollection x2   reservationService = new ReservationService(reservationRepository, availableDateCollectionRepository, bookingDateCollectionRepository);
		
		userService = new UserService(userRepository);
	}
}
