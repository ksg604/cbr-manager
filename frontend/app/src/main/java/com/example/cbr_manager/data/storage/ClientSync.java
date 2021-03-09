package com.example.cbr_manager.data.storage;

import android.content.Context;
import android.util.Log;

import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientSync {
    private static ClientDBService localdb;
    private static APIService apiService;
    private static ClientSync Instance;

    private static volatile List<Client> localClient = new ArrayList<>();
    private static volatile List<Client> serverClient = new ArrayList<>();



    public static ClientSync getInstance(Context context){
        if(Instance == null){
            Instance = new ClientSync(context);
        }
        return Instance;
    }

    private ClientSync(Context context) {
        localdb = ClientDBService.getInstance(context);
        apiService = APIService.getInstance();
    }

    public void requestSync(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Integer> callable = () -> {
            performSync();
            return 0;
        };
        executor.submit(callable);
        executor.shutdown();
    }


    public void performSync() throws ExecutionException, InterruptedException, IOException {

        refreshList();
        // Check if any client on local database is newly created client not updated to server yet
        for(int i=0; i<localClient.size(); i++) {
            if(localClient.get(i).isNewClient()) {
                localClient.get(i).setNewClient(false);
                serverInsert(localClient.get(i));
                localdb.delete(localClient.get(i));
            }
        }

        // Refresh the client list for both server and local
        refreshList();

        // Check by comparing timestamp to see if server client need to be updated from local
        int[] localUpdated = new int[localClient.size()];
        int[] serverUpdated = new int[serverClient.size()];

        for(int i=0; i<localClient.size(); i++) {
            for(int j=0; j<serverClient.size(); j++){
                if(matchID(localClient.get(i), serverClient.get(j)) && checkTimeStamp(localClient.get(i).getLastModifed(), serverClient.get(j).getLastModifed())){
                    // Need to modify and update since we know clients in local is modified after server client of same id
                    if(localUpdated[i] != 1){
                        localUpdated[i] = 1;
                        serverModify(localClient.get(i));
                    }
                }
            }

        }
        // Check by comparing timestamp to see if local client need to be updated by server
        for(int i=0; i<serverClient.size(); i++) {
            for(int j=0; j<localClient.size(); j++){
                if(matchID(serverClient.get(i), localClient.get(j)) && checkTimeStamp(serverClient.get(i).getLastModifed(), localClient.get(j).getLastModifed())){
                    // Need to modify and update since we know clients in server is modified after local client of same id
                    if(serverUpdated[i] != 1){
                        serverUpdated[i] = 1;
                        localdb.update(serverClient.get(i));
                    }
                }
            }

        }



        refreshList();
        downloadLocal();

    }

    private void refreshList() throws ExecutionException, InterruptedException, IOException {
        localClient.clear();
        localClient.addAll(localdb.readAll());
        serverClient.clear();
        fetchClientsToList(serverClient);
    }


    private boolean matchID(Client client1, Client client2){
        return client1.getId() == client2.getId();
    }

    private boolean checkTimeStamp(Timestamp date1, Timestamp date2){
        long Time1 = date1.getTime();
        long Time2 = date2.getTime();
        return Time1 > Time2;
    }

    private void downloadLocal(){
        localdb.clearAll();
        for(int i=0; i<serverClient.size(); i++){
            localdb.insert(serverClient.get(i));
        }
    }

    private void serverInsert(Client client){
        if (apiService.isAuthenticated()) {
            Call<Client> call = apiService.clientService.createClientManual(client);
            call.enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {

                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {

                }
            });

        }
    }

    private void serverModify(Client client){
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




    public void fetchClientsToList(List<Client> clientList) throws IOException {
        if (apiService.isAuthenticated()) {
            Call<List<Client>> callSync = apiService.clientService.getClients();
            Response<List<Client>> response = callSync.execute();
            if(response.isSuccessful()){
                List<Client> clients = response.body();
                clientList.addAll(clients);
            }
        }
    }





}
