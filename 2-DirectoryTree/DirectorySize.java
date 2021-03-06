
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/***********************************************************************
* This program provides a tool that given a name of a directory,       *
* explores all its sub-directories and files and does two things:      *
*  - computes the total size of all the files and sub-directories,     *
*  - prints a list of n largest files (their sizes and absolute paths) *
* ...much like the $du command in linux                                *
* @author Joanna Klukowska & Caspar Lant                               *
* @see $du -sh 														   *
* @see http://github.com/caspar/                                       *
***********************************************************************/

public class DirectorySize {

	//TODO
	//more arguments to help with command-line stuff

	/**list of files found in the directory structure */
	static List<FileOnDisk> listOfFiles ;
	/**list of visited directories (kept to avoid
	* circular links and infinite recursion) */
	static List<String> listOfVisitedDirs;
	static int total = 0;

	/**
	* This method expects one or two arguments,
	* which it checks for validity before passing them on to the rest of the program.
	* @param args Array of arguments passed to the program. The first one
	* is the name of the directory to be explored. The second (optional) is the
	* max number of largest files to be printed to the screen.
	* @throws IOException
	*/
	public static void main(String[] args) throws IOException{

		String directory;
		int numOfFiles = 20; //default value, if no value is specified
		File dir;
		byte pathOnly = 0;

		//Argument Handling; prints error messages to the user.
		if (args.length == 0){
			System.err.println("No directory specified");
			System.exit(0);
		}
		if (args[0].charAt(0)=='-'){
			if (args[0].contains("p")) //path only
				pathOnly = 1;
		}
		directory = args[0 + pathOnly];
		dir = new File(directory);
		if (!dir.isDirectory()){
			System.err.println("Specified directory does not exist");
			System.exit(0);
		}
		if (args.length > 1 + pathOnly){
			try{
				numOfFiles = Integer.parseInt(args[1]);
			}catch(Exception ruhRoh){
				System.err.println("Second argument must be an integer");
			}
		}

		//create an empty list of files
		listOfFiles = new LinkedList<FileOnDisk> ();
		//create an empty list of directories
		listOfVisitedDirs = new ArrayList<String> ();

		// Display the total size of the directory/file
		long size = getSize(dir);
		if (pathOnly == 0){
			if (size < 1024 ) //print bytes
			System.out.printf("Total space used: \n%7.2f bytes\n", (float) size  );
			else if (size/1024 < 1024 )//print kilobytes
			System.out.printf("Total space used: \n%7.2f KB\n", (float) size / 1024.0 );
			else if (size/1024/1024 < 1024 )//print megabytes
			System.out.printf("Total space used: \n%7.2f MB\n", (float) size / (1024.0 * 1024));
			else //print gigabytes
			System.out.printf("Total space used: \n%7.2f GB\n", (float) size / (1024.0 * 1024*1024));

			// Display the largest files in the directory
			if (numOfFiles == 1)
			System.out.printf("\nLargest file: \n");
			else
			System.out.printf("\nLargest %d files: \n", numOfFiles );
		}
		//Sort!
		Collections.sort(listOfFiles);

		for (int i = 0; i < listOfFiles.size() && i < numOfFiles; i++)
		//print from the back so that the largest files are printed
		if (pathOnly == 1)
			System.out.println(listOfFiles.get(listOfFiles.size() - i - 1).getAbsPath());
		else
			System.out.println(listOfFiles.get(listOfFiles.size() - i - 1));

	}

	/**
	* Recursively determines the size of a directory or file represented
	* by the abstract parameter object file.
	* This method populates the listOfFiles with all the files contained in the
	* explored directory.
	* This method populates the listOfVisitedDirs with canonical path names of
	* all the visited directories.
	* @param file directory/file name whose size should be determined
	* @return number of bytes used for storage on disk
	* @throws IOException
	*/
	public static long getSize(File file) throws IOException {
		long size = 0;
		if (file.isDirectory()){
			//done to avoid the duplication of file objects and misrepresentation of directory size
			//if (!listOfVisitedDirs.contains(file.getCanonicalPath())){
				listOfVisitedDirs.add(file.getCanonicalPath());
				for (File dir : file.listFiles()){
					size += getSize(dir);
				}
			//}
		}
		else {
			size += file.length();
			listOfFiles.add(new FileOnDisk(file.getCanonicalPath(), file.length()));
		}
		return size;
	}

}
