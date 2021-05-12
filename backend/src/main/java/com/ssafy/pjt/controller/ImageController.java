package com.ssafy.pjt.controller;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.pjt.dto.UploadFile;
import com.ssafy.pjt.service.ImageService;
import com.ssafy.pjt.util.MediaUtils;

/**
 * @author https://github.com/woobong/spring-boot-jpa-summernote-image-upload-example/
 */
@Controller
@RequestMapping("/api")
public class ImageController {
    private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);
	@Autowired
	ImageService imageService;

	/**
	 * 파일 업로드
	 * 
	 * @param file
	 * @return 고유URL주소
	 */
	@PostMapping({ "/data", "/image" })
	@ResponseBody
	public ResponseEntity<Object> handleFileUpload(@RequestParam("file") MultipartFile file) {
		try {
			UploadFile uploadedFile = imageService.store(file);
			return ResponseEntity.ok().body("/api/data/" + uploadedFile.getId());
		} catch (Exception e) {
			logger.error(e.toString());
			return ResponseEntity.badRequest().build();
		}
	}

	/**
	 * 파일 다운로드
	 * 
	 * @param 파일고유번호
	 * 
	 * @return HTTP프로토콜처리된파일
	 */
	@GetMapping({ "/data/{fileId}", "/image/{fileId}" })
	@ResponseBody
	public ResponseEntity<Object> serveFile(@PathVariable int fileId) {
		try {
			UploadFile uploadedFile = imageService.load(fileId);
			HttpHeaders headers = new HttpHeaders();

			String fileName = uploadedFile.getOriginFileName();
			headers.add(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\"");

			if (MediaUtils.containsImageMediaType(uploadedFile.getContentType())) {
				headers.setContentType(MediaType.valueOf(uploadedFile.getContentType()));
			} else {
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			}

			Resource resource = imageService.loadAsResource(uploadedFile.getSaveFileName());
			return ResponseEntity.ok().headers(headers).body(resource);

		} catch (Exception e) {
			logger.info(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

	/**
	 * 프로필 이미지 업로드
	 */
	@PostMapping({ "/member/profile/{uid}" })
	@ResponseBody
	public ResponseEntity<Object> handleProfileImageUpload(@PathVariable int uid, @RequestParam("file") MultipartFile file) {
		try {
			imageService.store(file, "/profile", String.valueOf(uid), true);
			return ResponseEntity.ok().body("/profile/" + uid);
		} catch (Exception e) {
			logger.info(e.getMessage());		
			return ResponseEntity.badRequest().body(e);
		}
	}

}
