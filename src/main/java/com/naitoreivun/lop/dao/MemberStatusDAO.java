package com.naitoreivun.lop.dao;

import com.naitoreivun.lop.domain.MemberStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberStatusDAO extends CrudRepository<MemberStatus, Long> {
    Optional<MemberStatus> findByStatus(String status);
}
