package quixplorer_uploader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import quixploder_uploader.upload.CopiaFileSuQuixplorer;
import quixplorer_uploader.dialogs.DataDialog;

public class UploaderHandler extends org.eclipse.core.commands.AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Shell shell = HandlerUtil.getActiveShell(event);

		String user, password, folder, folderFissa, url;

		// carico gli ultimi valori usati
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
		user = prefs.get("user", "");
		password = prefs.get("password", "");		
		folderFissa = prefs.get("folderFissa", "");
		url = prefs.get("url", "");
		
		
		String unicaDirectoryPerUpload = null;
		
		try {

			// get workbench window
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			// set selection service
			ISelectionService service = window.getSelectionService();
			// set structured selection
			IStructuredSelection structured = (IStructuredSelection) service.getSelection();

			Object[] oggettiSelezionati = structured.toArray();

 			List<File> fileDaCaricare = new ArrayList<File>();
			List<String> nomiFile = new ArrayList<String>();

			
			for (int i = 0; i < oggettiSelezionati.length; i++) {

				IFile fileCorrente = null;

				if (oggettiSelezionati[i] instanceof ICompilationUnit)

					fileCorrente = (IFile) ((ICompilationUnit) oggettiSelezionati[i]).getResource();
				else if (oggettiSelezionati[i] instanceof ICompilationUnit)
					fileCorrente = (IFile) oggettiSelezionati[i];

				// qua potrei gestire anche il tipo dei file...

				if (fileCorrente != null) {

					String cartellaFileCorrente = estraiPath(fileCorrente);

					if (unicaDirectoryPerUpload == null)
						unicaDirectoryPerUpload = cartellaFileCorrente;
					else if (!unicaDirectoryPerUpload.equals(cartellaFileCorrente))
						throw new QuiXploderException("QuiXplorer permette l'upload in contemporanea solo di file che si trovano nella stessa cartella");

					// String f =
					File f = new File(fileCorrente.getLocation().toPortableString());
					fileDaCaricare.add(f);
					nomiFile.add(f.getName());

				}
			}

			if (fileDaCaricare.size() == 0)
				throw new QuiXploderException("Seleziona almeno un file valido");
			if (fileDaCaricare.size() > 10)
				throw new QuiXploderException("Seleziona un numero di file massimo pari a 10");

			String[] files = nomiFile.toArray(new String[nomiFile.size()]);

			DataDialog dialog2 = new DataDialog(shell);
			dialog2.setInfoSuiFile(unicaDirectoryPerUpload, files);
			dialog2.setUrl(url);
			dialog2.setFolderFissa(folderFissa);
			dialog2.setUser(user);
			dialog2.setPassword(password);

			// get the new values from the dialog
			if (dialog2.open() == Window.OK) {
				user = dialog2.getUser();
				url = dialog2.getUrl();
				folderFissa = dialog2.getFolderFissa();
				folder = dialog2.getFolder();
				password = dialog2.getPassword();
				
				CopiaFileSuQuixplorer c = new CopiaFileSuQuixplorer();
				
				c.login(url, user, password);
				
				c.caricaFiles(folderFissa + folder, fileDaCaricare);

				MessageDialog.openInformation(shell, "OK", "File caricati con successo.");
			}

		} catch (QuiXploderException e) {
			MessageDialog.openError(shell, "ERRORE", e.getMessage());
		}
		
		//salvo per le chiamate successive per non dover ricompilare ogni volta la maschera
		salvaStato(user, password, url, folderFissa);

		return null;
	}

	// utility per tirare fuori le cartelle dagli IFile
	private String estraiPath(IFile ifile) {

		String str = ifile.getProjectRelativePath().toPortableString();

		int lastIndex = str.lastIndexOf('/');

		String path = "";
		if (lastIndex > -1)
			path = str.substring(0, lastIndex);

		return path;
	}

	private void salvaStato(String user, String password, String url, String folder) {

		// saves plugin preferences at the workspace level
		IEclipsePreferences prefs =	InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);

		prefs.put("user", user);
		prefs.put("password", password);
		prefs.put("url", url);
		prefs.put("folderFissa", folder);

		try {
			prefs.flush();
		} catch (org.osgi.service.prefs.BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

/*
 * class MyUtils {
 * 
 * public static String concatList(List<String> sList, String separator) {
 * Iterator<String> iter = sList.iterator(); StringBuilder sb = new
 * StringBuilder();
 * 
 * while (iter.hasNext()) { sb.append(iter.next()).append( iter.hasNext() ?
 * separator : ""); } return sb.toString(); } }
 */