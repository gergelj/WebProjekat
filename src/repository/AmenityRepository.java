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
import repository.sequencer.ISequencer;

public class AmenityRepository extends CSVRepository<Amenity,Long> implements IAmenityRepository {

   public AmenityRepository(ICsvStream<Amenity> stream, ISequencer<Long> sequencer) {
	   super("Amenity", stream, sequencer);
   }

}