package org.vbazurtob.hrrecruitapp.rest.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.vbazurtob.hrrecruitapp.rest.model.ApplicantWithPassword;


@Repository
public interface ApplicantRepository extends CrudRepository<ApplicantWithPassword, String> {

	public ApplicantWithPassword findOneByUsername(String username);
	
	public long countByUsernameAndPassword( String username, String password );
	
	public long countByUsername( String username );
	
}
