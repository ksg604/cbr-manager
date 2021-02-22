package com.example.cbr_manager.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.ResponseBody;

public class ErrorParser {

    private ResponseBody errorBody;

    private JSONObject jsonError;

    public ErrorParser(ResponseBody errorBody) {
        this.errorBody = errorBody;

        this.parseErrorBody();
    }

    private void parseErrorBody() {
        if (errorBody != null) {
            try {
                jsonError = new JSONObject(errorBody.string());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            jsonError = null;
        }

    }

    public JSONObject getJson() {
        return jsonError;
    }

    public String toString() {
        String stringError = "";
        try {
            Iterator<String> keys = jsonError.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                JSONArray jsonArray = jsonError.getJSONArray(key);
                for (int i = 0; i < jsonArray.length(); i++) {
                    stringError += key + " : " + jsonArray.getString(i) + "\n";
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringError;

    }
}
