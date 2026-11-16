package com.krishna.statecollect_batch.repository;

import com.krishna.statecollect_batch.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DepartmentRepository extends JpaRepository<Department,Integer> {
}
