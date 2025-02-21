package com.huytpq.SecurityEx.service.impl;


import com.huytpq.SecurityEx.dto.request.ObjectRequest;
import com.huytpq.SecurityEx.dto.response.ObjectResponse;
import com.huytpq.SecurityEx.entity.Object;
import com.huytpq.SecurityEx.exception.ResourceNotFoundException;
import com.huytpq.SecurityEx.repo.ObjectRepo;
import com.huytpq.SecurityEx.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObjectServiceImpl implements ObjectService {

    @Autowired
    private ObjectRepo objectRepo;

    @Override
    public List<ObjectResponse> getAllObjects() {
        return objectRepo.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public ObjectResponse getObjectById(Long id) {
        Object object = objectRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Object not found!"));
        return mapToResponse(object);
    }

    @Override
    public ObjectResponse createObject(ObjectRequest request) {
        Object object = new Object(null, request.getName(), request.getNameEn(), request.getTableName(), request.getDescription());
        return mapToResponse(objectRepo.save(object));
    }

    @Override
    public ObjectResponse updateObject(Long id, ObjectRequest request) {
        Object object = objectRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Object not found!"));
        object.setName(request.getName());
        object.setNameEn(request.getNameEn());
        object.setTableName(request.getTableName());
        object.setDescription(request.getDescription());
        return mapToResponse(objectRepo.save(object));
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
