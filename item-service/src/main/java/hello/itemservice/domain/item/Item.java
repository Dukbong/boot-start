package hello.itemservice.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
// @Data는 핵심 도메인 모델에 사용하기 위험하다.
public class Item {
	private Long id;
	private String itemName;
	private Integer price;
	private Integer quantity;
	
	public Item(String itemName, Integer price, Integer quantity) {
		this.itemName = itemName;
		this.price = price;
		this.quantity = quantity;
	}
}
