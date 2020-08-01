package repository.csv.converter;

import java.util.StringJoiner;

import beans.Account;

public class AccountCsvConverter implements ICsvConverter<Account> {

	private String delimiter = "~";
	
	@Override
	public String toCsv(Account entity) {
		StringJoiner joiner = new StringJoiner(delimiter);
		
		joiner.add(String.valueOf(entity.getId()));
		joiner.add(entity.getUsername());
		joiner.add(entity.getPassword());
		joiner.add(String.valueOf(entity.isDeleted()));
				
		return joiner.toString();
	}

	@Override
	public Account fromCsv(String entityCsv) {
		String[] tokens = entityCsv.split(delimiter);
		
		Account retVal = new Account();
		
		retVal.setId(Long.valueOf(tokens[0]));
		retVal.setUsername(tokens[1]);
		retVal.setPassword(tokens[2]);
		retVal.setDeleted(Boolean.valueOf(tokens[3]));
		
		return retVal;
	}
	
}
