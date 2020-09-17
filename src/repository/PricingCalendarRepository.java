package repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.PricingCalendar;
import beans.enums.DayOfWeek;
import exceptions.DatabaseException;
import repository.abstractrepository.IPricingCalendarRepository;
import repository.csv.CSVRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;

public class PricingCalendarRepository extends CSVRepository<PricingCalendar> implements IPricingCalendarRepository {

	public PricingCalendarRepository(ICsvStream<PricingCalendar> stream, LongSequencer sequencer) throws DatabaseException {
		super("Pricing Calculator", stream, sequencer);
	}

	@Override
	public List<PricingCalendar> getAll() throws DatabaseException {
		List<PricingCalendar> list = super.getAll();
	 	if(list.isEmpty()) {
			Map<DayOfWeek, Double> map = new HashMap<DayOfWeek, Double>();
			map.put(DayOfWeek.monday, 1.0);
			map.put(DayOfWeek.tuesday, 1.0);
			map.put(DayOfWeek.wednesday, 1.0);
			map.put(DayOfWeek.thursday, 1.0);
			map.put(DayOfWeek.friday, 1.0);
			map.put(DayOfWeek.saturday, 1.0);
			map.put(DayOfWeek.sunday, 1.0);
			PricingCalendar pc = new PricingCalendar(false, map, null, 1);
			pc = create(pc);
			list.add(pc);
	 	}
	 	return list;
	}

	

}
