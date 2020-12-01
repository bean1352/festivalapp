package com.example.finalclub;

import com.google.firebase.database.Exclude;

public class Event {
     public String documentId;
     public String event_name;
     public String club_name;
     public String event_price;

    public Event(){

    }
    @Exclude
    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Event(String event_name, String club_name, String event_price){
        this.event_name = event_name;
        this.event_price = event_price;
        this.club_name = club_name;
    }

    public String getEventName(){
     return event_name;
    }
    public String getEventPrice(){
        return event_price;
    }
    public String getClubName(){
        return club_name;
    }
    public void setEvent_name(String event_name){
        this.event_name = event_name;
    }
    public void setClub_name(String club_name){
        this.club_name = club_name;
    }
    public void setEvent_price(String event_price){
        this.event_price = event_price;
    }


}
