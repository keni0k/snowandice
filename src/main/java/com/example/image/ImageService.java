package com.example.image;

import com.example.repo.RepoService;

import java.util.List;

/**
 * Created by Keni0k on 25.07.2018.
 */

public interface ImageService extends RepoService<Image> {

    List<Image> getByProductId(long id);

    void editImageToken(int id, String token);

}