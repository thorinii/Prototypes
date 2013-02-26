/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demeteranalyser;

import java.util.*;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.*;
import org.apache.bcel.util.InstructionFinder;

/**
 *
 * @author lachlan
 */
public class ClassAnalyser {

	private JavaClass c;

	public ClassAnalyser(String className) throws ClassNotFoundException {
		c = Repository.lookupClass(className);
	}

	public Analysis analyse() {
		Analysis overallAnalysis = new Analysis(c.getClassName());

		for (Method m : c.getMethods()) {
			analyseMethod(m, c, overallAnalysis);
		}

		return overallAnalysis;
	}

	private void analyseMethod(Method m, JavaClass c,
			Analysis overallAnalysis) {
		Code code = m.getCode();
		ConstantPoolGen gen = new ConstantPoolGen(c.getConstantPool());

		Set<String> classesAllowed = new HashSet<>();
		Set<String> classesUsed = new HashSet<>();

		for (Type t : m.getArgumentTypes()) {
			if (t instanceof ObjectType) {
				classesAllowed.add(((ObjectType) t).getClassName());
			}
		}

		if (m.getReturnType() instanceof ObjectType) {
			classesAllowed.add(((ObjectType) m.getReturnType()).getClassName());
		}


		InstructionList instructions = new InstructionList(code.getCode());
		InstructionFinder finder = new InstructionFinder(instructions);

		Iterator itr = instructions.iterator();
		while (itr.hasNext()) {
			Instruction i = ((InstructionHandle) itr.next()).getInstruction();

			if (i instanceof InvokeInstruction) {
				Type type = ((InvokeInstruction) i).getReferenceType(gen);

				if (type instanceof ObjectType) {
					ObjectType obj = (ObjectType) type;

					classesUsed.add(obj.getClassName());
					if (i instanceof INVOKESPECIAL) {
						classesAllowed.add(obj.getClassName());
					} else {
						if (classesAllowed.contains(obj.getClassName())) {
							Type retType = ((InvokeInstruction) i).getReturnType(
									gen);
							System.out.println(retType);
							if (retType instanceof ObjectType) {
								classesAllowed.add(((ObjectType) retType).
										getClassName());
							}
						}
					}

				}
			}
		}

		Set<String> classesDisallowed = new HashSet(classesUsed);
		classesDisallowed.removeAll(classesAllowed);

		Analysis.Status status = Analysis.Status.Perfect;
		String reason = null;

		if (!classesDisallowed.isEmpty()) {
			status = Analysis.Status.Failed;

			reason = "Uses: " + classesDisallowed.toString();
			reason += " (" + classesUsed + " altogether)";
		} else {
			reason = "(" + classesUsed + ")";
		}

		overallAnalysis.pushMethodAnalysis(m.toString().replace('\n',
				' ').replace("\t", ""), status, reason);
	}

}
