package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.ModelService;
import com.turkcell.rentacar.business.dtos.requests.CreateModelRequest;
import com.turkcell.rentacar.business.dtos.responses.CreatedModelResponse;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperManager;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.dataAccess.abstracts.ModelRepository;
import com.turkcell.rentacar.entities.concretes.Model;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ModelManager implements ModelService {

    private ModelRepository modelRepository;

    private ModelMapperService modelMapperService;


    @Override
    public CreatedModelResponse add(CreateModelRequest createModelRequest) {
        Model model = this.modelMapperService.forRequest().map(createModelRequest,Model.class);
        model.setCreatedDate(LocalDateTime.now());
        Model createdModel = modelRepository.save(model);
        CreatedModelResponse createdModelResponse =
                this.modelMapperService.forResponse().map(createdModel,CreatedModelResponse.class);
        return createdModelResponse;
    }

    @Override
    public List<CreatedModelResponse> getAll() {
        List<Model> models = modelRepository.findAll();
        List<CreatedModelResponse> modelResponses = new ArrayList<>();
        for (var model:models){
            CreatedModelResponse createdModelResponse =
                    this.modelMapperService.forResponse().map(model,CreatedModelResponse.class);
            modelResponses.add(createdModelResponse);
        }
        return modelResponses;
    }

    @Override
    public CreatedModelResponse getById(int id) {
        Optional<Model> modelOptional = modelRepository.findById(id);
        Model model = modelOptional.get();
        CreatedModelResponse createdModelResponse =
                this.modelMapperService.forResponse().map(model,CreatedModelResponse.class);
        return createdModelResponse;
    }

    @Override
    public CreatedModelResponse update(int id, CreateModelRequest createModelRequest) {
        Optional<Model> modelOptional = modelRepository.findById(id);
        if (modelOptional.isPresent()){
            Model model = modelOptional.get();
            this.modelMapperService.forRequest().map(createModelRequest,model);
            model.setUpdatedDate(LocalDateTime.now());

            Model updatedModel = modelRepository.save(model);

            CreatedModelResponse createdModelResponse =
                    this.modelMapperService.forResponse().map(updatedModel,CreatedModelResponse.class);
            return createdModelResponse;
        }else {

            return null;
        }

    }

    @Override
    public void delete(int id) {

    }
/*
    @Override
    public List<Model> getAll() {
        return modelRepository.findAll();
    }

    @Override
    public Model getById(int id) {
        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public Model update(int id, Model model) {
        Model updatedModel = modelRepository.findById(id).orElse(null);
        updatedModel.setName(model.getName());
        return modelRepository.save(updatedModel);
    }

    @Override
    public void delete(int id) {
        modelRepository.deleteById(id);
    }
 */
}
