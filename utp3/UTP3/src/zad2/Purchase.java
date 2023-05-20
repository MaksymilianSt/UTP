/**
 * @author Stachnik Maksymilian S25304
 */

package zad2;


public class Purchase {
    private String id;
    private String customerName;
    private String productName;
    private double productPrice;
    private double productQuantity;

    public Purchase(String id, String customerName, String productName, double productPrice, double productQuantity) {
        this.id = id;
        this.customerName = customerName;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(double productQuantity) {
        this.productQuantity = productQuantity;
    }
    public String getCostText(){
        return " (koszt: " +getCost()  + ")";
    }
    public double getCost(){
        return getProductQuantity() * getProductPrice();
    }

    @Override
    public String toString() {
        return id + ";" + customerName + ";" +productName + ";" + productPrice + ";" +productQuantity;
    }
}
