package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.requests.CreateCarRequest;
import com.turkcell.rentacar.business.dtos.responses.CreatedCarResponse;

public interface CarService {
    CreatedCarResponse add(CreateCarRequest createCarRequest);
}