package cn.tx.snow.demo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MyImage extends JPanel{

    // 定义成员图片对象
    BufferedImage bgImage; // 表示当前窗口要显示的图片

    public static void main(String[] args) {
        // 创建窗口
        JFrame frame = new JFrame();
        // 设置尺寸大小
        frame.setSize(1000, 700);
        // 设置标题
        frame.setTitle("Digital Album");
        // 设置窗口居中
        frame.setLocationRelativeTo(null);
        // 关闭窗口，把JVM也停止
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // JFrame.EXIT_ON_CLOSE = 3, 所以括号内可以为3

        // 创建面板对象
        // JPanel jPanel = new JPanel();

        // 创建面板对象
        MyImage myImage = new MyImage();
        // 把面板对象存放到窗口上
        frame.add(myImage);
        // 调用初始化图片的方法
        myImage.initImgs();
        // 把图片画到窗口上去，调用绘图的方法
        // 重绘
        // myImage.repaint();

        // 写方法，让ff一直变，让图片由浅变深，开启新的线程
        myImage.begin();

        // 显示出窗口
        frame.setVisible(true);
    }

    // 图片显示比例值
    float ff = 0f;

    // 标志，表示数组的下标索引值
    int num = 0;

    /**
     * 让ff变量的值一直变
     */
    public void begin() {
        // 启动线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 一直去改变ff的值
                while (true) {

                    // 从数组中获取图片
                    bgImage = images[num];
                    // 让 num 累加
                    num++;
                    // 考虑数组越界异常
                    if (num == 4) {
                        num = 0;
                    }
                    while (true) {
                        if (ff < 100f) {
                            ff += 2f;
                            // 调用重绘
                            repaint();
                        } else {
                            ff = 0f;
                            break;
                        }

                        // 休眠
                        try {
                            Thread.sleep(75);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    // 需要重写父类的方法。提供绘画的方法。ctrl+o
    // Graphic g 画笔 画图片
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 转换成子类对象,提供了更多的方法
        Graphics2D graphics2D = (Graphics2D) g;

        // 画什么东西
        if(bgImage != null) {

            // 加入淡入的效果
            graphics2D.setComposite(AlphaComposite.SrcOver.derive(ff / 100f));
            // 把图片画到窗口上去
            graphics2D.drawImage(bgImage, 0, 0, bgImage.getWidth(), bgImage.getHeight(), null);
        }
    }

    // 定义图片类型数组
    // BufferedImage 表示图片对象
    BufferedImage[] images = new BufferedImage[4];

    /**
     *  加载提前准备好的一些图片
     */
    public void initImgs() {
        try {
            // 编写for循环
            for (int i = 1; i <= 4; i++) {
                // 每循环一次，都要加载一张图片
                BufferedImage image = ImageIO.read(MyImage.class.getResource("/images/bg" + i + ".png"));
                // 每读取到一张图片对象，把它存放到数组中
                images[i-1] = image;
            }

            // 给成员变量赋值
            bgImage = images[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
