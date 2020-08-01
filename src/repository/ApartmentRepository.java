/***********************************************************************
 * Module:  ApartmentRepository.java
 * Author:  Geri
 * Purpose: Defines the Class ApartmentRepository
 ***********************************************************************/

package repository;

import java.util.*;
import java.util.stream.Collectors;

import beans.Amenity;
import beans.Apartment;
import beans.Comment;
import beans.DateCollection;
import beans.DateRange;
import beans.User;
import exceptions.DatabaseException;
import repository.abstractrepository.IAmenityRepository;
import repository.abstractrepository.IApartmentRepository;
import repository.abstractrepository.IDateCollectionRepository;
import repository.abstractrepository.IUserRepository;
import repository.csv.CSVRepository;
import repository.csv.IEagerCsvRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;
import specification.ISpecification;

public class ApartmentRepository extends CSVRepository<Apartment> implements IApartmentRepository, IEagerCsvRepository<Apartment> {
   
	private IUserRepository userRepository;
	private IAmenityRepository amenityRepository;
	private IEagerCsvRepository<Comment> commentRepository;
	private IDateCollectionRepository availableDateCollectionRepository;
	private IDateCollectionRepository bookingDateCollectionRepository;
	
   public ApartmentRepository(ICsvStream<Apartment> stream, LongSequencer sequencer, IUserRepository userRepository, IAmenityRepository amenityRepository, IEagerCsvRepository<Comment> commentRepository, IDateCollectionRepository availableDateCollectionRepository, IDateCollectionRepository bookingDateCollectionRepository) {
	   super("Apartment", stream, sequencer);
	   this.userRepository = userRepository;
	   this.amenityRepository = amenityRepository;
	   this.commentRepository = commentRepository;
	   this.availableDateCollectionRepository = availableDateCollectionRepository;
	   this.bookingDateCollectionRepository = bookingDateCollectionRepository;
   }
   
   @Override
   public Apartment create(Apartment apartment) throws DatabaseException {
	   apartment = super.create(apartment);
	   
	   DateCollection availableDate = new DateCollection(apartment, false, new ArrayList<DateRange>());
	   DateCollection bookingDate = new DateCollection(apartment, false, new ArrayList<DateRange>());
	   
	   availableDateCollectionRepository.create(availableDate);
	   bookingDateCollectionRepository.create(bookingDate);
	   
	   return apartment;
   }
   
   @Override
   public void delete(long id) throws DatabaseException {
	   super.delete(id);
	   
	   availableDateCollectionRepository.deleteByApartment(id);
	   bookingDateCollectionRepository.deleteByApartment(id);
   }
   
   private void bind(List<Apartment> apartments) throws DatabaseException {
	   
	   List<Amenity> amenities = amenityRepository.getAll();
	   List<Comment> comments = commentRepository.getAllEager();
	   
	   for(Apartment apartment : apartments) {
		   apartment.setHost(getHostById(apartment.getHost()));
		   bindApartmentWithAmenities(apartment, amenities);
		   bindApartmentWithComments(apartment, comments);
	   }

   }

   private void bindApartmentWithComments(Apartment apartment, List<Comment> comments) {
	   List<Comment> commentIds = apartment.getComments();
	      
	   for(int i=0; i<commentIds.size(); i++) {
		   long commentId = commentIds.get(i).getId();
		   commentIds.set(i, comments.stream().filter(comment -> comment.getId() == commentId).findFirst().get());
	   }
	
   }

   private void bindApartmentWithAmenities(Apartment apartment, List<Amenity> amenities) {
	   List<Amenity> amenityIds = apartment.getAmenities();
	   
	   List<Amenity> amenitiesToAdd = new ArrayList<Amenity>();
	   
	   for(int i=0; i<amenityIds.size(); i++) {
		   long amenityId = amenityIds.get(i).getId();
		  
		try {
			   Amenity a = amenities.stream().filter(am -> am.getId() == amenityId).findFirst().get();
			   
			   amenitiesToAdd.add(a);
		} catch (NoSuchElementException e) {
			continue;
		}
	   }
	   
	   apartment.setAmenities(amenitiesToAdd);
   }

   private User getHostById(User host) throws DatabaseException {
	   return host == null ? null : userRepository.getById(host.getId());
   }
   
	public List<Apartment> find(ISpecification<Apartment> specification) throws DatabaseException {
      return getAllEager().stream().filter(apartment -> specification.isSatisfiedBy(apartment)).collect(Collectors.toList());
   }

   public Apartment getEager(long id) throws DatabaseException {
      Apartment apartment = getById(id);
      
      apartment.setHost(getHostById(apartment.getHost()));
      
      List<Amenity> amenities = amenityRepository.getAll();
      bindApartmentWithAmenities(apartment, amenities);
      
      List<Comment> comments = commentRepository.getAllEager();
      bindApartmentWithComments(apartment, comments);
      
      return apartment;
   }
   
   public List<Apartment> getAllEager() throws DatabaseException {
      List<Apartment> apartments = getAll();
      
      bind(apartments);
      
      return apartments;
   }

}