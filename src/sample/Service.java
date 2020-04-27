package sample;

import java.util.ArrayList;

public class Service {

        private double cost;
        private String nameOfService;
        private ArrayList<String> subCategories;
        private ArrayList<Double> subCosts;

    public serviceType sType;

    public enum serviceType {
        Repair, Inspection, Wash
    }


    public Service(serviceType sType, double totalCost, ArrayList<String> subCategories, ArrayList<Double> subCosts) {
        this.sType = sType;
        this.cost = totalCost;
        this.subCategories = subCategories;
    }


    public String getNameOfService() {
        return sType.toString();
    }

    public double getCost(){
        return cost;
    }

    public String getSubCategories(){
        String out = "";
        for (int i = 0; i < subCategories.size(); i++){
            out += subCategories.get(i) + ", ";
        }

        return out;
    }
}
