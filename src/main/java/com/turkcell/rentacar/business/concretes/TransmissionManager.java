package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.TransmissionService;
import com.turkcell.rentacar.business.dtos.requests.CreateTransmissionRequest;
import com.turkcell.rentacar.business.dtos.responses.CreatedTransmissionResponse;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.dataAccess.abstracts.TransmissionRepository;
import com.turkcell.rentacar.entities.concretes.Transmission;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TransmissionManager implements TransmissionService {

    private TransmissionRepository transmissionRepository;

    private ModelMapperService modelMapperService;


    @Override
    public CreatedTransmissionResponse add(CreateTransmissionRequest createTransmissionRequest) {
        Transmission transmission = this.modelMapperService.forRequest().map(createTransmissionRequest,Transmission.class);
        transmission.setCreatedDate(LocalDateTime.now());
        Transmission createdTransmission = transmissionRepository.save(transmission);
        CreatedTransmissionResponse createdTransmissionResponse =
                this.modelMapperService.forResponse().map(createdTransmission,CreatedTransmissionResponse.class);
        return createdTransmissionResponse;
    }

    @Override
    public List<CreatedTransmissionResponse> getAll() {
        List<Transmission> transmissions = transmissionRepository.findAll();
        List<CreatedTransmissionResponse> transmissionResponses = new ArrayList<>();
        for (var transmission: transmissions){
            CreatedTransmissionResponse createdTransmissionResponse =
                    this.modelMapperService.forResponse().map(transmission,CreatedTransmissionResponse.class);
            transmissionResponses.add(createdTransmissionResponse);
        }
        return transmissionResponses;
    }

    @Override
    public CreatedTransmissionResponse getById(int id) {
        Optional<Transmission> transmissionOptional = transmissionRepository.findById(id);
        Transmission transmission = transmissionOptional.get();
        CreatedTransmissionResponse createdTransmissionResponse =
                this.modelMapperService.forResponse().map(transmission,CreatedTransmissionResponse.class);

        return createdTransmissionResponse;
    }

    @Override
    public CreatedTransmissionResponse update(int id, CreateTransmissionRequest createTransmissionRequest) {
        Optional<Transmission> transmissionOptional = transmissionRepository.findById(id);
        if (transmissionOptional.isPresent()){
            Transmission transmission = transmissionOptional.get();
            this.modelMapperService.forRequest().map(createTransmissionRequest,transmission);
            transmission.setUpdatedDate(LocalDateTime.now());

            Transmission updatedTransmission = transmissionRepository.save(transmission);

            CreatedTransmissionResponse createdTransmissionResponse =
                    this.modelMapperService.forResponse().map(updatedTransmission,CreatedTransmissionResponse.class);

            return createdTransmissionResponse;
        }
        else {
            return null;
        }
    }

    @Override
    public void delete(int id) {

    }
/*
    @Override
    public List<Transmission> getAll() {
        return transmissionRepository.findAll();
    }

    @Override
    public Transmission getById(int id) {
        return transmissionRepository.findById(id).orElse(null);
    }

    @Override
    public Transmission update(int id, Transmission transmission) {
        Transmission updatedTransmission = transmissionRepository.findById(id).orElse(null);
        updatedTransmission.setName(transmission.getName());
        return transmissionRepository.save(updatedTransmission);
    }

    @Override
    public void delete(int id) {
        transmissionRepository.deleteById(id);
    }
 */
}
