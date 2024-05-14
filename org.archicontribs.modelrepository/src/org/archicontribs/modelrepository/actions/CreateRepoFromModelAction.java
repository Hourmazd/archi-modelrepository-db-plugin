/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.archicontribs.modelrepository.actions;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.archicontribs.modelrepository.IModelRepositoryImages;
import org.archicontribs.modelrepository.ModelRepositoryPlugin;
import org.archicontribs.modelrepository.authentication.internal.EncryptedCredentialsStorage;
import org.archicontribs.modelrepository.db.DBHelper;
import org.archicontribs.modelrepository.grafico.ArchiRepository;
import org.archicontribs.modelrepository.grafico.GraficoUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import com.archimatetool.editor.model.IEditorModelManager;
import com.archimatetool.model.IArchimateModel;

/**
 * Create an online repo from an existing model
 * 
 * @author Phillip Beauvoir
 */
public class CreateRepoFromModelAction extends AbstractModelAction {
    
    private IArchimateModel fModel;
	
    public CreateRepoFromModelAction(IWorkbenchWindow window, IArchimateModel model) {
        super(window);
        
        fModel = model;
        
        setImageDescriptor(IModelRepositoryImages.ImageFactory.getImageDescriptor(IModelRepositoryImages.ICON_CREATE_REPOSITORY));
        setText(Messages.CreateRepoFromModelAction_0);
        setToolTipText(Messages.CreateRepoFromModelAction_0);
    }

    @Override
    public void run() {
        try {
            if(!EncryptedCredentialsStorage.checkPrimaryKeySet()) {
                return;
            }
        }
        catch(GeneralSecurityException ex) {
            displayCredentialsErrorDialog(ex);
            return;
        }
        catch(IOException ex) {
            displayErrorDialog(Messages.CreateRepoFromModelAction_7, ex);
            return;
        }
        
        // Create a new local folder
        File localRepoFolder = GraficoUtils.getUniqueLocalFolder(ModelRepositoryPlugin.INSTANCE.getUserModelRepositoryFolder(), fModel.getName());
        setRepository(new ArchiRepository(localRepoFolder));
        
        try {
            
            // Set new file location
            fModel.setFile(getRepository().getTempModelFile());
            
			// And Save it
			IEditorModelManager.INSTANCE.saveModel(fModel);

			Exception[] exception = new Exception[1];
			IProgressService ps = PlatformUI.getWorkbench().getProgressService();
			ps.busyCursorWhile(new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor pm) {
					try {

						getDbRepository().InsertModel(DBHelper.convertModelIdToUuid(fModel.getId()), fModel.getName());

						// Save the checksum
						getRepository().saveChecksum();

					} catch (Exception e) {
						
						exception[0] = e;

						e.printStackTrace();

						fModel.setFile(null);
					}
				}
			});
			
			if(exception[0] != null) {
				throw exception[0];
			}
        }
        catch(Exception ex) {
            displayErrorDialog(Messages.CreateRepoFromModelAction_7, ex);
        }
    }
}
