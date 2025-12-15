package com.snorlax.modal;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Builder //Builder helps create object safely, Used mainly in DTO mapping or tests
@NoArgsConstructor
@AllArgsConstructor
public class StoreContact {
	
	private String address;
	private String phone;
	
	@Email(message = "Invalid Email Format")
	private String email;
	

}
