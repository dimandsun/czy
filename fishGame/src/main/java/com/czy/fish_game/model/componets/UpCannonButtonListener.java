package com.czy.fish_game.model.componets;

import com.czy.fish_game.manager.CannonManager;
import com.czy.fish_game.model.interfaces.OnClickListener;

/**
 * 提升大炮质量的按钮逻辑
 * @author Xiloerfan
 *
 */
public class UpCannonButtonListener implements OnClickListener{

	@Override
	public void onClick() {
		CannonManager.getCannonManager().upCannon();
	}

}
