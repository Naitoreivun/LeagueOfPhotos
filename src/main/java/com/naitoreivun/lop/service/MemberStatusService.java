package com.naitoreivun.lop.service;

import com.naitoreivun.lop.domain.MemberStatus;
import com.naitoreivun.lop.domain.UserGroup;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MemberStatusService {
    private static final List<String> acceptableStatuses = Arrays.asList(
            MemberStatus.MEMBER,
            MemberStatus.MODERATOR,
            MemberStatus.ADMIN);

    boolean isAcceptedUser(UserGroup userGroup) {
        return acceptableStatuses.contains(userGroup.getMemberStatus().getStatus());
    }
}
