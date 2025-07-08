package com.lemn.sitelemn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lemn.sitelemn.entity.OnsiteRequest;
import com.lemn.sitelemn.entity.User;

public interface OnsiteRequestRepository extends JpaRepository<OnsiteRequest, Long> {
	List<OnsiteRequest> findByUser(User user);
}
