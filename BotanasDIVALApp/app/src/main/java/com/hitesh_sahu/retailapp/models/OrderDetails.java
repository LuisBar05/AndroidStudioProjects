package com.hitesh_sahu.retailapp.models;

public class OrderDetails {
    private String orderDetailsKey;
    private String codProduct;
    private String orderKey;
    private String quantity;
    private String status;

    public OrderDetails(){

    }

    public String getOrderDetailsKey() {
        return orderDetailsKey;
    }

    public void setOrderDetailsKey(String orderDetailsKey) {
        this.orderDetailsKey = orderDetailsKey;
    }

    public String getCodProduct() {
        return codProduct;
    }

    public void setCodProduct(String codProduct) {
        this.codProduct = codProduct;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
