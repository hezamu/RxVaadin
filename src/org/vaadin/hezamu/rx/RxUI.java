package org.vaadin.hezamu.rx;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import rx.Observable;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Layout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings({ "rawtypes", "serial" })
public class RxUI extends UI {
	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		NativeSelect numberSelect = addImmediateSelect(layout, "Numbers");
		IntStream.range(0, 10).forEach(nbr -> {
			numberSelect.addItem(nbr);
		});

		NativeSelect letterSelect = addImmediateSelect(layout, "Letters");
		Stream.of('a', 'b', 'c', 'd', 'e', 'f').forEach(ch -> {
			letterSelect.addItem(ch);
		});

		// Get the value streams from the selects as Observables

		Observable<Integer> numbers = RxVaadin.values(numberSelect);

		Observable<Character> letters = RxVaadin.values(letterSelect);

		// Let's create some combinators on the value streams for fun.
		//
		// First, we'll combine the the latest value of each into a untyped
		// List, used here as a poor man's tuple. The new lambda syntax works
		// nicely here.
		Observable<List> pairs = Observable.combineLatest(numbers, letters, (
				number, letter) -> {
			return Arrays.asList(number, letter);
		});

		// Set up a filter on the stream so that we'll accept only pairs that
		// have an even number and the letter "b".
		Observable<List> filtered = pairs.filter((List pair) -> {
			Integer t1 = (Integer) pair.get(0);
			Character t2 = (Character) pair.get(1);
			return t1 % 2 == 0 && t2 == 'b';
		});

		// And finally, subscribe to the filtered value stream to do something
		// with the matching pairs.
		filtered.subscribe((List pair) -> {
			Integer t1 = (Integer) pair.get(0);
			Character t2 = (Character) pair.get(1);
			Notification.show("Got a match: " + t1 + "/" + t2);
		});
	}

	/**
	 * A trivial helper to instantiate the selects.
	 */
	private NativeSelect addImmediateSelect(Layout layout, String caption) {
		NativeSelect result = new NativeSelect(caption);
		result.setImmediate(true);
		layout.addComponent(result);
		return result;
	}
}