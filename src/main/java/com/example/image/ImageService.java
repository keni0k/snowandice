package com.example.image;

import java.util.List;

/**
 * Created by Keni0k on 25.07.2018.
 */

public interface ImageService {
    void addImage(Image image);

    void delete(long id);

    Image getById(long id);

    List<Image> getByProductId(long id);

    void editImageToken(int id, String token);

    List<Image> getAll();

}