package quixplorer_uploader.dialogs;

import java.awt.Color;
import java.awt.Font;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

public class DataDialog extends Dialog {
  private Text txtUser;
  private Text txtPassword;
  private String user = "";
  private String password = "";
  private Text txtUrlQ;
  private String urlQ = "";

  private Text txtFolder;
  private String folder;
  
  private Text txtFolderFissa;
  private String folderFissa;
  


  private String[] fileArray;
  

  public DataDialog(Shell parentShell) {
    super(parentShell);
    
    
  }
  
  @Override
	protected void configureShell(Shell newShell) {
		
		super.configureShell(newShell);				
		newShell.setText("QuiXplorer uploader");
		
	}

  @Override
  protected Control createDialogArea(Composite parent) {	  	  
	  
    Composite container = (Composite) super.createDialogArea(parent);
    GridLayout layout = new GridLayout(3, false);
    layout.marginRight = 5;
    layout.marginLeft = 10;
    container.setLayout(layout);

    
    Label lblUrl = new Label(container, SWT.WRAP);
    lblUrl.setText("Indirizzo server QuiXploder \n (escluso /index.php)");
    txtUrlQ = new Text(container, SWT.BORDER);
    txtUrlQ.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
        2, 1));
    txtUrlQ.setText(urlQ);
    
    
    Label lblUser = new Label(container, SWT.NONE);
    lblUser.setText("User:");
    txtUser = new Text(container, SWT.BORDER);
    txtUser.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
        2, 1));
    txtUser.setText(user);

    
    Label lblPassword = new Label(container, SWT.NONE);   
    lblPassword.setText("Password:");
    txtPassword = new Text(container, SWT.BORDER | SWT.PASSWORD);
    txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,        false, 2, 1));
    txtPassword.setText(password);
    
    
    Label lblSpacer = new Label(container, SWT.NONE);
    lblSpacer.setText("");
    lblSpacer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,        false, 3, 3));
   
    
    Label lblFolder = new Label(container, SWT.WRAP);
    lblFolder.setText("I file saranno caricati nella seguente cartella:");
    txtFolderFissa = new Text(container, SWT.BORDER);
    txtFolderFissa.setText(folderFissa);
    txtFolderFissa.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
            1, 1));
    txtFolder = new Text(container, SWT.BORDER);
    txtFolder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
        1, 1));
    txtFolder.setText( folder);  
      
    
    
    Label lblfiles = new  Label(container, SWT.NONE);
    lblfiles.setText("I file che saranno caricati sono i seguenti:");
    

    
    int style = SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL;
    List list = new List(parent, style);
    list.setItems(fileArray);
   
    //GridData listData =  new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
    GridData listData = new GridData(GridData.FILL_BOTH);
    listData.heightHint = 10 * list.getItemHeight(); // height for 10 rows
    listData.widthHint = 400; // width enough to display 25 chars
  
    list.setLayoutData(listData);
    
    return container;
  }

  // override method to use "Login" as label for the OK button
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, IDialogConstants.OK_ID, "Avvia upload", true);
    createButton(parent, IDialogConstants.CANCEL_ID,
        "Annulla", false);
  }

  @Override
  protected Point getInitialSize() {
    return new Point(600, 425);
  }

  @Override
  protected void okPressed() {
    // Copy data from SWT widgets into fields on button press.
    // Reading data from the widgets later will cause an SWT
    // widget diposed exception.
    user = txtUser.getText();
    password = txtPassword.getText();
    urlQ = txtUrlQ.getText();
    folderFissa = txtFolderFissa.getText();
    folder = txtFolder.getText();
    super.okPressed();
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFolder() {
	  return folder;
  }
  
  public void setFolderFissa(String f) {
	  folderFissa = f;
  }

  public String getFolderFissa() {
	  return folderFissa;
  }
  
  
  public String getUrl() {
	  return urlQ;
  }
  
  public void setUrl(String u) {
	  urlQ = u;
  }
  
  public void setInfoSuiFile(String folder, String[] files) {
	  this.folder = folder;
	  this.fileArray = files;	  
  }
  
} 