package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.JobTitleService;
import kodlamaio.hrms.core.utilities.business.BusinessRules;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.JobTitleDao;
import kodlamaio.hrms.entities.concretes.JobTitle;

@Service
public class JobTitleManager implements JobTitleService {

	private JobTitleDao jobTitleDao;

	@Autowired
	public JobTitleManager(JobTitleDao jobTitleDao) {
		super();
		this.jobTitleDao = jobTitleDao;
	}

	@Override
	public DataResult<List<JobTitle>> getAll() {
		return new SuccessDataResult<List<JobTitle>>(jobTitleDao.findAll(), "Data Listed.");
	}

	@Override
	public Result add(JobTitle jobTitle) {
		var result = BusinessRules.run(checkJobTitleNameExists(jobTitle.getTitle()));
		if (!result.isSuccess()) {
			return result;
		}
		this.jobTitleDao.save(jobTitle);
		return new SuccessResult("Successfult Added");
	}

	@Override
	public Result delete(JobTitle jobTitle) {
		this.jobTitleDao.delete(jobTitle);
		return new SuccessResult("Successfuly Deleted");
	}

	@Override
	public Result update(JobTitle jobTitle) {
		// simulator

		return new SuccessResult("Successfuly Updated");
	}

	private Result checkJobTitleNameExists(String jobTitleName) {
		var result = this.jobTitleDao.getByName(jobTitleName);
		if (result == null) {
			return new SuccessResult("Successfuly Added");
		}
		return new ErrorResult("This job title already exists");
	}

}
