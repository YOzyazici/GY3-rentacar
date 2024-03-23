package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.requests.CreateBrandRequest;
import com.turkcell.rentacar.business.dtos.responses.CreatedBrandResponse;
import com.turkcell.rentacar.business.dtos.responses.CreatedFuelResponse;
import com.turkcell.rentacar.business.rules.BrandBusinessRules;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.dataAccess.abstracts.BrandRepository;
import com.turkcell.rentacar.entities.concretes.Brand;
import com.turkcell.rentacar.entities.concretes.Fuel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BrandManager implements BrandService {

    private BrandRepository brandRepository;
    private ModelMapperService modelMapperService;
    private BrandBusinessRules brandBusinessRules;

/*
    @Override
    public CreatedBrandResponse add(CreateBrandRequest createBrandRequest) {
        Brand brand = new Brand();
        brand.setName(createBrandRequest.getName());
        brand.setCreatedDate(LocalDateTime.now());

        Brand createdBrand =  brandRepository.save(brand);

        CreatedBrandResponse createdBrandResponse = new CreatedBrandResponse();
        createdBrandResponse.setId(createdBrand.getId());
        createdBrandResponse.setCreatedDate(createdBrand.getCreatedDate());
        createdBrandResponse.setName(createdBrand.getName());

        return createdBrandResponse;
    }
 */
    @Override
    public CreatedBrandResponse add(CreateBrandRequest createBrandRequest) {
        brandBusinessRules.brandNameCanNotBeDuplicated(createBrandRequest.getName());
        Brand brand = this.modelMapperService.forRequest().map(createBrandRequest,Brand.class);
        brand.setCreatedDate(LocalDateTime.now());
        Brand createdBrand =  brandRepository.save(brand);
        CreatedBrandResponse createdBrandResponse =
                this.modelMapperService.forResponse().map(createdBrand,CreatedBrandResponse.class);
        return createdBrandResponse;
    }

    @Override
    public List<CreatedBrandResponse> getAll() {
        List<Brand> brands = brandRepository.findAll();
        List<CreatedBrandResponse> brandResponses = new ArrayList<>();

        for(var brand : brands){
            CreatedBrandResponse createdBrandResponse =
                    this.modelMapperService.forResponse().map(brands,CreatedBrandResponse.class);
            brandResponses.add(createdBrandResponse);
        }
        return brandResponses;
    }

    @Override
    public CreatedBrandResponse getById(int id) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        Brand brand = brandOptional.get();
        CreatedBrandResponse createdBrandResponse =
                this.modelMapperService.forResponse().map(brand,CreatedBrandResponse.class);
        return createdBrandResponse;
    }

    @Override
    public CreatedBrandResponse update(int id, CreateBrandRequest createBrandRequest) {
        Optional<Brand> brandOptional = brandRepository.findById(id);

        if (brandOptional.isPresent()) {
            Brand brand = brandOptional.get();
            // Güncelleme isteğiyle gelen bilgileri mevcut yakıt nesnesini aktarma
            this.modelMapperService.forRequest().map(createBrandRequest, brand);
            brand.setUpdatedDate(LocalDateTime.now());

            Brand updatedBrand = brandRepository.save(brand);

            // Güncellenmiş yakıt nesnesini CreatedFuelResponse nesnesine dönüştür
            CreatedBrandResponse createdBrandResponse =
                    this.modelMapperService.forResponse().map(updatedBrand, CreatedBrandResponse.class);

            return createdBrandResponse;
        } else {

            return null;
        }
    }

    @Override
    public void delete(int id) {
        brandRepository.deleteById(id);
    }
}


