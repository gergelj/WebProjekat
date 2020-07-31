/***********************************************************************
 * Module:  BookingDateCollectionRepository.java
 * Author:  Geri
 * Purpose: Defines the Class BookingDateCollectionRepository
 ***********************************************************************/

package repository;

import java.util.List;

import beans.DateCollection;
import exceptions.EntityNotFoundException;
import repository.abstractrepository.IDateCollectionRepository;
import repository.csv.CSVRepository;
import repository.csv.IEagerCsvRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;

public class BookingDateCollectionRepository extends CSVRepository<DateCollection> implements IDateCollectionRepository, IEagerCsvRepository<DateCollection> {

	public BookingDateCollectionRepository(ICsvStream<DateCollection> stream, LongSequencer sequencer) {
		super("BookingDateCollection", stream, sequencer);
	}

	@Override
	public DateCollection getEager(long id) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DateCollection> getAllEager() throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}