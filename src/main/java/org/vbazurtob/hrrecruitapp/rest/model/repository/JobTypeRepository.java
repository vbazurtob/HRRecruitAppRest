package org.vbazurtob.hrrecruitapp.rest.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.vbazurtob.hrrecruitapp.rest.model.JobType;

@Repository
public interface JobTypeRepository extends CrudRepository<JobType, Integer> {

	public List<JobType> findByDescription(String description);
	
	public List<JobType> findAllByOrderByDescriptionAsc();
}
