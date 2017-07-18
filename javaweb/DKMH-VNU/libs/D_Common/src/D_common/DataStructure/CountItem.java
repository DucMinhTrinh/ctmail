package D_common.DataStructure;

import java.util.HashMap;

public class CountItem {
    public CountItem() {
        items = new HashMap();
    }
    
    public void addItem(String item) {
        if (items.containsKey(item)) {
            Long val = items.get(item);
            items.put(item, val + 1);
        } else {
            Long val = (long) 1;
            items.put(item, val);
        }
    }
    
    public String getTheMostItem() {
        String ch = "";
        long count_max = -1;
        for (HashMap.Entry<String, Long> item : items.entrySet()) {
            if (item.getValue() > count_max) {
                count_max = item.getValue();
                ch = item.getKey();
            }
        }
        return ch;
    }
    
    HashMap<String, Long> items;
}
