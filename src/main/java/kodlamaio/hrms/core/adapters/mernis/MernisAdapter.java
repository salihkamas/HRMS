package kodlamaio.hrms.core.adapters.mernis;

public class MernisAdapter implements UserCheckService {

	@Override
	public boolean validate(String nationalityId, int birthYear) {
		if (nationalityId.length() == 11 && birthYear > 1900) {
			return true;
		} else {

			return false;
		}
	}

}
