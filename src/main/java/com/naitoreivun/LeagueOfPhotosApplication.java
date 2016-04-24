package com.naitoreivun;

import com.naitoreivun.dao.*;
import com.naitoreivun.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeagueOfPhotosApplication implements CommandLineRunner {

    @Autowired
    private AppRoleDAO appRoleDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private GroupTypeDAO groupTypeDAO;

    @Autowired
    private MemberStatusDAO memberStatusDAO;


    public static void main(String[] args) {
        SpringApplication.run(LeagueOfPhotosApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n\n\n@@@@@@@@@@@@@@@@@@@@@\n\n\n");

        AppRole userRole = new AppRole("ROLE_USER");
        AppRole adminRole = new AppRole("ROLE_ADMIN");
        GroupType privateGroup = new GroupType("PRIVATE");
        GroupType publicGroup = new GroupType("PUBLIC");
        MemberStatus member = new MemberStatus("MEMBER");
        MemberStatus admin = new MemberStatus("ADMIN");
        MemberStatus moderator = new MemberStatus("MODERATOR");
        MemberStatus banned = new MemberStatus("BANNED");
        MemberStatus leaver = new MemberStatus("LEAVER");
        MemberStatus requester = new MemberStatus("REQUESTER");
        appRoleDAO.save(adminRole);
        appRoleDAO.save(userRole);
        groupTypeDAO.save(privateGroup);
        groupTypeDAO.save(publicGroup);
        memberStatusDAO.save(member);
        memberStatusDAO.save(admin);
        memberStatusDAO.save(moderator);
        memberStatusDAO.save(banned);
        memberStatusDAO.save(leaver);
        memberStatusDAO.save(requester);

        User user1 = new User("naitoreivun", "XD", "user1@email.com", adminRole);
        User user2 = new User("stefan", "stefan", "user2@email.com", userRole);
        User user3 = new User("opos3", "haslo123", "user3@email.com", userRole);
        User user4 = new User("opos4", "haslo123", "user4@email.com", userRole);
        User user5 = new User("opos5", "haslo123", "user5@email.com", userRole);
        User user6 = new User("opos6", "haslo123", "user6@email.com", userRole);
        User user7 = new User("opos7", "haslo123", "user7@email.com", userRole);
        User user8 = new User("opos8", "haslo123", "user8@email.com", userRole);

        Group group1 = new Group("grupaNr1-prywatna", privateGroup);
        group1.getUserGroups().add(new UserGroup(user2, group1, member));
        group1.getUserGroups().add(new UserGroup(user3, group1, admin));
        group1.getUserGroups().add(new UserGroup(user4, group1, moderator));
        group1.getUserGroups().add(new UserGroup(user5, group1, member));
        Group group2 = new Group("grupaNr2-publcizna", publicGroup);
        group2.getUserGroups().add(new UserGroup(user6, group2, admin));
        group2.getUserGroups().add(new UserGroup(user7, group2, banned));
        group2.getUserGroups().add(new UserGroup(user8, group2, requester));
        group2.getUserGroups().add(new UserGroup(user2, group2, requester));
        Group group3 = new Group("grupaNr3-publiczna", publicGroup);
        group3.getUserGroups().add(new UserGroup(user2, group3, admin));
        group3.getUserGroups().add(new UserGroup(user5, group3, member));
        group3.getUserGroups().add(new UserGroup(user6, group3, requester));
        group3.getUserGroups().add(new UserGroup(user8, group3, leaver));

        userDAO.save(user1);
        userDAO.save(user2);
        userDAO.save(user3);
        userDAO.save(user4);
        userDAO.save(user5);
        userDAO.save(user6);
        userDAO.save(user7);
        userDAO.save(user8);

        groupDAO.save(group1);
        groupDAO.save(group2);
        groupDAO.save(group3);

//        Set<User> res = userDAO.gimmy();
        System.out.println(userDAO.findByUsername("opos4").get().getId());

        userDAO.save(new User("testSUer", "1234", "test@email.com", appRoleDAO.findOne(1L)));

        System.out.println("\n\n\n@@@@@@@@@@@@@@@@@@@@@\n\n\n");
    }
}
