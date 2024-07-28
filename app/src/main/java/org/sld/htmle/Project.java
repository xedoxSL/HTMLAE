package org.sld.htmle;

public class Project {
    private String name;
    private String desk;

    public Project(String name, String desk) {
        this.name = name;
        this.desk = desk;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesk() {
        return this.desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }
}
