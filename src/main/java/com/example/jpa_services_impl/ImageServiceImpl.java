package com.example.jpa_services_impl;

import com.example.models.Image;
import com.example.repo.ImageRepository;
import com.example.services.ImageService;

import java.util.List;

public class ImageServiceImpl implements ImageService {

    private ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void add(Image image) {
        imageRepository.saveAndFlush(image);
    }

    @Override
    public void update(Image model) {
        imageRepository.saveAndFlush(model);
    }

    @Override
    public void delete(Image model) {
        imageRepository.delete(model);
    }

    @Override
    public void delete(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public Image findById(Long id) {
        return imageRepository.getImageById(id);
    }

    @Override
    public List<Image> getByProductId(long idOfProduct) {
        return imageRepository.getImagesByIdOfProduct(idOfProduct);
    }

    @Override
    public void editImageToken(int id, String token) {
        Image image = imageRepository.getImageById(id);
        image.setToken(token);
        imageRepository.saveAndFlush(image);
    }

    @Override
    public List<Image> findAll() {
        return imageRepository.findAll();
    }
}
