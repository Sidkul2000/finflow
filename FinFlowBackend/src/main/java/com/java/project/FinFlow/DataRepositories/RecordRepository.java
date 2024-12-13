package com.java.project.FinFlow.DataRepositories;

import com.java.project.FinFlow.Tables.Records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Records, Long> {
    @Query("SELECT r FROM Records r WHERE r.user.userId = :user_id AND r.timestamp BETWEEN :start AND :end")
    public List<Records> getBetweenDatetimeRange(@Param("user_id") Long userId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT r FROM Records r WHERE r.user.userId = :user_id")
    public List<Records> getAllByUser(@Param("user_id") Long userId);

    @Query("SELECT COALESCE(SUM(r.amount), 0) FROM Records r WHERE r.user.userId = :user_id AND r.timestamp BETWEEN :start AND :end")
    public Double getSumByDatetimeRange(@Param("user_id") Long user_id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COALESCE(SUM(r.amount), 0) FROM Records r WHERE r.user.userId = :user_id AND r.category.id = :category_id AND r.timestamp BETWEEN :start AND :end")
    public Double getSumByCatogoryAndDatetimeRange(@Param("user_id") Long user_id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("category_id") Long category_id);

    @Query("SELECT r FROM Records r WHERE " +
    	       "(:userId IS NULL OR r.user.userId = :userId) AND " +
    	       "(:categoryId IS NULL OR r.category.id = :categoryId) AND " +
    	       "(:fromDate IS NULL OR r.timestamp >= :fromDate) AND " +
    	       "(:toDate IS NULL OR r.timestamp <= :toDate)")
	    List<Records> findByUserIDCategoryDate(@Param("userId") String userId, 
	                                 @Param("categoryId") String categoryId, 
	                                 @Param("fromDate") LocalDateTime fromDate, 
	                                 @Param("toDate") LocalDateTime toDate);

}
