package ru.newdv.entity.targett;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TargetTable {

    @Id
    private String targetLink;

    public String getTargetLink() {
        return targetLink;
    }

    public void setTargetLink(String targetLink) {
        this.targetLink = targetLink;
    }
}
