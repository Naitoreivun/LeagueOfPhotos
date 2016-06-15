package com.naitoreivun.lop.service;

import com.naitoreivun.lop.dao.MemberStatusDAO;
import com.naitoreivun.lop.domain.MemberStatus;
import com.naitoreivun.lop.domain.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberStatusService {
    private static final Map<String, Integer> statusHierarchy = new HashMap<>();
    private static final List<String> acceptableStatuses = Arrays.asList(
            MemberStatus.MEMBER,
            MemberStatus.MODERATOR,
            MemberStatus.ADMIN);

    static {
        statusHierarchy.put(MemberStatus.MEMBER, 1);
        statusHierarchy.put(MemberStatus.MODERATOR, 2);
        statusHierarchy.put(MemberStatus.ADMIN, 3);
    }

    @Autowired
    private MemberStatusDAO memberStatusDAO;

    public boolean isAcceptedUser(UserGroup userGroup) {
        return acceptableStatuses.contains(userGroup.getMemberStatus().getStatus());
    }

    public MemberStatus getByStatus(String status) {
        return memberStatusDAO.findByStatus(status).get();
    }

    public boolean hasMinStatus(UserGroup userGroup, String minStatus) {
        String status = userGroup.getMemberStatus().getStatus();
        return statusHierarchy.getOrDefault(status, 0) >= statusHierarchy.getOrDefault(minStatus, 0);
    }
}
