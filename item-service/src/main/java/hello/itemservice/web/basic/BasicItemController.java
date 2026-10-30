package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }


//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName, @RequestParam int price,
                       @RequestParam Integer quantity, Model model) {
//        Item item = new Item(itemName, price, quantity);
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    /**
     * ModelAttribute 방식
     * ModelAttribute("item") 이런식으로 이름을 지정해주면 addAttribute의 name을 item 으로 지정해줌 그래서 아래 코드도 필요X
     * ModelAttribute의 이름을 생략하면 클래스의 이름에서 앞 글자만 소문자로 변경하여 이름으로 사용 -> Item = item
     */
    //    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }


    // 새로고침시 Post add가 계속 호출되어 item이 계속 생성되는 치명적인 오류가 발생함 -> 수정 필요
    //    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    // ModelAttribute 자체도 생략가능 -> 객체 타입은 ModelAttribute
    // 잘 저장되었는지 알수가 없음 -> 해결 필요
    //    @PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        Item saveItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", saveItem.getId());        // 이 값을 return {itemId}으로 쓸 수 있음
        redirectAttributes.addFlashAttribute("status", true);   // 쿼리로 status=true 값이 들어가고 사라짐
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("{itemId}/edit")
    public String edit(@PathVariable long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";
    }


    /**
     * 테스트 용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
