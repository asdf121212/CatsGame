public abstract class Menu extends Level {

    public abstract boolean transition();

    public abstract void mouseMove(int x, int y);
    public abstract void mouseClick(int x, int y);

    @Override
    public void Dispose() {

    }
}
