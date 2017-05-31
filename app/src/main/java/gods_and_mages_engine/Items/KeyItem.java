package gods_and_mages_engine.Items;

import gods_and_mages_engine.World;

public class KeyItem extends BaseItem{
	public KeyItem(String name, String desc){
		super(name, name, desc, World.UNSELLABLE_ITEM_PRICE, World.UNSELLABLE_ITEM_PRICE);
	}
}
