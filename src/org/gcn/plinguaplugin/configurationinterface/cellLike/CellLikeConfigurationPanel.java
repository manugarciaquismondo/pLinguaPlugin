/* 
 * pLinguaPlugin: An Eclipse plug-in for Membrane Computing
 *              http://www.p-lingua.org
 *
 * Copyright (C) 2009  Manuel Garcia-Quismondo Fernandez
 *                      
 * This file is part of pLinguaPlugin.
 *
 * pLinguaPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * pLinguaPlugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with pLinguaCore.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.gcn.plinguaplugin.configurationinterface.cellLike;



import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.cellLike.CellLikeConfiguration;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeMembrane;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeNoSkinMembrane;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeSkinMembrane;
import org.gcn.plinguaplugin.configurationinterface.IConfigurationPanel;





/**
 * This class displays a Cell-Like configuration passed as constructor parameter
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class CellLikeConfigurationPanel implements IConfigurationPanel{
	private final RGB CELLCOLOR = new RGB(255, 255, 255);
	private final RGB DEFAULTCOLOR = new RGB(0, 0, 0);
	private final RGB SELECTEDCOLOR = new RGB(0, 0, 255);
	private static final String FONTNAME="Helvetica";
	private CellLikeConfiguration configuration;
	private Label topLabel;
	private Tree tree;
	private Shell shell;
	private Display display;
	private Text objectsTabItem;
	private TabFolder tabFolder;
	private Composite scrollerComposite;
	private CellLikeMembrane selected;
	private Map<Integer, Canvas> membraneMap;
	private Map<Integer, TreeItem> treeItemMap;
	private Composite scrolled;
	private Composite skinMembrane;
	private BarInitializer barInitializer;
	private Composite displayerComposite;
	private Stack<Shell> closerShells;
	private boolean isDisplayed;
	
	private Composite createComposite(Composite parent, int flag){
		Composite composite = new Composite(parent, flag);
		composite.setBackground(new Color(display, CELLCOLOR));
		return composite;
		
	}
	


	


	
	
	protected Configuration getConfiguration(){
		return configuration;
	}

	/**
	 * Creates a new CellLikeMembranePanel instance to display the configuration passed as parameter
	 * @param configuration the configuration to display
	 */
	public CellLikeConfigurationPanel(Configuration configuration) {
		super();
		isDisplayed=false;
		if (configuration == null)
			throw new NullPointerException(
					"configuration argument shouldn't be null");
		if(! (configuration instanceof CellLikeConfiguration))
			throw new IllegalArgumentException("The configuration argument should be a Cell-Like Configuration");
		this.configuration = (CellLikeConfiguration)configuration;
		membraneMap = new HashMap<Integer, Canvas>();
		treeItemMap = new HashMap<Integer, TreeItem>();
		/*The common display for all shells is created*/
		display = Display.getCurrent();
		/*The shell-to-close stack is created */
		closerShells = new Stack<Shell>();
		shell = new Shell(display);
		/*After the main shell is created, all displaying features should be set */
		shell.setText("Cell Like Configuration");
		shell.setBounds(100, 100, 1000, 400);
		shell.setLayout(new FormLayout());
		/*The shell is composed of different elements interrelated:
		 * a. The top label, which displays the common configuration info. It's located at the top of the shell
		 * b. The current configuration tree on the left, which displays the membrane structure in a tree-like shape
		 * c. The tab folder panel, which displays the objects contained within the selected membrane and the common configuration info (P-system rules and environment content)
		 * d. The configuration panel, which provides a graphical interface to display the configuration by deploying all membranes and objects included in the configuration 
		 */
		shell.setData("mainShell");
		/*The top label is created*/
		topLabel =topLabel();
		/*The current configuration tree is created*/
		tree = generateCurrentConfigTree(this.configuration);
		/*The tab folder panel is created*/
		tabFolder = generateTabFolderPanel();
		/*The configuration panel is created*/
		generateConfigPanel();
		
		// TODO Auto-generated constructor stub
	}
	/*A method for creating items contained in the tab folder*/
	private Text createTabItem(TabFolder result, String info, String name){
		// TODO Auto-generated method stub
		/*The tabItem and the composite to scroll it are created*/
		TabItem tabItem = new TabItem(result, 0);		
		scrollerComposite = new Composite(result, SWT.NULL);	
		/*Then, the scrolling composite will be the content of the tab item*/
		tabItem.setControl(scrollerComposite);
		tabItem.setText(name);
		/*The contained info should fill all area within the scrolling composite*/
		scrollerComposite.setLayout(new FillLayout());
		/*Finally, the content itself is embedded into the scrolled area*/
		Text environmentText = new Text(scrollerComposite, SWT.MULTI|SWT.V_SCROLL|SWT.H_SCROLL);
		environmentText.setBackground(new Color(display, CELLCOLOR));
		environmentText.setEditable(false);
		environmentText.setText(info);
		return environmentText;
	}

	private TabFolder generateTabFolderPanel(){
		/*First, the tab folder itself is created*/
		TabFolder result = new TabFolder(shell, SWT.BORDER);
		result.setLayout(new FillLayout());
		/*Then, the form data to attach the tab folder panel underneath the top label and on the right of the tree is created and properly initialized*/
		FormData formData = new FormData();
		formData.top = new FormAttachment(topLabel, 5);
		formData.left = new FormAttachment(tree, 5);
		formData.bottom = new FormAttachment(100, -5);
		result.setLayoutData(formData);
		/* Eventually, all tabs included in the tab folder panel are created*/
		createTabItem(result, printSet(configuration.getPsystem().getAlphabet()), "Alphabet");
		createTabItem(result, configuration.getEnvironment().toString(), "Environment");
		createTabItem(result, printSet(configuration.getPsystem().getRules()), "Rules");	
		createTabItem(result, configuration.getPsystem().getInitialMultiSets().toString(), "Initial Multisets");
		/* As the objects tab should be updated any time the membrane currently selected changes, it should be kept as a field*/
		objectsTabItem = createTabItem(result, "", "Objects");
		result.setLayoutData(formData);
		return result;
	}
	
	private String printSet(Set<?> alphabet) {
		// TODO Auto-generated method stub
		String result="";
		Iterator<?> iterator = alphabet.iterator();
		/*Each element within the set is appended with a separating text line*/
		while (iterator.hasNext()) {
			Object element = iterator.next();
			result+=element.toString()+"\n";
			
		}
		return result;
	}

	/*A method for creating the top label with the configuration info*/
	private Label topLabel(){
		Label result = new Label(shell,SWT.SINGLE);
		/*The skin membrane info can be considered part of the configuration common info*/
		CellLikeSkinMembrane skinMembrane = (CellLikeSkinMembrane)(configuration.getMembraneStructure());
		/*Hence, its info should be displayed as part of the common configuration info*/
		result.setText("Configuration: "+configuration.getNumber()+". Skin membrane ID: "+skinMembrane.getId()+", Label: "+skinMembrane.getLabel()+", Charge: "+skinMembrane.getCharge());
		/*The top label is located at the top of the shell*/
		FormData formData = new FormData();
		formData.top= new FormAttachment(0, 0);
		formData.left = new FormAttachment(0, 0);
		result.setLayoutData(formData);
		return result;
	}
	
	/*A method for creating the current configuration tree, which displays all membranes within the configuration and the environment*/
	private Tree generateCurrentConfigTree(CellLikeConfiguration configuration){
		/*The tree should be horizontally scrolled*/
		final Tree result = new Tree(shell, SWT.H_SCROLL|SWT.BORDER);
		/*Besides, it should be located on the left of the shell and underneath the top label*/
		FormData formData = new FormData();
		formData.top = new FormAttachment(topLabel, 5);
		formData.bottom = new FormAttachment(100, -5);
		result.setLayoutData(formData);
		/*The tree content should be the configuration, properly converted to a tree node structure*/
		convertToTreeItem(configuration, result);
		/*Besides, each time any item in the tree is selected, the configuration panel and the objects displayed should be updated*/
		result.addSelectionListener(new TreeSelectionListener(this));
		return result;
	}
	
	private void updateMultiSet(MultiSet<String> multiSet) {
		objectsTabItem.setText(multiSet.toString());	
	}
	
	private void convertToTreeItem(CellLikeConfiguration configuration, Tree tree){
		/*The first element to convert to a tree item is the environment*/
		TreeItem treeItem = new TreeItem(tree, 0);
		treeItem.setText("Environment");
		/*As the environment isn't a membrane, it has no ID. 
		 * However, we need a tree item map to access quickly all elements within the tree by addressing their ID's, so the environment ID is set to -1*/
		treeItemMap.put(-1, treeItem);
		/*Then, the environment info is added*/
		treeItem.setData(configuration.getEnvironment());
		/*Eventually, all membranes within the configuration are added, starting off with the skin and going on to its children*/
		addChildrenRecursively("Skin ", treeItem, (CellLikeSkinMembrane)(configuration.getMembraneStructure()));		
	}
	
	/*A method to convert membranes into tree items capable of being added to a SWT tree instance*/
	private void addChildrenRecursively(String prefix, TreeItem parent, CellLikeMembrane cellLikeMembrane){
		/*First, the tree item is created*/
		final TreeItem childItem = new TreeItem(parent, 0);
		/*Then, its text is set to the membrane info (ID, label and charge). The skin membrane is also denoted by the "Skin" prefix*/
		childItem.setText(prefix+"Membrane. ID: "+cellLikeMembrane.getId()+", Label: "+cellLikeMembrane.getLabel()+", Charge: "+cellLikeMembrane.getCharge());
		/*Then, its info is set to the membrane passed*/
		childItem.setData(cellLikeMembrane);
		/*The next thing to do is to add the membrane to the tree structure for this purpose*/
		treeItemMap.put(cellLikeMembrane.getId(), childItem);
		/*Eventually, the same process is carried out with all children membranes*/
		Iterator<CellLikeNoSkinMembrane> iterator = cellLikeMembrane.getChildMembranes().iterator();
		while (iterator.hasNext()) {
			CellLikeNoSkinMembrane childMembrane = iterator.next();
			addChildrenRecursively(childItem, childMembrane);
		}
	}
	
	private void addChildrenRecursively(TreeItem treeItem, CellLikeMembrane cellLikeMembrane) {		
		addChildrenRecursively("", treeItem, cellLikeMembrane);		
	}
	
	private Composite generateConfigPanel(){
		/*First of all, the composite to scroll the configuration info is created*/
		displayerComposite = new  Composite(shell, SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL);
		/*Then, the composite itself to hold the configuration info is created*/
		scrolled = new Composite(displayerComposite, 0);
		/*The info composite has a vertical structure, with the skin membrane at the top and the environment objects at the bottom*/
		RowLayout scrolledLayout = new RowLayout(SWT.VERTICAL);
		scrolled.setLayout(scrolledLayout);
		scrolled.setBackground(new Color(display, CELLCOLOR));
		/*Then it's time to create the attachments for the scrolling composite (displayerComposite)and the content composite (scrolled)*/
		/*The scrolling composite should be on the right of the top label (actually it means at the right of the shell)*/
		FormData displayerFormData = new FormData();
		displayerFormData.top = new FormAttachment(0, 5);
		displayerFormData.bottom = new FormAttachment(100, -5);
		displayerFormData.left = new FormAttachment(topLabel, 5);
		displayerFormData.right= new FormAttachment(100, -5);
		FormData tabFolderFormData = (FormData)tabFolder.getLayoutData();
		tabFolderFormData.right = new FormAttachment(displayerComposite, -5);
		tabFolder.setLayoutData(tabFolderFormData);
		displayerComposite.setLayoutData(displayerFormData);
		/*The scrolling composite has a cell color background*/
		displayerComposite.setBackground((new Color(display,CELLCOLOR)));		
		displayerComposite.setLayout(new FormLayout());
		FormData scrolledData = new FormData();
		/*As regards to the content attachment, it's located within all scrolling composite area (excepting the horizontal and vertical scroll bars)*/
		scrolledData.top = new FormAttachment(0, 0);		
		scrolledData.left = new FormAttachment(0, 0);
		scrolled.setLayoutData(scrolledData);
		/*The content composite also performs content-update actions when clicked, by dispatching the environment info to all widgets concerned*/
		scrolled.addListener(SWT.Selection, new EnvironmentListener(this));
		skinMembrane  = displayMembranesRecursively(scrolled, (CellLikeSkinMembrane)(configuration.getMembraneStructure()));
		if(!configuration.getEnvironment().isEmpty()){
			/*If the environment isn't empty, each time it's clicked its content should be displayed on the widgets concerned about the current selection status*/
			Composite multiSetComposite = displayMultiSet(scrolled, configuration.getEnvironment());
			multiSetComposite.setData("membrane", configuration.getEnvironment());
			multiSetComposite.addMouseListener(new MouseSubmitter(this, multiSetComposite));
		}
		/*In the end, the bar initializer for the content composite is created*/
		barInitializer = new BarInitializer(scrolled);
		return displayerComposite;
		
	}
	

	
	private Composite displayMembranesRecursively(Composite composite, CellLikeMembrane membrane){
		/*First of all, the membrane composite (that is, the composite to display the membrane) is created*/
		Composite membraneComposite = createComposite(composite, SWT.MULTI);
		
		FormLayout layout = new FormLayout();
		membraneComposite.setLayout(layout);
		/*Then, its background color and bounds are set*/
		membraneComposite.setBounds(0, 0, 100, 100);
		membraneComposite.setBackground(new Color(display, CELLCOLOR));
		/*The membrane composite contains a canvas to display the membrane content*/
		Canvas membraneCanvas = new Canvas(membraneComposite, 0);
		/*The outer square-shaped membrane form is painted*/
		RectanglePainter painter = new RectanglePainter(membraneCanvas);		
		membraneCanvas.addPaintListener(painter);
		/*The membrane map holds all canvas indexed by the ID of the membrane displayed*/
		membraneMap.put(membrane.getId(), membraneCanvas);
		/*Then, the label and charge are displayed*/
		Label label = displayMembraneStringInfo(membraneComposite, membraneCanvas, membrane.getLabel(), false);
		displayMembraneStringInfo(membraneComposite, membraneCanvas, getChargeSymbol(membrane.getCharge()), true);
		FormData formCanvas = new FormData();
		formCanvas.top = new FormAttachment(0, 10);
		formCanvas.left = new FormAttachment(0, 0);
		formCanvas.right = new FormAttachment(label, 0);
		formCanvas.bottom = new FormAttachment(100, -10);
		/*The membrane canvas has a narrow vertical margin regarding to the membrane composite*/
		membraneCanvas.setLayoutData(formCanvas);
		membraneComposite.setData("membrane", membrane);
		/*The composite should be sensitive to clicking and react to update the widgets concerned by the currently selected element, in case the membrane composite is clicked*/
		MembraneMouseSubmitter submitter = new MembraneMouseSubmitter(membraneComposite, display, this, displayerComposite, closerShells);
		membraneComposite.addMouseListener(submitter);
		/*It should also report about the capability of showing the membrane in a detached view, if possible*/
		membraneCanvas.addMouseTrackListener(new MembraneMenuListener(shell, membraneComposite));
		membraneCanvas.addMouseListener(submitter);
		/*The membrane canvas should have a cell color background*/
		membraneCanvas.setBackground(new Color(display, CELLCOLOR));	
		/*The membrane canvas should have a layout for sorting its content*/
		RowLayout canvasLayout = new RowLayout(SWT.HORIZONTAL);
		canvasLayout.marginHeight=30;
		canvasLayout.marginWidth=30;
		membraneCanvas.setLayout(canvasLayout);
		/*The number of rows within the canvas depends on whether the inner multiset is empty (only a row) or not (two rows)*/
		int rowsMultiplier=1;
		if(!membrane.getMultiSet().isEmpty())
			rowsMultiplier++;
		Composite childMembranesComposite = null;
		/*If the membrane displayed contains child membranes, they should be displayed*/
		if(!membrane.getChildMembranes().isEmpty()){

			/*The membranes contained are displayed within a square-shaped composite.
			 * Therefore, the number of rows of membranes displayed should be equal to the number of membranes displayed per row.
			 * This number is equal to the square radix of the number of membranes*/
			int numChildren = membrane.getChildMembranes().size();
			int membranesPerRow = (int)Math.ceil(Math.sqrt(numChildren));
			/*Each row within rows is a composite to display membranes in a row, which as many membranes as number of rows are*/
			Composite rows[] = new Composite[membranesPerRow*rowsMultiplier];
			/*Once we now the number of rows (and membranes per row), we can create a composite to hold all contained membranes*/
			childMembranesComposite = createChildMembranesComposite(rows.length, membraneCanvas, rows);
						
			int membranesCounter=0;
			/*Now we go on to display all child membranes*/
			Iterator<CellLikeNoSkinMembrane> iterator = membrane.getChildMembranes().iterator();
			while (iterator.hasNext()) {
				/*The membrane counter helps us to get the correct row composite to display the child membrane on. The right row should be the current membrane number divided by the number of membranes per row*/
				Composite rowComposite = rows[membranesCounter/membranesPerRow];
				CellLikeNoSkinMembrane elem = iterator.next();
				/*If there are still membranes to display, we go on to display them on its correct row*/
				if(membranesCounter<numChildren)
					displayMembranesRecursively(rowComposite, elem);
				/*else{
					Composite blankComposite = createComposite(rowComposite, 0);
				}*/
				membranesCounter++;
			}
		}
		/*If the membrane multiset isn't empty, it should be displayed*/
		Composite multiSetComposite=null;
		if(!membrane.getMultiSet().isEmpty()){
			multiSetComposite = displayMultiSet(membraneCanvas, membrane.getMultiSet());
			
		}
		/*In case the membrane displayed has child membranes, a form data object to represent its attachment to the membrane display should be created*/
		if(childMembranesComposite!=null){
			FormData childMembranesForm = new FormData();
			/*A 5 pixel margin is left on each canvas side*/
			childMembranesForm.left = new FormAttachment(0, 5);
			childMembranesForm.right = new FormAttachment(100, -5);
			childMembranesForm.top = new FormAttachment(0, 5);
			if(multiSetComposite!=null){
				/*If the membrane multiset isn't empty, the child membranes composite should be upon it*/
				childMembranesForm.bottom = new FormAttachment(multiSetComposite, 0);
			}
			else
				/*Otherwise, the child membranes composite fills all available area (excepting the membrane bound)*/
				childMembranesForm.bottom = new FormAttachment(100, -5);
			
		}
		/*In case the membrane multiset isn't empty, its attachment to the membrane canvas should be created*/
		if(multiSetComposite!=null){			
			FormData multiSetForm = new FormData();
			/*A 5 pixel margin is left on each canvas side*/
			multiSetForm.bottom = new FormAttachment(100, -5);
			multiSetForm.left = new FormAttachment(0, 5);
			multiSetForm.right = new FormAttachment(100, -5);
			/*If the child membranes set isn't empty, the membrane multiset should be underneath it*/
			if(childMembranesComposite!=null)
				multiSetForm.top = new FormAttachment(childMembranesComposite, 5);
			else
			/*Otherwise, the membrane multiset composite fills all canvas area (excepting the membrane bound)*/
				multiSetForm.top = new FormAttachment(0, 5);
		}
		return membraneComposite;
	}

	private String getChargeSymbol(byte charge) {
		// TODO Auto-generated method stub
		/*If the charge is neutral, its symbol is 0*/
		String result="0";
		/*If positive, its symbol is +*/
		if(charge>0)
			result="+";
		/*If negative, its symbol is -*/
		if(charge<0)
			result="- ";
		return result;
	}

	/*A method for creating child membranes composite in a squared form.
	 *  The idea is to create a composite composed of rows, so there's as many rows as membranes per row
	 *  Hence, the number of vertical membranes and horizontal membranes is equal, just like a square*/
	private Composite createChildMembranesComposite(int numRows,
			Composite membraneComposite, Composite rows[]){
		// TODO Auto-generated method stub
		/*First, the children membrane composite is created*/
		Composite composite = createComposite(membraneComposite, 0);
		/*The children membrane composite has a vertical row layout, so each element corresponds to a membrane row*/
		RowLayout childrenLayout = new RowLayout(SWT.VERTICAL);
		/*Then, the children membrane composite is added to the membrane composite info (further used for updating)*/
		membraneComposite.getParent().setData("row", composite);
		composite.setLayout(childrenLayout);
		for(int i=0; i<numRows; i++){
			/*Each row within the children membrane composite has an horizontal structure, displaying membranes as elements of an horizontal row*/
			Composite aux = createComposite(composite, 0);
			RowLayout childLayout = new RowLayout(SWT.HORIZONTAL);
			aux.setLayout(childLayout);
			/*The row is added to the array of rows*/
			rows[i] = aux;
		}
		return composite;
	}

	

	/*A method for displaying membrane charge and membrane label*/
	private Label displayMembraneStringInfo(Composite membraneComposite, Canvas membraneCanvas, String text, boolean top){
		/*First, the label to return is created*/
		Label label = new Label(membraneComposite, SWT.SINGLE);
		/*Then, its text is set*/
		label.setText(text);
		label.setBackground(new Color(display, CELLCOLOR));
		label.setFont(new Font(display, FONTNAME, 10, 0));
		/*Eventually, its attachment to the composite which represents the membrane is created*/
		FormData formLabel = new FormData();
		if(top){
			formLabel.top = new FormAttachment(0, 2);
		}
		else{
			formLabel.bottom = new FormAttachment(100, -2);
			
		}
		formLabel.right = new FormAttachment(100, 0);		
		label.setLayoutData(formLabel);
		return label;

	}
	
	/* A method for updating all info displayed in CellLikeMembranePanel when the item selected has changed*/
	protected void update(Object data) {
		/* The tab item objects displayed should be updated*/
		updateObjectsTabItem(data);
		/* The selected node within the tree should be updated*/
		updateTreeItem(data);
		/* The selected membrane on the configuration display is updated, as well*/
		updateMembranesDisplay(data);
		/* Eventually, the selected element is updated with the membrane recently selected.
		 * In case what is selected is the environment, the selected value is null.*/
		if(data instanceof CellLikeMembrane)
			selected = (CellLikeMembrane)data;
		else
			selected=null;
	}
	
	/*This method updates the tree item currently selected by highlighting it on the configuration tree*/
	private void updateTreeItem(Object data) {
		/*If the selected element is a membrane, it can be referenced by using its ID*/
		if(data instanceof CellLikeMembrane)
			tree.select(treeItemMap.get(((CellLikeMembrane)data).getId()));
		/*Otherwise, what's selected is the environment, referenced by -1*/
		else			
			tree.select(treeItemMap.get(-1));
		
	}
	
	/*This method updates the multiset tab item by switching the multiset displayed to the one currently selected*/
	private void updateObjectsTabItem(Object data){
		/*If the selected element is a membrane, we need to display its multiset*/
		if(data instanceof CellLikeMembrane)
			objectsTabItem.setText(((CellLikeMembrane)data).getMultiSet().toString());
		else
			/*Otherwise, what's selected is the environment, and it's displayed*/
			updateMultiSet((MultiSet<String>)data);		
	}


	/*This method updates the membranes display by switching the membrane highlighted to the one currently selected*/
	private void updateMembranesDisplay(Object data) {
		/*If there was a previously highlighted membrane, its color should be set to the default color, reporting it's not selected*/
		if(selected!=null)
			membraneMap.get(selected.getId()).setForeground(new Color(display, DEFAULTCOLOR));
		/*In case the selected object is a membrane, its display representation should be highlighted*/
		if(data instanceof CellLikeMembrane){
			membraneMap.get(((CellLikeMembrane)data).getId()).setForeground(new Color(display, SELECTEDCOLOR));
		}
		
	}

	/*A method for displaying a multiset by showing all its objects*/
	private Composite displayMultiSet(Composite composite, MultiSet<String> multiset){
		List<Label> objects = new LinkedList<Label>();
		Font objectFont = new Font(display, FONTNAME, 20, 0);
		/*The multiset composite is created*/
		Composite multiSetComposite =  new Composite(composite, 0);
		/*It's content is aligned in a square-shaped way, so there's as many objects displayed per row as many rows within the multiset display are*/
		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		/*The layout has a small margin on every side*/
		layout.marginTop = -5;
		layout.marginBottom = 5;
		layout.marginLeft = 5;
		layout.marginRight = 5;
		layout.spacing = 2;
		multiSetComposite.setBounds(new Rectangle(0, 0, multiset.size()*20, 20+10));
		multiSetComposite.setLayout(layout);
		multiSetComposite.setBackground(new Color(display, CELLCOLOR));
		/*The number of rows (and the number of membranes per row) is the square radix of the number of elements in the multiset*/
		int numRows = (int)Math.ceil(Math.sqrt(multiset.size()));
		Composite multiSetRows[] = new Composite [numRows];
		/*The same method to create the row structure for children membranes is used to create a row structure for the multiset*/
		createChildMembranesComposite(numRows, multiSetComposite, multiSetRows);
		/*We need an object counter to identify which row should we display the current object on*/
		int objectCounter=0;
		Iterator<String> iterator = multiset.entrySet().iterator();
		/*For each element within the multiset*/
		while (iterator.hasNext()) {
			String object = iterator.next();
			/*The object is printed on its correspondent row, indexed by objectCounter/number of rows*/
			printObject(object,multiSetRows[objectCounter/numRows], multiset.count(object), multiset.size(), objectFont, objects);
			objectCounter++;
		}
		/*Finally, the multiset info is included in the membrane composite*/
		composite.getParent().setData("multiset", multiSetComposite);
		return multiSetComposite;
	}
	

	/*A method for printing objects on the configuration display*/
	private void printObject(String object, Composite composite, long multiplicity, int total, Font objectFont, List<Label> objects){
		/*A composite to display the object is created*/
		Composite objectComposite = createComposite(composite, SWT.SIMPLE);
		Label label = new Label(objectComposite, SWT.SIMPLE);
		/*The object composite has a form layout to display its name, its multiplicity and its indexes*/
		objectComposite.setLayout(new FormLayout());
		objectComposite.setBackground(new Color(display, CELLCOLOR));
		/*Its attachment to the multiset composite is created*/
		RowData layoutData  = new RowData();
		objectComposite.setLayoutData(layoutData);			
		/*By default, the object name is the object itself*/
		String objectName= object;
		/*In case there are indexes in the objects or the multiplicity is greater than 1, the composite to display the object should display that additional information*/
		if(object.contains("{")|| multiplicity>1){
			/*A form layout is created to display the object info*/
			FormLayout layout = new FormLayout();
			layout.marginBottom =-2;
			layout.marginTop =2;
			layout.marginLeft =2;
			layout.marginRight =-2;
			/*If the multiplicity is greater than 1 it should be displayed as an object super-index*/
			if(multiplicity>1){
				displayMultiplicity(multiplicity,objectComposite, label);
			}			
			/*If the object has any indexes, they should be displayed as object sub-indexes*/
			if(object.contains("{")){
				/*The object name isn't the whole object string, but the part up to the first "{"*/
				objectName = object.substring(0, object.indexOf("{"));
				displayIndexes(printIndexes(object),objectComposite, label);
			}
		}
		/*Then, the object name is set*/
		label.setText(objectName);
		label.setBackground(new Color(display, CELLCOLOR));
		label.setFont(objectFont);
		/*Later, the attachment of the object name within the object composite is created*/
		FormData labelFormData = new FormData();
		labelFormData.bottom= new FormAttachment(100, 0);
		labelFormData.left= new FormAttachment(0, 5);
		label.setLayoutData(labelFormData);
		/*Eventually, the object label created is added to the object labels list*/
		objects.add(label);
			
	}

	/*A method for displaying indexes on the configuration display as part of the object info*/
	private void displayIndexes(String indexes,
			Composite objectInfo, Label toAttach) {				
		/*First, the object sub-indexes label is created*/
		Label subIndexes = new Label(objectInfo, SWT.SIMPLE);
		/*Then, the object sub-indexes label is set to the sub-indexes text*/
		subIndexes.setText(indexes);
		subIndexes.setBackground(new Color(display, CELLCOLOR));
		subIndexes.setFont(new Font(display, FONTNAME, 10, 0));
		/*Eventually, the index attachment to the object composite is created*/
		FormData indexesForm = new FormData();
		/*The element on the left should be the object name label*/
		indexesForm.left = new FormAttachment(toAttach, 0);
		indexesForm.bottom = new FormAttachment(100, 0);
		indexesForm.right = new FormAttachment(100, 0);
		subIndexes.setLayoutData(indexesForm);
	}

	/*A method for displaying the object multiplicity on the configuration display as part of the object info*/
	private void displayMultiplicity(long multiplicity,
			Composite objectInfo, Label toAttach) {
		/*First, the object super-index label is created*/
		Label superIndex = new Label(objectInfo, SWT.SIMPLE);
		superIndex.setBackground(new Color(display, CELLCOLOR));
		superIndex.setFont(new Font(display, FONTNAME, 10, 0));
		/*Then, the object super-index label is set to the multiplicity*/
		superIndex.setText(""+multiplicity);
		/*Eventually, the multiplicity attachment  to the object composite is created*/
		FormData multiplicityForm = new FormData();
		/*The element on the left should be the object name label*/
		multiplicityForm.left = new FormAttachment(toAttach, 0);
		multiplicityForm.top = new FormAttachment(0, 0);
		multiplicityForm.right= new FormAttachment(100, 0);
		superIndex.setLayoutData(multiplicityForm);

	}


	/*A method for extracting the sub-indexes string out of an object*/
	private String printIndexes(String object){
		return object.substring(object.indexOf("{")+1, object.lastIndexOf("}"));
	}
	

	/**
	 * @see org.gcn.plinguaplugin.configurationinterface.cellLike.IConfigurationPanel#isDisplayed()
	 */
	@Override
	public boolean isDisplayed(){
		return isDisplayed;
	}
	

	/**
	 * @see org.gcn.plinguaplugin.configurationinterface.cellLike.IConfigurationPanel#getPosition()
	 */
	public Point getPosition(){
		Rectangle shellBounds = shell.getBounds();
		return new Point(shellBounds.x, shellBounds.y);
	}
	

	/**
	 * @see org.gcn.plinguaplugin.configurationinterface.cellLike.IConfigurationPanel#setPosition(int, int)
	 */
	public void setPosition(int x, int y){
		/*Get the current bounds*/
		Rectangle shellBounds = shell.getBounds();
		/*Update the position*/
		shellBounds.x = x;
		shellBounds.y = y;
		/*Set the position*/
		shell.setBounds(shellBounds);
	}

	/**
	 * @see org.gcn.plinguaplugin.configurationinterface.cellLike.IConfigurationPanel#dispose()
	 */
	public void dispose(){
		shell.dispose();
		isDisplayed=false;
		
	}

	/**
	 * @see org.gcn.plinguaplugin.configurationinterface.cellLike.IConfigurationPanel#display()
	 */
	public void display(){
		/*First of all, the shell is opened*/
		shell.open();
		isDisplayed=true;
		/*Then, it's time to set up the scrolling bars of the configuration skin membrane representation*/
		barInitializer.setupBars(skinMembrane);
		/*Aftermath, all shells opened as a result of having detached views of displayed membranes should be closed before closing the root shell*/
		shell.addDisposeListener(new ClosePanelListener(closerShells));
		/*Then, the display sleeps until the shell is closed*/
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()) display.sleep();
		}
		isDisplayed=false;
		
	}
	
	
}
