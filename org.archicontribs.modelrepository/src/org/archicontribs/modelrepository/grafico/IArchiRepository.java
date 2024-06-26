package org.archicontribs.modelrepository.grafico;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.archicontribs.modelrepository.authentication.UsernamePassword;
import org.archicontribs.modelrepository.db.DatabaseElementEntity;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.PushResult;

import com.archimatetool.model.IArchimateModel;

public interface IArchiRepository extends IGraficoConstants {

    /**
     * @return The local repository folder
     */
    File getLocalRepositoryFolder();

    /**
     * @return The local repository's ".git" folder
     */
    File getLocalGitFolder();

    /**
     * @return The repository name - the file name
     */
    String getName();

    /**
     * @return The .archimate file in the local repo
     */
    File getTempModelFile();

    /**
     * Return the online URL of the Git repo, taken from the local config file.
     * We assume that there is only one remote per repo, and its name is "origin"
     * @return The online URL or null if not found
     * @throws IOException
     */
    String getOnlineRepositoryURL() throws IOException;

    /**
     * Locate a model in the model manager based on its file location
     * @return The model or null if not found
     */
    IArchimateModel locateModel();

    /**
     * @return true if the local .archimate file has been modified since the last Grafico export
     * @throws IOException 
     */
    boolean hasLocalChanges() throws IOException;

    /**
     * @return true if there are local changes to commit in the working tree
     * @throws IOException
     * @throws GitAPIException
     */
    boolean hasChangesToCommit() throws IOException, GitAPIException;

    /**
     * Commit any changes
     * @param commitMessage
     * @param amend If true, previous commit is amended
     * @return
     * @throws GitAPIException
     * @throws IOException
     */
    RevCommit commitChanges(String commitMessage, boolean amend) throws GitAPIException, IOException;

    /**
     * Clone a model
     * @param repoURL
     * @param npw
     * @param monitor
     * @throws GitAPIException
     * @throws IOException
     */
    void cloneModel(String repoURL, UsernamePassword npw, ProgressMonitor monitor) throws GitAPIException, IOException;

    /**
     * Push to Remote
     * @param userName
     * @param userPassword
     * @return 
     * @throws IOException
     * @throws GitAPIException
     */
    Iterable<PushResult> pushToRemote(UsernamePassword npw, ProgressMonitor monitor) throws IOException, GitAPIException;

    /**
     * Pull from Remote
     * @param npw
     * @return 
     * @throws IOException
     * @throws GitAPIException
     */
    PullResult pullFromRemote(UsernamePassword npw, ProgressMonitor monitor) throws IOException, GitAPIException;

    /**
     * Fetch from Remote
     * @param userName
     * @param userPassword
     * @param isDryrun
     * @throws IOException
     * @throws GitAPIException
     */
    FetchResult fetchFromRemote(UsernamePassword npw, ProgressMonitor monitor, boolean isDryrun) throws IOException, GitAPIException;

    /**
     * Create a new, local Git repository with name set to "origin"
     * @param URL online URL
     * @return The Git object
     * @throws GitAPIException
     * @throws IOException
     * @throws URISyntaxException
     */
    Git createNewLocalGitRepository(String URL) throws GitAPIException, IOException, URISyntaxException;

    /**
     * Return the contents of a file in the repo given its ref
     * Ref could be "HEAD" or "origin/master" for example
     * @param path
     * @param ref
     * @return The file contents or null if not found
     * @throws IOException
     */
    byte[] getFileContents(String path, String ref) throws IOException;

    /**
     * Get the file contents of a file in the working tree
     * @param path
     * @return
     * @throws IOException
     */
    String getWorkingTreeFileContents(String path) throws IOException;

    /**
     * Do a HARD reset to the given ref
     * @param ref can be "refs/heads/master" for local, or "origin/master" for remote ref
     * @throws IOException
     * @throws GitAPIException
     */
    void resetToRef(String ref) throws IOException, GitAPIException;

    /**
     * @return if the latest local HEAD commit and the remote commit are the same
     * @throws IOException
     * @throws GitAPIException 
     */
    boolean isHeadAndRemoteSame() throws IOException, GitAPIException;
    
    /**
     * Export the model to Grafico files
     * @throws IOException
     * @throws GitAPIException 
     */
    List<DatabaseElementEntity> exportModelToGraficoFiles() throws IOException;

    /**
     * Save a checksum
     * @return true if saved
     * @throws IOException
     */
    boolean saveChecksum() throws IOException;
    
    /**
     * @return User name and email from config. This is either local or global
     * @throws IOException
     */
    PersonIdent getUserDetails() throws IOException;
    
    /**
     * Save user name and email
     * @param name
     * @param email
     * @throws IOException
     */
    void saveUserDetails(String name, String email) throws IOException;
    
    /**
     * @return The Branch Status
     * @throws IOException
     * @throws GitAPIException
     */
    BranchStatus getBranchStatus() throws IOException, GitAPIException;
}