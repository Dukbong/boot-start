package hello.itemservice.web.form;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.itemservice.domain.item.DeliveryCode;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
@Slf4j
public class FormItemController {

    private final ItemRepository itemRepository;
    
    /* 메소드에 @ModelAttribute("KeyName")
     * 이러면 해당 컨트롤러에서는 모든 경로로 아래 keyName으로 반환된 값을 불러올수 있다.
     * 장점 : 중복되는 코드를 줄일 수 있다.
     * 성능을 고려한다면 static 영역에 저장해서 불러오는 방식으로 하는게 좋다.
     * */
    @ModelAttribute("regions")
    public Map<String, String> regions(){
    	Map<String, String> regions = new LinkedHashMap<>();
    	regions.put("SEOUL", "서울");
    	regions.put("BUSAN", "부산");
    	regions.put("JEJU", "제주");
    	return regions;
    }
    
    @ModelAttribute("itemType")
    public ItemType[] itemType() {
    	ItemType[] values = ItemType.values();
    	// Enum클래스.values() 하면  Enum에 정의된 것을 배열로 반환한다.
    	return values;
    }
    
    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
    	List<DeliveryCode> deliveryCodes = new ArrayList<>();
    	deliveryCodes.add(new DeliveryCode("FAST", "빠른배송"));
    	deliveryCodes.add(new DeliveryCode("NORMAL", "일반배송"));
    	deliveryCodes.add(new DeliveryCode("SLOW", "느린배송"));
    	
    	return deliveryCodes;
    }
    

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "form/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
//    	Map<String, String> regions = new LinkedHashMap<>();
//    	regions.put("SEOUL", "서울");
//    	regions.put("BUSAN", "부산");
//    	regions.put("JEJU", "제주");
//    	model.addAttribute(regions);
        return "form/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
    	model.addAttribute("item", new Item());
//    	Map<String, String> regions = new LinkedHashMap<>();
//    	regions.put("SEOUL", "서울");
//    	regions.put("BUSAN", "부산");
//    	regions.put("JEJU", "제주");
//    	model.addAttribute("regions",regions);
        return "form/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
    	
    	log.info("item.open = {}", item.getOpen());
    	log.info("item.regions = {}", item.getRegions());
    	log.info("item.itemType = {}", item.getItemType());
    	
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/form/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
//    	Map<String, String> regions = new LinkedHashMap<>();
//    	regions.put("SEOUL", "서울");
//    	regions.put("BUSAN", "부산");
//    	regions.put("JEJU", "제주");
//    	model.addAttribute("regions", regions);
        return "form/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/form/items/{itemId}";
    }

}

