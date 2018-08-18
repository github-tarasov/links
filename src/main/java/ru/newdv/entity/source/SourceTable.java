package ru.newdv.entity.source;

import org.hibernate.annotations.Type;
import javax.persistence.*;

@Entity
public class SourceTable {

    @Id
    private Long sourceID;

    @Type(type="text")
    private String sourceText;

    public Long getSourceID() {
        return sourceID;
    }

    public void setSourceID(Long sourceID) {
        this.sourceID = sourceID;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

}
