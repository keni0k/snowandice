package com.example.utils.remonlineAPI;

import java.util.ArrayList;

public class RemOrders {
    public long count;
    public ArrayList<RemOrder> data;
    public long page;
    public boolean success;
}

class OrderType {
    long id;
    String title;
}

class Part {
    long id;
    long engineer_id;
    String title;
    float cost;
    float price;
    float discount_value;
    float amount;
    long warranty;
    long warranty_period;
}

class Attachment {
    long created_by_id;
    long created_at;
    String url;
    String filename;
}

class MarketingSource {
    long id;
    String title;
}