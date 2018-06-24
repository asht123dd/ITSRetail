package com.sdl.dart.itsretail;

public class Quote {
    Double price;
    int quantity, quality;

    public Quote(Double price, int quantity, int quality) {
        this.price = price;
        this.quantity = quantity;
        this.quality = quality;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}
