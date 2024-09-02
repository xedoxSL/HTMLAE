package org.sld.htmle;

import java.io.File;

public final class Project {
    private String name;
    private File codeFile;
    private File config;
    
    public Project(){
        
    }

    public Project(String name, File codeFile, File config) {
        this.name = name;
        this.codeFile = codeFile;
        this.config = config;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getCodeFile() {
        return this.codeFile;
    }

    public void setCodeFile(File codeFile) {
        this.codeFile = codeFile;
    }

    public File getConfig() {
        return this.config;
    }

    public void setConfig(File config) {
        this.config = config;
    }
}
