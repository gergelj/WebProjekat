/***********************************************************************
 * Module:  ApartmentService.java
 * Author:  Geri
 * Purpose: Defines the Class ApartmentService
 ***********************************************************************/

package service;

import repository.ApartmentRepository;
import repository.CommentRepository;
import repository.DateCollectionRepository;
import beans.User;
import beans.UserType;
import beans.Apartment;
import beans.Comment;
import beans.DateCollection;
import dto.ApartmentFilterDTO;
import exceptions.DatabaseException;
import exceptions.InvalidUserException;
import javaxt.utils.Array;

import java.util.*;

import org.eclipse.jetty.server.handler.ContextHandler.Availability;

public class ApartmentService {
   private ApartmentRepository apartmentRepository;
   private DateCollectionRepository availableDateCollectionRepository;
   private DateCollectionRepository bookingDateCollectionRepository;

//Constructors
   public ApartmentService(ApartmentRepository apartmentRepository, DateCollectionRepository availableDateCollectionRepository, DateCollectionRepository bookingDateCollectionRepository) {
	   super();
	   this.apartmentRepository = apartmentRepository;
	   this.availableDateCollectionRepository = availableDateCollectionRepository;
	   this.bookingDateCollectionRepository = bookingDateCollectionRepository;
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
      }
      throw new InvalidUserException();
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
    * @throws InvalidUserException
    */
   public void update(Apartment apartment, UserType userType) throws DatabaseException, InvalidUserException {
      if(userType == UserType.host || userType == UserType.admin)
      {
    	  apartmentRepository.update(apartment);
      }
      throw new InvalidUserException();
   }
   
   /**  
    *  <b>Called by:</b> admin or host<br><br>
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   public void delete(Apartment apartment, UserType userType) throws DatabaseException, InvalidUserException {
      if(userType == UserType.admin || userType == userType.host)
      {
    	  apartmentRepository.delete(apartment.getId());
      }
      throw new InvalidUserException();
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
      throw new InvalidUserException();
   }
   
   /** Creates a new apartment<br><br>
    *  
    *  <b>Called by:</b> host<br>
    *  
    * @throws InvalidUserException
    * @throws DatabaseException 
    */
   public Apartment create(Apartment apartment, UserType userType) throws InvalidUserException, DatabaseException {
	   if(userType == UserType.host)
	   {
		   apartmentRepository.create(apartment);
	   }
	   throw new InvalidUserException();
   }
   
   /** Finds an apartment by filter<br>
    *  Guest, host and undefined users can only filter apartments with status "<b>active</b>", whereas admin can filter all<br><br>
    *  
    *  <b>Called by:</b> admin, host, guest, undefined user<br>
    *  
    * @throws DatabaseException 
    * @throws InvalidUserException
    */
   public List<Apartment> find(ApartmentFilterDTO filter, UserType userType) {
	   //TODO: pitati Gerga kako find funkcionise
	   if(userType != UserType.admin)
	   {
		   
	   }
	   if(userType == UserType.admin)
	   {
		   
	   }
	   return new ArrayList<Apartment>();
   }
   
}