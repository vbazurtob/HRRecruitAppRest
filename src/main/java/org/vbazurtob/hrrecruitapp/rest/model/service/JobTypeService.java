package org.vbazurtob.hrrecruitapp.rest.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.vbazurtob.hrrecruitapp.rest.model.Job;
import org.vbazurtob.hrrecruitapp.rest.model.JobSpecification;
import org.vbazurtob.hrrecruitapp.rest.model.JobType;
import org.vbazurtob.hrrecruitapp.rest.model.repository.JobRepository;
import org.vbazurtob.hrrecruitapp.rest.model.repository.JobTypeRepository;


@Service
public class JobTypeService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JobTypeRepository jobTypeRepository;
	

	public List<JobType> getListJobTypeUI(){
		
		ArrayList<JobType> jobTypeList = (ArrayList<JobType>) jobTypeRepository.findAllByOrderByDescriptionAsc();
		
		JobType jtAll = new JobType();
		jtAll.setId(0);
		jtAll.setDescription("All");
		
		ArrayList<JobType> newTmpLst = new ArrayList<>();		
		newTmpLst.add(jtAll);
		newTmpLst.addAll(jobTypeList);
		
		return newTmpLst;
	}
	
	

}
