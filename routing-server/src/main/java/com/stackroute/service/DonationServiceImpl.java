package com.stackroute.service;

import com.stackroute.domain.Charity;
import com.stackroute.domain.DeliveryBoy;
import com.stackroute.domain.Restaurant;
import com.stackroute.repository.CharityRepository;
import com.stackroute.repository.DeliveryBoyRepository;
import com.stackroute.repository.RestaurantRepository;
import com.stackroute.service.rabbitMQservice.SendOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DonationServiceImpl implements DonationService
{

    @Autowired
    private CharityRepository charityRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DeliveryBoyRepository deliveryBoyRepository;

    @Autowired
    private SendOutput sendOutput;

    @Override @Transactional
    public Restaurant saveRestaurant(String restaurantId, String restaurantName, String location){
        return restaurantRepository.saveRestaurant(restaurantId,restaurantName,location,"0","no");
    }


    @Override @Transactional
    public Charity saveCharity(String charityId, String charityName, String foodRequirement, String location) {
        return charityRepository.saveCharity(charityId,charityName,foodRequirement,location,"0",1.0);
    }


    @Override @Transactional
    public DeliveryBoy saveDeliveryBoy(String deliveryBoyId, String deliveryBoyName,Long mobile, String status, String location) {
        return deliveryBoyRepository.saveDeliveryBoy(deliveryBoyId,deliveryBoyName,mobile,status,location);
    }


    @Override @Transactional
    public String updateRestaurantFoodAvailability(String restaurantId, String foodAvailability) {
        restaurantRepository.updateRestaurantFoodAvailability(restaurantId,foodAvailability);
        return "Successfully Updated Food Availability";
    }


    @Override
    public DeliveryBoy updateDeliveryBoyStatusAndLocation(String deliveryBoyId, String status, String location) {
        return deliveryBoyRepository.updateDeliveryBoyStatusAndLocation(deliveryBoyId,status,location);
    }


    @Override @Transactional
    public String createRestaurantCharityRelation(String restaurantId, String charityId, int distance) {
        charityRepository.createRestaurantCharityRelation(restaurantId,charityId,distance,"no");
        return "Successfully Created Relation DONATES_TO FROM Restaurant-Id -> " + restaurantId  + " TO Charity-Id -> " + charityId + " WITH Distance -> " + distance;
    }


    @Override @Transactional
    public String createRestaurantDeliveryBoyRelation(String restaurantId, String deliveryBoyId, int distance) {
        deliveryBoyRepository.createRestaurantDeliveryBoyRelation(restaurantId, deliveryBoyId,distance);
        return "Successfully Created Relation LINKED_TO FROM Restaurant-Id -> " + restaurantId + " To DeliveryBoy-Id -> " + deliveryBoyId + " WITH Distance -> " + distance;
    }


    @Override @Transactional
    public String createPicksFromRelation(String restaurantId, String deliveryBoyId) {
        deliveryBoyRepository.createPicksFromRelation(restaurantId, deliveryBoyId);
        return "Successfully Created Relation PICKS_FROM for Restaurant-Id -> " + restaurantId + " With DeliveryBoy-Id -> " + deliveryBoyId;
    }


    @Override @Transactional
    public String createDeliveryBoyCharityRelation(String deliveryBoyId, String charityId, int distance) {
        deliveryBoyRepository.createDeliveryBoyCharityRelation(deliveryBoyId,charityId,distance);
        return "Successfully Created Relation LINKED_TO FROM DeliveryBoy-Id -> " + deliveryBoyId + " To Charity-Id -> " + deliveryBoyId + " WITH Distance -> " + distance;
    }


    @Override @Transactional
    public String createDeliversToRelation(String deliveryBoyId, String charityId) {
        deliveryBoyRepository.createDeliversToRelation(deliveryBoyId,charityId);
        return "Successfully Created Relation DELIVERS_TO for DeliveryBoy-Id -> " + deliveryBoyId + " With Charity-Id -> " + charityId;
    }


    @Override @Transactional(readOnly = true)
    public Charity findByCharityName(String charityName){
        return charityRepository.findByCharityName(charityName);
    }


    @Override @Transactional(readOnly = true)
    public DeliveryBoy findByDeliveryBoyName(String deliveryBoyName){
        return deliveryBoyRepository.findByDeliveryBoyName(deliveryBoyName);
    }


    @Override @Transactional
    public String removeRestaurantCharityRelation(String charityId) {
        charityRepository.removeRestaurantCharityRelation(charityId);
        return "Successfully Deleted All Incoming Relations for Charity with Id -> " + charityId;
    }


    @Override @Transactional
    public String removeRestaurantDeliveryBoyRelation(String deliveryBoyId) {
        deliveryBoyRepository.removeRestaurantDeliveryBoyRelation(deliveryBoyId);
        return "Successfully Deleted All Incoming Relations for DeliveryBoy with Id -> " + deliveryBoyId;
    }


    @Override @Transactional
    public String removeDeliveryBoyCharityRelation(String deliveryBoyId) {
        deliveryBoyRepository.removeDeliveryBoyCharityRelation(deliveryBoyId);
        return "Successfully Deleted All Outgoing Relations for DeliveryBoy with Id -> " + deliveryBoyId;
    }


    @Override @Transactional
    public String removeRestaurant(String restaurantId) {
        restaurantRepository.removeRestaurant(restaurantId);
        return "Successfully Deleted Restaurant with Id -> " + restaurantId;
    }


    @Override @Transactional
    public String removeCharity(String charityId) {
        charityRepository.removeCharity(charityId);
        return "Successfully Deleted Charity with Id -> " + charityId;
    }


    @Override @Transactional
    public String removeDeliveryBoy(String deliveryBoyId) {
        deliveryBoyRepository.removeDeliveryBoy(deliveryBoyId);
        return "Successfully Deleted DeliveryBoy with Id -> " + deliveryBoyId;
    }


    @Override @Transactional
    public String startRouting(){
        System.out.println("\n------------INSIDE ROUTING ALGORITHM----------\n");

        System.out.println("--------RESTAURANTS ALLOCATION--------\n");

        List<Charity> charityList = charityRepository.getSortedCharitiesByPrecedence();
        System.out.println("Fetched Charities Sorted By Higher Precedence\n");

        for (int i = 0; i < charityList.size(); i++){
            Charity charity = charityList.get(i);
            System.out.println("Charity { " + charity.getCharityId() + " " + charity.getCharityName() + " }");
            int foodAvailable = Integer.parseInt(charity.getFoodAvailable());
            int foodRequirement = Integer.parseInt(charity.getFoodRequirement());
            double precedence = charity.getPrecedence();
            System.out.println("Food Requirement = " + foodRequirement + " Food Available = " + foodAvailable + "\n");
            List<Restaurant> restaurantList = restaurantRepository.getSortedRestaurantsByDistanceAndAvailability((charity.getCharityId()));
            System.out.println("Fetched Restaurants For Charity : " + charity.getCharityName() + " Within 5KM Radius And Sorted By Distance And Restaurant's Availability\n");
            for (int j = 0; j < restaurantList.size(); j++){
                Restaurant restaurant = restaurantList.get(j);
                System.out.println("Restaurant { " + restaurant.getRestaurantId() + " " + restaurant.getRestaurantName() + " }");
                if ((foodAvailable+Integer.parseInt(restaurant.getFoodAvailability())<=foodRequirement)){
                    restaurantRepository.updateDonatedStatus(restaurant.getRestaurantId(),charity.getCharityId());
                    charityRepository.updateDonatedStatusOnRelation(restaurant.getRestaurantId(),charity.getCharityId());
                    foodAvailable+=Integer.parseInt(restaurant.getFoodAvailability());
                    System.out.println("Restaurant Allocated");
                    System.out.println("Food Available For " + charity.getCharityName() + " = " + foodAvailable + "\n");
                }
                else
                    System.out.println("Restaurant Discarded\n");

            }
            charityRepository.updateFoodAvailable(charity.getCharityId(),Integer.toString(foodAvailable));
            System.out.println("Total Food Available for " + charity.getCharityName() + " = " + foodAvailable);
            precedence = (precedence*4 +(1-((double)foodAvailable/(double)foodRequirement)))/5.0;
            System.out.println(precedence);
            charityRepository.updatePrecedence(charity.getCharityId(),precedence);
            System.out.println("Updated Precedence for " + charity.getCharityName() + " Based on Food Available\n");
        }

        System.out.println("--------DELIVERY BOYS ALLOCATION--------\n");

        System.out.println("Fetched Charities Sorted By Higher Precedence\n");

        for (int i = 0; i < charityList.size(); i++){
            Charity charity = charityList.get(i);
            List<DeliveryBoy> deliveryBoyList = deliveryBoyRepository.getSortedDeliveryBoysByDistanceAndAvailability(charity.getCharityId());
            System.out.println(" Fetched Delivery Boys for Cluster of Charity : " + charity.getCharityName() + " Sorted By Distance and their Availability\n");
            DeliveryBoy deliveryBoy = deliveryBoyList.get(0);
            System.out.println("Delivery Boy { " + deliveryBoy.getDeliveryBoyId() + " " + deliveryBoy.getDeliveryBoyName() + " }\n");
            List<Restaurant> restaurantList = restaurantRepository.getAllocatedRestaurants(charity.getCharityId());
            System.out.println("Fetched Allocated Restaurants for Charity : " + charity.getCharityName() + "\n");
            String s = "";
                for (int k = 0; k < restaurantList.size(); k++) {
                    Restaurant restaurant = restaurantList.get(k);
                    s=s+restaurant.getRestaurantName()+"      ";
//                    System.out.println("Restaurant { " + restaurant.getRestaurantId() + " " + restaurant.getRestaurantName() + " }");
                    deliveryBoyRepository.createPicksFromRelation(restaurant.getRestaurantId(), deliveryBoy.getDeliveryBoyId());
                }
            System.out.println(" Delivery Boy " + deliveryBoy.getDeliveryBoyName() + " Assigned to Restaurants " + s + "\n");
                deliveryBoyRepository.createDeliversToRelation(deliveryBoy.getDeliveryBoyId(),charity.getCharityId());
            System.out.println(" Delivery Boy : " + deliveryBoy.getDeliveryBoyName() + " Assigned to Charity " + charity.getCharityName());
            deliveryBoyRepository.updateDeliveryBoyStatusAndLocation(deliveryBoy.getDeliveryBoyId(),"NotAvailable",deliveryBoy.getLocation());
            System.out.println(" Updated Status to NotAvailable\n");
            }


       startSending();
        return "Routing Protocol Initiated -- You can Now Fetch Route for delivery Boys";
    }

    @Override
    public void startSending() {
        sendOutput.sendToRestaurantLogs();
        sendOutput.sendToCharityLogs();
        sendOutput.sendToDeliveryBoyLogs();
    }

    @Override @Transactional
    public String resetStats() {
        restaurantRepository.resetDonated();
        System.out.println(" Donated Status Reset of Restaurents Successful ");
        charityRepository.resetFoodAvailable();
        System.out.println(" Food Available Reset of Charities Successful ");
        deliveryBoyRepository.resetAvailabilityStatus();
        System.out.println(" Availability Status Reset of Delivery Boys Successful ");
        deliveryBoyRepository.removePicksFromRelation();
        System.out.println(" Removed All picks from Relations for Delivery Boys");
        deliveryBoyRepository.removeDeliversToRelation();
        System.out.println(" Removed All delivers to Relations for Delivery Boys");
        return "Reset of stats Successful";
    }


    @Override
    public String resetPrecedence() {
        charityRepository.resetPrecedence();
        System.out.println("Precedence Reset of Charities Successful");
        return "Reset of Precedence Successful";
    }

    @Override
    public String[] sendToCharity(String charityId) {
        System.out.println("--" + charityRepository.sendDetailsToCharity(charityId));
        return charityRepository.sendDetailsToCharity(charityId);
    }
}
