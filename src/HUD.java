import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	private JMenuItem rectangle;
    private JMenuItem sphere;
    private JMenuItem torus;
    private JMenuItem cuboid;
    private JMenu objectMenu;
    private List<JMenuItem> newobjects;
    private List<JMenuItem> objects;
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
    private List<Primitive> currObject = new ArrayList<Primitive>();
    private int currObjId;
    private JPanel panel = new JPanel();
    private boolean dirty = false;
    
    /**
     * basic hud containing data for X,Y,Scale 
     * allows for a choice of simple 3d Obkects to manipulate from the menu.
     */
    public HUD(){
    	// Elements and config
    	this.setTitle("HUD");        
        this.setSize(300,600);
        
        panel.setLayout(new GridLayout(16,0));
        
        Border black = new LineBorder(Color.black);
        JMenuBar menubar = new JMenuBar();
        menubar.setBorder(black);
        JMenu menu = new JMenu("New Object");
        this.objectMenu = new JMenu("Select Object");
        this.newobjects = new ArrayList<JMenuItem>();
        this.objects = new ArrayList<JMenuItem>();
        //this.currObject.add(new Rectangle(2,2,100));
        //registerNewObject("rectangle");
        // add new objects here
        this.newobjects.add(new JMenuItem("rectangle"));
        this.newobjects.add(new JMenuItem("sphere"));
        this.newobjects.add(new JMenuItem("torus"));
        this.newobjects.add(new JMenuItem("cuboid"));
        this.newobjects.add(new JMenuItem("cylinder"));
        for(JMenuItem obj : this.newobjects){
        	obj.addActionListener(this);
        	menu.add(obj);
        }
        menubar.add(menu);
        menubar.add(this.objectMenu);
        this.setJMenuBar(menubar);
        //Labels
        this.selected = new JLabel("current Object: none");
        this.LabelX = new JLabel("X");
        this.LabelY = new JLabel("Y");
        this.LabelZ = new JLabel("Z");
        this.LabelScale = new JLabel("Scale (%)");
        //Sliders
        this.SlideX = new JSlider();
        this.SlideX.setValue(2);
        this.SlideX.setMinimum(2);
        this.SlideX.setMaximum(100);
        this.SlideX.setMajorTickSpacing(10);
        this.SlideX.setMinorTickSpacing(1);
        this.SlideX.createStandardLabels(1);
        this.SlideX.setPaintTicks(true);
        this.SlideX.setPaintLabels(true);
        
        this.SlideY = new JSlider();
        this.SlideY.setValue(2);
        this.SlideY.setMinimum(2);
        this.SlideY.setMaximum(100);
        this.SlideY.setMajorTickSpacing(10);
        this.SlideY.setMinorTickSpacing(1);
        this.SlideY.createStandardLabels(1);
        this.SlideY.setPaintTicks(true);
        this.SlideY.setPaintLabels(true);
        
        this.SlideZ = new JSlider();
        this.SlideZ.setValue(2);
        this.SlideZ.setMinimum(2);
        this.SlideZ.setMaximum(100);
        this.SlideZ.setMajorTickSpacing(10);
        this.SlideZ.setMinorTickSpacing(1);
        this.SlideZ.createStandardLabels(1);
        this.SlideZ.setPaintTicks(true);
        this.SlideZ.setPaintLabels(true);
        
        this.SlideScale = new JSlider();
        this.SlideScale.setValue(100);
        this.SlideScale.setMinimum(10);
        this.SlideScale.setMaximum(1000);
        this.SlideScale.setMajorTickSpacing(200);
        this.SlideScale.setMinorTickSpacing(20);
        this.SlideScale.createStandardLabels(1);
        this.SlideScale.setPaintTicks(true);
        this.SlideScale.setPaintLabels(true);
        
        this.SlideR = new JSlider();
		this.SlideR.setMinimum(10);	        
        this.SlideR.setMaximum(this.SlideScale.getValue());
		this.SlideR.setValue(10);
        this.SlideR.setMajorTickSpacing(100);
        this.SlideR.setMinorTickSpacing(20);
        this.SlideR.createStandardLabels(1);
        this.SlideR.setPaintTicks(true);
        this.SlideR.setPaintLabels(true);
        this.LabelR = new JLabel("inner Radius (%)");
        
        
        this.SlideP = new JSlider();
        this.SlideP.setMinimum(1);
        this.SlideP.setMaximum(10);
        this.SlideP.setValue(1);
        this.SlideP.setMajorTickSpacing(5);
        this.SlideP.setMinorTickSpacing(1);
        this.SlideP.createStandardLabels(1);
        this.SlideP.setPaintTicks(true);
        this.SlideP.setPaintLabels(true);
        this.LabelP = new JLabel("P");
       
        
        this.SlideQ = new JSlider();
        this.SlideQ.setMinimum(1);
        this.SlideQ.setMaximum(10);
        this.SlideQ.setValue(1);
        this.SlideQ.setMajorTickSpacing(5);
        this.SlideQ.setMinorTickSpacing(1);
        this.SlideQ.createStandardLabels(1);
        this.SlideQ.setPaintTicks(true);
        this.SlideQ.setPaintLabels(true);
        this.LabelQ = new JLabel("Q");
        
         
        
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
        this.add(panel);
	}
    
    
	@Override
	/**
	 * if an Object is chosen from menu it will trigger it's respective action
	 * and load the respective Object with default values into the Buffer
	 */
	public void actionPerformed(ActionEvent e) {
		// rectangle - make sure these indexes are right
		if(e.getSource() == this.newobjects.get(0)){
			this.selected.setText(" current Object: rectangle");
			this.SlideX.setValue(2);
			this.SlideY.setValue(2);
			this.SlideScale.setValue(100);
			this.getCurrObjects().add(new Rectangle(SlideX.getValue(),SlideY.getValue(),SlideScale.getValue()));
			registerNewObject("rectangle");
			//System.out.println(currObject.getNormals().length);
			//System.out.println(currObject.getVertices().length);
			//System.out.println(currObject.getIndices().length);
		}
		else if(e.getSource() == this.newobjects.get(1)){
			this.selected.setText(" current Object: sphere");
			this.SlideX.setValue(3);
			this.SlideX.setMinimum(3);
			this.SlideY.setValue(3);
			this.SlideY.setMinimum(3);
			this.SlideScale.setValue(100);
			this.getCurrObjects().add(new Sphere(SlideX.getValue(),SlideY.getValue(),SlideScale.getValue()));
			registerNewObject("sphere");
		}
		else if(e.getSource() == this.newobjects.get(2)){
			this.selected.setText(" current Object: torus");
			
			this.SlideX.setValue(3);
			this.SlideX.setMinimum(3);
			
			this.SlideY.setValue(3);
			this.SlideY.setMinimum(3);
			
			this.SlideScale.setValue(200);
			
			this.SlideR.setMinimum(10);	        
	        this.SlideR.setMaximum(this.SlideScale.getValue());
			this.SlideR.setValue(100);

	        this.SlideP.setMinimum(1);
	        this.SlideP.setMaximum(10);
	        this.SlideP.setValue(1);

	        this.SlideQ.setMinimum(1);
	        this.SlideQ.setMaximum(10);
	        this.SlideQ.setValue(1);

			this.getCurrObjects().add(new Torus(this.SlideX.getValue(),this.SlideY.getValue(),this.SlideScale.getValue(), this.SlideR.getValue(), this.SlideP.getValue(), this.SlideQ.getValue()));
			registerNewObject("torus");
		}
		else if(e.getSource() == this.newobjects.get(3)){
			this.selected.setText(" current Object: cuboid");
			this.SlideX.setValue(1);
			this.SlideY.setValue(1);
			this.SlideZ.setValue(1);
			this.SlideScale.setValue(100);
			this.getCurrObjects().add(new Cuboid(SlideX.getValue(),SlideY.getValue(), SlideZ.getValue(), SlideScale.getValue()));
			registerNewObject("cuboid");
		}
		else if(e.getSource() == this.newobjects.get(4)){
			this.selected.setText(" current Object: cylinder");
			this.SlideX.setValue(10);
			this.SlideY.setValue(10);
			this.SlideR.setValue(100);
			this.SlideScale.setValue(100);
			this.getCurrObjects().add(new Cylinder(SlideX.getValue(),SlideY.getValue(), SlideR.getValue(), SlideScale.getValue()));	
			registerNewObject("cylinder");
		} else{
			int len = this.objects.size();
			for (int i = 0 ; i< len ; i++){
				if(e.getSource() == this.objects.get(i)){
					this.currObjId = i;
					this.selected.setText(" current Object: " + this.getCurrObjects().get(i).getType());
					Primitive obj = this.getCurrObjects().get(i);
					int x = obj.getX();
					int y = obj.getY();
					int z = obj.getZ();
					int r = obj.getR();
					int scale = obj.getScale();
					int p = obj.getP();
					int q = obj.getQ();
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
		getCurrObjects().get(0).create();
		this.setDirty(true);
	}

	public Primitive getCurrObject() {
		return currObject.get(0);
	}
	
	public Primitive getCurrObject(int i) {
		if (i < currObject.size()){
			return currObject.get(i);
		} else return currObject.get(0);
	}
	
	public List<Primitive> getCurrObjects() {
		return currObject;
	}


	public void setCurrObject(Primitive currObject) {
		this.currObject.remove(0);
		currObject.create();
		this.currObject.add(currObject);
		this.setDirty(true);
	}
	
	public void registerNewObject(String type){
		JMenuItem obj = new JMenuItem(type);
		this.objects.add(obj);
    	obj.addActionListener(this);
    	this.objectMenu.add(obj);
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
		int id = this.currObjId;
		System.out.println("[DEBUG] change event triggered");
		this.getCurrObjects().get(id).setX(SlideX.getValue());
		this.getCurrObjects().get(id).setY(SlideY.getValue());
		this.getCurrObjects().get(id).setScale(SlideScale.getValue());
		if (this.getCurrObjects().get(id).getType().equals("torus")){
			((Torus) this.getCurrObjects().get(id)).setR(SlideR.getValue());
			((Torus) this.getCurrObjects().get(id)).setP(SlideP.getValue());
			((Torus) this.getCurrObjects().get(id)).setQ(SlideQ.getValue());
			SlideR.setMaximum(SlideScale.getValue());
		}
		if (this.getCurrObjects().get(id).getType().equals("cuboid")){
			((Cuboid) this.getCurrObjects().get(id)).setZ(SlideZ.getValue());
		}
		if (this.getCurrObjects().get(id).getType().equals("cylinder")){
			((Cylinder) this.getCurrObjects().get(id)).setR(SlideR.getValue());
		}
		this.getCurrObjects().get(id).create();
		this.setDirty(true);
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


	public void setCurrObject(List<Primitive> currObject) {
		this.currObject = currObject;
	}

}
