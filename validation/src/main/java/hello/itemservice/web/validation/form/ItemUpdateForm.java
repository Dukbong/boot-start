package hello.itemservice.web.validation.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class ItemUpdateForm {
	
	@NotNull
	private Long id;

	@NotBlank
	private String itemName;
	
	@NotNull
	@Range(max=1000000, min=1000)
	private Integer price;
	
	// 수정에서는 수량은 자유롭게 변경 가능
	private Integer quantity;
}
