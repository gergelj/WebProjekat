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
import beans.User;
import exceptions.EntityNotFoundException;
import repository.abstractrepository.IAmenityRepository;
import repository.abstractrepository.IApartmentRepository;
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
	
   public ApartmentRepository(ICsvStream<Apartment> stream, LongSequencer sequencer, IUserRepository userRepository, IAmenityRepository amenityRepository, IEagerCsvRepository<Comment> commentRepository) {
	   super("Apartment", stream, sequencer);
	   this.userRepository = userRepository;
	   this.amenityRepository = amenityRepository;
	   this.commentRepository = commentRepository;
   }
   
   private void bind(List<Apartment> apartments) throws EntityNotFoundException {
	   
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
	      
	   for(int i=0; i<amenityIds.size(); i++) {
		   long amenityId = amenityIds.get(i).getId();
		   amenityIds.set(i, amenities.stream().filter(am -> am.getId() == amenityId).findFirst().get());
	   }
   }

   private User getHostById(User host) throws EntityNotFoundException {
	   return host == null ? null : userRepository.getById(host.getId());
   }
   
	public List<Apartment> find(ISpecification<Apartment> specification) throws EntityNotFoundException {
      return getAllEager().stream().filter(apartment -> specification.isSatisfiedBy(apartment)).collect(Collectors.toList());
   }

   public Apartment getEager(long id) throws EntityNotFoundException {
      Apartment apartment = getById(id);
      
      apartment.setHost(getHostById(apartment.getHost()));
      
      List<Amenity> amenities = amenityRepository.getAll();
      bindApartmentWithAmenities(apartment, amenities);
      
      List<Comment> comments = commentRepository.getAllEager();
      bindApartmentWithComments(apartment, comments);
      
      return apartment;
   }
   
   public List<Apartment> getAllEager() throws EntityNotFoundException {
      List<Apartment> apartments = getAll();
      
      bind(apartments);
      
      return apartments;
   }

}