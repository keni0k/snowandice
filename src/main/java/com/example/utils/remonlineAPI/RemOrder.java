package com.example.utils.remonlineAPI;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RemOrder {
    public long id;
    public String brand;
    public String model;
    public float price;
    public float payed;
    public String resume;
    public boolean urgent;
    public String serial;
    public Client client;
    public Status status;
    public long done_at;
    public boolean overdue;
    public long engineer_id;
    public long manager_id;
    public long branch_id;
    public String appearance;
    public long created_by_id;
    public ArrayList<Part> parts;
    public ArrayList<Part> operations;
    public ArrayList<Attachment> attachments;
    public long created_at;
    public long assigned_at;
    public long closed_at;
    public long modified_at;
    public String packagelist;
    public String kindof_good;
    public String malfunction;
    public String id_label;
    public long closed_by_id;
    public long warranty_date;
    public String manager_notes;
    public String estimated_cost;
    public String engineer_notes;
    public boolean warranty_granted;
    public long estimated_done_at;
    public Object custom_fields;
}
