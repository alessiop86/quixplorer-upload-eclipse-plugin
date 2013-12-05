package quixplorer_uploader;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class UploaderHandler extends org.eclipse.core.commands.AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	
		Shell shell = HandlerUtil.getActiveShell(event);
		
		MessageDialog.openInformation(shell, "titolo", "messaggio");
		
		return null;
	}

}
