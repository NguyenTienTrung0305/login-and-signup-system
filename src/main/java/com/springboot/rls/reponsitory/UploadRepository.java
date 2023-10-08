package com.springboot.rls.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.rls.model.Images;


public interface UploadRepository extends JpaRepository<Images, Integer>{
	Images findByImageName(String imageName);
}
