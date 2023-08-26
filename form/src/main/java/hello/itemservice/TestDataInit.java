package hello.itemservice;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;

    /**
     * 테스트용 데이터 추가
     */
    /*
     * Spring Bean의 생명 주기
     * 1. bean 등록
     * 2. 의존성 주입
     * 3. 초기화 콜백 >> @PostConstruct
     * 4. 사용
     * 5. 소멸전 콜백 >> @PreDestroy
     * 6. 소멸
     * */
    @PostConstruct // 초기화 콜백
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

}