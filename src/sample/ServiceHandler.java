package sample;

import java.util.ArrayList;

public abstract class ServiceHandler {

    private ArrayList<Service> availableServices = DBC.getInstance().getAvailableServices();


    protected double getServiceCost(String service){
        double cost = 0;
        for (int i = 0; i < availableServices.size(); i++){
            if (service.matches(availableServices.get(i).getserviceName())){
                cost =  availableServices.get(i).getCurrentCost();
            }
        }
        return cost;
    }

    protected String getServiceCostAsString(String service){
        String out = "";
        for (int i = 0; i < availableServices.size(); i++){
            if (service.matches(availableServices.get(i).getserviceName())){
                out =  availableServices.get(i).getCostAndDiscountAsString();
            }
        }
        return out;
    }

}
