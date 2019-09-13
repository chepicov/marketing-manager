package entities;

public class Order {
    private String id;
    private String productId;
    private String userId;
    private String status;

    public Order() {}

    public Order(String id, String productId, String userId) {
        this.productId = productId;
        this.userId = userId;
        this.status = "active";
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
