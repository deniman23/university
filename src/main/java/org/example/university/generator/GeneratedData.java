package org.example.university.generator;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "generated_data")
public class GeneratedData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pk;

    @Column(length = 10)
    private String varchar1;

    @Column(length = 10)
    private String varchar2;

    @Column(length = 10)
    private String varchar3;

    private int int1;
    private int int2;

    private Timestamp timestamp;

    private boolean bool;


    public GeneratedData() {
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getVarchar1() {
        return varchar1;
    }

    public void setVarchar1(String varchar1) {
        this.varchar1 = varchar1;
    }

    public String getVarchar2() {
        return varchar2;
    }

    public void setVarchar2(String varchar2) {
        this.varchar2 = varchar2;
    }

    public String getVarchar3() {
        return varchar3;
    }

    public void setVarchar3(String varchar3) {
        this.varchar3 = varchar3;
    }

    public int getInt1() {
        return int1;
    }

    public void setInt1(int int1) {
        this.int1 = int1;
    }

    public int getInt2() {
        return int2;
    }

    public void setInt2(int int2) {
        this.int2 = int2;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }
}
