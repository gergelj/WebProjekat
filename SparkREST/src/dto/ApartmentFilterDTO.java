/***********************************************************************
 * Module:  ApartmentFilterDTO.java
 * Author:  Geri
 * Purpose: Defines the Class ApartmentFilterDTO
 ***********************************************************************/

package dto;

import beans.DateRange;
import beans.PriceRange;
import java.util.*;

public class ApartmentFilterDTO {
   private String city;
   private int numberOfRooms;
   private int numberOfGuests;
   
   private DateRange dateRange;
   private PriceRange priceRange;

}