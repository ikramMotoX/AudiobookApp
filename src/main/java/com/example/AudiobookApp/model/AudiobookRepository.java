package com.example.AudiobookApp.model;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AudiobookRepository extends JpaRepository<Audiobook, Long>{
	List<Audiobook> findByTitleContainingIgnoreCase(String title);
}
