import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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
    public JSlider SlideX;
    public JSlider SlideY;
    public JSlider SlideScale;
    public JSlider SlideR;
    public JSlider SlideP;
    public JSlider SlideQ;
    private JLabel selected;
    private JLabel LabelX; 
    private JLabel LabelY; 
    private JLabel LabelScale;
    private JLabel LabelR; 
    private JLabel LabelP; 
    private JLabel LabelQ; 
    private Primitive currObject;
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
        
        panel.setLayout(new GridLayout(14,0));
        
        Border black = new LineBorder(Color.black);
        JMenuBar menubar = new JMenuBar();
        menubar.setBorder(black);
        JMenu menu = new JMenu("Choose Object");
        this.currObject = new Rectangle(2,2,100);
        this.rectangle = new JMenuItem("rectangle");
        this.sphere = new JMenuItem("sphere");
        this.torus = new JMenuItem("torus");
        this.rectangle.addActionListener(this);
        this.sphere.addActionListener(this);
        this.torus.addActionListener(this);
        
        menu.add(this.rectangle);
        menu.add(this.sphere);
        menu.add(this.torus);
        menubar.add(menu);
        this.setJMenuBar(menubar);
        //Labels
        this.selected = new JLabel(" current Object: rectangle");
        this.LabelX = new JLabel("X");
        this.LabelY = new JLabel("Y");
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
		// TODO Auto-generated method stub
		if(e.getSource() == this.rectangle){
			this.selected.setText(" current Object: rectangle");
			this.SlideX.setValue(2);
			this.SlideY.setValue(2);
			this.SlideScale.setValue(100);
			this.currObject = new Rectangle(SlideX.getValue(),SlideY.getValue(),SlideScale.getValue());
			//System.out.println(currObject.getNormals().length);
			//System.out.println(currObject.getVertices().length);
			//System.out.println(currObject.getIndices().length);
		}
		else if(e.getSource() == this.sphere){
			this.selected.setText(" current Object: sphere");
			this.SlideX.setValue(3);
			this.SlideX.setMinimum(3);
			this.SlideY.setValue(3);
			this.SlideY.setMinimum(3);
			this.SlideScale.setValue(100);
			this.currObject = new Sphere(SlideX.getValue(),SlideY.getValue(),SlideScale.getValue());
		}
		else if(e.getSource() == this.torus){
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

			this.currObject = new Torus(this.SlideX.getValue(),this.SlideY.getValue(),this.SlideScale.getValue(), this.SlideR.getValue(), this.SlideP.getValue(), this.SlideQ.getValue());
		}
		currObject.create();
		this.setDirty(true);
	}

	public Primitive getCurrObject() {
		return currObject;
	}


	public void setCurrObject(Primitive currObject) {
		this.currObject = currObject;
		currObject.create();
		this.setDirty(true);
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
		System.out.println("[DEBUG] change event triggered");
		this.currObject.setX(SlideX.getValue());
		this.currObject.setY(SlideY.getValue());
		this.currObject.setScale(SlideScale.getValue());
		if (this.currObject.getType().equals("torus")){
			((Torus) this.currObject).setR(SlideR.getValue());
			((Torus) this.currObject).setP(SlideP.getValue());
			((Torus) this.currObject).setQ(SlideQ.getValue());
			SlideR.setMaximum(SlideScale.getValue());
		}
		this.currObject.create();
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

}
