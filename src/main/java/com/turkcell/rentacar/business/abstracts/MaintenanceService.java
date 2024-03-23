package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.requests.CreateMaintenanceRequest;
import com.turkcell.rentacar.business.dtos.responses.CreatedMaintenanceResponse;


public interface MaintenanceService {
    CreatedMaintenanceResponse add(CreateMaintenanceRequest createMaintenanceRequest);
}