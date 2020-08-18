package repository;

import beans.PricingCalendar;
import exceptions.DatabaseException;
import repository.abstractrepository.IPricingCalendarRepository;
import repository.csv.CSVRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;

public class PricingCalendarRepository extends CSVRepository<PricingCalendar> implements IPricingCalendarRepository {

	public PricingCalendarRepository(ICsvStream<PricingCalendar> stream, LongSequencer sequencer) throws DatabaseException {
		super("Pricing Calculator", stream, sequencer);
	}



}
