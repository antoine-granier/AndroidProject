package com.example.final_projet_android.ui.main;

import java.util.ArrayList;
import java.util.Objects;

public class AndroidContact {
    private String name;
    private String tel;
    private int ID;

    public AndroidContact() {
        name = "";
        tel = "";
        ID = 0;
    }

    public AndroidContact(String name, String tel, int ID) {
        this.name = name;
        this.tel = tel;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AndroidContact that = (AndroidContact) o;
        return ID == that.ID && Objects.equals(name, that.name) && Objects.equals(tel, that.tel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tel, ID);
    }

    @Override
    public String toString() {
        return name + '(' + tel + ')';
    }
}
