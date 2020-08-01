package repository;

import beans.Account;
import repository.abstractrepository.IAccountRepository;
import repository.csv.CSVRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;

public class AccountRepository extends CSVRepository<Account> implements IAccountRepository {

	public AccountRepository(ICsvStream<Account> stream, LongSequencer sequencer) {
		super("Account", stream, sequencer);
	}

}
