/***********************************************************************
 * Module:  AvailableDateCollectionRepository.java
 * Author:  Geri
 * Purpose: Defines the Class AvailableDateCollectionRepository
 ***********************************************************************/

package repository;

import beans.DateCollection;
import repository.abstractrepository.IDateCollectionRepository;
import repository.csv.CSVRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.ISequencer;

public class AvailableDateCollectionRepository extends CSVRepository<DateCollection,Long> implements IDateCollectionRepository {
   
	public AvailableDateCollectionRepository(ICsvStream<DateCollection> stream, ISequencer<Long> sequencer) {
		super("AvailableDateCollection", stream, sequencer);
	}

}