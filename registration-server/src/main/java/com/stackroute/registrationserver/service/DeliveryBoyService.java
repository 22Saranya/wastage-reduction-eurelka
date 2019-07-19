package com.stackroute.registrationserver.service;

import com.stackroute.registrationserver.domain.DeliveryBoyProfile;
import com.stackroute.registrationserver.domain.DeliveryBoys;

import java.util.Optional;

public interface DeliveryBoyService {

    DeliveryBoyProfile saveDeliveryBoy(DeliveryBoys deliveryBoys) throws Exception;
    Optional<DeliveryBoyProfile> displayDeliveryBoy(String username) throws Exception;
    public DeliveryBoyProfile updateDeliveryBoy(DeliveryBoys deliveryBoys) throws Exception;
}
