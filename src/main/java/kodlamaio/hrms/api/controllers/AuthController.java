package kodlamaio.hrms.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.AuthService;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.dtos.EmployerForRegisterDto;
import kodlamaio.hrms.entities.dtos.JobSeekerForRegisterDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private AuthService authService;

	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/jobSeekerRegister")
	public Result registerJobSeeker(@RequestBody JobSeekerForRegisterDto jobSeeker) {
		return this.authService.jobSeekerForRegister(jobSeeker);
	}

	@PostMapping("/jobSeekerRegister")
	public Result registerEmployer(@RequestBody EmployerForRegisterDto employer) {
		return this.authService.employerForRegister(employer);
	}

}
