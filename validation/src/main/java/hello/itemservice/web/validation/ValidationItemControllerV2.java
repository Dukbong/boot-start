package hello.itemservice.web.validation;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
    private final ItemValidator itemValidator; // 검증을 위한 클래스
    
    @InitBinder
    // 컨트롤러가 호출될때 마다 실행된다.
    public void init(WebDataBinder dataBinder) {
    	dataBinder.addValidators(itemValidator);
    }
    
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
    
//    @PostMapping("/add")
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
    
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
    	
    	// BindingResult는 자기가 처리해야할 Object에 대해 알고 있다.
    	// 그 이유는 타켓 바로 뒤에 BindingResult를 넣어야하다는 것에 숨어있다.
    	
    	log.info("Object Name = {}", bindingResult.getObjectName());
    	log.info("target = {}", bindingResult.getTarget());
    	
    	if(!StringUtils.hasText(item.getItemName())) {
    		bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[] {"required.item.itemName"}, null, null)); // default 메시지가 필요없다.
    	}
    	if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
    		bindingResult.addError(new FieldError("item", "price",item.getPrice(), false, new String[] {"range.item.price"}, new Object[] {1000, 1000000}, null));
    	}
    	if(item.getQuantity() == null || item.getQuantity() >= 9999) {
    		bindingResult.addError(new FieldError("item", "quantity",item.getQuantity(), false, new String[] {"max.item.quantity"}, new Object[] {9999}, null));
    	}
    	
    	if(item.getPrice() != null && item.getQuantity() != null) {
    		int resultPrice = item.getPrice() * item.getQuantity();
    		if(resultPrice < 10000) {
    			bindingResult.addError(new ObjectError("item",new String[] {"totalPriceMin"},new Object[] {10000, resultPrice}, null));
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

//    @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
    	// BindingResult는 자기가 처리해야할 Object에 대해 알고 있다.
    	// 그 이유는 타켓 바로 뒤에 BindingResult를 넣어야하다는 것에 숨어있다.
    	
    	log.info("Object Name = {}", bindingResult.getObjectName());
    	log.info("target = {}", bindingResult.getTarget());
    	
    	/*
    	 * rejectValue()
    	 * 1. field : 오류 필드명
    	 * 2. errorCode : 오류 코드 (메시지에 등록된 코드가 아니다.)
    	 * 3. errorArgs : 오류 메시지에서 {0}을 치환하기 위한 값
    	 * 4. default Message : 기본 메시지
    	 * */
    	
    	// ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required");
    	// 비어있거나 널인경우 / 사용은 비추천... 할 수 있는게 공백같은 단순 기능만 있기 때문이다.
    	// 아래 if문과 같은 효과이다.
    	if(!StringUtils.hasText(item.getItemName())) {
//    		bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[] {"required.item.itemName"}, null, null)); // default 메시지가 필요없다.
    		bindingResult.rejectValue("itemName", "required");
    		// 아직은 required라고 만 적으면 messageResolver가 required와 ObjectName과 필드명을 조합하여 message를 찾는다.
    	}
    	if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//    		bindingResult.addError(new FieldError("item", "price",item.getPrice(), false, new String[] {"range.item.price"}, new Object[] {1000, 1000000}, null));
    		bindingResult.rejectValue("price", "range", new Object[] {1000,1000000}, null);
    	}
    	
    	if(item.getQuantity() == null || item.getQuantity() >= 9999) {
//    		bindingResult.addError(new FieldError("item", "quantity",item.getQuantity(), false, new String[] {"max.item.quantity"}, new Object[] {9999}, null));
    		bindingResult.rejectValue("quantity", "max", new Object[] {9999},null);
    	}
    	
    	if(item.getPrice() != null && item.getQuantity() != null) {
    		int resultPrice = item.getPrice() * item.getQuantity();
    		if(resultPrice < 10000) {
//    			bindingResult.addError(new ObjectError("item",new String[] {"totalPriceMin"},new Object[] {10000, resultPrice}, null));
    			bindingResult.reject("totalPriceMin", new Object[] {10000, resultPrice},null);
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
    
//    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
    	
    	itemValidator.validate(item, bindingResult);
    	
    	if(bindingResult.hasErrors()) {
    		log.info("errors = {}", bindingResult);
    		return "/validation/v2/addForm";
    	}
    	
    	Item savedItem = itemRepository.save(item);
    	redirectAttributes.addAttribute("itemId", savedItem.getId());
    	redirectAttributes.addAttribute("status", true);
    	return "redirect:/validation/v2/items/{itemId}";
    }
    
    @PostMapping("/add")
    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
    	
    	// @Validataed 어노테이션을 적으면 위에서 @InitBinder로 만든것이 작동한다.
    	// @Validataed는 검증기를 실행해라 라는 어노테이션이다.
    	// 이 어노테이션이 붙으면 앞서 등록한 WebDataBinder에 등록한 검증기를 찾아서 실행하낟.
    	// 만약 여러 검증기가 등록되면 그 중 어느것을 실행할지 구분해야하는데 이를 supports()가 해준다.
    	// support(Item.getClass())가 호출되고 결과가 true이면 validate()가 호출된다.
    	
//    	itemValidator.validate(item, bindingResult);
    	
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

