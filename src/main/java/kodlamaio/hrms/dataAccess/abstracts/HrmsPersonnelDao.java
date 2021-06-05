package kodlamaio.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.HrmsPersonnel;

public interface HrmsPersonnelDao extends JpaRepository<HrmsPersonnel, Integer> {

}
