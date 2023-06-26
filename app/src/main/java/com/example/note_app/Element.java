package com.example.note_app;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "note_table")
public class Element {

    // name <String>
    // modification_date <Date>
    // description <String>

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long element_id;


    @NonNull
    @ColumnInfo(name="name")
    private String name;

    @NonNull
    @ColumnInfo(name="modification_date")
    private Date modification_date;

    @NonNull
    @ColumnInfo(name="description")
    private String description;


    public Element(@NonNull String name, @NonNull Date modification_date, @NonNull String description) {
        this.name = name;
        this.modification_date = modification_date;
        this.description = description;
    }

    @Ignore
    public Element(long element_id, @NonNull String name, @NonNull Date modification_date, @NonNull String description) {
        this.element_id = element_id;
        this.name = name;
        this.modification_date = modification_date;
        this.description = description;
    }


    public long getElement_id() {
        return element_id;
    }

    @NonNull
    public String getName() {
        return name;
    }


    @NonNull
    public Date getModification_date() {
        return modification_date;
    }


    @NonNull
    public String getDescription() {
        return description;
    }


    public void setElement_id(long element_id) {
        this.element_id = element_id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setModification_date(@NonNull Date modification_date) {
        this.modification_date = modification_date;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }
}