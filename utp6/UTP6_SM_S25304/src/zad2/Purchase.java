/**
 * @author Stachnik Maksymilian S25304
 */

package zad2;


import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

public class Purchase {
    private final VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport(this);
    private String prod;
    private String data;
    private Double price;

    public Purchase(String prod, String data, Double price) {
        this.prod = prod;
        this.data = data;
        this.price = price;
    }

    public String getProd() {
        return prod;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) throws PropertyVetoException {
        vetoableChangeSupport.fireVetoableChange("data", this.data, data);

//        System.out.println("Change value of: data from: " + this.data + " to: " + data);;
        this.data = data;


    }

    public Double getPrice() {
        return price;
    }


    public void setPrice(Double price) throws PropertyVetoException {
        vetoableChangeSupport.fireVetoableChange("price", this.price, price);

//        System.out.println("Change value of: price from: " + this.price + " to: " + price);
        this.price = price;
    }

    public void addVetoableChangeListener(VetoableChangeListener listener) {
        vetoableChangeSupport.addVetoableChangeListener(listener);
    }

    @Override
    public String toString() {
        return "Purchase [prod=" + prod + ", data=" + data + ", price=" + price + "]";
    }
}
