package com.naitoreivun.lop.security;

import com.naitoreivun.lop.domain.MemberStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ForGroupMember {
    int requestNr();
    int idNr();
    Class<?> idClass();
    String minStatus() default MemberStatus.MEMBER;
}
