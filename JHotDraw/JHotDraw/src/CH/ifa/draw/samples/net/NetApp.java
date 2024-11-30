/*
 * @(#)NetApp.java
 *
 * Project:		JHotdraw - a GUI framework for technical drawings
 *				http://www.jhotdraw.org
 *				http://jhotdraw.sourceforge.net
 * Copyright:	 by the original author(s) and all contributors
 * License:		Lesser GNU Public License (LGPL)
 *				http://www.opensource.org/licenses/lgpl-license.html
 */

package CH.ifa.draw.samples.net;

import javax.swing.JToolBar;
import CH.ifa.draw.framework.*;
import CH.ifa.draw.standard.*;
import CH.ifa.draw.figures.*;
import CH.ifa.draw.application.DrawApplication;

/**
 * @version <$CURRENT_VERSION$>
 */
public class NetApp extends DrawApplication {

	public NetApp() {
		super("Net");
	}

	CrowFootMany crowFootMany = new CrowFootMany();
	CrowFootOne crowFootOne = new CrowFootOne();

	protected void createTools(JToolBar palette) {
		super.createTools(palette);

		Tool tool = new TextTool(this, new NodeText());
		palette.add(createToolButton(IMAGES + "TEXT", "Text Tool", tool));

		tool = new TextTool(this, new EntityNode());
		palette.add(createToolButton(IMAGES + "ENT", "Entity", tool));

		// tool = new TextTool(this, new NodeText());
		// palette.add(createToolButton(IMAGES + "Text", "Text Tool", tool));

		// tool = new CreationTool(this, new NodeFigure());
		// palette.add(createToolButton(IMAGES + "RECT", "Create Org Unit", tool));

		// tool = new CreationTool(this, new EntityNode());
		// palette.add(createToolButton(IMAGES + "RECT", "Create Entity", tool));

		tool = new ConnectionTool(this, new NewConnection(crowFootOne, crowFootOne));
		palette.add(createToolButton(IMAGES + "OTO", "Connection Tool", tool));

		tool = new ConnectionTool(this, new NewConnection(crowFootOne, crowFootMany));
		palette.add(createToolButton(IMAGES + "OTM", "Connection Tool", tool));

		tool = new ConnectionTool(this, new NewConnection(crowFootMany, crowFootMany));
		palette.add(createToolButton(IMAGES + "MTM", "Connection Tool", tool));
	}

	// -- main -----------------------------------------------------------

	public static void main(String[] args) {
		DrawApplication window = new NetApp();
		window.open();
	}
}
