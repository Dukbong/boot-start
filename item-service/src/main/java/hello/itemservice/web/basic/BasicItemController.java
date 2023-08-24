package hello.itemservice.web.basic;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.itemservice.domain.ItemRepository;
import hello.itemservice.domain.item.Item;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

	private final ItemRepository itemRepository;

//	생성자가 하나면 @Autowired를 생략할 수 있기 때문에 lombok의 @RequiredArgsConstrucotr 이용가능
//	@RequriedArgsConstructor는 final이 붙은 필드를 생성자로 만들어준다.
	
//	@Autowired
//	public BasicItemController(ItemRepository itemRepository) {
//		this.itemRepository = itemRepository;
//	}
	
	@GetMapping
	public String items(Model model) {
		List<Item> items = itemRepository.findAll();
		model.addAttribute("items", items);
		return "basic/items";
	}
	
	@GetMapping("/{itemId}")
	public String item(@PathVariable Long itemId, Model model) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "basic/item";
	}
	
	@GetMapping("/add")
	public String addForm() {
		return "basic/addForm";
	}
//	@PostMapping("/add")
	public String addItemV1(@RequestParam String itemName,
					   @RequestParam int price,
					   @RequestParam Integer quantity,
					   Model model)
	{
		Item item = new Item();
		item.setItemName(itemName);
		item.setPrice(price);
		item.setQuantity(quantity);
		
		itemRepository.save(item);
		model.addAttribute("item", item);
		
		return "basic/item";
	}
	
	//@PostMapping("/add")
	public String addItemV2(@ModelAttribute("item") Item item)
	{
		itemRepository.save(item);
		
//		model.addAttribute("item", item);
//		@ModelAttribute(name="xxx") Test test
//		이렇게 이름을 지정하면 자체적으로 Model객체에 만들고 담아주기 까지 한다.
//		>> new Model().addAttribute("xxx", test)를 자동으로 해주는거다.
		
		return "basic/item";
	}
	
	//@PostMapping("/add")
	public String addItemV3(@ModelAttribute Item item)
	{
		itemRepository.save(item);
		
//		model.addAttribute("item", item);
//		@ModelAttribute에 이름을 지정하지 않으면
//		클래스명의 앞글자로 소문자로 변경하여 new Modle().addAttribute("xxx", xxx)를 자동으로 해준다.
		
		return "basic/item";
	}
	
	//@PostMapping("/add")
	public String addItemV4(Item item) // @ModelAttribute 생략 가능
	{
		itemRepository.save(item);
		return "basic/item";
	}
	
	//@PostMapping("/add")
	public String addItemV5(Item item) 
	{
		itemRepository.save(item);
		return "redirect:/basic/items/" + item.getId();
	}
	@PostMapping("/add")
	public String addItemV6(Item item,RedirectAttributes redirectAttribute) 
	{
		Item savedItem = itemRepository.save(item);
		redirectAttribute.addAttribute("itemId", savedItem.getId());
		redirectAttribute.addAttribute("status", true);
		return "redirect:/basic/items/{itemId}";
		// RedirectAttribute.addAttribute에 저장한 값의 키값으로 치환된다.
		// 만약 {}으로 치환 시키지 않으면 쿼리 파라미터로 생성된다.
		// 위에 코드는 /basic/items/1?statud=true 가 나오게 되는것이다.
	}
	
	@GetMapping("/{itemId}/edit")
	public String editForm(@PathVariable Long itemId, Model model) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "basic/editForm";
	}
	
	@PostMapping("/{itemId}/edit")
	public String edit(@PathVariable Long itemId, Item item) {
		itemRepository.update(itemId, item);
		// @ModelAttribute 내부에서 Model에 item을 저장한다.
		return "redirect:/basic/items/{itemId}";
		// 반환 값에도 @PathVariable에 있는 값으로 치환된다.
	}
	
	/**
	 * 테스트용 데이터 추가
	 */
	@PostConstruct
	public void init() {
		itemRepository.save(new Item("itemA", 2000, 10));
		itemRepository.save(new Item("itemB", 3000, 20));
	}
}
