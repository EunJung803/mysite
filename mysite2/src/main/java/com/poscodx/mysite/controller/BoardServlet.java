package com.poscodx.mysite.controller;

import java.util.Map;

import com.poscodx.mysite.action.controller.board.DeleteAction;
import com.poscodx.mysite.action.controller.board.ListAction;
import com.poscodx.mysite.action.controller.board.ModifyAction;
import com.poscodx.mysite.action.controller.board.ModifyFormAction;
import com.poscodx.mysite.action.controller.board.ViewAction;
import com.poscodx.mysite.action.controller.board.WriteAction;
import com.poscodx.mysite.action.controller.board.WriteFormAction;

public class BoardServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Action> mapAction = Map.of(
		"writeform", new WriteFormAction(),
		"write", new WriteAction(),
		"view", new ViewAction(),
		"delete", new DeleteAction(),
		"modifyform", new ModifyFormAction(),
		"modify", new ModifyAction()
	);
			
	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new ListAction());
	}

}
