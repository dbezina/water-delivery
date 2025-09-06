package com.bezina.water_delivery.delivery_service.DAO;

import com.bezina.water_delivery.core.model.assignment.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, String> {
    Optional<Assignment> findByOrderNo(Long orderNo);

    List<Assignment> findAllByCourierId(String courierId);
    List<Assignment> findAllByOrderByUpdatedAtDesc();


    List<Assignment> findAllByCourierIdOrderByUpdatedAtDesc(String courierId);
}
