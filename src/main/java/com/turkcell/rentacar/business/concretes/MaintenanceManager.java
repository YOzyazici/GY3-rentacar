package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.MaintenanceService;
import com.turkcell.rentacar.business.dtos.requests.CreateMaintenanceRequest;
import com.turkcell.rentacar.business.dtos.responses.CreatedMaintenanceResponse;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.dataAccess.abstracts.MaintenanceRepository;
import com.turkcell.rentacar.entities.concretes.Maintenance;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class MaintenanceManager implements MaintenanceService {
    private MaintenanceRepository maintenanceRepository;
    private ModelMapperService modelMapperService;

    @Override
    public CreatedMaintenanceResponse add(CreateMaintenanceRequest createMaintenanceRequest) {
        Maintenance maintenance = this.modelMapperService.forRequest().map(createMaintenanceRequest,Maintenance.class);

        maintenance.setCreatedDate(LocalDateTime.now());
        maintenance.setDateSend(LocalDateTime.now());

        Maintenance createdMaintenance = maintenanceRepository.save(maintenance);

        CreatedMaintenanceResponse createdMaintenanceResponse = this.modelMapperService.forResponse()
                .map(createdMaintenance,CreatedMaintenanceResponse.class);

        return createdMaintenanceResponse;
    }
}