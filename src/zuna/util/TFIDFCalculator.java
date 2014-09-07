package zuna.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

import zuna.model.MyMethod;
import zuna.refactoring.ProjectAnalyzer;

public class TFIDFCalculator {
	static LinkedHashMap<String, Double> classTfMap;
	static LinkedHashMap<String, Double> classIdfMap;
	static LinkedHashMap<String, Double> classTfIdfMap;

	static double identifierSize;

	public static LinkedHashMap<String, Double> TfIdfCal(String key) {

		ArrayList<MyMethod> MethodList = ProjectAnalyzer.project.getClass(key).getOwnedMethods();
		classTfMap = new LinkedHashMap<String, Double>();
		classTfIdfMap = new LinkedHashMap<String, Double>();
		classIdfMap = new LinkedHashMap<String, Double>();
		identifierSize = 0;

		// String className =
		// ProjectAnalyzer.project.getClass(key).getID();

		for (int i = 0; i < MethodList.size(); i++) {
			for (int j = 0; j < MethodList.get(i).getIdentifiers().size(); j++) {
				if (!classTfMap.containsKey(MethodList.get(i).getIdentifiers().get(j))) {
					classTfMap.put(MethodList.get(i).getIdentifiers().get(j), (double) 1);
					classIdfMap.put(MethodList.get(i).getIdentifiers().get(j), null);
				} else {
					classTfMap.replace(MethodList.get(i).getIdentifiers().get(j),
							classTfMap.get(MethodList.get(i).getIdentifiers().get(j)) + 1);
				}

			}
			identifierSize = identifierSize + MethodList.get(i).getIdentifiers().size();
		}

		tfCal();
		idfCal();

		for (String tfIdfkey : classTfMap.keySet()) {
			double tf = classTfMap.get(tfIdfkey);
			double idf = classIdfMap.get(tfIdfkey);
			classTfIdfMap.put(tfIdfkey, tf * idf);
		}

		return classTfIdfMap;
	}

	public static void tfCal() {

		for (String classTfMapKey : classTfMap.keySet()) {
			classTfMap.replace(classTfMapKey, classTfMap.get(classTfMapKey) / identifierSize);

		}

	}

	public static void idfCal() {

		Set<String> classList = ProjectAnalyzer.project.getClassList().keySet();
		double classListSize = ProjectAnalyzer.project.getClassList().keySet().size();

		for (String idfKey : classIdfMap.keySet()) {
			double count = 0;
			classList: for (String classKey : classList) {
				ArrayList<MyMethod> idfMyMethod = new ArrayList<MyMethod>();
				idfMyMethod = ProjectAnalyzer.project.getClass(classKey).getOwnedMethods();
				for (int i = 0; i < idfMyMethod.size(); i++) {
					for (int j = 0; j < idfMyMethod.get(i).getIdentifiers().size(); j++) {
						if (idfMyMethod.get(i).getIdentifiers().contains(idfKey)) {
							count++;
							continue classList;
						}
					}
				}

			}
			classIdfMap.replace(idfKey, Math.log(1 / ((double) count / classListSize)));

		}

	}

}
