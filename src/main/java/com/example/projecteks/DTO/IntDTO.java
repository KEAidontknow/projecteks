package com.example.projecteks.DTO;

public class IntDTO {
    private int i;

    public IntDTO() {
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void add(int i){
        this.i += i;
    }
}
