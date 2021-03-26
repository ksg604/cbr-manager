package com.example.cbr_manager.ui.viewmodel;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.cbr_manager.repository.ClientRepository;
import com.example.cbr_manager.service.client.Client;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

@HiltViewModel
public class ClientViewModel extends ViewModel {
    private final ClientRepository clientRepository;

    private final SavedStateHandle savedStateHandle;

    @Inject
    public ClientViewModel(SavedStateHandle savedStateHandle, ClientRepository clientRepository) {
        this.savedStateHandle = savedStateHandle;
        this.clientRepository = clientRepository;
    }

    public Observable<List<Client>> getAllClients() {
        return this.clientRepository.getAllClient();
    }

    public Disposable insert(Client client, File photo) {
        return this.clientRepository.insert(client, photo);
    }

    public Disposable update(Client client, File photo) {
        return this.clientRepository.update(client, photo);
    }

    public Observable<List<Client>> sync() {
        return clientRepository.sync();
    }


}
