package structures.node;

// NODO para arboles binarios
// DOS REFERENCIAS: izquierda y derecha
public class Node<T> {

    private T value;
    private Node<T> left;
    private Node<T> right;

    // Constructor ES LO QUE CREA EL NODO
    // NECESARIO SOLO EL VALOR, LAS REFERENCIAS SE INICIALIZAN EN NULL
    public Node(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Node [" + value + "]";
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        if (left == null) {
            if (other.left != null)
                return false;
        } else if (!left.equals(other.left))
            return false;
        if (right == null) {
            if (other.right != null)
                return false;
        } else if (!right.equals(other.right))
            return false;
        return true;
    }

}
