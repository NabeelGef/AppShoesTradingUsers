package com.example.firebasenabeel;

public class Content_account {
     static String uri , account , name ,numbertel , ID;

    public static String getID() {
        return ID;
    }

    public static void setID(String ID) {
        Content_account.ID = ID;
    }

    static Content_account content;
     public static Content_account getInstance()
     {
         if (content==null)
             return new Content_account();
         return content;
     }
    public String getUri() {
        return uri;
    }

    public void setUri(String uri1) {
        uri = uri1;
    }

    public String getAccount() {
        return account;
    }
    public void setAccount(String account1) {
        account = account1;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name1) {
        name = name1;
    }

    public static String getNumbertel() {
        return numbertel;
    }

    public static void setNumbertel(String numbertel1) {
        numbertel = numbertel1;
    }
}
