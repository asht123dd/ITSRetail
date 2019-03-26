package com.sdl.dart.itsretail;

public class Quote {
    int price;
    double quantity;
    String commodity;

    public String getCommodity() {
        return commodity;
    }

    public Quote(double quantity, int price, String commodity) {
        this.price = price;
        this.quantity = quantity;
        this.commodity=commodity;

    }
    public Quote()
    {}


    public int getPrice() {
        return price;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }


}
