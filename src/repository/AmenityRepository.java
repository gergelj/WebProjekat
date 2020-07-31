/***********************************************************************
 * Module:  AmenityRepository.java
 * Author:  Geri
 * Purpose: Defines the Class AmenityRepository
 ***********************************************************************/

package repository;

import beans.Amenity;
import repository.abstractrepository.IAmenityRepository;
import repository.csv.CSVRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;

public class AmenityRepository extends CSVRepository<Amenity> implements IAmenityRepository {

   public AmenityRepository(ICsvStream<Amenity> stream, LongSequencer sequencer) {
	   super("Amenity", stream, sequencer);
   }

}