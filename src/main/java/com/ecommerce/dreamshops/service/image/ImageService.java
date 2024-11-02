package com.ecommerce.dreamshops.service.image;

import com.ecommerce.dreamshops.dto.ImageDto;
import com.ecommerce.dreamshops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImageService implements IImageService {

    @Override
    public Image getImageById(Long id) {
        return null;
    }

    @Override
    public void deleteImageById(Long id) {

    }

    @Override
    public List<ImageDto> saveImages(Long productId, List<MultipartFile> files) {
        return List.of();
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {

    }
}
