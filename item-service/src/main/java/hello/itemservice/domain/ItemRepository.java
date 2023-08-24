package hello.itemservice.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import hello.itemservice.domain.item.Item;

@Repository
public class ItemRepository {

	private static final Map<Long, Item> store = new HashMap<>();
	// 실무에서는 멀티 스레드 환경이기 때문에 동기화 처리된 ConcurrentHashMap<>();
	private static long sequence = 0L;
	// 실무에서는 동기화 처리된 LongAccumulator
	
	public Item save(Item item) {
		item.setId(++sequence);
		store.put(item.getId(), item);
		return item;
	}
	
	public Item findById(Long id) {
		return store.get(id);
	}
	
	public List<Item> findAll(){
		return new ArrayList<>(store.values());
	}
	
	public void update(Long itemId, Item updateParam) {
		Item findItem = findById(itemId);
		findItem.setItemName(updateParam.getItemName());
		findItem.setPrice(updateParam.getPrice());
		findItem.setQuantity(updateParam.getQuantity());
	}
	
	// Test용도
	public void clearStore() {
		store.clear();
	}
}
