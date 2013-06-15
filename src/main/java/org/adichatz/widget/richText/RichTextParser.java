package org.adichatz.widget.richText;
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


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

// TODO: Auto-generated Javadoc
/**
 * 
 * @author Yves Arpheuil The Class RichTextParser.
 */
public final class RichTextParser {

	/**
	 * Parses the.
	 * 
	 * @param formattedText
	 *            the formatted text
	 * @return the rich text parser
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the sAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static RichTextParser parse(String formattedText) throws ParserConfigurationException, SAXException, IOException {
		return new RichTextParser(formattedText);
	}

	/** The text. */
	private StringBuilder text = new StringBuilder();

	/** The style ranges. */
	private List<StyleRange> styleRanges = new ArrayList<StyleRange>();

	/**
	 * Instantiates a new rich text parser.
	 * 
	 * @param formattedText
	 *            the formatted text
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the sAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private RichTextParser(String formattedText) throws ParserConfigurationException, SAXException, IOException {
		StringReader reader = new StringReader(formattedText);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		DefaultHandler handler = new RichTextContentHandler();
		parser.parse(new InputSource(reader), handler);
	}

	/** The href. */
	private String href;

	/**
	 * Gets the text.
	 * 
	 * @return the text
	 */
	public String getText() {
		return text.toString();
	}

	/**
	 * Gets the style ranges.
	 * 
	 * @return the style ranges
	 */
	public StyleRange[] getStyleRanges() {
		return styleRanges.toArray(new StyleRange[styleRanges.size()]);
	}

	/**
	 * Invalid tag.
	 * 
	 * @param qName
	 *            the q name
	 * @return true, if successful
	 */
	private boolean invalidTag(String qName) {
		return !"a".equals(qName) && !"b".equals(qName) && !"i".equals(qName) && !"ins".equals(qName) && !"del".equals(qName);
	}

	/**
	 * The Class RichTextContentHandler.
	 */
	private class RichTextContentHandler extends DefaultHandler {

		/** The tags stack. */
		private Stack<List<RichTextResources.TagStyle>> tagsStack = new Stack<List<RichTextResources.TagStyle>>();

		/** The last text chunk. */
		private String lastTextChunk = null;

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
		 */
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			lastTextChunk = new String(ch, start, length);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
		 */
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			// If there is not any previous text chunk parsed then return
			if (lastTextChunk == null)
				return;
			// If the tag found is not a supported one then return
			if (invalidTag(qName)) {
				text.append(lastTextChunk);
				lastTextChunk = null;
				return;
			}

			List<RichTextResources.TagStyle> lastStyles = lastTagStyles(true);
			StyleRange range = transform(lastStyles);
			range.start = currentIndex() + 1;
			range.length = lastTextChunk.length();
			if (qName.equals("a") && href != null)
				range.data = href;
			styleRanges.add(range);

			text.append(lastTextChunk);
			lastTextChunk = null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String,
		 * org.xml.sax.Attributes)
		 */
		@Override
		public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
			if ("a".equals(qName))
				href = atts.getValue(0);
			else if ("p".equals(qName) && text.length() > 0)
				text.append("\r\n");

			// If the tag found is not a supported one then return
			if (invalidTag(qName)) {
				return;
			}

			List<RichTextResources.TagStyle> lastStyles = lastTagStyles(false);
			if (lastTextChunk == null) {
				if (lastStyles == null) {
					lastStyles = new ArrayList<RichTextResources.TagStyle>();
					tagsStack.add(lastStyles);
				}
			} else {
				if (lastStyles != null) {
					StyleRange range = transform(lastStyles);
					range.start = currentIndex() + 1;
					range.length = lastTextChunk.length();
					styleRanges.add(range);
				}

				text.append(lastTextChunk);
				lastTextChunk = null;
			}

			if ("b".equals(qName)) {
				lastStyles.add(RichTextResources.TagStyle.BOLD);
			} else if ("i".equals(qName)) {
				lastStyles.add(RichTextResources.TagStyle.ITALIC);
			} else if ("ins".equals(qName)) {
				lastStyles.add(RichTextResources.TagStyle.UNDERLINE);
			} else if ("del".equals(qName)) {
				lastStyles.add(RichTextResources.TagStyle.STRIKE_THROUGH);
			} else if ("a".equals(qName)) {
				lastStyles.add(RichTextResources.TagStyle.HYPERLINK);
			} else if ("p".equals(qName)) {
				lastStyles.add(RichTextResources.TagStyle.PARAGRAPH);
			} else {
				System.err.println("Wrong tag: <" + qName + ">!");
			}
		}

		/**
		 * Transform.
		 * 
		 * @param tags
		 *            the tags
		 * @return the style range
		 */
		private StyleRange transform(List<RichTextResources.TagStyle> tags) {
			StyleRange range = new StyleRange();
			range.start = currentIndex() + 1;
			range.length = lastTextChunk.length();
			if (null != tags)
				for (RichTextResources.TagStyle tag : tags) {
					switch (tag) {
					case BOLD:
						range.fontStyle ^= SWT.BOLD;
						break;
					case ITALIC:
						range.fontStyle ^= SWT.ITALIC;
						break;
					case STRIKE_THROUGH:
						range.strikeout = true;
						break;
					case UNDERLINE:
						range.underline = true;
						break;
					case HYPERLINK:
						range.underline = true;
						range.underlineStyle = SWT.UNDERLINE_LINK;
						break;
					default:
						break;
					}
				}
			return range;
		}

		/**
		 * Last tag styles.
		 * 
		 * @param remove
		 *            the remove
		 * @return the list
		 */
		private List<RichTextResources.TagStyle> lastTagStyles(boolean remove) {
			List<RichTextResources.TagStyle> lastTags = null;
			if (tagsStack.size() > 0) {
				if (remove) {
					lastTags = tagsStack.pop();
				} else {
					lastTags = tagsStack.peek();
				}
			} else if (!remove) {
				lastTags = new ArrayList<RichTextResources.TagStyle>();
				tagsStack.add(lastTags);
			}
			return lastTags;
		}

		/**
		 * Current index.
		 * 
		 * @return the int
		 */
		private int currentIndex() {
			return text.length() - 1;
		}

	}
}