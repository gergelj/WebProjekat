/***********************************************************************
 * Module:  ApartmentService.java
 * Author:  Geri
 * Purpose: Defines the Class ApartmentService
 ***********************************************************************/

package service;

import repository.ApartmentRepository;
import repository.DateCollectionRepository;
import specification.ISpecification;
import specification.filterconverter.ApartmentFilterConverter;
import beans.User;
import beans.enums.DateStatus;
import beans.enums.UserType;
import beans.Address;
import beans.Apartment;
import beans.DateCollection;
import beans.Location;
import beans.Picture;
import dto.ApartmentDTO;
import dto.ApartmentFilterDTO;
import exceptions.BadRequestException;
import exceptions.DatabaseException;
import exceptions.InvalidDateException;
import exceptions.InvalidUserException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;


public class ApartmentService {
   private ApartmentRepository apartmentRepository;
   private DateCollectionRepository dateCollectionRepository;
   
   private String fieldError = "Empty fields: %s";

//Constructors
   public ApartmentService(ApartmentRepository apartmentRepository, DateCollectionRepository dateCollectionRepository) {
	   super();
	   this.apartmentRepository = apartmentRepository;
	   this.dateCollectionRepository = dateCollectionRepository;
   }
   
   
//Methods
   /** Returns a all apartments with status "<b>active</b>"<br><br>
    *  
    *  <b>Called by:</b> admin, guest or undefined user 
    */
   public List<Apartment> getActiveApartments(UserType userType) throws DatabaseException, InvalidUserException {
      
	  if(userType != UserType.host)
      {
    	  List<Apartment> allApartments = apartmentRepository.getAllEager();
    	  List<Apartment> retVal = new ArrayList<Apartment>();
    	  
    	  for(Apartment apartment: allApartments)
    	  {
    		  //TODO: proveriti da li je ovo status (active/not active)
    		  if(apartment.isActive())
    			  retVal.add(apartment);
    	  }
    	  return retVal;
      }
	  else {		  
		  throw new InvalidUserException();
	  }
   }
   
   
   /** Returns a list of all apartments of specific "<b>host</b>"<br><br>
    *  
    *  <b>Called by:</b> admin or host<br>
    *  @throws DatabaseException
    *  @throws InvalidUserException
    */
   public List<Apartment> getApartmentsByHost(User host, UserType userType) throws DatabaseException, InvalidUserException {
	   if(userType == UserType.host || userType == UserType.admin)
	   {
		   List<Apartment> allApartments = apartmentRepository.getAllEager();
		   
		   List<Apartment> retVal = new ArrayList<Apartment>();
		   
		   for(Apartment apartment: allApartments)
		   {
			   if(apartment.getHost().getId() == host.getId())
				   retVal.add(apartment);
		   }
		   
		   return retVal;
	   }
	   throw new InvalidUserException();
   }
   
   public Apartment getById(long id) throws DatabaseException {
	   return apartmentRepository.getById(id);
   }
   
   
   /** 
    *  <b>Called by:</b> admin or host<br>
    * @throws DatabaseException 
 * @throws BadRequestException 
    */
   public void update(Apartment apartment, List<Date> bookingDates, User user) throws DatabaseException, BadRequestException {
      if(user.getUserType() == UserType.host || user.getUserType() == UserType.admin) {
    	  if(user.getUserType() == UserType.host) {
    		  if(!apartment.getHost().equals(user)) throw new InvalidUserException();
    	  }
    	  
    	  DateCollection dateCollection = dateCollectionRepository.getByApartmentId(apartment.getId());
    	  List<Date> oldBookingDates = dateCollection.getAvailableDates();
    	  
    	  List<Date> addedDates = bookingDates.stream().filter(d -> !oldBookingDates.contains(d)).collect(Collectors.toList());
    	  List<Date> removedDates = oldBookingDates.stream().filter(d -> !bookingDates.contains(d)).collect(Collectors.toList());
    	    
		  for (Date date : addedDates) {
			  dateCollection.addAvailableForBookingDate(date);  
		  }
		  
		  for (Date date : removedDates) {
			  dateCollection.removeAvailableForBookingDate(date);
		  }
    	  
		  dateCollectionRepository.update(dateCollection);
    	  apartmentRepository.update(apartment);
      }
      else {
    	  throw new InvalidUserException();    	  
      }
   }

/**  
    *  <b>Called by:</b> admin or host<br><br>
    * @throws DatabaseException 
 * @throws BadRequestException 
    */
   public void delete(Apartment apartment, User user) throws DatabaseException, BadRequestException {
      if(user.getUserType() == UserType.admin){
    	  apartmentRepository.delete(apartment.getId());
    	  dateCollectionRepository.deleteByApartment(apartment.getId());
      }
      else if(user.getUserType() == UserType.host) {
    	  if(apartment.getHost().equals(user)) {
    		  apartmentRepository.delete(apartment.getId());
        	  dateCollectionRepository.deleteByApartment(apartment.getId());
    	  }
    	  else {    		  
    		  throw new BadRequestException("Hosts can only delete their own apartment.");    	  
    	  }
      }
      else {
    	  throw new InvalidUserException();    	  
      }
   }
   
   /** Returns a list of all apartments<br><br>
    *  
    *  <b>Called by:</b> admin<br>
    *  
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   public List<Apartment> getAll(UserType userType) throws DatabaseException, InvalidUserException {
	   if(userType == UserType.admin)
	   {
		   return apartmentRepository.getAllEager();
	   }
	   throw new InvalidUserException();
   }
   
   /** Deletes all apartments<br><br>
    *  
    *  <b>Called by:</b> admin<br>
    *  
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   public void deleteAll(UserType userType) throws DatabaseException, InvalidUserException {
      if(userType == UserType.admin)
      {
    	  List<Apartment> apartments = apartmentRepository.getAllEager();
    	  
    	  for(Apartment apartment: apartments)
    	  {
    		  apartmentRepository.delete(apartment.getId());
    	  }
      }
      else {    	  
    	  throw new InvalidUserException();
      }
   }
   
   /** Creates a new apartment<br><br>
    *  
    *  <b>Called by:</b> host<br>
    *  
    * @throws DatabaseException 
 * @throws BadRequestException 
    */
   public Apartment create(ApartmentDTO apartment, User host) throws DatabaseException, BadRequestException {
	   if(host.getUserType() == UserType.host)
	   {
		   validateApartment(apartment);
		   
		   List<Picture> pictures = savePictures(apartment.getPictures());
		   Location apartmentLocation = new Location(apartment.getLatitude(), apartment.getLongitude(), new Address(apartment.getStreet(), apartment.getHouseNumber(), apartment.getCity(), apartment.getPostalCode()));
		   Apartment a = new Apartment(apartment.getNumberOfRooms(), apartment.getNumberOfGuests(), apartment.getPricePerNight(), false, false, apartment.getCheckInHour(), apartment.getCheckOutHour(), apartment.getApartmentType(), apartmentLocation, host, pictures, apartment.getAmenities(), null);
		   Apartment createdApartment = apartmentRepository.create(a);
		   
		   List<Date> bookingDates = apartment.getBookingDates();
		   DateCollection dc = new DateCollection(createdApartment, false, new HashMap<Date, DateStatus>());

		   for(Date date : bookingDates) {
			   dc.addAvailableForBookingDate(date);
		   }
		   
		   dateCollectionRepository.create(dc);
		      
		   return createdApartment;
	   }
	   else {		   
		   throw new InvalidUserException();
	   }
   }
   
   private void validateApartment(ApartmentDTO apartment) throws BadRequestException {
	   boolean hasPrice, hasLatitude, hasLongitude;
	   
	   hasPrice = apartment.getPricePerNight() > 0;
	   hasLatitude = apartment.getLatitude() != 0;
	   hasLongitude = apartment.getLongitude() != 0;
	   
	   if(!hasPrice || !hasLatitude || !hasLongitude) {
		   String emptyFields = (!hasPrice ? "price " : "") + (!hasLatitude ? "latitude " : "") + (!hasLongitude ? "longitude" : "");
		   throw new BadRequestException(String.format(fieldError, emptyFields));
	   }
   }

   private List<Picture> savePictures(List<String> pictures) throws DatabaseException {
		List<Picture> retVal = new ArrayList<Picture>();
		
		for(String pictureString : pictures) {
			
			String[] strings = pictureString.split(",");
	        String extension = "";
	        
	        try {		        	
	        	extension = strings[0].split(";")[0].split("/")[1];
	        }catch(Exception e) {
	        	
	        }
	        
	        if(extension.equals(""))
	        	extension = "bin"; // fallback extension
	        
	        String filename = "uploads" + File.separator + new Date().getTime();
	        
	        //convert base64 string to binary data
	        byte[] data = Base64.getDecoder().decode(strings[1].getBytes());
	        
	        File file = new File("static" + File.separator + filename + "." + extension);
	        
	        if(file.exists())
	        	filename += "-" + new Date().getTime();
	        
	        
	        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
	            outputStream.write(data);
	            
	            retVal.add(new Picture(filename + "." + extension));
	            
	        } catch (IOException e) {
	        	throw new DatabaseException("Internal Server Error");
	        }
		}
		
		return retVal;
	}
   
   /** Finds an apartment by filter<br>
    *  Guest, host and undefined users can only filter apartments with status "<b>active</b>", whereas admin can filter all<br><br>
    *  
    *  <b>Called by:</b> admin, host, guest, undefined user<br>
    *  
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   public List<Apartment> find(ApartmentFilterDTO filter, User loggedInUser) throws DatabaseException, InvalidUserException {
	   //TODO: pitati Gerga kako find funkcionise
	   List<Apartment> retVal = new ArrayList<Apartment>();
	   ISpecification<Apartment> specification = ApartmentFilterConverter.getSpecification(filter, dateCollectionRepository);
	   
	   if(loggedInUser == null)
		   retVal = getActiveApartments(UserType.undefined).stream().filter(apartment -> specification.isSatisfiedBy(apartment)).collect(Collectors.toList());
	   else if(loggedInUser.getUserType() == UserType.admin)
		   retVal = apartmentRepository.find(specification);
	   else if(loggedInUser.getUserType() == UserType.host)
		   retVal = getApartmentsByHost(loggedInUser, loggedInUser.getUserType()).stream().filter(apartment -> specification.isSatisfiedBy(apartment)).collect(Collectors.toList());
	   else
		   retVal = getActiveApartments(loggedInUser.getUserType()).stream().filter(apartment -> specification.isSatisfiedBy(apartment)).collect(Collectors.toList());
	   
	   return retVal;
   }
   
   public void activateApartment(Apartment apartment, User loggedInUser, boolean toActivate) throws InvalidUserException, DatabaseException {
	   if(loggedInUser.getUserType() == UserType.admin) {
		   apartment.setActive(toActivate);
		   apartmentRepository.update(apartment);
	   }
	   else {
		   throw new InvalidUserException();
	   }
   }
   
}