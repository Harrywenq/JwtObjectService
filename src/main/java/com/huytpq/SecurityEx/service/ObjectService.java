package com.huytpq.SecurityEx.service;

import com.huytpq.SecurityEx.dto.request.ObjectRequest;
import com.huytpq.SecurityEx.dto.response.ObjectResponse;

import java.util.List;

public interface ObjectService {
    List<ObjectResponse> getAllObjects();
    ObjectResponse getObjectById(Long id);
    ObjectResponse createObject(ObjectRequest request);
    ObjectResponse updateObject(Long id, ObjectRequest request);
    void deleteObject(Long id);
}
