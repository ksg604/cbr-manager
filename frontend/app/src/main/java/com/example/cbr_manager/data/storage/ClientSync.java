package com.example.cbr_manager.data.storage;

import android.content.Context;
import android.util.Log;

import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientSync {
    private static ClientDBService localdb;
    private static APIService apiService;
    private static ClientSync Instance;
    private static final List<Client> localClient = new ArrayList<>();
    private static final List<Client> serverClient = new ArrayList<>();

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
        for(int i=0; i<localClient.size(); i++) {
            if(localClient.get(i).isNewClient()) {
                localClient.get(i).setNewClient(false);
                serverInsert(localClient.get(i));
                localdb.delete(localClient.get(i));
            }
        }

        refreshList();

        int[] localUpdated = new int[localClient.size()];

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
