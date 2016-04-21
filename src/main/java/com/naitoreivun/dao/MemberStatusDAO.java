package com.naitoreivun.dao;

import com.naitoreivun.domain.MemberStatus;
import org.springframework.data.repository.CrudRepository;

public interface MemberStatusDAO extends CrudRepository<MemberStatus, Long> {
}
