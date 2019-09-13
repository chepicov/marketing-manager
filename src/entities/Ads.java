package entities;

public class Ads {
    private String product;
    private String header;
    private String text;
    private String phone;
    private String companyName;
    private double cpc;
    private int views;
    private int clicks;
    private String status;

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
        this.status = "active";
        this.phone = phone;
        this.companyName = "Shop No1";
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
                ", status='" + status + '\'' +
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

    public String getStatus() {
        return status;
    }

    public String getPhone() {
        return phone;
    }

    public String getCompanyName() {
        return companyName;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String show() {
        return header + "\n" + text + "\n" + companyName + ". Телефон: " + phone;
    }
}
