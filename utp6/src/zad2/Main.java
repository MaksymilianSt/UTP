/**
 * @author Stachnik Maksymilian S25304
 */

package zad2;


import java.beans.PropertyVetoException;

public class Main {
    public static void main(String[] args) {

        Purchase purch = new Purchase("komputer", "nie ma promocji", 3000.00);
        System.out.println(purch);

        purch.addVetoableChangeListener(event -> {
            if (event.getPropertyName().equals("price")) {
                Double newValue = (Double) (event.getNewValue());
                if (newValue < 1000)
                    throw new PropertyVetoException("Price change to: " + newValue + " not allowed", event);
            }
        });

        try {
            purch.setData("w promocji");
            purch.setPrice(2000.00);
            System.out.println(purch);

            purch.setPrice(500.00);

        } catch (PropertyVetoException exc) {
            System.out.println(exc.getMessage());
        }
        System.out.println(purch);

    }
}
