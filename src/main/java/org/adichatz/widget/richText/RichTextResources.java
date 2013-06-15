/*******************************************************************************
 * Copyright © Adichatz (2007-2013) - www.adichatz.org
 *
 * arpheuil@adichatz.org
 *
 * This software is a computer program whose purpose is to build easily
 * Eclipse RCP applications using JPA in a JEE or JSE context.
 *
 * This software is governed by the CeCILL license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *
 *
 ********************************************************************************
 *
 * Copyright © Adichatz (2007-2013) - www.adichatz.org
 *
 * arpheuil@adichatz.org
 *
 * Ce logiciel est un programme informatique servant à construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE.
 *
 * Ce logiciel est régi par la licence CeCILL soumise au droit français et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA
 * sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilité au code source et des droits de copie,
 * de modification et de redistribution accordés par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
 * seule une responsabilité restreinte pèse sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les concédants successifs.
 *
 * A cet égard  l'attention de l'utilisateur est attirée sur les risques
 * associés au chargement,  à l'utilisation,  à la modification et/ou au
 * développement et à la reproduction du logiciel par l'utilisateur étant
 * donné sa spécificité de logiciel libre, qui peut le rendre complexe à
 * manipuler et qui le réserve donc à des développeurs et des professionnels
 * avertis possédant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
 * logiciel à leurs besoins dans des conditions permettant d'assurer la
 * sécurité de leurs systèmes et ou de leurs données et, plus généralement,
 * à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 *
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
 * pris connaissance de la licence CeCILL, et que vous en avez accepté les
 * termes.
 *******************************************************************************/
package org.adichatz.widget.richText;

import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Shell;

// TODO: Auto-generated Javadoc
/**
 * The Class RichTextResources.
 */
public class RichTextResources {

	/** The resource bundle. */
	private static ResourceBundle resourceBundle;

	/** The BOLD image for tool item. */
	public static Image IMG_BOLD;

	/** The ITALIC image for tool item. */
	public static Image IMG_ITALIC;

	/** The UNDERLINE image for tool item. */
	public static Image IMG_UNDERLINE;

	/** The STRIKE THROUGH image for tool item. */
	public static Image IMG_STRIKE_THROUGH;

	/** The FONT image for tool item. */
	public static Image IMG_FONT;

	/** The BACKGROUND image for tool item. */
	public static Image IMG_BACKGROUND;

	/** The FOREGROUND image for tool item. */
	public static Image IMG_FOREGROUND;

	/** The CUT image for tool item. */
	public static Image IMG_CUT;

	/** The COPY image for tool item. */
	public static Image IMG_COPY;

	/** The PASTE image for tool item. */
	public static Image IMG_PASTE;

	/** The HYPERLINK image for tool item. */
	public static Image IMG_HYPERLINK;

	/** The BOLD tooltip label for tool item. */
	public static String TOOLTIP_BOLD;

	/** The ITALIC tooltip label for tool item. */
	public static String TOOLTIP_ITALIC;

	/** The UNDERLINE tooltip label for tool item. */
	public static String TOOLTIP_UNDERLINE;

	/** The STRIKE_THROUGH tooltip label for tool item. */
	public static String TOOLTIP_STRIKE_THROUGH;

	/** The FONT tooltip label for tool item. */
	public static String TOOLTIP_FONT;

	/** The BACKGROUND tooltip label for tool item. */
	public static String TOOLTIP_BACKGROUND;

	/** The FOREGROUND tooltip label for tool item. */
	public static String TOOLTIP_FOREGROUND;

	/** The CUT tooltip label for tool item. */
	public static String TOOLTIP_CUT;

	/** The COPY tooltip label for tool item. */
	public static String TOOLTIP_COPY;

	/** The PASTE tooltip label for tool item. */
	public static String TOOLTIP_PASTE;

	/** The HYPERLINK tooltip label for tool item. */
	public static String TOOLTIP_HYPERLINK;

	static {
		IMG_BOLD = getImage("IMG_BOLD.png");
		IMG_ITALIC = getImage("IMG_ITALIC.png");
		IMG_UNDERLINE = getImage("IMG_UNDERLINE.png");
		IMG_STRIKE_THROUGH = getImage("IMG_STRIKE.png");
		IMG_FONT = getImage("IMG_FONT.png");
		IMG_BACKGROUND = getImage("IMG_BACKGROUND.png");
		IMG_FOREGROUND = getImage("IMG_FOREGROUND.png");
		IMG_CUT = getImage("IMG_CUT.png");
		IMG_COPY = getImage("IMG_TOOL_COPY.gif");
		IMG_PASTE = getImage("IMG_PASTE.png");
		IMG_HYPERLINK = getImage("IMG_HYPERLINK.png");

		try {
			resourceBundle = new PropertyResourceBundle(RichText.class.getResourceAsStream("RichText.properties"));
		} catch (IOException e) {
			error(e);
		}
		TOOLTIP_BOLD = getString("richText.bold");
		TOOLTIP_ITALIC = getString("richText.italic");
		TOOLTIP_UNDERLINE = getString("richText.underline");
		TOOLTIP_STRIKE_THROUGH = getString("richText.strikethrough");
		TOOLTIP_FONT = getString("richText.font");
		TOOLTIP_BACKGROUND = getString("richText.background");
		TOOLTIP_FOREGROUND = getString("richText.foreground");
		TOOLTIP_CUT = getString("richText.cut");
		TOOLTIP_COPY = getString("richText.copy");
		TOOLTIP_PASTE = getString("richText.paste");
		TOOLTIP_HYPERLINK = getString("richText.hyperlink");
	}

	/**
	 * Gets the image.
	 * 
	 * @param imageKey
	 *            the image key
	 * @return the image
	 */
	public static Image getImage(String imageKey) {
		return ImageDescriptor.createFromImageData(new ImageData(RichText.class.getResourceAsStream(imageKey))).createImage();
	}

	/**
	 * Gets the string.
	 * 
	 * @param key
	 *            the key
	 * @return the string
	 */
	public static String getString(String key) {
		return resourceBundle.getString(key);
	}

	/**
	 * The Enum TagStyle.
	 */
	public enum TagStyle {

		/** The PARAGRAPH. */
		PARAGRAPH,
		/** The BOLD. */
		BOLD,
		/** The ITALIC. */
		ITALIC,
		/** The STRIK e_ through. */
		STRIKE_THROUGH,
		/** The UNDERLINE. */
		UNDERLINE,
		/** The HYPERLINK. */
		HYPERLINK
	}

	/**
	 * Error.
	 * 
	 * @param e
	 *            the e
	 */
	public static void error(Exception e) {
		e.printStackTrace();
	}

	/**
	 * Open hyperlin dialog.
	 * 
	 * @param shell
	 *            the shell
	 * @param richText
	 *            the rich text
	 * @param text
	 *            the text
	 * @param href
	 *            the href
	 * @param styleRange
	 *            the style range
	 */
	public static void openHyperlinDialog(Shell shell, RichText richText, String text, String href, StyleRange styleRange) {
		new HyperlinkDialog(shell, richText, text, href, styleRange).open();
	}
}
