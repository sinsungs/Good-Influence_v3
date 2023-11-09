package com.influence.domain.meet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.influence.domain.meet.dto.MeetDTO;
import com.influence.domain.meet.entity.Meet;
import com.influence.domain.meet.service.MeetService;
import com.influence.global.utils.S3UploadService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/meet")
@RequiredArgsConstructor
public class MeetController {

    private final MeetService meetService;
//    private final S3UploadService s3UploadService;
	
    @PostMapping("/create")
    public ResponseEntity<Boolean> createMeet(@RequestBody MeetDTO dto, Authentication authentication) {
    	
        boolean result = meetService.createMeet(dto, authentication);
        
	    if (result) {
	        return ResponseEntity.ok(true);
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
	    }
    }
    
    
    @GetMapping("/list")
    public ResponseEntity<List<MeetDTO>> listMeet() {
    	
        List<MeetDTO> listMeet = meetService.listMeet();
        
		if(!listMeet.isEmpty()) {
			return ResponseEntity.ok(listMeet);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    }
    
    
    @PutMapping("/update/{meetId}")
    public ResponseEntity<Boolean> updateMeet(@PathVariable Long meetId, @RequestBody MeetDTO dto) {
    	
    	boolean result = meetService.updateMeet(meetId, dto);
        
	    if (result) {
	        return ResponseEntity.ok(true);
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
	    }
    }
    

    @DeleteMapping("/delete/{meetId}")
    public ResponseEntity<Boolean> deleteMeet(@PathVariable Long meetId, Authentication authentication) {
    	
        boolean result = meetService.deleteMeet(meetId);
        
	    if (result) {
	        return ResponseEntity.ok(true);
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
	    }
    }
    
    
    
}
