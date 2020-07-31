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
import repository.sequencer.LongSequencer;

public class BookingDateCollectionRepository extends CSVRepository<DateCollection> implements IDateCollectionRepository {

	public BookingDateCollectionRepository(ICsvStream<DateCollection> stream, LongSequencer sequencer) {
		super("BookingDateCollection", stream, sequencer);
	}

}