package org.adichatz.widget;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.adichatz.widget.richText.RichText;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.xml.sax.SAXException;

public class RichTextDemo {
	static Shell shell;

	private String initialText = "<html><p>A Epic <b>Drama</b>of a Feminist And a Mad <b>Scientist</b> who must Battle a Teacher in The Canadian Rockies</p><p>see <a href=\"www.imdb.com\">web site</a> (Double-click or Ctl-click to open URL).</p></html>";

	public static void main(String[] args) {
		new RichTextDemo().createWindow();
	}

	private void createWindow() {
		Display display = new Display();
		shell = new Shell(display);
		shell.setLayout(new FillLayout());

		shell.setImages(new Image[] { JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_INFO),
				JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_WARNING) });

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout());

		final RichText richText = new RichText(composite, SWT.NONE);
		richText.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Text initText = new Text(composite, SWT.BORDER);
		initText.setText(initialText);
		initText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite buttonComposite = new Composite(composite, SWT.NONE);
		buttonComposite.setLayout(new GridLayout(2, true));
		buttonComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Button printFormattedTextButton = new Button(buttonComposite, SWT.PUSH);
		printFormattedTextButton.setText("print formatted text");
		printFormattedTextButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Button refreshFormattedTextButton = new Button(buttonComposite, SWT.PUSH);
		refreshFormattedTextButton.setText("refresh formatted text");
		refreshFormattedTextButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		refreshFormattedTextButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				try {
					richText.setFormattedText(initText.getText());
				} catch (ParserConfigurationException | SAXException | IOException error) {
					error.printStackTrace();
				}
			}
		});
		final Text printedText = new Text(composite, SWT.BORDER);
		initText.setText(initialText);
		printFormattedTextButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String formattedText = richText.getFormattedText();
				printedText.setText(null == formattedText ? "" : formattedText);
			}
		});
		printedText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		try {
			richText.setFormattedText(initText.getText());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		shell.setSize(600, 500);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}