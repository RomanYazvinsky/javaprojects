package by.edu.server.web.dto.simple;

import java.util.Date;

public class NoteSimpleDto {
    private String name;
    private Date lastEditDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(Date lastEditDate) {
        this.lastEditDate = lastEditDate;
    }
}
