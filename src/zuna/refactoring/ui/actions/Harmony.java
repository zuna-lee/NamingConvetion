package zuna.refactoring.ui.actions;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import zuna.refactoring.ProjectAnalyzer;
import zuna.util.NameTokenizer;
import zuna.util.TFIDFCalculator;

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

	@SuppressWarnings("null")
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

				Set<String> classList = ProjectAnalyzer.project.getClassList().keySet();

				for (String key : classList) {
					String className = ProjectAnalyzer.project.getClass(key).getID();

					// /////// TF-IDF //////

					// System.out.println("================" + className +
					// "================");
					// for (String string :
					// TFIDFCalculator.TfIdfCal(key).keySet()) {
					// System.out.println(string + ": " +
					// TFIDFCalculator.TfIdfCal(key).get(string));
					//
					// }

					// //////////////// relation //////////////////
					int isAbstract = ProjectAnalyzer.project.getClass(key).isAbstract() ? 1 : 0;
					int isInterface = ProjectAnalyzer.project.getClass(key).isInterface() ? 1 : 0;
					int isImplemented = ProjectAnalyzer.project.getClass(key).getInterface().size() != 0 ? 1 : 0;
					int fanIn = ProjectAnalyzer.project.getClass(key).getUsedClasses().size() != 0 ? 1 : 0;
					int fanOut = ProjectAnalyzer.project.getClass(key).getUseClasses().size() != 0 ? 1 : 0;

					if (className.endsWith(">")) {
						className = className.substring(0, className.indexOf("<"));
					}
					if (className.contains(".")) {
						className = className.substring(className.lastIndexOf(".") + 1);
					}

					String[] tokens = NameTokenizer.tokenizer(className);
					String[] fixes = new String[2];
					System.out.print("" + isAbstract + isInterface + isImplemented + fanIn + fanOut + ",");
					// for (int i = 0; i < tokens.length; i++) {
					// System.out.print(tokens[i]);
					// if (i < tokens.length - 1) {
					// System.out.print(" ");
					// }
					// }

					if (tokens[0].equals("Action") || tokens[0].equals("Event") || tokens[0].equals("Input")
							|| tokens[0].equals("Output") || tokens[0].equals("I") || tokens[0].equals("Abstract")
							|| tokens[0].equals("Factory") || tokens[0].equals("Util") || tokens[0].equals("Handle")
							|| tokens[0].equals("Manager") || tokens[0].equals("Object") || tokens[0].equals("Impl")) {
						fixes[0] = tokens[0];
					} else if (tokens[0].startsWith("I") && Character.isUpperCase(tokens[0].charAt(1))) {
						fixes[0] = "I";

					} else if (tokens[0].endsWith("er")) {
						fixes[0] = "er";
					} else if (tokens[0].endsWith("or")) {
						fixes[0] = "or";
					} else if (tokens[0].endsWith("ing")) {
						fixes[0] = "ing";
					} else {
						fixes[0] = " ";

					}
					if (tokens[tokens.length - 1].equals("Action") || tokens[tokens.length - 1].equals("Event")
							|| tokens[tokens.length - 1].equals("Input") || tokens[tokens.length - 1].equals("Output")
							|| tokens[tokens.length - 1].equals("I") || tokens[tokens.length - 1].equals("Abstract")
							|| tokens[tokens.length - 1].equals("Factory") || tokens[tokens.length - 1].equals("Util")
							|| tokens[tokens.length - 1].equals("Handle") || tokens[tokens.length - 1].equals("Manager")
							|| tokens[tokens.length - 1].equals("Object") || tokens[tokens.length - 1].equals("Impl")) {
						fixes[1] = tokens[tokens.length - 1];
					} else if (tokens[tokens.length - 1].endsWith("er")) {
						fixes[1] = "er";
					} else if (tokens[tokens.length - 1].endsWith("or")) {
						fixes[1] = "or";
					} else if (tokens[tokens.length - 1].endsWith("ing")) {
						fixes[1] = "ing";
					} else {
						fixes[1] = " ";
					}
					System.out.print(fixes[0] + "," + fixes[1] + "," + className);

					System.out.println();

				}

				// Set<String> classListSet1 =
				// ProjectAnalyzer.project.getClassList().keySet();
				// Set<String> classListSet2 =
				// ProjectAnalyzer.project.getClassList().keySet();

				// for (String key1 : classListSet1) {
				// HashSet<MyClass> useClass1 =
				// ProjectAnalyzer.project.getClass(key1).getUseClasses();
				// HashSet<MyClass> usedClass1 =
				// ProjectAnalyzer.project.getClass(key1).getUsedClasses();
				//
				// MyClass parentClass1 =
				// ProjectAnalyzer.project.getClass(key1).getSuperClass();
				// ArrayList<MyClass> childClass1 =
				// ProjectAnalyzer.project.getClass(key1).getChildClasses();
				//
				// ArrayList<Boolean> constraints1 = new ArrayList<Boolean>();
				// constraints1.add(ProjectAnalyzer.project.getClass(key1).isAbstract());
				// constraints1.add(ProjectAnalyzer.project.getClass(key1).isEnumuration());
				// constraints1.add(ProjectAnalyzer.project.getClass(key1).isInterface());
				//
				// for (String key2 : classListSet2) {
				// // ///////////////////////////////////// Relationship
				// // similarity
				// HashSet<MyClass> useClass2 =
				// ProjectAnalyzer.project.getClass(key2).getUseClasses();
				// HashSet<MyClass> usedClass2 =
				// ProjectAnalyzer.project.getClass(key2).getUsedClasses();
				// HashSet<MyClass> useUnionXY = new
				// HashSet<MyClass>(useClass1);
				// useUnionXY.addAll(useClass2);
				// HashSet<MyClass> useIntersectionXY = new
				// HashSet<MyClass>(useClass1);
				// useIntersectionXY.retainAll(useClass2);
				// HashSet<MyClass> usedUnionXY = new
				// HashSet<MyClass>(usedClass1);
				// usedUnionXY.addAll(usedClass2);
				// HashSet<MyClass> usedIntersectionXY = new
				// HashSet<MyClass>(usedClass1);
				// usedIntersectionXY.retainAll(usedClass2);
				// double oSim = (double) useIntersectionXY.size() / (double)
				// useUnionXY.size();
				// if (useClass1.size() == 0 && useClass2.size() == 0) {
				// oSim = 0.0;
				// }
				// double iSim = (double) usedIntersectionXY.size() / (double)
				// usedUnionXY.size();
				// if (usedClass1.size() == 0 && usedClass2.size() == 0) {
				// iSim = 0.0;
				// }
				// double rAvg = (oSim + iSim) / 2;
				//
				// // /////////////////////////////////////// Hierachy
				// // similarity
				// MyClass parentClass2 =
				// ProjectAnalyzer.project.getClass(key2).getSuperClass();
				// int pSim = 0;
				// if (parentClass1 == parentClass2) {
				// pSim = 1;
				// }
				//
				// ArrayList<MyClass> childClass2 =
				// ProjectAnalyzer.project.getClass(key2).getChildClasses();
				//
				// HashSet<MyClass> childUnionXY = new
				// HashSet<MyClass>(childClass1);
				// childUnionXY.addAll(childClass2);
				// HashSet<MyClass> childIntersectionXY = new
				// HashSet<MyClass>(childClass1);
				// childIntersectionXY.retainAll(childClass2);
				// double cSim = (double) childIntersectionXY.size() / (double)
				// childUnionXY.size();
				// if (childClass1.size() == 0 && childClass2.size() == 0) {
				// cSim = 0.0;
				// }
				// double hAvg = ((double) pSim + cSim) / 2d;
				//
				// // ///////////////////////////////////// Constraint
				// // similarity
				// ArrayList<Boolean> constraints2 = new ArrayList<Boolean>();
				// constraints2.add(ProjectAnalyzer.project.getClass(key2).isAbstract());
				// constraints2.add(ProjectAnalyzer.project.getClass(key2).isEnumuration());
				// constraints2.add(ProjectAnalyzer.project.getClass(key2).isInterface());
				// double conSim = 0;
				// for (int i = 0; i < constraints1.size(); i++) {
				//
				// if (constraints1.get(i) == constraints2.get(i)) {
				// conSim++;
				// }
				//
				// }
				// if (conSim != 0) {
				// conSim = conSim / 3d;
				// }
				//
				// System.out.println(ProjectAnalyzer.project.getClass(key1).getID()
				// + " : "
				// + ProjectAnalyzer.project.getClass(key2).getID() +
				// "//////// conSim: " + conSim);
				//
				// }
				// }

				//
				//
				// for (String key : classList.keySet()) {
				//

				// //
				// String category = null;
				// if
				// (ProjectAnalyzer.project.getClass(key).getUseClasses().size()
				// != 0
				// &&
				// ProjectAnalyzer.project.getClass(key).getUsedClasses().size()
				// != 0) {
				// category = "control";
				// } else if
				// (ProjectAnalyzer.project.getClass(key).getUseClasses().size()
				// == 0
				// &&
				// ProjectAnalyzer.project.getClass(key).getUsedClasses().size()
				// != 0) {
				// category = "entity";
				// } else if
				// (ProjectAnalyzer.project.getClass(key).getUseClasses().size()
				// != 0
				// &&
				// ProjectAnalyzer.project.getClass(key).getUsedClasses().size()
				// == 0) {
				// category = "boundary";
				// }
				//
				// System.out.println("Class name:" +
				// key.substring(key.lastIndexOf(".") + 1) + " isAbastract:"
				// + ProjectAnalyzer.project.getClass(key).isAbstract() +
				// " isInterface:"
				// + ProjectAnalyzer.project.getClass(key).isInterface() +
				// " noOfCalls:"
				// + ProjectAnalyzer.project.getClass(key).getNoOfCalls() +
				// " Category:" + category);
				//
				// System.out.println("============Child Classes============");
				// for (int i = 0; i <
				// ProjectAnalyzer.project.getClass(key).getChildClasses().size();
				// i++) {
				// System.out.println(ProjectAnalyzer.project.getClass(key).getChildClasses().get(i).getID());
				// }
				//
				// System.out.println("============Parent Class=============");
				// if (ProjectAnalyzer.project.getClass(key).getSuperClass() !=
				// null) {
				// System.out.println(ProjectAnalyzer.project.getClass(key).getSuperClass().getID());
				// }
				// System.out.println("=========Implemented Classes=========");
				// for (int i = 0; i <
				// ProjectAnalyzer.project.getClass(key).getImplementedClasses().size();
				// i++) {
				// System.out.println(ProjectAnalyzer.project.getClass(key).getImplementedClasses().get(i).getID());
				// }
				// System.out.println("=============Interfaces=============");
				// for (int i = 0; i <
				// ProjectAnalyzer.project.getClass(key).getInterface().size();
				// i++) {
				// System.out.println(ProjectAnalyzer.project.getClass(key).getInterface().get(i).getID());
				// }
				// System.out.println("============Uses Classes============");
				// for (Iterator<MyClass> iter =
				// ProjectAnalyzer.project.getClass(key).getUseClasses().iterator();
				// iter
				// .hasNext();) {
				// System.out.println(iter.next().getID());
				// }
				// System.out.println("============Used Classes============");
				// for (Iterator<MyClass> iter =
				// ProjectAnalyzer.project.getClass(key).getUsedClasses().iterator();
				// iter
				// .hasNext();) {
				// System.out.println(iter.next().getID());
				// }
				//
				// System.out.println();

				// }

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
