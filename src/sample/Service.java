package sample;

import java.sql.Timestamp;
import java.util.Date;

public class Service {

        private double cost = 0;
        private double discount = 0;
        private String serviceName = "";
        private Timestamp discountStart;
        private Timestamp discountEnd;

        private Timestamp current_time = new Timestamp(System.currentTimeMillis());

    public Service(String serviceName, double cost, double discount, Timestamp discountStart, Timestamp discountEnd) {
        this.serviceName = serviceName;
        this.cost = cost;
        this.discount = discount;
        this.discountStart = discountStart;
        this.discountEnd = discountEnd;
    }


    public String getserviceName() { return serviceName; }

    public double getCurrentCost() {
        double discount = this.discount / 100;

        if (discountStart != null && discountEnd != null) {
            if (current_time.after(discountStart) && current_time.before(discountEnd)) {
                return Math.round(cost * (1 - discount));
            } else {
                return cost;
            }

        }
        return cost;
    }

    public String getCostAndDiscountAsString() {
        double discount = this.discount / 100;
        String out = "";
        if (discountStart != null && discountEnd != null) {
            if (current_time.after(discountStart) && current_time.before(discountEnd)) {
                System.out.println("DEBUG: Discount applied");
                out = "$" + cost + "(-" + this.discount + "%) , NOW: $" + Math.round(cost * (1 - discount)) ;
            }
        }else{
            out = "$" + cost;
        }
        return out;
    }

    public double getDiscount(){return discount;};
    public Date getDiscountStart(){return discountStart;};
    public Date getDiscountEnd(){return discountEnd;};
}
