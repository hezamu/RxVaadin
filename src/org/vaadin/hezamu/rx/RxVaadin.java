package org.vaadin.hezamu.rx;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.Observable.OnSubscribeFunc;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Property.ValueChangeNotifier;

/**
 * An example on how to convert the event streams of Vaadin listeners to
 * Observables. This is just a quick PoC at this point.
 * 
 * @author henri@vaadin.com
 */
@SuppressWarnings("serial")
public class RxVaadin {
	/**
	 * Get an {@link Observable} corresponding with the event stream of a
	 * {@link ValueChangeNotifier}.
	 * 
	 * @param notifier
	 * @return
	 */
	public static Observable<ValueChangeEvent> valueChangeEvents(
			ValueChangeNotifier notifier) {
		return Observable.create(new OnSubscribeFunc<ValueChangeEvent>() {
			public Subscription onSubscribe(
					final Observer<? super ValueChangeEvent> observer) {
				final ValueChangeListener listener = new ValueChangeListener() {
					@Override
					public void valueChange(ValueChangeEvent event) {
						observer.onNext(event);
					}
				};

				notifier.addValueChangeListener(listener);

				return new Subscription() {
					public void unsubscribe() {
						notifier.removeValueChangeListener(listener);
					}
				};
			}
		});
	}

	/**
	 * Convert a Observable<ValueChangeEvent> into a Observable of values
	 * represented by the events.
	 * 
	 * @param notifier
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Observable<T> values(ValueChangeNotifier notifier) {
		return valueChangeEvents(notifier).map((ValueChangeEvent vce) -> {
			return (T) vce.getProperty().getValue();
		});
	}
}