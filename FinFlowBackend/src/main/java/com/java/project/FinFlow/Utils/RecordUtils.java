package com.java.project.FinFlow.Utils;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.project.FinFlow.DTO.RecordDTO;
import com.java.project.FinFlow.Services.CategoryService;
import com.java.project.FinFlow.Services.UserService;
import com.java.project.FinFlow.Tables.User;
import com.java.project.FinFlow.Tables.Categories;
import com.java.project.FinFlow.Tables.Records;

@Component
public class RecordUtils {
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private CategoryService categoryService;
	
	private static final Logger logger = LoggerFactory.getLogger(RecordUtils.class);
	
	public Records convertRecordDTO(RecordDTO dto) {
	    Records record = new Records();
	    record.setAmount(dto.getAmount());
	    record.setTimestamp(dto.getTimestamp());
	    
	    // Assume service methods exist to fetch entities by ID
	    Optional<User> user = userService.getById(dto.getUserId());
	    Categories category = categoryService.getById(dto.getCategoryId());
	    
	    if (user.isPresent()) {
	    	record.setUser(user.get());
	    }
	    record.setCategory(category);
	    
	    logger.debug("convertRecordDTO: {}", record);
	    
	    return record;
	}
}
