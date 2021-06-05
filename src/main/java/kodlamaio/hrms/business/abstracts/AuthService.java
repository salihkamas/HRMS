package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.dtos.EmployerForRegisterDto;
import kodlamaio.hrms.entities.dtos.JobSeekerForRegisterDto;

public interface AuthService {

	Result employerForRegister(EmployerForRegisterDto employer);

	Result jobSeekerForRegister(JobSeekerForRegisterDto jobSeeker);
}
