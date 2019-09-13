package entities;

public class Order {
    private String id;
    private String product;
    private String user;
    private double price;
    private String status;

    public Order() {}

    public Order(String id, String product, String user, double price) {
        this.product = product;
        this.user = user;
        this.status = "active";
        this.price = price;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public String getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
