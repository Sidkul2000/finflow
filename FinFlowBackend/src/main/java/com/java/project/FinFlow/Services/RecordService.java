package com.java.project.FinFlow.Services;

import com.java.project.FinFlow.DTO.RecordDTO;
import com.java.project.FinFlow.DataRepositories.CategoryRepository;
import com.java.project.FinFlow.DataRepositories.RecordRepository;
import com.java.project.FinFlow.DataRepositories.UserRepository;
import com.java.project.FinFlow.Tables.Categories;
import com.java.project.FinFlow.Tables.Records;
import com.java.project.FinFlow.Tables.User;
import com.java.project.FinFlow.Utils.RecordUtils;

import jakarta.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class RecordService {
    @Autowired
    final private RecordRepository recordRepository;

    @Autowired
    final private CategoryRepository categoryRepository;

    @Autowired
    final private UserRepository userRepository;
    
    @Autowired
	private RecordUtils recordUtils;
    
    private static final Logger logger = LoggerFactory.getLogger(RecordService.class);

    public RecordService(RecordRepository recordRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.recordRepository = recordRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Records getById(Long id) throws EntityNotFoundException {
        return recordRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No record with id: " + id));
    }

    public List<Records> getAll(Long userId) {
        return recordRepository.getAllByUser(userId);
    }

    @Transactional
    public Records insertRecord(RecordDTO recordDTO) {
    	Records record = recordUtils.convertRecordDTO(recordDTO);
        return recordRepository.save(record);
    }

    @Transactional
    Records insertRecord(double amount, LocalDateTime timestamp, Long categoryId, Long userId) {
        Optional<Categories> categoriesOptional = categoryRepository.findById(categoryId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("No user: " + userId);
        } else if (categoriesOptional.isEmpty()) {
            throw new EntityNotFoundException("No category: " + categoryId);
        } else {
            Records record = new Records(amount, timestamp, categoriesOptional.get(), userOptional.get());
            return recordRepository.save(record);
        }
    }

    public Records changeAmount(Long recordId, Double amount) throws EntityNotFoundException {
        Optional<Records> recordsOptional = recordRepository.findById(recordId);
        if (recordsOptional.isEmpty()) {
            throw new EntityNotFoundException("No record with id: " + recordId);
        } else {
            Records record = recordsOptional.get();
            record.setAmount(amount);
            return recordRepository.save(record);
        }
    }

    public Records changeCategory(Long recordId, Long categoryId) throws EntityNotFoundException {
        Optional<Records> recordsOptional = recordRepository.findById(recordId);
        if (recordsOptional.isEmpty()) {
            throw new EntityNotFoundException("No record with id: " + recordId);
        } else {
            Records record = recordsOptional.get();
            Optional<Categories> categoriesOptional = categoryRepository.findById(categoryId);
            if (categoriesOptional.isEmpty()) {
                throw new EntityNotFoundException("No category with id: " + categoryId);
            } else {
                record.setCategory(categoriesOptional.get());
                return recordRepository.save(record);
            }
        }
    }

    public Records changeTimestamp(Long recordId, LocalDateTime timestamp) throws EntityNotFoundException {
        Optional<Records> recordsOptional = recordRepository.findById(recordId);
        if (recordsOptional.isEmpty()) {
            throw new EntityNotFoundException("No record with id: " + recordId);
        } else {
            Records record = recordsOptional.get();
            record.setTimestamp(timestamp);
            return recordRepository.save(record);
        }
    }

    @Transactional
    public void deleteRecord(Long recordId) throws EntityNotFoundException {
        Optional<Records> recordsOptional = recordRepository.findById(recordId);
        if (recordsOptional.isEmpty()) {
            throw new EntityNotFoundException("No record with id: " + recordId);
        } else {
            Records record = recordsOptional.get();
            recordRepository.delete(record);
        }
    }

    public List<Records> getRecordsByTimeRange(Long userId, LocalDateTime start, LocalDateTime end) throws IllegalArgumentException {
        if (start.isBefore(end)) {
            return recordRepository.getBetweenDatetimeRange(userId, start, end);
        } else {
            throw new IllegalArgumentException("start time should be earlier than end time!");
        }
    }

    public Double getSumByDatetimeRange(Long userId, LocalDateTime start, LocalDateTime end) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        return recordRepository.getSumByDatetimeRange(userId, start, end);
    }

    public Double getSumByCategoryAndDatetimeRange(Long userId, Long categoryId, LocalDateTime start, LocalDateTime end) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));
        return recordRepository.getSumByCatogoryAndDatetimeRange(userId, start, end, categoryId);
    }

    public List<Records> findByUserIDCategoryDate(String userId, String categoryId, String fromDateStr, String toDateStr) {
    	LocalDateTime fromDate = null;
    	LocalDateTime toDate = null;

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        try {
            if (fromDateStr != null) {
                fromDate = LocalDate.parse(fromDateStr, formatter).atStartOfDay();
            }
            if (toDateStr != null) {
                toDate = LocalDate.parse(toDateStr, formatter).atTime(23, 59, 59);
            }
        } catch (DateTimeParseException e) {
            logger.error("Error parsing dates", e);
        }

        logger.warn("RecordService: Finding record with userId: {}, categoryId: {}, fromDate: {}, toDate: {}. ", 
                    userId, categoryId, fromDate, toDate);

        return recordRepository.findByUserIDCategoryDate(userId, categoryId, fromDate, toDate);
    }

}
