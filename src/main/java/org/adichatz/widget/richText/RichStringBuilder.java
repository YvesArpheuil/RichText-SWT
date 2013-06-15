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


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;

// TODO: Auto-generated Javadoc
/**
 * The Class RichStringBuilder.
 * 
 * @author Yves Arpheuil
 */
public class RichStringBuilder {

	/** The Constant LINE_DELIMITER. */
	public static final String LINE_DELIMITER = "<br/>";

	/** The builder. */
	private StringBuilder builder;

	/** The tag style stack. */
	private Stack<RichTextResources.TagStyle> tagStyleStack;

	/**
	 * Instantiates a new rich string builder.
	 */
	public RichStringBuilder() {
		builder = new StringBuilder();
		tagStyleStack = new Stack<RichTextResources.TagStyle>();
	}

	/**
	 * Append.
	 * 
	 * @param text
	 *            the text
	 * @return the rich string builder
	 */
	public RichStringBuilder append(String text) {
		builder.append(text);
		return this;
	}

	/**
	 * Append line break.
	 * 
	 * @return the rich string builder
	 */
	public RichStringBuilder appendLineBreak() {
		builder.append(LINE_DELIMITER);
		return this;
	}

	/**
	 * Start paragraph.
	 * 
	 * @return the rich string builder
	 */
	public RichStringBuilder startParagraph() {
		builder.append("<p>");
		return this;
	}

	/**
	 * Start tag styles.
	 * 
	 * @param range
	 *            the range
	 * @return the list
	 */
	public List<RichTextResources.TagStyle> startTagStyles(StyleRange range) {
		List<RichTextResources.TagStyle> tags = translateTag(range);
		for (RichTextResources.TagStyle fontStyle : tags) {
			tagStyleStack.push(fontStyle);
			switch (fontStyle) {
			case BOLD:
				builder.append("<b>");
				break;
			case ITALIC:
				builder.append("<i>");
				break;
			case STRIKE_THROUGH:
				builder.append("<del>");
				break;
			case HYPERLINK:
				builder.append("<a href=\"").append(range.data).append("\">");
				break;
			case UNDERLINE:
				builder.append("<ins>");
				break;
			}
		}
		return tags;
	}

	/**
	 * Translate tag.
	 * 
	 * @param range
	 *            the range
	 * @return the list
	 */
	private List<RichTextResources.TagStyle> translateTag(StyleRange range) {
		List<RichTextResources.TagStyle> list = new ArrayList<RichTextResources.TagStyle>();

		if ((range.fontStyle & SWT.BOLD) != 0) {
			list.add(RichTextResources.TagStyle.BOLD);
		}
		if ((range.fontStyle & SWT.ITALIC) != 0) {
			list.add(RichTextResources.TagStyle.ITALIC);
		}
		if (range.strikeout) {
			list.add(RichTextResources.TagStyle.STRIKE_THROUGH);
		}
		if (SWT.UNDERLINE_LINK == range.underlineStyle) {
			list.add(RichTextResources.TagStyle.HYPERLINK);
		} else if (range.underline) {
			list.add(RichTextResources.TagStyle.UNDERLINE);
		}

		return list;
	}

	/**
	 * End tag styles.
	 * 
	 * @param count
	 *            the count
	 * @return the rich string builder
	 */
	public RichStringBuilder endTagStyles(int count) {
		for (int i = 0; i < count; i++) {
			endStyle();
		}
		return this;
	}

	/**
	 * End style.
	 * 
	 * @return the rich string builder
	 */
	public RichStringBuilder endStyle() {
		if (tagStyleStack.size() > 0) {
			RichTextResources.TagStyle tag = tagStyleStack.pop();
			internalEndFontStyle(tag);
		}
		return this;
	}

	/**
	 * End paragraph.
	 * 
	 * @return the rich string builder
	 */
	public RichStringBuilder endParagraph() {
		flushStyles();
		builder.append("</p>");
		return this;
	}

	/**
	 * Flush styles.
	 */
	public void flushStyles() {
		while (tagStyleStack.size() > 0) {
			endStyle();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (null == o)
			return false;
		if (!(o instanceof RichStringBuilder))
			return false;

		return ((RichStringBuilder) o).builder.equals(builder);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return builder.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return builder.toString();
	}

	/**
	 * Internal end font style.
	 * 
	 * @param tagStyle
	 *            the tag style
	 */
	private void internalEndFontStyle(RichTextResources.TagStyle tagStyle) {
		switch (tagStyle) {
		case BOLD:
			builder.append("</b>");
			break;
		case ITALIC:
			builder.append("</i>");
			break;
		case STRIKE_THROUGH:
			builder.append("</del>");
			break;
		case HYPERLINK:
			builder.append("</a>");
			break;
		case UNDERLINE:
			builder.append("</ins>");
			break;
		}
	}
}
