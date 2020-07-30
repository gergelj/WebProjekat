/***********************************************************************
 * Module:  ApartmentService.java
 * Author:  Geri
 * Purpose: Defines the Class ApartmentService
 ***********************************************************************/

package service;

import repository.ApartmentRepository;
import repository.AvailableDateCollectionRepository;
import repository.BookingDateCollectionRepository;
import beans.User;
import beans.Apartment;
import dto.ApartmentFilterDTO;
import java.util.*;

public class ApartmentService {
   private ApartmentRepository apartmentRepository;
   private AvailableDateCollectionRepository availableDateCollectionRepository;
   private BookingDateCollectionRepository bookingDateCollectionRepository;
   
   public List<Apartment> getActiveApartments() {
      // TODO: implement
      return null;
   }
   
   public List<Apartment> getApartmentsByHost(User host) {
      // TODO: implement
      return null;
   }
   
   public Apartment getById(long id) {
      // TODO: implement
      return null;
   }
   
   public void update(Apartment apartment) {
      // TODO: implement
   }
   
   public void delete(Apartment apartment) {
      // TODO: implement
   }
   
   public List<Apartment> getAll() {
      // TODO: implement
      return null;
   }
   
   public void deleteAll() {
      // TODO: implement
   }
   
   public Apartment create(Apartment apartment) {
      // TODO: implement
      return null;
   }
   
   public List<Apartment> find(ApartmentFilterDTO filter) {
      // TODO: implement
      return null;
   }

}