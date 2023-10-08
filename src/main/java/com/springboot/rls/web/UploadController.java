package com.springboot.rls.web;



import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.rls.model.Images;
import com.springboot.rls.reponsitory.UploadRepository;


@Controller
public class UploadController {

	@Autowired
	private UploadRepository uploadRepository;

	public UploadController() {
		super();
	}

	@PostMapping("/upload")
	public String imageUpload(@RequestParam("file") MultipartFile file, HttpSession session, Model model) throws IOException {
		
		Images im = new Images();
		
		Path path = Paths.get("uploads/"); // get path directory
		
		try {
			InputStream inputStream = file.getInputStream();
			// copy file từ path vào inputStream , ghì đè nếu file đã tồn tại
			Files.copy(inputStream, path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			System.out.println(file.getOriginalFilename());
			System.out.println(inputStream);
			im.setImageName(file.getOriginalFilename().toLowerCase());
			uploadRepository.save(im);
		
			model.addAttribute("images" , im.getImageName().toString());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/showinfo";
	}
	
}
