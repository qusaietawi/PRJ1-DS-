package application;


public class MyList<T extends Comparable<T>> implements Listable<T> {
	private T arr[];
	private int n = 0;

	public MyList(int size) {
		arr = (T[]) new Comparable[size];
	}

	public void insert(T o) {
		if (n < arr.length) {
			arr[n] = o;
			n++;
		} else
			System.out.println("Error: list is full!!!!");
	}

	public boolean delete(T data) {
		for (int i = 0; i < n; i++)
			if (data.compareTo(arr[i]) == 0) {
				for (int j = i + 1; j < n; j++)
					arr[j - 1] = arr[j];
				n--;
				return true;
			}
		return false;
	}

	public boolean search(T value) {
		for (int i = 0; i < n; i++)
			if (value.compareTo(arr[i]) == 0)
				return true;
		return false;
	}

	@Override
	public void clear() {
		n = 0;
	}

	public void print() {
		for (int i = 0; i < n; i++)
			System.out.println(i + "\t" + arr[i]);
	}

	@Override
	public T getAt(int index) {
		if(index < n)
			return arr[index];
		return null;
	}

	@Override
	public int size() {
		return n;
	}
}