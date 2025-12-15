package com.snorlax.modal;

import java.time.LocalDateTime;

import com.snorlax.domain.StoreStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Entity
@Data
public class Store {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String brand;
	
	@OneToOne
	private User storeAdmin;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	private String description;
	
	private String storeType;
	
	private StoreStatus status;
	
	@Embedded //is not a table, used for grouping related fields
	private StoreContact contact = new StoreContact(); 
	
	@PrePersist //Runs before INSERT
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		status = StoreStatus.PENDING;
	}
	
	@PreUpdate //Runs before UPDATE
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
	
}
