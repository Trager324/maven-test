package com.syd.java17.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class PeekingIterator implements Iterator<Integer> {
	List<Integer> values;
	int idx = 0;
	public PeekingIterator(Iterator<Integer> iterator) {
	    // initialize any member here.
	    values = new ArrayList<>();
		while (iterator.hasNext()) {
			values.add(iterator.next());
		}
	}

    // Returns the next element in the iteration without advancing the iterator.
	public Integer peek() {
		return values.get(idx);
	}

	// hasNext() and next() should behave the same as in the Iterator interface.
	// Override them if needed.
	@Override
	public Integer next() {
		return values.get(idx++);
	}

	@Override
	public boolean hasNext() {
		return idx < values.size();
	}
}
