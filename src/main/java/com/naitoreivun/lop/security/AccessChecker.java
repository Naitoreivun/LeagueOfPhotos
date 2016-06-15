package com.naitoreivun.lop.security;

import com.naitoreivun.lop.domain.Contest;
import com.naitoreivun.lop.domain.Group;
import com.naitoreivun.lop.domain.Image;
import com.naitoreivun.lop.domain.Season;
import com.naitoreivun.lop.service.GroupService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class AccessChecker {
    private static List<Class<?>> acceptableIdClasses = Arrays.asList(
            Group.class,
            Season.class,
            Contest.class,
            Image.class
    );

    @Autowired
    private GroupService groupService;

    @Around("@annotation(ForGroupMember)")
    public Object isMemberOfGroup(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        AnnotationBoxValues box = new AnnotationBoxValues(proceedingJoinPoint);
        if (!groupService.isMemberOfGroupWithMinStatus(box.userId, box.id, box.idClass, box.minStatus)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }

    private class AnnotationBoxValues {
        private Long userId;
        private Long id; //groupId, seasonId, contestId, imageId
        private Class<?> idClass;
        private String minStatus;

        AnnotationBoxValues(JoinPoint joinPoint) throws ServletException {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            ForGroupMember annotation = signature.getMethod().getAnnotation(ForGroupMember.class);
            final Object[] args = joinPoint.getArgs();
            HttpServletRequest request = (HttpServletRequest) args[annotation.requestNr()];
            this.userId = ClaimGetter.getCurrentUserId(request);
            this.id = (Long) args[annotation.idNr()];
            this.idClass = annotation.idClass();
            validateIdClass();
            this.minStatus = annotation.minStatus();
        }

        private void validateIdClass() {
            if (!acceptableIdClasses.contains(idClass)) { // TODO: 2016-06-15 throw error?
                throw new IllegalArgumentException("'idClass' has to be one of: " + acceptableIdClasses);
            }
        }
    }
}
