package org.vbazurtob.hrrecruitapp.rest.model.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.vbazurtob.hrrecruitapp.rest.model.Job;

public interface JobRepository extends CrudRepository<Job, Long>, JpaSpecificationExecutor<Job> {

	public List<Job> findByTitle(String title);
	
	

	public Page<Job> findAll(Pageable page);
	
	public Page<Job> findAll(Specification spec, Pageable page);
	
	public Page<Job> findAllByTitleContainingOrStatusOrJobTypeId(String title, String status,  int jobTypeId, Pageable page );
}
