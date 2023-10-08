package edu.wkd.adminappbanghangonline.model.obj;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String image;
    private int price;
    private String description;
    private float rating;
    @SerializedName("quantity_rating")
    private int quantityRating;
    @SerializedName("product_type_id")
    private int productTypeId;

    private int quantity;
    public Product() {
    }

    public Product(int id, String name, String image, int price, String description, float rating, int quantityRating, int productTypeId) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.rating = rating;
        this.quantityRating = quantityRating;
        this.productTypeId = productTypeId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", quantityRating=" + quantityRating +
                ", productTypeId=" + productTypeId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getQuantityRating() {
        return quantityRating;
    }

    public void setQuantityRating(int quantityRating) {
        this.quantityRating = quantityRating;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
