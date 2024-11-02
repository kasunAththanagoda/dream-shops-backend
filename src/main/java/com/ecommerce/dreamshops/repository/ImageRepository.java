package com.ecommerce.dreamshops.repository;

import com.ecommerce.dreamshops.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
