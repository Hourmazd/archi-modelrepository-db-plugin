/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.archicontribs.modelrepository.preferences;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.archicontribs.modelrepository.ModelRepositoryPlugin;
import org.archicontribs.modelrepository.authentication.internal.EncryptedCredentialsStorage;
import org.archicontribs.modelrepository.grafico.IGraficoConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.archimatetool.editor.ui.UIUtils;


/**
 * Model Repository Preferences Page
 * 
 * @author Phillip Beauvoir
 */
public class ModelRepositoryPreferencePage
extends PreferencePage
implements IWorkbenchPreferencePage, IPreferenceConstants {
    
    public static final String ID = "org.archicontribs.modelrepository.preferences.ModelRepositoryPreferencePage";  //$NON-NLS-1$
    
    private static final String HELP_ID = "org.archicontribs.modelrepository.prefsValidator"; //$NON-NLS-1$
    
    private Combo fDatabaseEngine;
    
    private Text fServerAddressTextField;
    private Text fServerPortTextField;
    private Text fDatabaseNameTextField;
    
    private Text fUserNameTextField;
    private Text fUserPasswordTextField;
    
    private Text fUserRepoFolderTextField;
        
    private Button fFetchInBackgroundButton;
    private Spinner fFetchInBackgroundIntervalSpinner;
    
    private String[] DATABASE_ENGINES = {
    		Messages.ModelRepositoryPreferencePage_28,
    		Messages.ModelRepositoryPreferencePage_29
    };
    
    private boolean serverPasswordChanged;
    
	public ModelRepositoryPreferencePage() {
		setPreferenceStore(ModelRepositoryPlugin.INSTANCE.getPreferenceStore());
	}
	
    @Override
    protected Control createContents(Composite parent) {
        // Help
        PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, HELP_ID);

        Composite client = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        layout.marginWidth = layout.marginHeight = 0;
        client.setLayout(layout);
        
        // DB Details Group
        Group dbDetailsGroup = new Group(client, SWT.NULL);
        dbDetailsGroup.setText(Messages.ModelRepositoryPreferencePage_1);
        dbDetailsGroup.setLayout(new GridLayout(2, false));
        dbDetailsGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        Label label = new Label(dbDetailsGroup, SWT.NULL);
        label.setText(Messages.ModelRepositoryPreferencePage_27);
        
        fDatabaseEngine = new Combo(dbDetailsGroup, SWT.READ_ONLY);
        fDatabaseEngine.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        fDatabaseEngine.setItems(DATABASE_ENGINES);
                
        label = new Label(dbDetailsGroup, SWT.NULL);
        label.setText(Messages.ModelRepositoryPreferencePage_2);
        
        fServerAddressTextField = UIUtils.createSingleTextControl(dbDetailsGroup, SWT.BORDER, false);
        fServerAddressTextField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        label = new Label(dbDetailsGroup, SWT.NULL);
        label.setText(Messages.ModelRepositoryPreferencePage_3);
        
        fServerPortTextField = UIUtils.createSingleTextControl(dbDetailsGroup, SWT.BORDER, false);
        fServerPortTextField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        label = new Label(dbDetailsGroup, SWT.NULL);
        label.setText(Messages.ModelRepositoryPreferencePage_30);
        
        fDatabaseNameTextField = UIUtils.createSingleTextControl(dbDetailsGroup, SWT.BORDER, false);
        fDatabaseNameTextField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
                
        // Authentication Group
        Group authGroup = new Group(client, SWT.NULL);
        authGroup.setText(Messages.ModelRepositoryPreferencePage_0);
        authGroup.setLayout(new GridLayout(3, false));
        authGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        label = new Label(authGroup, SWT.NULL);
        label.setText(Messages.ModelRepositoryPreferencePage_15);
        
        fUserNameTextField = UIUtils.createSingleTextControl(authGroup, SWT.BORDER, false);
        fUserNameTextField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        label = new Label(authGroup, SWT.NULL);

        label = new Label(authGroup, SWT.NULL);
        label.setText(Messages.ModelRepositoryPreferencePage_16);
        
        fUserPasswordTextField = UIUtils.createSingleTextControl(authGroup, SWT.BORDER | SWT.PASSWORD, false);
        fUserPasswordTextField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        Button testConnectionButton = new Button(authGroup, SWT.PUSH);
        testConnectionButton.setText("Test Connection");
        testConnectionButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	testConnection();
            }
        });
        
     // Workspace Group
        Group workspaceGroup = new Group(client, SWT.NULL);
        workspaceGroup.setText(Messages.ModelRepositoryPreferencePage_4);
        workspaceGroup.setLayout(new GridLayout(3, false));
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.widthHint = 500;
        workspaceGroup.setLayoutData(gd);
        
        // Workspace folder location
        label = new Label(workspaceGroup, SWT.NULL);
        label.setText(Messages.ModelRepositoryPreferencePage_5);
        
        fUserRepoFolderTextField = UIUtils.createSingleTextControl(workspaceGroup, SWT.BORDER, false);
        fUserRepoFolderTextField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        Button folderButton = new Button(workspaceGroup, SWT.PUSH);
        folderButton.setText(Messages.ModelRepositoryPreferencePage_6);
        folderButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String folderPath = chooseFolderPath();
                if(folderPath != null) {
                    fUserRepoFolderTextField.setText(folderPath);
                }
            }
        });
        
        // Fetch in background
        fFetchInBackgroundButton = new Button(workspaceGroup, SWT.CHECK);
        fFetchInBackgroundButton.setText(Messages.ModelRepositoryPreferencePage_19);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        fFetchInBackgroundButton.setLayoutData(gd);
        
        // Refresh interval
        label = new Label(workspaceGroup, SWT.NULL);
        label.setText(Messages.ModelRepositoryPreferencePage_21);

        fFetchInBackgroundIntervalSpinner = new Spinner(workspaceGroup, SWT.BORDER);
        fFetchInBackgroundIntervalSpinner.setMinimum(30);
        fFetchInBackgroundIntervalSpinner.setMaximum(3000);

        setValues();
        
        return client;
    }

    private String chooseFolderPath() {
        DirectoryDialog dialog = new DirectoryDialog(Display.getCurrent().getActiveShell());
        dialog.setText(Messages.ModelRepositoryPreferencePage_17);
        dialog.setMessage(Messages.ModelRepositoryPreferencePage_18);
        File file = new File(fUserRepoFolderTextField.getText());
        if(file.exists()) {
            dialog.setFilterPath(fUserRepoFolderTextField.getText());
        }
        return dialog.open();
    }
    
    private void testConnection()
    {
    	MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Connection Test", "Done");
    }    

    private void setValues() {

    	fDatabaseEngine.setText(getPreferenceStore().getString(PREFS_DATABASE_ENGINE));
        fServerAddressTextField.setText(getPreferenceStore().getString(PREFS_DATABASE_SERVER_ADDRESS));
        fServerPortTextField.setText(getPreferenceStore().getString(PREFS_DATABASE_SERVER_PORT));
        fDatabaseNameTextField.setText(getPreferenceStore().getString(PREFS_DATABASE_NAME));
        
        fUserNameTextField.setText(getPreferenceStore().getString(PREFS_DATABASE_USER_NAME));
        try {
            EncryptedCredentialsStorage dbCredentials = getServerCredentials();
            fUserPasswordTextField.setText(dbCredentials.hasPassword() ? "********" : ""); //$NON-NLS-1$ //$NON-NLS-2$
            
            fUserPasswordTextField.addModifyListener(event -> {
            	serverPasswordChanged = true;
            });
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        
        // Workspace folder
        fUserRepoFolderTextField.setText(getPreferenceStore().getString(PREFS_REPOSITORY_FOLDER));
        
        // Refresh in background
        fFetchInBackgroundButton.setSelection(getPreferenceStore().getBoolean(PREFS_FETCH_IN_BACKGROUND));
        fFetchInBackgroundIntervalSpinner.setSelection(getPreferenceStore().getInt(PREFS_FETCH_IN_BACKGROUND_INTERVAL));
               
    }
    
    @Override
    public boolean performOk() {
        
    	getPreferenceStore().setValue(PREFS_DATABASE_ENGINE, fDatabaseEngine.getText());
    	getPreferenceStore().setValue(PREFS_DATABASE_SERVER_ADDRESS, fServerAddressTextField.getText());
    	getPreferenceStore().setValue(PREFS_DATABASE_SERVER_PORT, fServerPortTextField.getText());
    	getPreferenceStore().setValue(PREFS_DATABASE_NAME, fDatabaseNameTextField.getText());
    	getPreferenceStore().setValue(PREFS_DATABASE_USER_NAME, fUserNameTextField.getText());
    	
        getPreferenceStore().setValue(PREFS_REPOSITORY_FOLDER, fUserRepoFolderTextField.getText());
        
        getPreferenceStore().setValue(PREFS_FETCH_IN_BACKGROUND, fFetchInBackgroundButton.getSelection());
        getPreferenceStore().setValue(PREFS_FETCH_IN_BACKGROUND_INTERVAL, fFetchInBackgroundIntervalSpinner.getSelection());


        // If password changed
        if(serverPasswordChanged) {
            try {
                EncryptedCredentialsStorage dbCredentials = getServerCredentials();
                dbCredentials.storePassword(fUserPasswordTextField.getTextChars());
            }
            catch(GeneralSecurityException | IOException ex) {
                ex.printStackTrace();
                return false;
            }
        }
               
        return true;
    }
    
    private EncryptedCredentialsStorage getServerCredentials() {
        return new EncryptedCredentialsStorage(new File(ModelRepositoryPlugin.INSTANCE.getUserModelRepositoryFolder(),
                IGraficoConstants.DB_CREDENTIALS_FILE));
    }
    
    @Override
    protected void performDefaults() {
    	
    	fDatabaseEngine.setText(getPreferenceStore().getDefaultString(PREFS_DATABASE_ENGINE));

        fServerAddressTextField.setText(getPreferenceStore().getDefaultString(PREFS_DATABASE_SERVER_ADDRESS));
        fServerPortTextField.setText(getPreferenceStore().getDefaultString(PREFS_DATABASE_SERVER_PORT));
        fDatabaseNameTextField.setText(getPreferenceStore().getDefaultString(PREFS_DATABASE_NAME));
        
        fUserNameTextField.setText(PREFS_DATABASE_USER_NAME); //$NON-NLS-1$
        fUserPasswordTextField.setText(PREFS_DATABASE_USER_PASS); //$NON-NLS-1$

        fUserRepoFolderTextField.setText(getPreferenceStore().getDefaultString(PREFS_REPOSITORY_FOLDER));
        
        fFetchInBackgroundButton.setSelection(getPreferenceStore().getDefaultBoolean(PREFS_FETCH_IN_BACKGROUND));
        fFetchInBackgroundIntervalSpinner.setSelection(getPreferenceStore().getDefaultInt(PREFS_FETCH_IN_BACKGROUND_INTERVAL));        
    }    
    
    
    @Override
    public void init(IWorkbench workbench) {
    }
}