package com.example.services;

import com.example.models.Image;

import java.util.List;

/**
 * Created by Keni0k on 25.07.2018.
 */

public interface ImageService extends BaseService<Image> {

    List<Image> getByProductId(long id);

    void editImageToken(int id, String token);

}