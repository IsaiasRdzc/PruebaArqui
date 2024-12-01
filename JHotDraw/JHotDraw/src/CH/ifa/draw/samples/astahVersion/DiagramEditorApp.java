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

package CH.ifa.draw.samples.astahVersion;

import javax.swing.JToolBar;
import CH.ifa.draw.framework.*;
import CH.ifa.draw.standard.*;
import CH.ifa.draw.figures.*;
import CH.ifa.draw.application.DrawApplication;

/**
 * @version <$CURRENT_VERSION$>
 */
public class DiagramEditorApp extends DrawApplication {

	private CrowFootMany crowFootMany;
	private CrowFootOne crowFootOne;

	public DiagramEditorApp() {
		super("Diagram Editor");
		crowFootMany = new CrowFootMany();
		crowFootOne = new CrowFootOne();
	}

	// CrowFootMany crowFootMany = new CrowFootMany();
	// CrowFootOne crowFootOne = new CrowFootOne();

	protected void createTools(JToolBar palette) {
		super.createTools(palette);

		Tool tool = new TextTool(this, new NodeText());
		palette.add(createToolButton(IMAGES + "TEXT", "Text Area", tool));

		tool = new TextTool(this, new NodeComponent());
		palette.add(createToolButton(IMAGES + "ENT", "New Entity", tool));

		tool = new ConnectionTool(this, new DecoratedLineConnection(crowFootOne, crowFootOne));
		palette.add(createToolButton(IMAGES + "OTO", "One to One Connection", tool));

		tool = new ConnectionTool(this, new DecoratedLineConnection(crowFootOne, crowFootMany));
		palette.add(createToolButton(IMAGES + "OTM", "One to Many Connection", tool));

		tool = new ConnectionTool(this, new DecoratedLineConnection(crowFootMany, crowFootMany));
		palette.add(createToolButton(IMAGES + "MTM", "Many to Many Connection", tool));
	}

	// -- main -----------------------------------------------------------

	public static void main(String[] args) {
		DrawApplication window = new DiagramEditorApp();
		window.open();
	}
}
