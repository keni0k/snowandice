package com.example.image;

import com.example.repo.ImageRepository;
import com.example.repo.UserRepository;

import java.util.List;

public class ImageServiceImpl implements ImageService {

    private ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void addImage(Image image) {
        imageRepository.saveAndFlush(image);
    }

    @Override
    public void delete(long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public Image getById(long id) {
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
    public List<Image> getAll() {
        return imageRepository.findAll();
    }
}
