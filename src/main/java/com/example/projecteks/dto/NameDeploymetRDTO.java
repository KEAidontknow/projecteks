package com.example.projecteks.dto;

import java.util.ArrayList;

public class NameDeploymetRDTO {
    private ArrayList<String> nameList;
    private int deploymentRate;

    public NameDeploymetRDTO() {
    }

    public ArrayList<String> getNameList() {
        return nameList;
    }

    public void setNameList(ArrayList<String> nameList) {
        this.nameList = nameList;
    }

    public int getDeploymentRate() {
        return deploymentRate;
    }

    public void setDeploymentRate(int deploymentRate) {
        this.deploymentRate = deploymentRate;
    }
}
