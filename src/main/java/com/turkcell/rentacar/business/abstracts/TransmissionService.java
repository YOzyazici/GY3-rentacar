package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.requests.CreateTransmissionRequest;
import com.turkcell.rentacar.business.dtos.responses.CreatedTransmissionResponse;
import com.turkcell.rentacar.entities.concretes.Model;
import com.turkcell.rentacar.entities.concretes.Transmission;

import java.util.List;

public interface TransmissionService {
    CreatedTransmissionResponse add(CreateTransmissionRequest createTransmissionRequest);
    List<CreatedTransmissionResponse> getAll();
    CreatedTransmissionResponse getById(int id);
    CreatedTransmissionResponse update(int id, CreateTransmissionRequest createTransmissionRequest);
    void delete(int id);
}
