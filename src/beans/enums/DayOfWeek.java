package beans.enums;

public enum DayOfWeek {

	monday,
	tuesday,
	wednesday,
	thursday,
	friday,
	saturday,
	sunday;
	
	// Warning! Integer value is Calendar's DAY_OF_WEEK value
	public static DayOfWeek fromInteger(int x) {
        switch(x) {
        case 1:
            return DayOfWeek.sunday;
        case 2:
            return DayOfWeek.monday;
        case 3:
            return DayOfWeek.tuesday;
        case 4:
            return DayOfWeek.wednesday;
        case 5:
            return DayOfWeek.thursday;
        case 6:
            return DayOfWeek.friday;
        case 7:
            return DayOfWeek.saturday;
            
        }
        return null;
    }
}
