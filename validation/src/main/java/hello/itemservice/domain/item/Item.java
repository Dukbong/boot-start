package hello.itemservice.domain.item;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import lombok.Data;

@Data
// @ScriptAssert(lang = "javascript", script="_this.price * _this.quantity >= 10000", message = "총합이 10000원 이상이어야 합니다.")
// Object Error는 이런거보다 직접 만드는게 좋다. 해당 기능이 너무 약하며 복잡한 경우 사용할 수 없다.
public class Item {

	@NotNull(groups = UpdateCheck.class)
    private Long id;
    
    @NotBlank(message = "공백 x", groups = {SaveCheck.class, UpdateCheck.class}) // 이렇게 메시지를 바꿀 수 있따. 
    // 빈값 + 공백만 있는 경우를 허용하지 않는다.
    private String itemName;
    
    @NotNull(groups = {SaveCheck.class, UpdateCheck.class}) // null을 허용하지 않는다.
//    @Range(min=1000, max=1000000) // 범위 안의 값이어야한다.
    @Range(max = 1000000, min=1000)
    private Integer price;
    
    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Max(value=9999, groups = {SaveCheck.class}) // 최대값 까지만 허용한다.
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
