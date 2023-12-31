package views;

import controllers.MenuController;
import model.CanadaGame;
import utils.GameConfig;
import utils.SpriteLoader;
import utils.UIStyle;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuView extends UIView{

    private MenuController controller;
    private JButton startButton;
    private JButton exitButton;
    private JLabel titleLabel;

    private JLabel maxScoreLabel;

    private BufferedImage background;

    public MenuView(CanadaGame game){
        super(game);
        controller = new MenuController(game, this);
        this.background = SpriteLoader.getSprite("/sprites/ui/mainmenu_background.png");
    }

    @Override
    public void buildView(){

        GameConfig gc = GameConfig.getInstance();

        this.setSize(gc.getWinWidth(), gc.getWinHeight());

        this.setLayout(null);

        // Title

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(40,40,40,190));

        int titleWidth = (int)(gc.getWinWidth() * 0.4);
        int titleHeight = (int)(gc.getWinHeight() * 0.15);
        titlePanel.setBounds((gc.getWinWidth() - titleWidth) / 2 , (gc.getWinHeight() - titleHeight) / 8, titleWidth, titleHeight);
        titlePanel.setBorder(new EmptyBorder(20,25,20,25));
        titleLabel = new JLabel("CANADA CAMP");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setAlignmentY(CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);

        // Max Score

        JPanel maxScorePanel = new JPanel();
        maxScorePanel.setBackground(new Color(40,40,40,190));

        int maxScoreWidth = (int)(gc.getWinWidth() * 0.25);
        int maxScoreHeight = (int)(gc.getWinHeight() * 0.09);
        maxScorePanel.setBounds(0 , 0, maxScoreWidth, maxScoreHeight);
        maxScorePanel.setBorder(new EmptyBorder(8,10,8,10));
        maxScoreLabel = new JLabel("Max Score : 0");
        maxScoreLabel.setForeground(Color.WHITE);
        maxScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        maxScoreLabel.setAlignmentX(CENTER_ALIGNMENT);
        maxScoreLabel.setAlignmentY(CENTER_ALIGNMENT);

        maxScorePanel.add(maxScoreLabel);

        // Boutons

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.PAGE_AXIS));
        int buttonsWidth = (int)(gc.getWinWidth() * 0.2);
        int buttonsHeight = (int)(gc.getWinHeight() * 0.4);
        buttonContainer.setBounds((gc.getWinWidth() - buttonsWidth) / 2 , (gc.getWinHeight() - buttonsHeight) / 2, buttonsWidth, buttonsHeight);
        buttonContainer.setBackground(new Color(0,0,0,0));

        startButton = new JButton("START");
        UIStyle.setButtonStyle(startButton);

        exitButton = new JButton("EXIT");
        UIStyle.setButtonStyle(exitButton);

        startButton.addActionListener(e -> {
            controller.notifyButtonStartPressed();
        });

        exitButton.addActionListener(e -> {
            controller.notifyButtonExitPressed();
        });

        buttonContainer.add(startButton);
        buttonContainer.add(Box.createRigidArea(new Dimension(0,20)));
        buttonContainer.add(exitButton);

        this.add(titlePanel);
        this.add(maxScorePanel);
        this.add(buttonContainer);

    }


    @Override
    public void update() {
        maxScoreLabel.setText("Max Score : " + game.getMaxScore());
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }

}
