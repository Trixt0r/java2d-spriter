# Java2D Spriter

This project implements the abstract loader and drawer classes of the generic
Java implementation for Spriter.

Simply import `com.brashmonkey.spriter.java2d.Loader` and
`com.brashmonkey.spriter.java2d.Drawer` and you should be ready to go.

Example
=======

```
public class SpriterTest extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final AffineTransform identity = new AffineTransform();

    Player player;
    Drawer drawer;
    Loader loader;

    public SpriterTest() {
        String scml = "";
        try {
            // Read the file
            String[] tmp = {""};
            Files.readAllLines(Paths.get("assets/monster/basic_002.scml"))
                    .forEach(line -> tmp[0] += line);
            scml = tmp[0];
        } catch (IOException ex) {
            System.out.println(ex);
        }
        Data data = new SCMLReader(scml).getData();
        player = new Player(data.getEntity(0));
        player.setPosition(640, 360);
        this.loader = new Loader(data);
        this.loader.load("assets/monster");

        MainLoop loop = new MainLoop();
        loop.setDoubleBuffered(true);
        drawer = new Drawer(this, loader);

        super.getContentPane().add(loop);
        super.setSize(new Dimension(1280,720));
        super.setTitle("Java2D test");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);
        super.setVisible(true);

        new Thread(loop).start();
    }

    private class MainLoop extends JPanel implements Runnable {

        private static final long serialVersionUID = 1L;

        @Override
        public void run() {
            while(super.isVisible()){
                try{
                    Thread.sleep(15);
                } catch(InterruptedException e){
                    System.err.println("Thread got interrupted!");
                }

                try {
                    SwingUtilities.invokeAndWait(new Runnable() {

                        @Override
                        public void run() {
                            player.update();
                            repaint();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        public void paintComponent(Graphics g) {
            drawer.graphics = (Graphics2D)g;
            g.clearRect(0, 0, getWidth(), getHeight());
            if(player.getCurrentKey() != null){
                drawer.draw(player);
                drawer.graphics.setTransform(identity);
                drawer.graphics.scale(1, -1);
                drawer.graphics.translate(0, -getContentPane().getHeight());
                drawer.drawBones(player);
                drawer.drawBoxes(player);
            }
        }
    }

    public static void main(String[] args) {
        new SpriterTest();
    }
}
```

Dependency management with maven
================================
Add the following repository url:

```
https://raw.github.com/Trixt0r/java2d-spriter/mvn/
```
GroupId is `com.brashmonkey.spriter`

ArtifactId is `java2d-spriter`