/***********************************************************************
 * Module:  AvailableDateCollectionRepository.java
 * Author:  Geri
 * Purpose: Defines the Class AvailableDateCollectionRepository
 ***********************************************************************/

package repository;

import java.util.List;

import beans.Apartment;
import beans.DateCollection;
import exceptions.EntityNotFoundException;
import repository.abstractrepository.IApartmentRepository;
import repository.abstractrepository.IDateCollectionRepository;
import repository.csv.CSVRepository;
import repository.csv.IEagerCsvRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;

public class AvailableDateCollectionRepository extends CSVRepository<DateCollection> implements IDateCollectionRepository, IEagerCsvRepository<DateCollection> {
   
	private IApartmentRepository apartmentRepository;
	
	public AvailableDateCollectionRepository(ICsvStream<DateCollection> stream, LongSequencer sequencer, IApartmentRepository apartmentRepository) {
		super("AvailableDateCollection", stream, sequencer);
		this.apartmentRepository = apartmentRepository;
	}
	
	private void bind(List<DateCollection> dateCollections) throws EntityNotFoundException
	{
		for(DateCollection dateCollection: dateCollections)
		{
			dateCollection.setApartment(getApartmentById(dateCollection.getApartment()));
		}
	}
	
	public DateCollection getEager(long id) throws EntityNotFoundException {
		DateCollection dateCollection = getById(id);
		
		dateCollection.setApartment(getApartmentById(dateCollection.getApartment()));
		
		return dateCollection;
	}

	public List<DateCollection> getAllEager() throws EntityNotFoundException {
		List<DateCollection> dateCollections = getAll();
		
		bind(dateCollections);
		
		return dateCollections;
	}

	private Apartment getApartmentById(Apartment apartment) throws EntityNotFoundException
	{
		return apartment == null? null : apartmentRepository.getById(apartment.getId());
	}
}