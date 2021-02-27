package com.example.cbr_manager.service.referral;

import com.example.cbr_manager.service.referral.ServiceDetails.PhysiotherapyServiceDetail;
import com.example.cbr_manager.service.referral.ServiceDetails.ServiceDetail;
import com.example.cbr_manager.service.referral.ServiceDetails.WheelchairServiceDetail;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ReferralSerializer implements JsonDeserializer<Referral> {

    private static Map<String, Class> map;

    // place service details mapping here
    static {
        map = new HashMap<>();
        map.put("Wheelchair", WheelchairServiceDetail.class);
        map.put("Physiotherapy", PhysiotherapyServiceDetail.class);
    }

    @Override
    public Referral deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();
        Referral referral = gson.fromJson(json, Referral.class);

        ServiceDetail serviceDetail = deserializeServiceDetail((JsonObject) json);
        referral.setServiceDetail(serviceDetail);

        return referral;
    }

    private ServiceDetail deserializeServiceDetail(JsonObject json) {
        Gson gson = new Gson();

        JsonObject referral_json = (JsonObject) json.get("service_detail");
        String referral_type = json.get("service_type").getAsString();
        Class<ServiceDetail> serializer_class =  map.get(referral_type);

        if (serializer_class != null) {
            return gson.fromJson(referral_json, serializer_class);
        } else {
            throw new JsonParseException("Could not deserialize service detail.");
        }
    }
}
