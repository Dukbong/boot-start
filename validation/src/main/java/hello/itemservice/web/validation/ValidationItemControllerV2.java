package hello.itemservice.web.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/* BindingResult*/
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
@Slf4j
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
    	// Item 객체에 담길 수 없는 타입을 들어오면 BindingResult에 담긴다.
    	// 필수 : @ModelAttribute 다음에 바로 BindingResult가  작동한다.
    	
    	// 검증 오류 결과를 보관
//    	BindingResult가 대신 해주기 때문에 Map을 만들 필요가 없다.
//    	Map<String, String> errors = new HashMap<>();
    	
    	// 검증 로직 시작
    	if(!StringUtils.hasText(item.getItemName())) {
//    		errors.put("itemName", "상품 이름은 필수 입니다.");
    		bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수 입니다."));
    		// 필드 부분에 있는 것들은 FieldError로
    		// 1. 현재 검증할 객체의 참조변수명 item
    		// 2. Item 객체의 필드명
    		// 3. 오류 메시지
    	}
    	if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//    		errors.put("price", "가격은 1,000 ~ 1,000,000까지 허용합니다.");
    		bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000까지 허용합니다."));
    	}
    	if(item.getQuantity() == null || item.getQuantity() >= 9999) {
//    		errors.put("quantity", "수량은 최대 9,999개 입니다.");
    		bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999개 입니다."));
    	}
    	
    	// 특정 필드가 아닌 복합 룰 검증
    	if(item.getPrice() != null && item.getQuantity() != null) {
    		int resultPrice = item.getPrice() * item.getQuantity();
    		if(resultPrice < 10000) {
//    			errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice);
    			bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
    		}
    	}
    	// 검증 로직 끝
    	
    	// 검증에 실패하면 다시 입력 폼으로
//    	if(!errors.isEmpty()) {
    	if(bindingResult.hasErrors()) {
    		log.info("errors = {}", bindingResult);
//    		model.addAttribute("errors", errors);
//    		BindingResult는 model에 담지 않아도 넘어간다.
    		return "/validation/v2/addForm";
    	}
    	
    	// 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }
    
    @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
    	
    	/*
    	 * FieldError 생성자 파라미터 목록
    	 * 1. ObjectName : 오류가 발생한 객체 이름
    	 * 2. field : 오류 필드 (1번의 인스턴스 변수들)
    	 * 3. rejectedValue : 사용자가 입력한 값 (거절된 값)
    	 * 4. bindingFailure : 타입 오류 같은 바인딩 실패인지, 검증 실패인지 구분
    	 *    - false : 검증 실패 / true : 타입 오류
    	 * 5. codes : 메시지 코드
    	 * 6. arguments : 메시지에서 사용하는 인자
    	 * 7. defaultMessage : 기본 오류 메시지*/
    	
    	if(!StringUtils.hasText(item.getItemName())) {
//    		bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수 입니다."));
    		bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null, "상품 이름은 필수 입니다."));
    	}
    	if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//    		bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000까지 허용합니다."));
    		bindingResult.addError(new FieldError("item", "price",item.getPrice(), false, null, null, "가격은 1,000 ~ 1,000,000까지 허용합니다."));
    	}
    	if(item.getQuantity() == null || item.getQuantity() >= 9999) {
//    		bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999개 입니다."));
    		bindingResult.addError(new FieldError("item", "quantity",item.getQuantity(), false, null, null, "수량은 최대 9,999개 입니다."));
    	}
    	
    	if(item.getPrice() != null && item.getQuantity() != null) {
    		int resultPrice = item.getPrice() * item.getQuantity();
    		if(resultPrice < 10000) {
    			bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
    		}
    	}
    	
    	if(bindingResult.hasErrors()) {
    		log.info("errors = {}", bindingResult);
    		return "/validation/v2/addForm";
    	}
    	
    	Item savedItem = itemRepository.save(item);
    	redirectAttributes.addAttribute("itemId", savedItem.getId());
    	redirectAttributes.addAttribute("status", true);
    	return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

