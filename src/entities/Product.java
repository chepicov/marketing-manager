package entities;

public class Product {
    private String name;
    private String category;
    private String desc;
    private double price;
    private String status;

    private String id;

    public Product() {
    }

    public Product(String id, String name, String category, double price, String desc) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.status = "active";
        this.desc = desc;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price='" + price + '\'' +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String show() {
        return name + "\n" + price + " BYN";
    }
}
