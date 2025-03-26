package com.huytpq.SecurityEx.recipe.service.impl;


import com.huytpq.SecurityEx.recipe.dto.request.ObjectRequest;
import com.huytpq.SecurityEx.recipe.dto.response.ObjectResponse;
import com.huytpq.SecurityEx.recipe.entity.Object;
import com.huytpq.SecurityEx.base.exception.ResourceNotFoundException;
import com.huytpq.SecurityEx.recipe.mapper.ModelMapper;
import com.huytpq.SecurityEx.recipe.repo.ObjectRepo;
import com.huytpq.SecurityEx.recipe.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObjectServiceImpl implements ObjectService {

    @Autowired
    private ObjectRepo objectRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ObjectResponse> getListObjects() {
        return objectRepo.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public ObjectResponse getObjectById(Long id) {
        var object =
                objectRepo
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Object not found!"));
        var out = modelMapper.convertToObjectOutput(object);
        return out;
    }

    @Override
    public ObjectResponse createObject(ObjectRequest request) {
//        objectRepo.findByTableName(request.getTableName());
        if (objectRepo
                .findByName(request.getName())
                .isPresent()) {
            throw new ResourceNotFoundException("Object already exists!");
        }
        var object = modelMapper.createObject(request);
        objectRepo.save(object);
        return null;
    }

    @Override
    public ObjectResponse updateObject(Long id, ObjectRequest request) {
        var record =
                objectRepo
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Object not found!"));
        var updateObject = modelMapper.updateObject(record, request);
        objectRepo.save(updateObject);
        return null;
    }

    @Override
    public void deleteObject(Long id) {
        Object object = objectRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Object not found!"));
        objectRepo.delete(object);
    }
    private ObjectResponse mapToResponse(Object object) {
        ObjectResponse response = new ObjectResponse();
        response.setId(object.getId());
        response.setName(object.getName());
        response.setNameEn(object.getNameEn());
        response.setTableName(object.getTableName());
        response.setDescription(object.getDescription());
        return response;
    }
}
