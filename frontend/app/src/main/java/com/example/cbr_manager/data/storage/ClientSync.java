package com.example.cbr_manager.data.storage;

import android.content.Context;
import android.util.Log;

import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientSync {
    private static ClientDBService localdb;
    private static APIService apiService;
    private static ClientSync Instance;
    private CountDownLatch latch;

    public static ClientSync getInstance(Context context) {
        if(Instance == null){
            Instance = new ClientSync();
            Instance.localdb = ClientDBService.getInstance(context);
            Instance.apiService = APIService.getInstance();
        }
        return Instance;
    }

    public void performSync(List<Client> serverClient) throws ExecutionException, InterruptedException {

        List<Client> localClient = localdb.readAll();


        if(localClient.size() != serverClient.size()){
            // TODO: may need to override comparison method for clients

            Collection<Client> similar = new HashSet<Client>(localClient);
            Collection<Client> different = new HashSet<Client>();
            different.addAll(localClient);
            different.addAll(serverClient);

            similar.retainAll(serverClient);
            different.removeAll(similar);

            // The different here should be the clients we are missing (need testing)
            List<Client> clientDiff = new ArrayList<>(different);

            Log.d("Checkpoint 3", "Successful up until C3");
            int[] localChanged = new int[localClient.size()];
            for(int i=0; i<localChanged.length; i++){
                localChanged[i] = 0;
            }
            int[] serverChanged = new int[serverClient.size()];
            for(int i=0; i<serverChanged.length; i++){
                serverChanged[i] = 0;
            }


            for(int i=0; i<clientDiff.size(); i++){
                if(localClient.contains(clientDiff.get(i)) == false){
                    if(serverClient.contains(clientDiff.get(i)) == true){
                        // Client exist on server but does not exist in local

                        boolean replaced = false;
                        for(int j=0; j<localClient.size(); j++){
                            if(localClient.get(j).getId() == clientDiff.get(i).getId() && localChanged[j] != 1){
                                // The client id already exist in local database, need update
                                localdb.update(clientDiff.get(i));
                                localChanged[j] = 1;
                                replaced = true;
                            }
                        }
                        if(replaced == false){
                            // The client id does not exist in local database, need insert
                            localdb.insert(clientDiff.get(i));
                        }

                    }

                }


                if(serverClient.contains(clientDiff.get(i)) == false){
                    if(localClient.contains(clientDiff.get(i)) == true){
                        // Client exist on local but does not exist in server

                        boolean replaced = false;
                        for(int j=0; j<serverClient.size(); j++){
                            if(serverClient.get(j).getId() == clientDiff.get(i).getId() && serverChanged[j] != 1){
                                // The client id already exist in server database, need update depending on timestamp
                                //TODO: Check timestamp, if replaced in server here then set replaced flag true
                                serverModify(clientDiff.get(i));
                                serverChanged[j] = 1;
                                replaced = true;
                            }
                        }
                        if(replaced == false){
                            // The client id does not exist in server database, need insert/create
                            serverInsert(clientDiff.get(i));
                        }
                    }

                }
            }

        }
        else{
            // TODO: check time stamp for local and server client and update as needed

        }


    }

    public void serverInsert(Client client){
        if (apiService.isAuthenticated()) {
            apiService.clientService.createClient(client).enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {
                    // TODO: May need client photo upload


                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {

                }
            });
        }
    }

    public void serverModify(Client client){
        if (apiService.isAuthenticated()) {
            apiService.clientService.modifyClient(client).enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {

                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {

                }
            });
        }
    }









}
