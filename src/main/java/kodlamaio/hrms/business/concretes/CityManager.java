package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.CityService;
import kodlamaio.hrms.core.utilities.business.BusinessRules;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.CityDao;
import kodlamaio.hrms.entities.concretes.City;

@Service
public class CityManager implements CityService {

	private CityDao cityDao;

	@Autowired
	public CityManager(CityDao cityDao) {
		super();
		this.cityDao = cityDao;
	}

	@Override
	public DataResult<List<City>> getAll() {
		return new SuccessDataResult<List<City>>(this.cityDao.findAll(), "Successfuly Listed");
	}

	@Override
	public Result add(City city) {
		Result result = BusinessRules.run(checkCityExists(city.getName()));
		if (!result.isSuccess()) {
			return result;
		}
		this.cityDao.save(city);
		return new SuccessResult("Successfuly Added");
	}

	@Override
	public Result delete(City city) {
		this.cityDao.delete(city);
		return new SuccessResult("Successfuly Deleted");
	}

	@Override
	public Result update(City city) {
		// simulator
		
		return new SuccessResult("Successfuly Updated");
	}

	private Result checkCityExists(String cityName) {
		if (this.cityDao.getByName(cityName) != null) {
			return new ErrorResult("This city already exists");
		}
		return new SuccessResult();
	}

}
