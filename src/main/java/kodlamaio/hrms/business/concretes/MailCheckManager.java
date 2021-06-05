package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.MailCheckService;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;

public class MailCheckManager implements MailCheckService {

	@Override
	public Result verification(String email) {
		return new SuccessResult("Successfuly checked");
	}

}
