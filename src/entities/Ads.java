package entities;

public class Ads {
    private String product;
    private String header;
    private String text;
    private String phone;
    private String company;
    private double cpc;
    private int views;
    private int clicks;

    private String id;

    public Ads() {
    }

    public Ads(String id, String product, String header, String text, double cpc, String phone) {
        this.id = id;
        this.product = product;
        this.header = header;
        this.text = text;
        this.cpc = cpc;
        this.views = 0;
        this.clicks = 0;
        this.phone = phone;
        this.company = "Shop No1";
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", product='" + product + '\'' +
                ", header='" + header + '\'' +
                ", text='" + text + '\'' +
                ", cpc='" + cpc + '\'' +
                ", views='" + views + '\'' +
                ", clicks='" + clicks + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public String getHeader() {
        return header;
    }

    public String getText() {
        return text;
    }

    public double getCpc() {
        return cpc;
    }

    public int getViews() {
        return views;
    }

    public int getClicks() {
        return clicks;
    }

    public String getPhone() {
        return phone;
    }

    public String getCompany() {
        return company;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCpc(double cpc) {
        this.cpc = cpc;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String show() {
        return header + "\n" + text + "\n" + company + ". Телефон: " + phone;
    }
}
