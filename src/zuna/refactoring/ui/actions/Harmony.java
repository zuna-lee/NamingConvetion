package zuna.refactoring.ui.actions;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import zuna.model.MyClass;
import zuna.model.MyMethod;
import zuna.refactoring.ProjectAnalyzer;

@SuppressWarnings("restriction")
public class Harmony implements IWorkbenchWindowActionDelegate {
	private static IWorkbenchWindow window;
	public static double th = 21;

	/**
	 * The constructor.
	 */
	public Harmony() {

	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */

	@Override
	public void run(IAction action) {

		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			try {
				// 10 is the workload, so in your case the number of files to
				// copy
				IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
				// dont touch
				Object firstElement = selection.getFirstElement();
				init();
				IProject project = (IProject) ((IAdaptable) firstElement).getAdapter(IProject.class);
				ProjectAnalyzer.firstElement = (IAdaptable) firstElement;
				ProjectAnalyzer.analyze(project);
				//

				HashMap<String, MyClass> classList = ProjectAnalyzer.project.getClassList();

				for (String key : classList.keySet()) {
					String category = null;
					if (ProjectAnalyzer.project.getClass(key).getUseClasses().size() != 0
							&& ProjectAnalyzer.project.getClass(key).getUsedClasses().size() != 0) {
						category = "control";
					} else if (ProjectAnalyzer.project.getClass(key).getUseClasses().size() == 0
							&& ProjectAnalyzer.project.getClass(key).getUsedClasses().size() != 0) {
						category = "entity";
					} else if (ProjectAnalyzer.project.getClass(key).getUseClasses().size() != 0
							&& ProjectAnalyzer.project.getClass(key).getUsedClasses().size() == 0) {
						category = "boundary";
					}

					System.out.println("Class name:" + key.substring(key.lastIndexOf(".") + 1) + " isAbastract:"
							+ ProjectAnalyzer.project.getClass(key).isAbstract() + " isInterface:"
							+ ProjectAnalyzer.project.getClass(key).isInterface() + " noOfCalls:"
							+ ProjectAnalyzer.project.getClass(key).getNoOfCalls() + " Category:" + category);

					System.out.println("============Child Classes============");
					for (int i = 0; i < ProjectAnalyzer.project.getClass(key).getChildClasses().size(); i++) {
						System.out.println(ProjectAnalyzer.project.getClass(key).getChildClasses().get(i).getID());
					}

					System.out.println("============Parent Class=============");
					if (ProjectAnalyzer.project.getClass(key).getSuperClass() != null) {
						System.out.println(ProjectAnalyzer.project.getClass(key).getSuperClass().getID());
					}
					System.out.println("=========Implemented Classes=========");
					for (int i = 0; i < ProjectAnalyzer.project.getClass(key).getImplementedClasses().size(); i++) {
						System.out.println(ProjectAnalyzer.project.getClass(key).getImplementedClasses().get(i).getID());
					}
					System.out.println("=============Interfaces=============");
					for (int i = 0; i < ProjectAnalyzer.project.getClass(key).getInterface().size(); i++) {
						System.out.println(ProjectAnalyzer.project.getClass(key).getInterface().get(i).getID());
					}
					System.out.println("============Uses Classes============");
					for (Iterator<MyClass> iter = ProjectAnalyzer.project.getClass(key).getUseClasses().iterator(); iter
							.hasNext();) {
						System.out.println(iter.next().getID());
					}
					System.out.println("============Used Classes============");
					for (Iterator<MyClass> iter = ProjectAnalyzer.project.getClass(key).getUsedClasses().iterator(); iter
							.hasNext();) {
						System.out.println(iter.next().getID());
					}

					System.out.println();

				}

				HashMap<String, MyMethod> methodList = ProjectAnalyzer.project.getMethodList();

				System.out.println("Class:" + classList.size() + " Method:" + methodList.size());
				System.out.println();

			} catch (java.lang.NullPointerException e) {
				e.printStackTrace();
			} catch (java.lang.ClassCastException e2) {
				e2.printStackTrace();
			}
		}
	}

	private void init() {
		ProjectAnalyzer.project = null;
		ProjectAnalyzer.firstElement = null;
	}

	private String getClassID(CompilationUnit cu) {
		String classID = cu.getPath().toString().replace(cu.getPackageFragmentRoot().getPath().toString() + "/", "");
		classID = classID.replace("/", ".");
		classID = classID.substring(0, classID.length() - 5);
		return classID;
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	@Override
	public void dispose() {

	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	// private HashMap<String, MyMethod> getRefactoredMode(Repo p1, Repo p2){
	// ArrayList<MyMethod> diff = ASTParserUtil.findDifferences(p1, p2);
	// HashMap<String, MyMethod> changedMethods = new HashMap<String,
	// MyMethod>();
	// for(MyMethod m: diff){
	// changedMethods.put(m.getID(), m);
	// }
	//
	// return changedMethods;
	// }

}
