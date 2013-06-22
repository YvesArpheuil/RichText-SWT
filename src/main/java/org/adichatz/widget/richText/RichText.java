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


import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.ToolTip;
import org.xml.sax.SAXException;

// TODO: Auto-generated Javadoc
/**
 * The Class RichText.
 * 
 * This class is derived from work of Alonso Dominguez
 * 
 * @see http://www.javacodegeeks.com/2012/07/richtext-editor-component-for-swt-based.html
 * 
 * @author Yves Arpheuil
 */
public class RichText extends Composite {

	/** The cached styles. */
	private List<StyleRange> cachedStyles = Collections.synchronizedList(new LinkedList<StyleRange>());

	/** The tool bar. */
	private ToolBar toolBar;

	/** The styled text. */
	private StyledText styledText;

	/** The bold item. */
	private ToolItem boldItem;

	/** The italic item. */
	private ToolItem italicItem;

	/** The strike through item. */
	private ToolItem strikeThroughItem;

	/** The underline item. */
	private ToolItem underlineItem;

	/** The paste item. */
	private ToolItem pasteItem;

	/** The hyperlink item. */
	private ToolItem hyperlinkItem;

	/**
	 * Instantiates a new rich text.
	 * 
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public RichText(Composite parent, int style) {
		super(parent, style);
		initComponents();
	}

	/**
	 * Gets the styled text.
	 * 
	 * @return the styled text
	 */
	public StyledText getStyledText() {
		return styledText;
	}

	/**
	 * Obtain an HTML formatted text from the component contents.
	 * 
	 * @return an HTML formatted text
	 */
	public String getFormattedText() {
		String plainText = styledText.getText();
		if (0 == plainText.length())
			return null;

		RichStringBuilder builder = new RichStringBuilder();
		builder.append("<html>");
		Integer[] lineBreaks = getLineBreaks();

		int brIdx = 0;
		int start = 0;
		int end = (lineBreaks.length > brIdx ? lineBreaks[brIdx++] : plainText.length());

		while (start <= end) {
			builder.startParagraph();
			StyleRange[] ranges = styledText.getStyleRanges(start, (end - start));
			if (ranges != null && ranges.length > 0) {
				for (StyleRange range : ranges) {
					if (start < range.start)
						builder.append(plainText.substring(start, range.start));
					addFormattedText(plainText.substring(range.start, range.start + range.length), builder, range);
					start = (range.start + range.length);
				}
			}
			if (start < end) {
				builder.append(plainText.substring(start, end));
			}
			start = end + styledText.getLineDelimiter().length();
			end = (lineBreaks.length > brIdx ? lineBreaks[brIdx++] : plainText.length());
			builder.endParagraph();
		}
		return builder.append("</html>").toString();
	}

	/**
	 * Obtain an HTML formatted text from the component contents. Set replacement range and value.
	 * 
	 * @param replacementRange
	 *            the replacement range
	 * @param replacementValue
	 *            the replacement value
	 * @param deletedCharacters
	 *            the deleted characters
	 * @return the formatted text
	 */
	public String getFormattedText(StyleRange replacementRange, String replacementValue, int deletedCharacters) {
		String plainText = styledText.getText();
		RichStringBuilder builder = new RichStringBuilder();
		builder.append("<html>");
		Integer[] lineBreaks = getLineBreaks();

		int brIdx = 0;
		int start = 0;
		int end = (lineBreaks.length > brIdx ? lineBreaks[brIdx++] : plainText.length());

		boolean replaced = false;

		while (start <= end) {
			builder.startParagraph();
			StyleRange[] ranges = styledText.getStyleRanges(start, (end - start));
			if (ranges != null && ranges.length > 0) {
				for (StyleRange range : ranges) {
					if (!replaced && null != replacementRange && replacementRange.start < range.start + range.length) {
						if (range.start < replacementRange.start)
							addFormattedText(plainText.substring(range.start, replacementRange.start), builder, range);
						addFormattedText(replacementValue, builder, replacementRange);
						if (range.length > deletedCharacters) {
							String remainingString = plainText.substring(replacementRange.start + deletedCharacters, range.start
									+ range.length);
							addFormattedText(remainingString, builder, range);
							// start += remainingString.length();
						}
						replaced = true;
					} else {
						addFormattedText(plainText.substring(range.start, range.start + range.length), builder, range);
					}
					start = (range.start + range.length);
				}
			}
			if (start < end) {
				if (!replaced && replacementRange.start > start && replacementRange.start < end) {
					builder.append(plainText.substring(start, replacementRange.start));
					addFormattedText(replacementValue, builder, replacementRange);
					if (replacementRange.start + replacementRange.length < end)
						builder.append(plainText.substring(replacementRange.start + replacementRange.length, end));
					replaced = true;
				} else
					builder.append(plainText.substring(start, end));
			} else if (0 == start && 0 == end)
				addFormattedText(replacementValue, builder, replacementRange);
			start = end + styledText.getLineDelimiter().length();
			end = (lineBreaks.length > brIdx ? lineBreaks[brIdx++] : plainText.length());
			builder.endParagraph();
		}
		return builder.append("</html>").toString();
	}

	/**
	 * Sets the formatted text.
	 * 
	 * @param text
	 *            the new formatted text
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the sAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void setFormattedText(String text) throws ParserConfigurationException, SAXException, IOException {
		RichTextParser parser = RichTextParser.parse(text);
		styledText.setText(parser.getText());
		styledText.setStyleRanges(parser.getStyleRanges());
	}

	/**
	 * Adds the formatted text.
	 * 
	 * @param text
	 *            the text
	 * @param builder
	 *            the builder
	 * @param styleRange
	 *            the style range
	 */
	private void addFormattedText(String text, RichStringBuilder builder, StyleRange styleRange) {
		List<RichTextResources.TagStyle> tags = builder.startTagStyles(styleRange);
		builder.append(text);
		builder.endTagStyles(tags.size());
	}

	/**
	 * Apply font style to selection.
	 * 
	 * @param tag
	 *            the style
	 */
	protected void applyTagStyleToSelection(RichTextResources.TagStyle tag) {
		Point sel = styledText.getSelectionRange();
		if ((sel == null) || (sel.y == 0)) {
			return;
		}

		StyleRange newStyle;
		for (int i = sel.x; i < (sel.x + sel.y); i++) {
			StyleRange range = styledText.getStyleRangeAtOffset(i);
			if (range != null) {
				newStyle = (StyleRange) range.clone();
				newStyle.start = i;
				newStyle.length = 1;
			} else {
				newStyle = new StyleRange(i, 1, null, null, SWT.NORMAL);
			}

			switch (tag) {
			case BOLD:
				newStyle.fontStyle ^= SWT.BOLD;
				break;
			case ITALIC:
				newStyle.fontStyle ^= SWT.ITALIC;
				break;
			case STRIKE_THROUGH:
				newStyle.strikeout = !newStyle.strikeout;
				break;
			case UNDERLINE:
				newStyle.underline = !newStyle.underline;
				break;
			}

			styledText.setStyleRange(newStyle);
			styledText.notifyListeners(SWT.Modify, null);
		}

		styledText.setSelectionRange(sel.x + sel.y, 0);
	}

	/**
	 * Clear all styled data.
	 */
	protected void clearStylesFromSelection() {
		Point sel = styledText.getSelectionRange();
		if ((sel != null) && (sel.y != 0)) {
			StyleRange style = new StyleRange(sel.x, sel.y, null, null, SWT.NORMAL);
			styledText.setStyleRange(style);
		}
		styledText.setSelectionRange(sel.x + sel.y, 0);
	}

	/**
	 * Gets the line breaks.
	 * 
	 * @return the line breaks
	 */
	private Integer[] getLineBreaks() {
		String text = styledText.getText();
		List<Integer> list = new ArrayList<Integer>();
		int lastIdx = 0;
		String lineDelimiter = styledText.getLineDelimiter();
		int lineDelimiterLength = lineDelimiter.length();
		while (true) {
			int br = text.indexOf(lineDelimiter, lastIdx);
			if (-1 != br)
				list.add(br);
			else
				break;
			lastIdx = br + lineDelimiterLength;
		}
		Collections.sort(list);
		return list.toArray(new Integer[list.size()]);
	}

	/**
	 * Handle cut copy.
	 */
	protected void handleCutCopy() {
		// Save the cut/copied style info so that during paste we will maintain the style information. Cut/copied text is put in the
		// clipboard in RTF format, but is not pasted in RTF format. The other way to handle the pasting of styles would be to
		// access the Clipboard directly and parse the RTF text.
		cachedStyles = Collections.synchronizedList(new LinkedList<StyleRange>());
		Point sel = styledText.getSelectionRange();
		int startX = sel.x;
		for (int i = sel.x; i <= sel.x + sel.y - 1; i++) {
			StyleRange style = styledText.getStyleRangeAtOffset(i);
			if (style != null) {
				style.start = style.start - startX;
				if (!cachedStyles.isEmpty()) {
					StyleRange lastStyle = cachedStyles.get(cachedStyles.size() - 1);
					if (lastStyle.similarTo(style) && lastStyle.start + lastStyle.length == style.start) {
						lastStyle.length++;
					} else {
						cachedStyles.add(style);
					}
				} else {
					cachedStyles.add(style);
				}
			}
		}
		pasteItem.setEnabled(true);
	}

	/**
	 * Handle extended modified.
	 * 
	 * @param event
	 *            the event
	 */
	private void handleExtendedModified(ExtendedModifyEvent event) {
		if (event.length == 0)
			return;

		StyleRange style;
		if (event.length == 1 || styledText.getTextRange(event.start, event.length).equals(styledText.getLineDelimiter())) {
			// Have the new text take on the style of the text to its right (during typing) if no style information is active.
			int caretOffset = styledText.getCaretOffset();
			style = null;
			if (caretOffset < styledText.getCharCount())
				style = styledText.getStyleRangeAtOffset(caretOffset);
			if (style != null) {
				style = (StyleRange) style.clone();
				style.start = event.start;
				style.length = event.length;
			} else {
				style = new StyleRange(event.start, 1, null, null, SWT.NORMAL);
			}
			if (boldItem.getSelection())
				style.fontStyle |= SWT.BOLD;
			if (italicItem.getSelection())
				style.fontStyle |= SWT.ITALIC;
			style.strikeout = strikeThroughItem.getSelection();
			if (SWT.UNDERLINE_LINK != style.underlineStyle)
				style.underline = underlineItem.getSelection();
			if (!style.isUnstyled())
				styledText.setStyleRange(style);
		} else {
			// paste occurring, have text take on the styles it had when it was cut/copied
			for (int i = 0; i < cachedStyles.size(); i++) {
				style = cachedStyles.get(i);
				StyleRange newStyle = (StyleRange) style.clone();
				newStyle.start = style.start + event.start;
				styledText.setStyleRange(newStyle);
			}
		}
	}

	/**
	 * Handle text selected.
	 * 
	 * @param event
	 *            the event
	 */
	private void handleTextSelected(SelectionEvent event) {
		Point sel = styledText.getSelectionRange();
		if ((sel != null) && (sel.y != 0)) {
			StyleRange[] styles = styledText.getStyleRanges(sel.x, sel.y);
			boolean enabled = styles.length <= 1;
			hyperlinkItem.setEnabled(enabled);
		} else {
			hyperlinkItem.setEnabled(true);
		}
	}

	/**
	 * Handle key released.
	 * 
	 * @param event
	 *            the event
	 */
	private void handleKeyReleased(KeyEvent event) {
		if ((event.keyCode == SWT.ARROW_LEFT) || (event.keyCode == SWT.ARROW_UP) || (event.keyCode == SWT.ARROW_RIGHT)
				|| (event.keyCode == SWT.ARROW_DOWN)) {
			updateStyleButtons();
		}
	}

	/**
	 * Update style buttons.
	 */
	private void updateStyleButtons() {
		Point sel = styledText.getSelectionRange();
		if (sel != null && sel.y > 0) {
			StyleRange[] styles = styledText.getStyleRanges(sel.x, sel.y);
			if (styles != null) {
				if (1 == styles.length)
					enableFontItems(styles[0]);
				else
					disableFontItems();
				return;
			}
		}

		StyleRange style = null;
		int caretOffset = styledText.getCaretOffset();
		if (caretOffset >= 0 && caretOffset < styledText.getCharCount()) {
			style = styledText.getStyleRangeAtOffset(caretOffset);
		}

		if (style != null) {
			enableFontItems(style);
		} else {
			disableFontItems();
		}
	}

	/**
	 * Enable font items.
	 * 
	 * @param style
	 *            the style
	 */
	private void enableFontItems(StyleRange style) {
		boldItem.setSelection((style.fontStyle & SWT.BOLD) != 0);
		italicItem.setSelection((style.fontStyle & SWT.ITALIC) != 0);
		underlineItem.setSelection(style.underline && style.underlineStyle != SWT.UNDERLINE_LINK);
		strikeThroughItem.setSelection(style.strikeout);
		hyperlinkItem.setSelection(true);
	}

	/**
	 * Disable font items.
	 */
	private void disableFontItems() {
		boldItem.setSelection(false);
		italicItem.setSelection(false);
		underlineItem.setSelection(false);
		strikeThroughItem.setSelection(false);
		hyperlinkItem.setSelection(false);
	}

	/**
	 * Inits the components.
	 */
	private void initComponents() {
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginBottom = 0;
		layout.marginHeight = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.marginTop = 0;
		layout.marginWidth = 0;
		setLayout(layout);

		createToolBar(this);

		styledText = new StyledText(this, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		styledText.setLayoutData(new GridData(GridData.FILL_BOTH));

		final ToolTip toolTip = new ToolTip(styledText.getShell(), SWT.BALLOON);
		styledText.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent event) {
				toolTip.setVisible(false);
				if ((event.stateMask & SWT.MOD1) != 0) {
					try {
						linkActivated(getHyperlink(event));
					} catch (IllegalArgumentException e) {
						// no character under event.x, event.y
					}
				}
			}

			@Override
			public void mouseDown(MouseEvent event) {
				String message = getHyperlink(event);
				if (null != message) {
					toolTip.setMessage(message);
					toolTip.setVisible(true);
				}
			}

			@Override
			public void mouseDoubleClick(MouseEvent event) {
				toolTip.setVisible(false);
				try {
					linkActivated(getHyperlink(event));
				} catch (IllegalArgumentException e) {
					// no character under event.x, event.y
				}
			}
		});
		styledText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				handleKeyReleased(e);
			}
		});
		styledText.addExtendedModifyListener(new ExtendedModifyListener() {
			@Override
			public void modifyText(ExtendedModifyEvent event) {
				handleExtendedModified(event);
			}
		});
		styledText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				updateStyleButtons();
			}
		});
		styledText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				handleTextSelected(event);
			}
		});
	}

	/**
	 * Gets the hyperlink.
	 * 
	 * @param event
	 *            the event
	 * @return the hyperlink
	 */
	private String getHyperlink(MouseEvent event) {
		try {
			int offset = styledText.getOffsetAtLocation(new Point(event.x, event.y));
			StyleRange styleRange = styledText.getStyleRangeAtOffset(offset);
			if (styleRange != null && styleRange.underline && styleRange.underlineStyle == SWT.UNDERLINE_LINK)
				return (String) styleRange.data;
		} catch (IllegalArgumentException e) { // Invalid offset location
		}
		return null;
	}

	/**
	 * Link activated.
	 * 
	 * @param href
	 *            the href
	 */
	protected void linkActivated(String href) {
		if (null != href) {
			Desktop desktop = Desktop.getDesktop();
			try {
				if (href.startsWith("mailto://"))
					desktop.mail(new URI(href));
				else
					desktop.browse(new URI(href));
			} catch (IOException | URISyntaxException e) {
				RichTextResources.error(e);
			}
		}
	}

	/**
	 * Creates the tool bar.
	 * 
	 * @param parent
	 *            the parent
	 * @return the tool bar
	 */
	protected void createToolBar(final Composite parent) {
		toolBar = new ToolBar(parent, SWT.FLAT);
		toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		boldItem = new ToolItem(toolBar, SWT.CHECK);
		boldItem.setImage(RichTextResources.IMG_BOLD);
		boldItem.setToolTipText(RichTextResources.TOOLTIP_BOLD);
		boldItem.addSelectionListener(new TagStyleButtonListener(RichTextResources.TagStyle.BOLD));

		italicItem = new ToolItem(toolBar, SWT.CHECK);
		italicItem.setImage(RichTextResources.IMG_ITALIC);
		italicItem.setToolTipText(RichTextResources.TOOLTIP_ITALIC);
		italicItem.addSelectionListener(new TagStyleButtonListener(RichTextResources.TagStyle.ITALIC));

		underlineItem = new ToolItem(toolBar, SWT.CHECK);
		underlineItem.setImage(RichTextResources.IMG_UNDERLINE);
		underlineItem.setToolTipText(RichTextResources.TOOLTIP_UNDERLINE);
		underlineItem.addSelectionListener(new TagStyleButtonListener(RichTextResources.TagStyle.UNDERLINE));

		strikeThroughItem = new ToolItem(toolBar, SWT.CHECK);
		strikeThroughItem.setImage(RichTextResources.IMG_STRIKE_THROUGH);
		strikeThroughItem.setToolTipText(RichTextResources.TOOLTIP_STRIKE_THROUGH);
		strikeThroughItem.addSelectionListener(new TagStyleButtonListener(RichTextResources.TagStyle.STRIKE_THROUGH));

		new ToolItem(toolBar, SWT.SEPARATOR);

		ToolItem cutItem = new ToolItem(toolBar, SWT.PUSH);
		cutItem.setImage(RichTextResources.IMG_CUT);
		cutItem.setToolTipText(RichTextResources.TOOLTIP_CUT);
		cutItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleCutCopy();
				styledText.cut();
			}
		});

		ToolItem copyItem = new ToolItem(toolBar, SWT.PUSH);
		copyItem.setImage(RichTextResources.IMG_COPY);
		copyItem.setToolTipText(RichTextResources.TOOLTIP_COPY);
		copyItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleCutCopy();
				styledText.copy();
			}
		});

		pasteItem = new ToolItem(toolBar, SWT.PUSH);
		pasteItem.setEnabled(false);
		pasteItem.setImage(RichTextResources.IMG_PASTE);
		pasteItem.setToolTipText(RichTextResources.TOOLTIP_PASTE);
		pasteItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				styledText.paste();
			}
		});

		new ToolItem(toolBar, SWT.SEPARATOR);

		hyperlinkItem = new ToolItem(toolBar, SWT.PUSH);
		hyperlinkItem.setImage(RichTextResources.IMG_HYPERLINK);
		hyperlinkItem.setToolTipText(RichTextResources.TOOLTIP_HYPERLINK);
		hyperlinkItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StyleRange styleRange = null;
				Point selectionRange;
				if (0 == styledText.getCharCount())
					selectionRange = new Point(0, 0);
				else {
					selectionRange = styledText.getSelectionRange();
					if (selectionRange != null)
						if (selectionRange.y == 0) {
							styleRange = styledText.getStyleRangeAtOffset(selectionRange.x);
						} else {
							StyleRange[] styles = styledText.getStyleRanges(selectionRange.x, selectionRange.y);
							if (styles != null && styles.length == 1)
								styleRange = styles[0];
						}
				}
				String text = null;
				String href = null;
				if (null != styleRange) {
					if (SWT.UNDERLINE_LINK == styleRange.underlineStyle) {
						int[] ranges = styledText.getRanges();
						int index = 0;
						while (index < ranges.length) {
							int start = ranges[index++];
							int length = ranges[index++];
							if (selectionRange.x >= start && selectionRange.x < start + length) {
								styledText.setSelectionRange(start, length);
								styleRange.start = start;
								styleRange.length = length;
								break;
							}
						}
					}
					href = (String) styleRange.data;
				}
				text = styledText.getSelectionText();
				if (null == styleRange) {
					styleRange = new StyleRange();
					styleRange.start = selectionRange.x;
					styleRange.length = selectionRange.y;
				}
				RichTextResources.openHyperlinDialog(parent.getShell(), RichText.this, text, href, styleRange);
			}
		});
	}

	/**
	 * The listener interface for receiving fontStyleButton events. The class that is interested in processing a fontStyleButton
	 * event implements this interface, and the object created with that class is registered with a component using the component's
	 * <code>addFontStyleButtonListener<code> method. When
	 * the fontStyleButton event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see FontStyleButtonEvent
	 */
	private class TagStyleButtonListener extends SelectionAdapter {

		/** The style. */
		private RichTextResources.TagStyle tag;

		/**
		 * Instantiates a new font style button listener.
		 * 
		 * @param tag
		 *            the style
		 */
		public TagStyleButtonListener(RichTextResources.TagStyle tag) {
			this.tag = tag;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			applyTagStyleToSelection(tag);
		}
	}

	/**
	 * Gets the tool bar.
	 * 
	 * @return the tool bar
	 */
	public ToolBar getToolBar() {
		return toolBar;
	}
}