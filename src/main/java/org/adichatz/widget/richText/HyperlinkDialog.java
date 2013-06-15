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

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.xml.sax.SAXException;

// TODO: Auto-generated Javadoc
/**
 * The Class HyperlinkDialog.
 */
public class HyperlinkDialog extends TrayDialog {

	/** The ok button. */
	private Button okButton;

	/** The link text txt. */
	private Text linkTextTXT;

	/** The link target txt. */
	private Text linkTargetTXT;

	/** The text. */
	private String text;

	/** The href. */
	private String href;

	/** The modify listener. */
	private ModifyListener modifyListener;

	/** The style range. */
	private StyleRange styleRange;

	/** The deleted character. */
	private int deletedCharacter;

	/** The rich text. */
	private RichText richText;

	/**
	 * Create a dialog to host the given input.
	 * 
	 * @param shell
	 *            a shell
	 * @param richText
	 *            the rich text
	 * @param text
	 *            the text
	 * @param href
	 *            the href
	 * @param styleRange
	 *            the style range
	 */
	public HyperlinkDialog(Shell shell, RichText richText, String text, String href, StyleRange styleRange) {
		super(shell);
		this.text = text;
		this.href = href;
		this.styleRange = styleRange;
		deletedCharacter = null == text ? 0 : text.length();
		this.richText = richText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID, JFaceResources.getString(IDialogLabelKeys.OK_LABEL_KEY), true);
		okButton.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID, JFaceResources.getString(IDialogLabelKeys.CANCEL_LABEL_KEY), false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		String title = RichTextResources.getString("richText.hyperlink");
		getShell().setText(title);
		getShell().setImage(RichTextResources.IMG_HYPERLINK);
		// getShell().setSize(600, 250);

		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout(2, false));

		modifyListener = new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				okButton.setEnabled(!linkTextTXT.getText().isEmpty() && !linkTargetTXT.getText().isEmpty());
			}
		};

		GridData gridData = new GridData(GridData.FILL_BOTH);

		Label linkTextLBL = new Label(composite, SWT.NONE);
		linkTextLBL.setText(RichTextResources.getString("richtext.text"));
		linkTextTXT = new Text(composite, SWT.BORDER);
		linkTextTXT.setText(text);
		linkTextTXT.setLayoutData(gridData);
		linkTextTXT.addModifyListener(modifyListener);

		Label linkTargetLBL = new Label(composite, SWT.NONE);
		linkTargetLBL.setText(RichTextResources.getString("richtext.target"));
		linkTargetTXT = new Text(composite, SWT.BORDER);
		if (null != href)
			linkTargetTXT.setText(href);
		linkTargetTXT.setLayoutData(gridData);
		linkTargetTXT.addModifyListener(modifyListener);

		applyDialogFont(composite);
		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
	 */
	protected void buttonPressed(int buttonId) {
		if (buttonId == OK) {
			String target = linkTargetTXT.getText();
			String newText = linkTextTXT.getText();

			if (!newText.equals(text) || !target.equals(href)) {
				StyleRange newRange = new StyleRange();
				newRange.underlineStyle = SWT.UNDERLINE_LINK;
				newRange.data = target;
				newRange.start = styleRange.start;
				newRange.length = newText.length();
				try {
					richText.setFormattedText(richText.getFormattedText(newRange, newText, deletedCharacter));
				} catch (ParserConfigurationException | SAXException | IOException e) {
					RichTextResources.error(e);
				}
				super.okPressed();
			}
			super.cancelPressed();
		}
		super.buttonPressed(buttonId);
	}
}
