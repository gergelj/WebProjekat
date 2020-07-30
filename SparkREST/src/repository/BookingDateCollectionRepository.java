/***********************************************************************
 * Module:  BookingDateCollectionRepository.java
 * Author:  Geri
 * Purpose: Defines the Class BookingDateCollectionRepository
 ***********************************************************************/

package repository;

import beans.DateCollection;
import repository.abstractrepository.IDateCollectionRepository;
import repository.csv.CSVRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.ISequencer;

public class BookingDateCollectionRepository extends CSVRepository<DateCollection,Long> implements IDateCollectionRepository {

	public BookingDateCollectionRepository(ICsvStream<DateCollection> stream, ISequencer<Long> sequencer) {
		super("BookingDateCollection", stream, sequencer);
	}

}