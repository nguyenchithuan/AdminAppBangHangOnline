package edu.wkd.adminappbanghangonline.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import edu.wkd.adminappbanghangonline.model.obj.Order;

public class OrderResponse {
    private boolean success;
    private String message;
    @SerializedName("result")
    private ArrayList<Order> listOrder;

    @Override
    public String toString() {
        return "OrderResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", listOrder=" + listOrder +
                '}';
    }

    public ArrayList<Order> getListOrder() {
        return listOrder;
    }

    public void setListOrder(ArrayList<Order> listOrder) {
        this.listOrder = listOrder;
    }
}
