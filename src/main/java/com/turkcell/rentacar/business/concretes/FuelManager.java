package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.FuelService;
import com.turkcell.rentacar.business.dtos.requests.CreateFuelRequest;
import com.turkcell.rentacar.business.dtos.responses.CreatedFuelResponse;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.dataAccess.abstracts.FuelRepository;
import com.turkcell.rentacar.entities.concretes.Fuel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FuelManager implements FuelService {

    private FuelRepository fuelRepository;
    private ModelMapperService modelMapperService;

    @Override
    public CreatedFuelResponse add(CreateFuelRequest createFuelRequest) {
        Fuel fuel = this.modelMapperService.forRequest().map(createFuelRequest,Fuel.class);
        fuel.setCreatedDate(LocalDateTime.now());
        Fuel createdFuel = fuelRepository.save(fuel);
        CreatedFuelResponse createdFuelResponse =
                this.modelMapperService.forResponse().map(createdFuel,CreatedFuelResponse.class);
        return createdFuelResponse;
    }

    @Override
    public List<CreatedFuelResponse> getAll() {
        List<Fuel> fuels = fuelRepository.findAll();
        List<CreatedFuelResponse> fuelResponses = new ArrayList<>();

        for (var fuel : fuels) {
            CreatedFuelResponse createdFuelResponse =
                    this.modelMapperService.forResponse().map(fuel, CreatedFuelResponse.class);
            fuelResponses.add(createdFuelResponse);
        }

        return fuelResponses;
    }

    @Override
    public CreatedFuelResponse getById(int id) {
        Optional<Fuel> fuelOptional = fuelRepository.findById(id);
        Fuel fuel = fuelOptional.get();
        CreatedFuelResponse createdFuelResponse =
                this.modelMapperService.forResponse().map(fuel, CreatedFuelResponse.class);

        return createdFuelResponse;
    }

    @Override
    public CreatedFuelResponse update(int id, CreateFuelRequest createFuelRequest) {
        Optional<Fuel> fuelOptional = fuelRepository.findById(id);

        if (fuelOptional.isPresent()) {
            Fuel fuel = fuelOptional.get();
            // Güncelleme isteğiyle gelen bilgileri mevcut yakıt nesnesini aktarma
            this.modelMapperService.forRequest().map(createFuelRequest, fuel);
            fuel.setUpdatedDate(LocalDateTime.now());

            Fuel updatedFuel = fuelRepository.save(fuel);

            // Güncellenmiş yakıt nesnesini CreatedFuelResponse nesnesine dönüştür
            CreatedFuelResponse createdFuelResponse =
                    this.modelMapperService.forResponse().map(updatedFuel, CreatedFuelResponse.class);

            return createdFuelResponse;
        } else {

            return null;
        }
    }

    @Override
    public void delete(int id) {
        fuelRepository.deleteById(id);
    }
}
