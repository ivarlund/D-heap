
public class DHeap<AnyType extends Comparable<? super AnyType>> {

	private int dimension;
	private static final int DEFAULT_CAPACITY = 10;
	private int currentSize;
	private AnyType[] array;

	public DHeap() {
		this(DEFAULT_CAPACITY);
		dimension = 2;
	}

	public DHeap(int dimension) {
		if (dimension < 2)
			throw new IllegalArgumentException();
		this.dimension = dimension;
		int capacity = DEFAULT_CAPACITY;
		currentSize = 0;
		array = (AnyType[]) new Comparable[capacity + 1];
	}

	public int firstChildIndex(int parent) {
		if (parent < 1)
			throw new IllegalArgumentException();
		return parent * dimension - (dimension - 2);
	}

	public int parentIndex(int child) {
		if (child < 2)
			throw new IllegalArgumentException();
		return (child + (dimension - 2)) / dimension;
	}

	public int size() {
		return currentSize;
	}

	AnyType get(int index) {
		return array[index];
	}

	public void insert(AnyType x) {
		if (currentSize == array.length - 1)
			enlargeArray(array.length * 2 + 1);

		int hole = ++currentSize;
		if (hole > 1) {
			int parent = parentIndex(hole);
			while (hole != 0 && x.compareTo(array[parent]) < 0) {
				array[hole] = array[parent];
				hole = parent;
				if (hole == 1)
					break;
				parent = parentIndex(hole);
			}
		}
		array[hole] = x;
	}
	
	private void enlargeArray(int newSize) {
		AnyType[] old = array;
		array = (AnyType[]) new Comparable[newSize];
		for (int i = 0; i < old.length; i++)
			array[i] = old[i];
	}

	public AnyType findMin() {
		if (isEmpty())
			throw new UnderflowException();
		return array[1];
	}

	public AnyType deleteMin() {
		if (isEmpty())
			throw new UnderflowException();

		AnyType minItem = findMin();
		array[1] = array[currentSize--];
		percolateDown(1);

		return minItem;
	}

	private void buildHeap() {
		for (int i = currentSize / 2; i > 0; i--)
			percolateDown(i);
	}

	public boolean isEmpty() {
		return currentSize == 0;
	}

	public void makeEmpty() {
		currentSize = 0;
	}

	private void percolateDown(int hole) {
		int child = firstChildIndex(hole);
		AnyType tmp = array[hole];

		for (; firstChildIndex(hole) <= currentSize; hole = child) {
			child = firstChildIndex(hole);
			int minChild = child;
			for (int i = 0; i < dimension; i++) {
				if (minChild == currentSize) {
					break;
				}
				if (array[child].compareTo(array[minChild + 1]) > 0) {
					child = minChild + 1;
				}
				minChild++;
			}
			if (tmp.compareTo(array[child]) > 0) {
				array[hole] = array[child];
			} else {
				break;
			}
		}
		array[hole] = tmp;
	}
}
