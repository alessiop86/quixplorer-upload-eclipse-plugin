package quixplorer_uploader;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class FormView extends ViewPart {
	 private FormToolkit toolkit;
	 private ScrolledForm form;

	 /**
	  * The constructor.
	  */
	 public FormView() {
	 }

	 /**
	  * This is a callback that will allow us to create the viewer and
	  * initialize it.
	  */
	 public void createPartControl(Composite parent) {
	  toolkit = new FormToolkit(parent.getDisplay());
	  form = toolkit.createScrolledForm(parent);
	  form.setText("Hello, Eclipse Forms");
	 }

	 /**
	  * Passing the focus request to the form.
	  */
	 public void setFocus() {
	  form.setFocus();
	 }

	 /**
	  * Disposes the toolkit
	  */
	 public void dispose() {
	  toolkit.dispose();
	  super.dispose();
	 }


	}