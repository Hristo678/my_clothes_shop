package com.example.clothes_shop.services.cloudinary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CloudinaryImageRepository extends JpaRepository<CloudinaryImage, Long> {
    CloudinaryImage findByPublicId(String publicId);
}
