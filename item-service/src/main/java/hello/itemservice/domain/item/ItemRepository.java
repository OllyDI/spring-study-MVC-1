package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository - 데이터 접근
 * Service - 비즈니즈 로직 구현
 * Controller - HTTP 요청 처리
 */
@Repository
public class ItemRepository {
    // 실제로는 HashMap 사용 금지 -> 여러개가 동시 접근 시 오류 발생 -> ConCurrentHashMap
    // static long도 마찬가지

    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    // 원래는 아이디를 제외한 객체Dto를 생성하여 하는 게 좋음
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
