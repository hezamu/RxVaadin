package org.vaadin.hezamu.rx;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.Observable.OnSubscribeFunc;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Property.ValueChangeNotifier;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * An example on how to convert the event streams of Vaadin listeners to
 * Observables. This is just a quick PoC at this point.
 * 
 * @author henri@vaadin.com
 */
public class RxVaadin {
	/**
	 * Get a {@link Observable} corresponding with the click stream of a
	 * {@link Button}.
	 */
	public static Observable<ClickEvent> clicks(Button button) {
		return Observable.create(new OnSubscribeFunc<ClickEvent>() {
			public Subscription onSubscribe(
					final Observer<? super ClickEvent> observer) {
				final ClickListener listener = e -> observer.onNext(e);

				button.addClickListener(listener);

				return () ->  button.removeClickListener(listener);
			}
		});
	}
			
	/**
	 * Get a {@link Observable} corresponding with the event stream of a
	 * {@link ValueChangeNotifier}.
	 */
	public static Observable<ValueChangeEvent> valueChangeEvents(
			ValueChangeNotifier notifier) {
		return Observable.create(new OnSubscribeFunc<ValueChangeEvent>() {
			public Subscription onSubscribe(
					final Observer<? super ValueChangeEvent> observer) {
				final ValueChangeListener listener = e -> observer.onNext(e);

				notifier.addValueChangeListener(listener);

				return () -> notifier.removeValueChangeListener(listener);
			}
		});
	}

	/**
	 * Convert a the stream of {@link ValueChangeEvent}s from a
	 * {@link ValueChangeNotifier} into a Observable of values contained in the
	 * events.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Observable<T> values(ValueChangeNotifier notifier) {
		return valueChangeEvents(notifier).map(vce -> {
			return (T) vce.getProperty().getValue();
		});
	}

	/**
	 * Convert a the stream of {@link ValueChangeEvent}s from a
	 * {@link ValueChangeNotifier} into a Observable of values contained in the
	 * events. The resulting Observable will have the given value prepended to
	 * it to make sure it will always have a value.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Observable<T> valuesWithDefault(
			ValueChangeNotifier notifier, T defaultValue) {
		return ((Observable<T>) values(notifier)).startWith(defaultValue);
	}

	/**
	 * Hook up a {@link Property} to display the values emitted by a
	 * {@link Observable}.
	 */
	public static <T> void follow(Property<T> target, Observable<T> source) {
		source.subscribe(value -> {
			target.setValue(value);
		});
	}

	/**
	 * Hook up a String {@link Property} to clear whenever the given
	 * {@link Observable} emits a value.
	 */
	public static <T> void clearBy(Property<String> target, Observable<T> source) {
		clearBy(target, source, "");
	}

	/**
	 * Hook up a {@link Property} to set to a value whenever the given
	 * {@link Observable} emits a value.
	 */
	public static <T, R> void clearBy(Property<T> target, Observable<R> source,
			T clearValue) {
		source.subscribe(value -> {
			target.setValue(clearValue);
		});
	}
}