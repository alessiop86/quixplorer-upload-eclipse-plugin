package quixplorer_uploader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class UploaderHandler extends org.eclipse.core.commands.AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Shell shell = HandlerUtil.getActiveShell(event);

		try {

			// event.getCommand().get

			// get workbench window
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			// set selection service
			ISelectionService service = window.getSelectionService();
			// set structured selection
			IStructuredSelection structured = (IStructuredSelection) service.getSelection();

			Object[] oggettiSelezionati = structured.toArray();

			List<File> fileDaCaricare = new ArrayList<File>();

			String unicaDirectoryPerUpload = null;
			for (int i = 0; i < oggettiSelezionati.length; i++) {

				IFile fileCorrente = null;

				if (oggettiSelezionati[i] instanceof ICompilationUnit)

					fileCorrente = (IFile) ((ICompilationUnit) oggettiSelezionati[i]).getResource();
				else if (oggettiSelezionati[i] instanceof ICompilationUnit)
					fileCorrente = (IFile) oggettiSelezionati[i];
				// else
				// MessageDialog.openInformation(shell,
				// "TIPO DI FILE ANCORA DA GESTIRE:",
				// oggettiSelezionati[i].getClass().toString());

				if (fileCorrente != null) {
					fileDaCaricare.add(new File(fileCorrente.getLocation().toPortableString()));

					String cartellaFileCorrente = estraiPath(fileCorrente);

					if (unicaDirectoryPerUpload == null)
						unicaDirectoryPerUpload = cartellaFileCorrente;
					else if (unicaDirectoryPerUpload != cartellaFileCorrente)
						throw new QuiXploderException("Seleziona solo file della stessa directory");
				}
			}

		} catch (QuiXploderException e) {
			MessageDialog.openError(shell, "ERRORE", e.getMessage());
		}

		return null;
	}

	//utility per tirare fuori le cartelle dagli IFile
	private String estraiPath(IFile ifile) {

		String str = ifile.getProjectRelativePath().toPortableString();

		int lastIndex = str.lastIndexOf('/');

		String path = "";
		if (lastIndex > -1)
			path = str.substring(0, lastIndex);

		return path;
	}
}
