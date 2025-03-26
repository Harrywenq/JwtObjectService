package com.huytpq.SecurityEx.recipe.service;

import com.huytpq.SecurityEx.recipe.dto.request.ObjectRequest;
import com.huytpq.SecurityEx.recipe.dto.response.ObjectResponse;

import java.util.List;

public interface ObjectService {
    List<ObjectResponse> getListObjects();
    ObjectResponse getObjectById(Long id);
    ObjectResponse createObject(ObjectRequest request);
    ObjectResponse updateObject(Long id, ObjectRequest request);
    void deleteObject(Long id);
}
