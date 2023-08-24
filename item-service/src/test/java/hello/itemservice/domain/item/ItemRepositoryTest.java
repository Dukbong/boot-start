package hello.itemservice.domain.item;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hello.itemservice.domain.ItemRepository;

public class ItemRepositoryTest {

	ItemRepository itemRepository = new ItemRepository();
	
	@AfterEach
	void afterEach(){
		itemRepository.clearStore();
	}
	
	@Test
	@DisplayName("Repository 저장")
	public void save() {
		// given
		Item item = new Item("itemA", 2000, 10);
		
		// when
		Item saveItem = itemRepository.save(item);
		
		// then
		Assertions.assertThat(saveItem).isEqualTo(item);
	}
	
	@Test
	@DisplayName("Repository 아이디로 찾기")
	void findById() {
		// given
		Item item = new Item("itemA", 200, 10);
		itemRepository.save(item);
		Long itemId = item.getId();
		
		// when
		Item findItem = itemRepository.findById(itemId);
		
		// then
		Assertions.assertThat(findItem).isEqualTo(item);
	}
	
	@Test
	@DisplayName("Repository 모두 찾기")
	void findAll() {
		// given
		Item item1 = new Item("itemA", 2000, 10);
		Item item2 = new Item("itemB", 3000, 20);
		
		itemRepository.save(item1);
		itemRepository.save(item2);
		
		// when
		List<Item> items = itemRepository.findAll();
		
		// then
		Assertions.assertThat(items.size()).isSameAs(2);
		Assertions.assertThat(items).contains(item1, item2);
	}
	
	@Test
	@DisplayName("Repository 업데이트")
	void updateItem() {
		// given
		Item savedItem = new Item("itemA", 2000, 100);
		itemRepository.save(savedItem);
		Long itemId = savedItem.getId();
		
		// when
		Item updateParam = new Item("itemUp", 3000, 200);
		itemRepository.update(itemId, updateParam);
		
		// then
		Item findItem = itemRepository.findById(itemId);
		Assertions.assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
		Assertions.assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
		Assertions.assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
	}
}
