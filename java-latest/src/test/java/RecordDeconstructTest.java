public class RecordDeconstructTest {
    record P(int x, int y) {}

    sealed interface IShape permits Circle {}
    record Circle(P c, int r) implements IShape {}

    public static int getMinY(IShape shape) {
        return switch (shape) {
            case Circle(var __, var r)when r < 0 -> throw new IllegalArgumentException();
            case Circle(var p, var r) -> p.y - r;
            default -> throw new RuntimeException();
        };
    }

    public static void main(String[] args) {}
}
