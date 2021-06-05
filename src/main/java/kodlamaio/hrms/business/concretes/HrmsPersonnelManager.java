package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.HrmsPersonnelService;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;

public class HrmsPersonnelManager implements HrmsPersonnelService {

	@Override
	public Result confimation() {
		return new SuccessResult("Confirmation successful");
	}

}
