package com.springboot.rls.web;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ImageUploadController {
	@RequestMapping(value = "getimage/{imageName}" , method = RequestMethod.GET)
	public ResponseEntity<ByteArrayResource> getImage(@PathVariable("imageName") String imageName){
		if(!imageName.equals("") || imageName != null) {
			try {
				Path path = Paths.get("uploads" , imageName);
				System.err.println(path);
				byte[] buffer = Files.readAllBytes(path);
				ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
				return ResponseEntity.ok()
						.contentLength(buffer.length)
						.contentType(MediaType.parseMediaType("image/png"))
						.body(byteArrayResource);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return ResponseEntity.badRequest().build();
	}
}
