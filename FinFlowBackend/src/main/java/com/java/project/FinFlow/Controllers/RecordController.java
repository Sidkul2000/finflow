package com.java.project.FinFlow.Controllers;

import com.java.project.FinFlow.Assemblers.RecordAssembler;
import com.java.project.FinFlow.DTO.RecordDTO;
import com.java.project.FinFlow.Services.RecordService;
import com.java.project.FinFlow.Tables.Records;
import jakarta.persistence.EntityNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    final private RecordService recordService;

    @Autowired
    final private RecordAssembler recordAssembler;
    
    private static final Logger logger = LoggerFactory.getLogger(RecordController.class);

    public RecordController(RecordService recordService, RecordAssembler recordAssembler) {
        this.recordService = recordService;
        this.recordAssembler = recordAssembler;
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        try {
            Records record = recordService.getById(id);
            return ResponseEntity.ok(recordAssembler.toModel(record));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @PostMapping("/add")
    public ResponseEntity<Records> addRecord(@RequestBody RecordDTO recordDTO) {
        try {
            Records createdRecord = recordService.insertRecord(recordDTO);
            return ResponseEntity.ok(createdRecord);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> detail(@RequestParam long userId) {
        try {
        	logger.warn("RecordController: Get all records for user.=-------------");
            List<Records> record = recordService.getAll(userId);
            return ResponseEntity.ok(record);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @PutMapping("/{recordId}")
    public ResponseEntity<?> updateRecord(@PathVariable Long recordId,
    									  @RequestParam(required = false) Optional<Double> amount,
    									  @RequestParam(required = false) Optional<Long> categoryId) {
    	try {
    		logger.warn("RecordController: Updating record " + recordId + " amount: " + amount.orElse(0.00) + " categpry: " + categoryId.get() + " " + categoryId.orElse(0L) + ".=-------------");
    		Records updatedRecord = null;
    		if (amount.isPresent()) {
            	recordService.changeAmount(recordId, amount.get());
            }
            if (categoryId.isPresent()) {
            	updatedRecord = recordService.changeCategory(recordId, categoryId.get());
            }
            return ResponseEntity.ok(updatedRecord);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @DeleteMapping("/{recordId}")
    public ResponseEntity<?> deleteRecord(@PathVariable Long recordId) {
    	try {
    		logger.warn("Trying to delete record: " + recordId);
    		recordService.deleteRecord(recordId);
    		return ResponseEntity.ok("Record deleted successfully");
    	} catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping
    public ResponseEntity<?> findByUserIDCategoryDate(
        @RequestParam(required = false) Optional<String> userId,
        @RequestParam(required = false) Optional<String> categoryId,
        @RequestParam(required = false) Optional<String> fromDate,
        @RequestParam(required = false) Optional<String> toDate) {

        logger.warn("RecordController: Finding record with userId: {}, categoryId: {}, fromDate: {}, toDate: {}.",
                    userId.orElse("None"), categoryId.orElse("None"), fromDate.orElse("None"), toDate.orElse("None"));

        try {
            List<Records> records = recordService.findByUserIDCategoryDate(
                userId.orElse(null), categoryId.orElse(null), fromDate.orElse(null), toDate.orElse(null));

            if (!records.isEmpty()) {
                return ResponseEntity.ok(records);
            } else {
                logger.warn("RecordController: Record with userId: {}, categoryId: {}, fromDate: {}, toDate: {} not found.",
                            userId.orElse("None"), categoryId.orElse("None"), fromDate.orElse("None"), toDate.orElse("None"));
                return ResponseEntity.ok(Collections.EMPTY_LIST);
            }
        } catch (Exception e) {
            logger.warn("RecordController: Error in finding record with userId: {}, categoryId: {}, fromDate: {}, toDate: {} - {}",
                        userId.orElse("None"), categoryId.orElse("None"), fromDate.orElse("None"), toDate.orElse("None"), e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}
