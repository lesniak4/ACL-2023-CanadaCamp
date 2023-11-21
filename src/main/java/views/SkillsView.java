package views;

import engine.Cmd;
import model.CanadaGame;
import model.skills.PlayerSkill;
import utils.GameConfig;
import utils.SpriteLoader;

import javax.swing.*;
import java.awt.*;

public class SkillsView extends UIView{

    private JLabel speedIcon;
    private JLabel speedLabel;

    private JLabel invisibleIcon;
    private JLabel invisibleLabel;


    public SkillsView(CanadaGame game) {
        super(game);
    }

    @Override
    public void buildView() {

        GameConfig gc = GameConfig.getInstance();

        int width = gc.getWinWidth()/6;
        this.setBounds((gc.getWinWidth() - width)/2, 0, width, (int)(gc.getWinHeight()* 0.10));
        this.setBackground(new Color(40,40,40,190));
        this.setAlignmentX(CENTER_ALIGNMENT);

        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        speedIcon = new JLabel(new ImageIcon(SpriteLoader.getInstance().getSpeedUI()));
        invisibleIcon = new JLabel(new ImageIcon(SpriteLoader.getInstance().getInvisibilityUI()));
        speedLabel = new JLabel("Speed");
        invisibleLabel = new JLabel("Invisible");
        speedLabel.setForeground(Color.WHITE);
        invisibleLabel.setForeground(Color.WHITE);

        JPanel speed = skillContainer(speedIcon, speedLabel);
        JPanel invisible = skillContainer(invisibleIcon, invisibleLabel);

        this.add(speed);
        this.add(invisible);

        layout.putConstraint(SpringLayout.WEST, speed, 15, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, invisible, 15, SpringLayout.EAST, speed);

        layout.putConstraint(SpringLayout.VERTICAL_CENTER, speed, 0, SpringLayout.VERTICAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, invisible, 0, SpringLayout.VERTICAL_CENTER, this);
    }

    @Override
    public void update() {

        updateSkillLabel(speedLabel, Cmd.SKILL_1);
        updateSkillLabel(invisibleLabel, Cmd.SKILL_2);
    }

    public void updateSkillLabel(JLabel label, Cmd cmd){

        PlayerSkill skill = game.getSkills().getSkill(cmd);
        if(game.getSkills().isSkillAvailable(cmd)){
            label.setText(skill.getCmdString() + "|" + skill.getCost());
            label.setForeground(Color.WHITE);
            label.getParent().getComponent(1).setVisible(true);
        }else{
            label.setText((int)game.getSkills().getCooldown(cmd) + " s");
            label.setForeground(Color.red);
            label.getParent().getComponent(1).setVisible(false);
        }
    }

    public JPanel skillContainer(JLabel icon, JLabel label){

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0,0,0,0));

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        icon.setAlignmentX(CENTER_ALIGNMENT);

        JLabel goldIcon = new JLabel(new ImageIcon(SpriteLoader.getInstance().getGoldCoinsSmallUI()));

        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(new Color(0,0,0,0));
        labelPanel.add(label);
        labelPanel.add(goldIcon);

        labelPanel.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(icon);
        panel.add(labelPanel);

        return panel;
    }
}