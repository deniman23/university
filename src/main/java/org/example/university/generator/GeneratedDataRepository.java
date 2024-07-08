package org.example.university.generator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneratedDataRepository extends JpaRepository<GeneratedData, Integer> {
}