package com.example.cbr_manager.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.cbr_manager.repository.ClientRepository;
import com.example.cbr_manager.service.client.Client;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Completable;
import io.reactivex.Single;

@HiltViewModel
public class ClientViewModel extends ViewModel {
    private final ClientRepository clientRepository;

    private final SavedStateHandle savedStateHandle;

    @Inject
    public ClientViewModel(SavedStateHandle savedStateHandle, ClientRepository clientRepository) {
        this.savedStateHandle = savedStateHandle;
        this.clientRepository = clientRepository;
    }

    public LiveData<List<Client>> getAllClients() {
        return this.clientRepository.getClientsAsLiveData();
    }

    public LiveData<Client> getClient(int id) {
        return clientRepository.getClient(id);
    }

    public Single<Client> createClient(Client client) {
        return this.clientRepository.createClient(client);
    }

    public Completable uploadphoto(File file, Client client) {
        return this.clientRepository.uploadPhoto(file, client);
    }

    public Completable modifyClient(Client client) {
        return this.clientRepository.modifyClient(client);
    }


}
