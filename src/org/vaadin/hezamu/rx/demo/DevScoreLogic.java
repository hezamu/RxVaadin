package org.vaadin.hezamu.rx.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import rx.Observable;

public class DevScoreLogic {
	public static Observable<Double> scores(Observable<String> coverages,
			Observable<Set<String>> agilities,
			Observable<String> teamSizeStrings,
			Observable<String> pMTechnicalities, Observable<Boolean> vaadinUses) {
		Observable<Inputs> inputs = Observable.combineLatest(coverages,
				agilities, teamSizeStrings, pMTechnicalities, vaadinUses,
				DevScoreLogic::combineInputs);

		return inputs.filter(DevScoreLogic::areInputsValid).map(
				DevScoreLogic::inputsToScore);
	}

	public static Observable<List<String>> faults(Observable<String> coverages,
			Observable<Set<String>> agilities,
			Observable<String> teamSizeStrings,
			Observable<String> pMTechnicalities, Observable<Boolean> vaadinUses) {
		Observable<Inputs> inputs = Observable.combineLatest(coverages,
				agilities, teamSizeStrings, pMTechnicalities, vaadinUses,
				DevScoreLogic::combineInputs);

		return inputs.map(DevScoreLogic::invalidInputs).filter(
				invalidInputs -> {
					return !invalidInputs.isEmpty();
				});
	}

	private static boolean areInputsValid(Inputs inputs) {
		return invalidInputs(inputs).isEmpty();
	}

	private static List<String> invalidInputs(Inputs inputs) {
		List<String> result = new ArrayList<>();
		if (inputs.coverage == null)
			result.add("coverage");

		if (inputs.teamSize == 0)
			result.add("team size");

		if (inputs.PMTechnicality == null)
			result.add("PM technicality");

		return result;
	}

	private static Double inputsToScore(Inputs inputs) {
		Double result = 0.0;
		if (areInputsValid(inputs)) {
			result += DevScorePresenter.coverages.get(inputs.coverage);

			result += (double) inputs.agility
					/ DevScorePresenter.agilities.size();

			result += Math.min(1.0, 3.0 / inputs.teamSize);

			result += DevScorePresenter.PMTechnicalities
					.get(inputs.PMTechnicality);

			result += inputs.vaadinUsed ? 1.0 : 0.5;

			result *= 20; // Map to range 0..100
		}

		return result;
	}

	private static Inputs combineInputs(String coverage, Set<String> agility,
			String teamSize, String PMTechnicality, Boolean vaadinUsed) {
		return new DevScoreLogic().new Inputs(coverage, agility, teamSize,
				PMTechnicality, vaadinUsed);
	}

	/**
	 * This class would be unneccessary if only Java 8 had tuples, like Scala or
	 * Groovy.
	 */
	private class Inputs {
		public String coverage;
		public int teamSize;
		public String PMTechnicality;
		public int agility;
		public boolean vaadinUsed;

		public Inputs(String coverage, Set<String> agilities,
				String teamSizeStr, String PMTechnicality, Boolean vaadinUsed) {
			this.coverage = coverage;

			this.agility = agilities != null ? agilities.size() : 0;

			try {
				teamSize = Integer.parseInt(teamSizeStr);
			} catch (NumberFormatException nfe) {
				teamSize = 0;
			}

			this.PMTechnicality = PMTechnicality;

			this.vaadinUsed = vaadinUsed != null ? vaadinUsed.booleanValue()
					: false;
		}
	}
}