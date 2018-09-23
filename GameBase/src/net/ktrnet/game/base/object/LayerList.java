package net.ktrnet.game.base.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class LayerList<E> extends HashMap<Integer, List<E>> {

	public int size(int layer) {
		return this.get(layer).size();
	}

	public boolean isEmpty(int layer) {
		return this.get(layer).isEmpty();
	}

	public boolean contains(int layer, Object o) {
		return this.get(layer).contains(o);
	}

	public int indexOf(int layer, Object o) {
		return this.get(layer).indexOf(o);
	}

	public int lastIndexOf(int layer, Object o) {
		return this.get(layer).lastIndexOf(o);
	}

	public Object[] toArray(int layer) {
		return this.get(layer).toArray();
	}

	public <T> T[] toArray(int layer, T[] a) {
		return this.get(layer).toArray(a);
	}

	public E get(int layer, int index) {
		return super.get(layer).get(index);
	}

	public E set(int layer, int index, E element) {
		return super.get(index).set(index, element);
	}

	public boolean add(int layer, E e) {
		if (!this.containsKey(layer)) {
			this.put(layer, new ArrayList<E>());
		}
		return this.get(layer).add(e);
	}

	public E remove(int layer, int index) {
		return this.get(layer).remove(index);
	}

	public boolean remove(int layer, Object o) {
		return this.get(layer).remove(o);
	}

	public void clear(int layer) {
		this.get(layer).clear();
	}

	public boolean addAll(int layer, Collection<? extends E> c) {
		return this.get(layer).addAll(c);
	}

	public boolean addAll(int layer, int index, Collection<? extends E> c) {
		return this.get(layer).addAll(index, c);
	}

	public boolean removeAll(int layer, Collection<?> c) {
		return this.get(layer).removeAll(c);
	}

	public boolean retainAll(int layer, Collection<?> c) {
		return this.get(layer).retainAll(c);
	}

	public ListIterator<E> listIterator(int layer, int index) {
		return this.get(layer).listIterator(index);
	}

	public ListIterator<E> listIterator(int layer) {
		return this.get(layer).listIterator();
	}

	public Iterator<E> iterator(int layer) {
		return this.get(layer).iterator();
	}

	public List<E> subList(int layer, int fromIndex, int toIndex) {
		return this.get(layer).subList(fromIndex, toIndex);
	}

	public void forEach(int layer, Consumer<? super E> action) {
		this.get(layer).forEach(action);
	}

	public Spliterator<E> spliterator(int layer) {
		return this.get(layer).spliterator();
	}

	public boolean removeIf(int layer, Predicate<? super E> filter) {
		return this.get(layer).removeIf(filter);
	}

	public void replaceAll(int layer, UnaryOperator<E> operator) {
		this.get(layer).replaceAll(operator);
	}

	public void sort(int layer, Comparator<? super E> c) {
		this.get(layer).sort(c);
	}

}
