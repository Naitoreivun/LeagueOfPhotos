package com.naitoreivun.lop.service;

import com.naitoreivun.lop.dao.MemberStatusDAO;
import com.naitoreivun.lop.domain.MemberStatus;
import com.naitoreivun.lop.domain.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MemberStatusService {
    @Autowired
    private MemberStatusDAO memberStatusDAO;

    private static final List<String> acceptableStatuses = Arrays.asList(
            MemberStatus.MEMBER,
            MemberStatus.MODERATOR,
            MemberStatus.ADMIN);

    public boolean isAcceptedUser(UserGroup userGroup) {
        return acceptableStatuses.contains(userGroup.getMemberStatus().getStatus());
    }

    public MemberStatus getByStatus(String status) {
        return memberStatusDAO.findByStatus(status).get();
    }
}
