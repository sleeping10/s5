package sample;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class Service {

        private double cost;
        private double discount;
        private String serviceName;
        private int estimatedTime;
        private Timestamp discountStart;
        private Timestamp discountEnd;
        private Timestamp current_time = new Timestamp(System.currentTimeMillis());

        public Service(String serviceName, double cost, double discount, Timestamp discountStart, Timestamp discountEnd, int estimatedTime) {
            this.serviceName = serviceName;
            this.cost = cost;
            this.discount = discount;
            this.discountStart = discountStart;
            this.discountEnd = discountEnd;
            this.estimatedTime = estimatedTime;
        }

    public String getServiceName() { return serviceName; }

    public double getDiscount(){
            return discount;
    }

    public Timestamp getDiscountStart(){
            return discountStart;
    }

    public Timestamp getDiscountEnd(){
            return discountEnd;
    }

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

    public double getCost(){
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

    public int getEstimatedTime(){
        int hours = estimatedTime / 60; //since both are ints, you get an int
        int minutes = estimatedTime % 60;

        //return String.format("%d:%02d", hours, minutes);
        return estimatedTime;
    }
}
