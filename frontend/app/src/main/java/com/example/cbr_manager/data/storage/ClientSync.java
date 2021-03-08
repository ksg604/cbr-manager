package com.example.cbr_manager.data.storage;

import android.content.Context;

import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientSync {
    private static ClientDBService localdb;
    private static APIService remotedb;
    private static ClientSync Instance;

    public ClientSync getInstance(Context context){
        if(Instance == null){
            synchronized (ClientSync.class){
                if(Instance == null){
                    localdb = ClientDBService.getInstance(context);
                    remotedb = APIService.getInstance();
                }
            }
        }
        return Instance;
    }

    public void performSync() throws ExecutionException, InterruptedException {
        List<Client> localClient = localdb.readAll();
        List<Client> serverClient = new ArrayList<>();
        fetchClientsToList(serverClient);

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

            for(int i=0; i<clientDiff.size(); i++){
                if(localClient.contains(clientDiff.get(i)) == false){
                    if(serverClient.contains(clientDiff.get(i)) == true){
                        // Client exist on server but does not exist in local

                        boolean replaced = false;
                        for(int j=0; j<localClient.size(); j++){
                            if(localClient.get(j).getId() == clientDiff.get(i).getId()){
                                // The client id already exist in local database, need update
                                localdb.update(clientDiff.get(i));
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
                            if(serverClient.get(j).getId() == clientDiff.get(i).getId()){
                                // The client id already exist in server database, need update depending on timestamp
                                //TODO: Check timestamp, if replaced in server here then set replaced flag true

                                replaced = true;
                            }
                        }
                        if(replaced == false){
                            // The client id does not exist in server database, need insert/create
                            remotedb.clientService.createClient(clientDiff.get(i));
                        }
                    }

                }
            }

        }


    }




    // Copied from ClientListFragment
    public void fetchClientsToList(List<Client> clientList) {
        if (remotedb.isAuthenticated()) {
            remotedb.clientService.getClients().enqueue(new Callback<List<Client>>() {
                @Override
                public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                    if (response.isSuccessful()) {
                        List<Client> clients = response.body();
                        clientList.addAll(clients);
                    }
                }

                @Override
                public void onFailure(Call<List<Client>> call, Throwable t) {

                }
            });
        }
    }




}
