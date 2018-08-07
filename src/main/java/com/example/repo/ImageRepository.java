package com.example.repo;

import com.example.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Image getImageById(long id);

    List<Image> getImagesByIdOfProduct(long idOfProduct);

}
