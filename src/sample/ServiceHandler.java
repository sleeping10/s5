package sample;

import java.util.ArrayList;

public abstract class ServiceHandler {

    private ArrayList<Service> availableServices = DBC.getInstance().getAvailableServices();

    protected double getServiceCost(String service){
        double cost = 0;
        for (int i = 0; i < availableServices.size(); i++){
            if (service.matches(availableServices.get(i).getServiceName())){
                cost =  availableServices.get(i).getCurrentCost();
            }
        }
        return cost;
    }

    protected double getDefaultServiceCost(String service){
        availableServices = DBC.getInstance().getAvailableServices(); //Updates arraylist with new costs
        double cost = 0;
        for (int i = 0; i < availableServices.size(); i++){
            if (service.matches(availableServices.get(i).getServiceName())){
                cost =  availableServices.get(i).getCost();
            }
        }
        return cost;
    }

    protected String getServiceCostAsString(String service){
        String out = "";
        for (int i = 0; i < availableServices.size(); i++){
            if (service.matches(availableServices.get(i).getServiceName())){
                out =  availableServices.get(i).getCostAndDiscountAsString();
            }
        }
        return out;
    }

}
