package model.componets;

import manager.CannonManager;
import model.interfaces.OnClickListener;

/**
 * 降低大炮质量的按钮逻辑
 * @author Xiloerfan
 *
 */
public class DownCannonButtonListener implements OnClickListener{

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		CannonManager.getCannonManager().downCannon();
	}

}
