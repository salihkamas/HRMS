package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.AuthService;
import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.business.abstracts.HrmsPersonnelService;
import kodlamaio.hrms.business.abstracts.JobSeekerService;
import kodlamaio.hrms.business.abstracts.MailCheckService;
import kodlamaio.hrms.business.abstracts.UserService;
import kodlamaio.hrms.core.adapters.mernis.UserCheckService;
import kodlamaio.hrms.core.utilities.business.BusinessRules;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.JobSeeker;
import kodlamaio.hrms.entities.dtos.EmployerForRegisterDto;
import kodlamaio.hrms.entities.dtos.JobSeekerForRegisterDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthManager implements AuthService {

	private UserCheckService userCheckService;
	private MailCheckService mailCheckService;
	private UserService userService;
	private EmployerService employerService;
	private JobSeekerService jobSeekerService;
	private HrmsPersonnelService hrmsPersonnelService;
	private ModelMapper modelMapper;

	@Autowired
	public AuthManager(UserCheckService userCheckService, MailCheckService mailCheckService, UserService userService,
			EmployerService employerService, JobSeekerService jobSeekerService,
			HrmsPersonnelService hrmsPersonnelService, ModelMapper modelMapper) {
		super();
		this.userCheckService = userCheckService;
		this.mailCheckService = mailCheckService;
		this.userService = userService;
		this.employerService = employerService;
		this.jobSeekerService = jobSeekerService;
		this.hrmsPersonnelService = hrmsPersonnelService;
		this.modelMapper = modelMapper;
	}

	@Override
	public Result employerForRegister(EmployerForRegisterDto employer) {
		var result = BusinessRules.run(checkEmailVerification(employer.getEmail()), checkHrmsConfirmation(),
				checkPasswordVerification(employer.getPassword(), employer.getRepeatPassword()),
				checkEmailExists(employer.getEmail()));

		if (result != null) {
			return result;
		}

		Employer createEmployer = modelMapper.map(employer, Employer.class);
		this.employerService.add(createEmployer);
		return new SuccessResult("Registration Successful");
	}

	@Override
	public Result jobSeekerForRegister(JobSeekerForRegisterDto jobSeeker) {
		var result = BusinessRules.run(checkEmailVerification(jobSeeker.getEmail()), checkHrmsConfirmation(),
				checkPasswordVerification(jobSeeker.getPassword(), jobSeeker.getRepeatPassword()),
				checkEmailExists(jobSeeker.getEmail()), checkMernis(jobSeeker),
				checkNationalityId(jobSeeker.getNationalityId()));
		if (result != null) {
			return result;
		}
		JobSeeker createJobSeeker = modelMapper.map(jobSeeker, JobSeeker.class);
		this.jobSeekerService.add(createJobSeeker);
		return new SuccessResult("Registration Successful");
	}

	private Result checkEmailVerification(String email) {
		if (this.mailCheckService.verification(email) == null) {
			return new ErrorResult("Email could't be verified");
		}
		return new SuccessResult();
	}

	private Result checkEmailExists(String email) {
		if (userService.getByEmail(email).getMessage() != null) {
			return new ErrorResult("Email already exists");
		}
		return new SuccessResult();
	}

	private Result checkPasswordVerification(String password, String repeatPassword) {
		if (!password.equals(repeatPassword)) {
			return new ErrorResult("Password ERROR");
		}
		return new SuccessResult();
	}

	private Result checkHrmsConfirmation() {
		if (this.hrmsPersonnelService.confimation().isSuccess()) {
			return new SuccessResult();
		}
		return new ErrorResult("Your registration application has not been approved");
	}

	private Result checkNationalityId(String nationalityId) {
		if (this.jobSeekerService.getByNationalityId(nationalityId).getMessage() != null) {
			return new ErrorResult("User already exists");
		}
		return new SuccessResult();
	}

	private Result checkMernis(JobSeekerForRegisterDto jobSeeker) {
		if (userCheckService.validate(jobSeeker.getNationalityId(), jobSeeker.getBirthYear()) == false) {
			return new ErrorResult("ERROR");
		}
		return new SuccessResult();
	}

}
