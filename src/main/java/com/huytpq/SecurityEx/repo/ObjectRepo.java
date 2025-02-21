package com.huytpq.SecurityEx.repo;

import com.huytpq.SecurityEx.entity.Object;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectRepo extends JpaRepository<Object,Long> {

}
