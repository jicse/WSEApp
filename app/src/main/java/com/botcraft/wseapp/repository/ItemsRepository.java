package com.botcraft.wseapp.repository;

import android.app.Application;
import android.media.AsyncPlayer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.botcraft.wseapp.model.Electronics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ItemsRepository {

    private Application application;

    public ItemsRepository(Application application) {
        this.application = application;
    }

    private String loadData() {

        String json = null;
        try {
            InputStream is = application.getAssets().open("items.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public LiveData<List<Electronics>> populateData() {

        MutableLiveData<List<Electronics>> listLiveData = new MutableLiveData<>();

        List<Electronics> items = new ArrayList<>();

        try {
            JSONArray m_jArry = new JSONArray(loadData());

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                Electronics electronics = new Electronics();
                electronics.setName(jo_inside.getString("name"));
                electronics.setPrice(jo_inside.getString("price"));
                electronics.setImageUrl(jo_inside.getString("image"));

                items.add(electronics);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listLiveData.setValue(items);

        return listLiveData;
    }
}
