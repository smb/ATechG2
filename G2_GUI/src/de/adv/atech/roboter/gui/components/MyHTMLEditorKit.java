package de.adv.atech.roboter.gui.components;

import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

import de.adv.atech.roboter.gui.media.MyImageView;

public class MyHTMLEditorKit extends HTMLEditorKit {

	public ViewFactory getViewFactory() {
		return new HTMLFactoryX();
	}

	public static class HTMLFactoryX extends HTMLFactory implements ViewFactory {

		public View create(Element elem) {
			Object o = elem.getAttributes().getAttribute(
					StyleConstants.NameAttribute);
			if (o instanceof HTML.Tag) {
				HTML.Tag kind = (HTML.Tag) o;
				if (kind == HTML.Tag.IMG)
					return new MyImageView(elem);
			}
			return super.create(elem);
		}
	}
}
