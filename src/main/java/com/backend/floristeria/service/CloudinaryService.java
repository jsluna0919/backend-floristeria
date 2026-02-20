package com.backend.floristeria.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public String subirImagen(MultipartFile archivo) throws IOException {
        // Subimos el archivo y obtenemos el mapa de respuesta
        Map subirImagen = cloudinary.uploader().upload(archivo.getBytes(), ObjectUtils.emptyMap());
        // Retornamos la URL pública que genera la nube
        return   subirImagen.get("url").toString();
    }
}
