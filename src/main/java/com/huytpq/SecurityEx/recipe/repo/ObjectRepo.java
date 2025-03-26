package com.huytpq.SecurityEx.recipe.repo;

import com.huytpq.SecurityEx.recipe.entity.Object;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObjectRepo extends JpaRepository<Object,Long> {

    @Query(
            value =
                    "SELECT co "
                            + "FROM Object co "
                            + "WHERE (:keyword IS NULL "
                            + "OR LOWER(co.name) LIKE LOWER(CONCAT('%', REPLACE(:keyword, '%', '\\%'), '%')) ESCAPE '\\' "
                            + "OR LOWER(CAST(CONVERT_TO_UN_SIGN(co.name) AS string)) LIKE LOWER(CONCAT('%', REPLACE(:keyword, '%', '\\%'), '%')) ESCAPE '\\' "
                            + "OR LOWER(co.nameEn) LIKE LOWER(CONCAT('%', REPLACE(:keyword, '%', '\\%'), '%')) ESCAPE '\\' "
                            + "OR LOWER(CAST(CONVERT_TO_UN_SIGN(co.nameEn) AS string)) LIKE LOWER(CONCAT('%', REPLACE(:keyword, '%', '\\%'), '%')) ESCAPE '\\') "
                            + "AND (:tableName IS NULL OR co.tableName = :tableName) "
                            + "ORDER BY co.updatedAt DESC"
    )
    Object findByCondition(String keyword, String tableName);

    Optional<Object> findByName(String name);

    Optional<Object> findById(Long id);

    Optional<Object> findByTableName(String tableName);
}
