package com.naitoreivun.lop.domain.dto;

import com.naitoreivun.lop.domain.Group;
import org.joda.time.DateTime;

public class GroupDTO {
    private Long id;
    private String name;
    private String description;
    private DateTime creationDate;
    private String type;

    public GroupDTO() {
    }

    public GroupDTO(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.description = group.getDescription();
        this.creationDate = group.getCreationDate();
        this.type = group.getGroupType().getType();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public String getType() {
        return type;
    }
}
