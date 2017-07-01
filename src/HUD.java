import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * GUI containing control features for geometrical manipulation of 2d and 3d Objects in OpenGL
 * @author Andreas Berger
 *
 */
public class HUD extends JFrame implements ActionListener, ChangeListener{

    private JMenu objectMenu;
    private JMenu editMenu;
    private List<JMenuItem> newobjects;
	private Map<String, JMenuItem> objects;
    public JSlider SlideX;
    public JSlider SlideY;
    public JSlider SlideZ;
    public JSlider SlideScale;
    public JSlider SlideR;
    public JSlider SlideP;
    public JSlider SlideQ;
    private JLabel selected;
    private JLabel LabelX; 
    private JLabel LabelY; 
    private JLabel LabelZ; 
    private JLabel LabelScale;
    private JLabel LabelR; 
    private JLabel LabelP; 
    private JLabel LabelQ; 
    private JLabel LabelFPS; 
    private JLabel LabelUPS;
    private JLabel Kills; 
    private JLabel Level;
    //private JLabel Leben;
    //private List<Primitive> currObject = new ArrayList<Primitive>();
    private String currObj;
    private JMenuItem currObject;
    private JPanel panel = new JPanel();
    private boolean dirty = false;
    private Game game;
    /**
     * basic hud containing data for X,Y,Scale 
     * allows for a choice of simple 3d Obkects to manipulate from the menu.
     */
    public HUD(Game game){
    	this.setGame(game);
    	// Elements and config
    	this.setTitle("HUD");        
        this.setSize(400,800);
        
        panel.setLayout(new GridLayout(20,0));
        
        Border black = new LineBorder(Color.black);
        JMenuBar menubar = new JMenuBar();
        menubar.setBorder(black);
        JMenu menu = new JMenu("New Object");
        this.setObjectMenu(new JMenu("Select Object"));
        this.newobjects = new ArrayList<JMenuItem>();
        this.setObjects(new HashMap<String, JMenuItem>());
        // add new objects here
        this.newobjects.add(new JMenuItem("player"));
        this.newobjects.add(new JMenuItem("tube"));
        for(JMenuItem obj : this.newobjects){
        	obj.addActionListener(this);
        	menu.add(obj);
        }
        //edit menu
        editMenu = new JMenu("Edit");
        JMenuItem delete = new JMenuItem("delete");
        delete.addActionListener(this);
        editMenu.add(delete);
        
        menubar.add(menu);
        menubar.add(this.getObjectMenu());
        menubar.add(editMenu);
        this.setJMenuBar(menubar);
        //Labels
        this.selected = new JLabel("current Object: none");
        this.LabelX = new JLabel("X");
        this.LabelY = new JLabel("Y");
        this.LabelZ = new JLabel("Z");
        this.LabelScale = new JLabel("Scale (%)");
        this.LabelR = new JLabel("inner Radius (%)");
        this.LabelP = new JLabel("P");
        this.LabelQ = new JLabel("Q");
        
        this.LabelFPS = new JLabel("FPS: 0");
        this.LabelUPS = new JLabel("FPS: 0");
        this.Kills = new JLabel("Abschuesse: 0");
        this.Level = new JLabel("Level: 0");
        
        //Sliders
        this.SlideX = new JSlider();
        this.SlideX.setValue(100);
        this.SlideX.setMinimum(1);
        this.SlideX.setMaximum(1000);
        this.SlideX.setMajorTickSpacing(100);
        this.SlideX.setMinorTickSpacing(50);
        this.SlideX.createStandardLabels(1);
        this.SlideX.setPaintTicks(true);
        this.SlideX.setPaintLabels(true);
        
        this.SlideY = new JSlider();
        this.SlideY.setValue(100);
        this.SlideY.setMinimum(1);
        this.SlideY.setMaximum(1000);
        this.SlideY.setMajorTickSpacing(100);
        this.SlideY.setMinorTickSpacing(50);
        this.SlideY.createStandardLabels(1);
        this.SlideY.setPaintTicks(true);
        this.SlideY.setPaintLabels(true);
        
        this.SlideZ = new JSlider();
        this.SlideZ.setValue(100);
        this.SlideZ.setMinimum(1);
        this.SlideZ.setMaximum(1000);
        this.SlideZ.setMajorTickSpacing(100);
        this.SlideZ.setMinorTickSpacing(50);
        this.SlideZ.createStandardLabels(1);
        this.SlideZ.setPaintTicks(true);
        this.SlideZ.setPaintLabels(true);
        
        this.SlideScale = new JSlider();
        this.SlideScale.setValue(100);
        this.SlideScale.setMinimum(1);
        this.SlideScale.setMaximum(1000);
        this.SlideScale.setMajorTickSpacing(100);
        this.SlideScale.setMinorTickSpacing(50);
        this.SlideScale.createStandardLabels(1);
        this.SlideScale.setPaintTicks(true);
        this.SlideScale.setPaintLabels(true);
        
        this.SlideR = new JSlider();
        this.SlideR.setValue(100);
		this.SlideR.setMinimum(1);	        
        this.SlideR.setMaximum(1000);
        this.SlideR.setMajorTickSpacing(100);
        this.SlideR.setMinorTickSpacing(50);
        this.SlideR.createStandardLabels(1);
        this.SlideR.setPaintTicks(true);
        this.SlideR.setPaintLabels(true);
        
        
        this.SlideP = new JSlider();
        
        this.SlideP.setMinimum(1);
        this.SlideP.setMaximum(10);
        this.SlideP.setValue(1);
        this.SlideP.setMajorTickSpacing(1);
        this.SlideP.setMinorTickSpacing(1);
        this.SlideP.createStandardLabels(1);
        this.SlideP.setPaintTicks(true);
        this.SlideP.setPaintLabels(true);
       
        
        this.SlideQ = new JSlider();
        this.SlideQ.setMinimum(1);
        this.SlideQ.setMaximum(10);
        this.SlideQ.setValue(1);
        this.SlideQ.setMajorTickSpacing(1);
        this.SlideQ.setMinorTickSpacing(1);
        this.SlideQ.createStandardLabels(1);
        this.SlideQ.setPaintTicks(true);
        this.SlideQ.setPaintLabels(true);
        
         
        
        this.SlideX.addChangeListener(this);
        this.SlideY.addChangeListener(this);
        this.SlideZ.addChangeListener(this);
        this.SlideScale.addChangeListener(this);
        this.SlideR.addChangeListener(this);
        this.SlideP.addChangeListener(this);
        this.SlideQ.addChangeListener(this);
        
        //structure of elements
        panel.add(this.selected);
        
        panel.add(this.LabelX);
        panel.add(this.SlideX);
        panel.add(this.LabelY);
        panel.add(this.SlideY);
        panel.add(this.LabelZ);
        panel.add(this.SlideZ);
        panel.add(this.LabelScale);
        panel.add(this.SlideScale);
        panel.add(this.LabelR);
        panel.add(this.SlideR);
        panel.add(this.LabelP);
        panel.add(this.SlideP);
        panel.add(this.LabelQ);
        panel.add(this.SlideQ);
        panel.add(this.LabelFPS);
        panel.add(this.LabelUPS);
        panel.add(this.Kills);
        panel.add(this.Level);
        //panel.add(this.Leben);
        this.add(panel);
	}
    
    
	@Override
	/**
	 * if an Object is chosen from menu it will trigger it's respective action
	 * and load the respective Object with default values into the Buffer
	 */
	public void actionPerformed(ActionEvent e) {
		// rectangle - make sure these indexes are right
		JMenuItem source = (JMenuItem) e.getSource();
		//System.out.println("you just slected: " + source.getText());
		GameObject gameObject = null;
		if(e.getSource() == this.newobjects.get(0)){
			this.selected.setText(" current Object: player");
			this.SlideX.setValue(100);
			this.SlideY.setValue(100);
			this.SlideScale.setValue(100);
			gameObject = new Player("player", this.game);
			this.game.addGameObject(gameObject);
			//System.out.println(currObject.getNormals().length);
			//System.out.println(currObject.getVertices().length);
			//System.out.println(currObject.getIndices().length);
		}
		else if(e.getSource() == this.newobjects.get(1)){
			this.selected.setText(" current Object: tube");
			this.SlideX.setValue(100);
			this.SlideY.setValue(100);
			this.SlideScale.setValue(100);
			gameObject =new Tube("tube", this.game);
			this.game.addGameObject(gameObject);
		}
		else if(e.getSource() == this.editMenu.getItem(0) ){
			this.deleteObject();
		}
		else{
			Iterator<JMenuItem> menuitems = this.getObjects().values().iterator();
	    	while(menuitems.hasNext()){
	    		JMenuItem menuitem = menuitems.next();
				//System.out.println("checking selector: " + menuitem.getText() + " == " + ((JMenuItem) e.getSource()).getText() + " is " + (e.getSource() == menuitem));
				if(e.getSource() == menuitem){
					String name = menuitem.getText();
					gameObject = this.game.getGameObjects().get(name);
					this.currObj = name;
					this.currObject = menuitem;
					this.selected.setText(" current Object: " + name);
					int x = gameObject.getxScale();
					int y = gameObject.getyScale();
					int z = gameObject.getzScale();
					int r = gameObject.getrScale();
					int scale = gameObject.getScale();
					int p = gameObject.getP();
					int q = gameObject.getQ();
					this.SlideX.setValue(x);
					this.SlideY.setValue(y);
					this.SlideZ.setValue(z);
					this.SlideP.setValue(p);
					this.SlideQ.setValue(q);
					this.SlideR.setValue(r);
					this.SlideScale.setValue(scale);
					break;
				}
			}
				
		}
		//if(gameObject != null){
		//	gameObject.buffer();
		//}
		this.setDirty(true);
	}

	public void registerNewObject(String name){
		JMenuItem obj = new JMenuItem(name);
		this.getObjects().put(name, obj);
    	obj.addActionListener(this);
    	this.getObjectMenu().add(obj);
    	obj.getActionListeners()[0].actionPerformed(new ActionEvent(obj, ActionEvent.ACTION_PERFORMED, null));
	}
	public void deleteObject(){
		this.game.destroyObject(this.currObj);

		if (this.objects.size() > 0){
			JMenuItem newobj = this.objects.get(0);
			this.currObj = newobj.getText();
			this.currObject = newobj;
		}
		else{
			this.currObj = null;
			this.currObject = null;
		}
	}

	@Override
	/**
	 * SLiders will trigger this actionset.
	 * Changing a value will force a recalculation of the objects geometry and reload it into the buffer
	 * through the dirty bit
	 */
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		// can be optimized a little more by checking for source and changing only respective value ~see above :P
		String name = this.currObj;
		GameObject gameObject = this.game.getGameObjects().get(name);
		System.out.println("[DEBUG] change event triggered");
		if(gameObject != null){
			gameObject.setxScale(SlideX.getValue());
			gameObject.setyScale(SlideY.getValue());
			gameObject.setzScale(SlideZ.getValue());
			gameObject.setScale(SlideScale.getValue());
			gameObject.setrScale(SlideR.getValue());
			gameObject.setP(SlideP.getValue());
			gameObject.setQ(SlideQ.getValue());
			SlideR.setMaximum(SlideScale.getValue());
			gameObject.setDirty(true);
		}
	}


	public JSlider getSlideR() {
		return SlideR;
	}


	public void setSlideR(JSlider slideR) {
		SlideR = slideR;
	}


	public JSlider getSlideP() {
		return SlideP;
	}


	public void setSlideP(JSlider slideP) {
		SlideP = slideP;
	}


	public JSlider getSlideQ() {
		return SlideQ;
	}


	public void setSlideQ(JSlider slideQ) {
		SlideQ = slideQ;
	}


	public boolean isDirty() {
		return dirty;
	}


	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}



	public Game getGame() {
		return game;
	}


	public void setGame(Game game) {
		this.game = game;
	}


	public JLabel getLabelFPS() {
		return LabelFPS;
	}


	public void setLabelFPS(JLabel labelFPS) {
		LabelFPS = labelFPS;
	}


	public JLabel getLabelUPS() {
		return LabelUPS;
	}


	public void setLabelUPS(JLabel labelUPS) {
		LabelUPS = labelUPS;
	}


	public Map<String, JMenuItem> getObjects() {
		return objects;
	}


	public void setObjects(Map<String, JMenuItem> objects) {
		this.objects = objects;
	}


	public JMenuItem getCurrObject() {
		return currObject;
	}


	public void setCurrObject(JMenuItem currObject) {
		this.currObject = currObject;
	}


	public JMenu getObjectMenu() {
		return objectMenu;
	}


	public void setObjectMenu(JMenu objectMenu) {
		this.objectMenu = objectMenu;
	}


	public JLabel getKills() {
		return Kills;
	}


	public void setKills(JLabel kills) {
		Kills = kills;
	}


	public JLabel getLevel() {
		return Level;
	}


	public void setLevel(JLabel level) {
		Level = level;
	}

}
