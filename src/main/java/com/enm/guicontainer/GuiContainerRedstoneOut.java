package com.enm.guicontainer;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.enm.api.network.MachineNetWork;
import com.enm.api.network.Machine_Counter;
import com.enm.api.network.Machine_FluidCounter;
import com.enm.api.network.Machine_FluidFlowMeter;
import com.enm.api.network.Machine_FluidName;
import com.enm.api.network.Machine_FluidStorageInfo;
import com.enm.api.network.Machine_FluidValve;
import com.enm.api.network.Machine_FluxMeter;
import com.enm.api.network.Machine_Redstone;
import com.enm.api.network.Machine_StorageInfo;
import com.enm.api.network.Machine_Switch;
import com.enm.api.network.NetWorkUtil;
import com.enm.api.util.Vector3;
import com.enm.container.ENMContainer;
import com.enm.guipanel.myinstallation.StringTag;
import com.enm.guipanel.myinstallation.Tag.Type;
import com.enm.guiutil.GuiButtonTerminal;
import com.enm.guiutil.GuiButtonTerminal.Icon;
import com.enm.guiutil.ResourceLocationRegister;
import com.enm.tileEntity.TileEntityRedstoneOut;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class GuiContainerRedstoneOut extends GuiContainer
{
	TileEntityRedstoneOut tileentity;
	
	List<TileEntity> machines;
	TileEntity machine;
	String machine_type = "";
	
	GuiButtonTerminal button_slectUp, button_slectDown, button_slectNext, button_slect2Up, button_slect2Down, button_slect2Next, button_EndOk;
	GuiButton button_boolean_tf, button_test;
	GuiTextField textField_argument;
	private int width_save, height_save;
	
	int id_machine = 0;
	int id_function = 0;
	
	//step 4 
	long var01;
	int var02;
	String var03;
	boolean var04 = true;
	byte varArgument = 0;
	Class<?> var_type;
	
	public GuiContainerRedstoneOut(InventoryPlayer pl, TileEntityRedstoneOut te)
	{
		super(new ENMContainer());
		machines = NetWorkUtil.GetAllMachines(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
		tileentity = te;
		//args \/
	}
	
	@Override
	public void initGui()
	{
		buttonList.clear();
		
		button_slectUp = new GuiButtonTerminal(10, 2, 2, Icon.Up);
		button_slectDown = new GuiButtonTerminal(11, 2, 22, Icon.Down);
		button_slectNext = new GuiButtonTerminal(12, 194, 2, Icon.Next);
		
		button_slect2Up = new GuiButtonTerminal(13, 2, 45, Icon.Up);
		button_slect2Down = new GuiButtonTerminal(14, 2, 65, Icon.Down);
		button_slect2Next = new GuiButtonTerminal(15, 194, 45, Icon.Next);
		
		button_EndOk = new GuiButtonTerminal(9, width/2 - 20, 129, Icon.Ok);
		
		button_boolean_tf = new GuiButton(16, 2, 98, 50, 20, "true");
		button_test = new GuiButton(17, 2, 98,20, 20, "=");
		textField_argument = new GuiTextField(fontRendererObj, 2, 98, 169, 20);
		
		button_slect2Up.visible = false;
		button_slect2Down.visible = false;
		button_slect2Next.visible = false;
		
		button_boolean_tf.visible = false;
		button_EndOk.visible = false;
		button_test.visible = false;
		textField_argument.setVisible(false);
		
		buttonList.add(button_slectUp);
		buttonList.add(button_slectDown);
		buttonList.add(button_slectNext);
		buttonList.add(button_slect2Up);
		buttonList.add(button_slect2Down);
		buttonList.add(button_slect2Next);
		buttonList.add(button_EndOk);
		buttonList.add(button_boolean_tf);
		buttonList.add(button_test);
		
		int iid = 0;
		for(TileEntity te: machines)
		{
			if(te.xCoord == tileentity.posmachine.x && te.yCoord == tileentity.posmachine.y && te.zCoord == tileentity.posmachine.z)
			{
				machine = te;
				machine_type = NetWorkUtil.GetType(machine).getSimpleName();
				if(machine != null)
				{
					button_slect2Up.visible = true;
					button_slect2Down.visible = true;
					button_slect2Next.visible = true;
					id_machine = iid;
					
					if(tileentity.VarType == 0)var_type = boolean.class;
					else if(tileentity.VarType == 1)var_type = String.class;
					else if(tileentity.VarType == 2)var_type = int.class;
					else if(tileentity.VarType == 3)var_type = long.class;
					
					if(var_type != null)
					{
						button_EndOk.visible = true;
						id_function = tileentity.methode;
						
						var01 = tileentity.Var3;
						var02 = tileentity.Var2;
						var03 = tileentity.var1;
						var04 = tileentity.var0;
						
						varArgument = tileentity.ComparType;
						if(var_type == boolean.class)button_boolean_tf.visible = true;
						textField_argument.setVisible(!button_boolean_tf.visible);
						
						button_test.visible = true;
						
						if(var_type == String.class)
						{
							if(varArgument == 0)button_test.displayString = "=";
							else if(varArgument == 1)button_test.displayString = "!=";
							else if(varArgument == 2)button_test.displayString = "~";
							textField_argument.setText(var03);
						}
						else if(var_type == int.class || var_type == long.class)
						{
							if(varArgument == 0)button_test.displayString = "=";
							else if(varArgument == 1)button_test.displayString = "<";
							else if(varArgument == 2)button_test.displayString = ">";
						}
						
						if(var_type == boolean.class)button_boolean_tf.displayString = ""+var04;
						if(var_type == int.class)textField_argument.setText(""+var02);
						if(var_type == long.class)textField_argument.setText(""+var01);
						
					}
				}
				break;
			}
			++iid;
		}
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		//System.out.println(machines.size());
		mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
		setColorRGBA(32, 32, 32, 255);
		drawTexturedModalRect(0, 0, 0, 0, width, height);
		
		setColorRGBA(255, 128, 0, 255);
		drawTexturedModalRect(width/2 - 159, 1, 0, 0, 276, 42);
		
		if(machine != null)
		{
			setColorRGBA(0, 128, 255, 255);
			drawTexturedModalRect(width/2 - 159, 44, 0, 0, 276, 42);
			//System.out.println(machine_type);
			if(machine_type.contains("Machine_Counter"))
			{
				//Consomation long
				drawString(fontRendererObj, "Function: Consomation", width/2 - 73, 50, 0xffffff);
				drawString(fontRendererObj, "Comparison Type: long", width/2 - 73, 59, 0xffffff);
				drawString(fontRendererObj, "long: number -9x10^18 to 9x10^18", width/2 - 73, 68, 0xffffff);
				
			}
			else if(machine_type.contains("Machine_FluidCounter"))
			{
				//Consomation long
				drawString(fontRendererObj, "Function: Consomation", width/2 - 73, 50, 0xffffff);
				drawString(fontRendererObj, "Comparison Type: long", width/2 - 73, 59, 0xffffff);
				drawString(fontRendererObj, "long: number -9x10^18 to 9x10^18", width/2 - 73, 68, 0xffffff);
			}
			else if(machine_type.contains("Machine_FluidFlowMeter"))
			{
				//Flow int
				drawString(fontRendererObj, "Function: Flow", width/2 - 73, 50, 0xffffff);
				drawString(fontRendererObj, "Comparison Type: int", width/2 - 73, 59, 0xffffff);
				drawString(fontRendererObj, "int: number -2147483648 to 2147483647", width/2 - 73, 68, 0xffffff);
			}
			else if(machine_type.contains("Machine_FluidName"))
			{
				//FluidName String
				drawString(fontRendererObj, "Function: FluidName", width/2 - 73, 50, 0xffffff);
				drawString(fontRendererObj, "Comparison Type: String", width/2 - 73, 59, 0xffffff);
				drawString(fontRendererObj, "String: text example(abcdefghijklmno)", width/2 - 73, 68, 0xffffff);
			}
			else if(machine_type.contains("Machine_FluidStorageInfo"))
			{
				//FluidAmount ([]) int
				//FluidCapacity ([]) int
				//FluidName ([]) String
				if(id_function == 0)
				{
					drawString(fontRendererObj, "Function: FluidAmount", width/2 - 73, 50, 0xffffff);
					drawString(fontRendererObj, "Comparison Type: int", width/2 - 73, 59, 0xffffff);
					drawString(fontRendererObj, "int: number -2147483648 to 2147483647", width/2 - 73, 68, 0xffffff);
				}
				else if(id_function == 1)
				{
					drawString(fontRendererObj, "Function: FluidCapacity", width/2 - 73, 50, 0xffffff);
					drawString(fontRendererObj, "Comparison Type: int", width/2 - 73, 59, 0xffffff);
					drawString(fontRendererObj, "int: number -2147483648 to 2147483647", width/2 - 73, 68, 0xffffff);
				}
				else
				{
					drawString(fontRendererObj, "Function: FluidName", width/2 - 73, 50, 0xffffff);
					drawString(fontRendererObj, "Comparison Type: String", width/2 - 73, 59, 0xffffff);
					drawString(fontRendererObj, "String: text example(abcdefghijklmno)", width/2 - 73, 68, 0xffffff);
				}
			}
			else if(machine_type.contains("Machine_FluidValve"))
			{
				//ValvePosition boolean
				drawString(fontRendererObj, "Function: ValvePosition", width/2 - 73, 50, 0xffffff);
				drawString(fontRendererObj, "Comparison Type: boolean", width/2 - 73, 59, 0xffffff);
				drawString(fontRendererObj, "boolean: true or false", width/2 - 73, 68, 0xffffff);
			}
			else if(machine_type.contains("Machine_FluxMeter"))
			{
				//Flux int
				drawString(fontRendererObj, "Function: Flux", width/2 - 73, 50, 0xffffff);
				drawString(fontRendererObj, "Comparison Type: int", width/2 - 73, 59, 0xffffff);
				drawString(fontRendererObj, "int: number -2147483648 to 2147483647", width/2 - 73, 68, 0xffffff);
			}
			else if(machine_type.contains("Machine_StorageInfo"))
			{
				//EnergyStored int
				//MaxEnergyStored int
				if(id_function == 0)
				{
					drawString(fontRendererObj, "Function: EnergyStored", width/2 - 73, 50, 0xffffff);
					drawString(fontRendererObj, "Comparison Type: int", width/2 - 73, 59, 0xffffff);
					drawString(fontRendererObj, "int: number -2147483648 to 2147483647", width/2 - 73, 68, 0xffffff);
				}
				else
				{
					drawString(fontRendererObj, "Function: MaxEnergyStored", width/2 - 73, 50, 0xffffff);
					drawString(fontRendererObj, "Comparison Type: int", width/2 - 73, 59, 0xffffff);
					drawString(fontRendererObj, "int: number -2147483648 to 2147483647", width/2 - 73, 68, 0xffffff);
				}
			}
			else if(machine_type.contains("Machine_Switch"))
			{
				//SwitchPosition boolean
				drawString(fontRendererObj, "Function: ValvePosition", width/2 - 73, 50, 0xffffff);
				drawString(fontRendererObj, "Comparison Type: boolean", width/2 - 73, 59, 0xffffff);
				drawString(fontRendererObj, "boolean: true or false", width/2 - 73, 68, 0xffffff);
			}
			if(var_type != null)
			{
				mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
				setColorRGBA(64, 255, 0, 255);
				drawTexturedModalRect(width/2 - 159, 87, 0, 0, 276, 63);
				
				drawString(fontRendererObj, "Redstone sinal:", width/2 - 73, 88, 0xffffff);
				drawString(fontRendererObj, "function", width/2 - 120, 103, 0xffffff);
				
				//System.out.println(textField_argument.getVisible());
				if(textField_argument.getVisible())
					textField_argument.drawTextBox();
			}
		}
		
		setColorRGBA(255, 255, 255, 255);
		
		drawBigNumber(width/2 - 147, 8, 1);
		if(machine != null) drawBigNumber(width/2 - 147, 50, 2);
		if(var_type != null) drawBigNumber(width/2 - 147, 92, 3);
		
		if(id_machine < machines.size()) drawElement(machines.get(id_machine), width/2 - 75, 2);
	}
	
	@Override
	protected void keyTyped(char c, int id)
	{
		if(tileentity != null && machines != null)
		{
			/*if(id == 200)
			{
				if(id_machine > 0) --id_machine;
				else id_machine = machines.size()-1;
			}
			else if(id == 208)
			{
				if(id_machine < machines.size()-1) ++id_machine;
				else id_machine = 0;
			}
			else if(id == 28)
			{
				//System.out.println(NetWorkUtil.GetType(machines.get(id_machine)));
			}*/
			
			if(textField_argument.getVisible())
			{
				if(var_type == String.class)
				{
					textField_argument.textboxKeyTyped(c, id);
				}
				else if(var_type == int.class || var_type == long.class)
				{
					if("0123456789".indexOf(c) != -1 || id == 14)
					{
						textField_argument.textboxKeyTyped(c, id);
					}
				}
			}
		}
		
		super.keyTyped(c, id);
	}
	
	private void acction(int id)
	{
		if(tileentity != null && machines != null)
		{
			if(id == 1)
			{
				if(id_machine > 0) --id_machine;
				else id_machine = machines.size()-1;
			}
			else if(id == 2)
			{
				if(id_machine < machines.size()-1) ++id_machine;
				else id_machine = 0;
			}
			else if(id == 3)
			{
				if(NetWorkUtil.GetType(machines.get(id_machine)) != null )
				{
					machine = machines.get(id_machine);
					machine_type = NetWorkUtil.GetType(machine).getSimpleName();
					
					button_slect2Up.visible = true;
					button_slect2Down.visible = true;
					button_slect2Next.visible = true;
					textField_argument.setText("");
					var_type = null;
					id_function = 0;
				}
				else
				{
					machine = null;
					machine_type = "";
					button_slect2Up.visible = false;
					button_slect2Down.visible = false;
					button_slect2Next.visible = false;
					var_type = null;
				}
				
				button_boolean_tf.visible = false;
				button_test.visible = false;
			}
			else if(id == 4)
			{
				if(machine_type.contains("Machine_FluidStorageInfo"))
				{
					if(id_function > 0) --id_function;
					else id_function = 2;
				}
				else if(machine_type.contains("Machine_StorageInfo"))
				{
					if(id_function > 0) --id_function;
					else id_function = 1;
				}
			}
			else if(id == 5)
			{
				if(machine_type.contains("Machine_FluidStorageInfo"))
				{
					if(id_function < 2) ++id_function;
					else id_function = 0;
				}
				else if(machine_type.contains("Machine_StorageInfo"))
				{
					if(id_function < 1) ++id_function;
					else id_function = 0;
				}
			}
			else if(id == 6)
			{
				button_boolean_tf.visible = false;
				button_test.visible = false;
				textField_argument.setText("");
				
				if(machine_type.contains("Machine_Counter"))
				{
					//Consomation long
					var_type = long.class;
				}
				else if(machine_type.contains("Machine_FluidCounter"))
				{
					//Consomation long
					var_type = long.class;
				}
				else if(machine_type.contains("Machine_FluidFlowMeter"))
				{
					//Flow int
					var_type = int.class;
				}
				else if(machine_type.contains("Machine_FluidName"))
				{
					//FluidName String
					var_type = String.class;
				}
				else if(machine_type.contains("Machine_FluidStorageInfo"))
				{
					//FluidAmount ([]) int
					//FluidCapacity ([]) int
					//FluidName ([]) String
					if(id_function == 0 || id_function == 1) var_type = int.class;
					else var_type = String.class;
				}
				else if(machine_type.contains("Machine_FluidValve"))
				{
					//ValvePosition boolean
					var_type = boolean.class;
					button_boolean_tf.visible = true;
				}
				else if(machine_type.contains("Machine_FluxMeter"))
				{
					//Flux int
					var_type = int.class;
				}
				else if(machine_type.contains("Machine_StorageInfo"))
				{
					//EnergyStored int
					//MaxEnergyStored int
					var_type = int.class;
				}
				else if(machine_type.contains("Machine_Switch"))
				{
					//SwitchPosition boolean
					var_type = boolean.class;
					button_boolean_tf.visible = true;
				}
				
				if(var_type != null)
				{
					button_test.visible = true;
					button_test.displayString = "=";
					textField_argument.setVisible(!button_boolean_tf.visible);
				}
			}
		}
		
		if(var_type != null)
		{
			button_EndOk.visible = true;
			//TODO
		}
		else
		{
			button_EndOk.visible = false;
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button.id == 10)
		{
			acction(1);
		}
		else if(button.id == 11)
		{
			acction(2);
		}
		else if(button.id == 12)
		{
			acction(3);
		}
		else if(button.id == 13)
		{
			acction(4);
		}
		else if(button.id == 14)
		{
			acction(5);
		}
		else if(button.id == 15)
		{
			acction(6);
		}
		else if(button.id == 16)
		{
			if(button_boolean_tf.visible && button_boolean_tf.displayString == "true")
			{
				button_boolean_tf.displayString = "false";
				var04 = false;
			}
			else
			{
				button_boolean_tf.displayString = "true";
				var04 = true;
			}
		}
		else if(button.id == 9)
		{
			//TODO var register
			if(var_type == boolean.class)
			{
				if(button_boolean_tf.displayString == "true") var04 = true;
				else var04 = false;
				
				tileentity.posmachine = new Vector3(machine.xCoord, machine.yCoord, machine.zCoord);
				tileentity.machine_type = machine_type;
				tileentity.VarType = 0;
				tileentity.ComparType = varArgument;
				tileentity.var0 = var04;
				tileentity.methode = id_function;
				tileentity.updateInventory();
			}
			else if(var_type == int.class)
			{
				if(textField_argument.getVisible() && textField_argument.getText() != null && textField_argument.getText().length() > 0)
				{
					try
					{
						int val = Integer.parseInt(textField_argument.getText());
						varArgument = 0;
						if(button_test.displayString == "<")
						{
							varArgument = 1;
						}
						else if(button_test.displayString == ">")
						{
							varArgument = 2;
						}
						var02 = val;
						
					}
					catch (Exception e)
					{
						
					}
					
					tileentity.posmachine = new Vector3(machine.xCoord, machine.yCoord, machine.zCoord);
					tileentity.machine_type = machine_type;
					tileentity.VarType = 2;
					tileentity.ComparType = varArgument;
					tileentity.Var2 = var02;
					tileentity.methode = id_function;
					tileentity.updateInventory();
					//System.out.println("int " + var02);
				}
			}
			else if(var_type == long.class)
			{
				if(textField_argument.getVisible() && textField_argument.getText() != null && textField_argument.getText().length() > 0)
				{
					try
					{
						long val = Long.parseLong(textField_argument.getText());
						varArgument = 0;
						if(button_test.displayString == "<")
						{
							varArgument = 1;
						}
						else if(button_test.displayString == ">")
						{
							varArgument = 2;
						}
						var01 = val;
						
					}
					catch (Exception e)
					{
						
					}
					
					tileentity.posmachine = new Vector3(machine.xCoord, machine.yCoord, machine.zCoord);
					tileentity.machine_type = machine_type;
					tileentity.VarType = 3;
					tileentity.ComparType = varArgument;
					tileentity.Var3 = var01;
					tileentity.methode = id_function;
					tileentity.updateInventory();
					
				//System.out.println("long " + var01);
				}
			}
			else if(var_type == String.class)
			{
				if(textField_argument.getVisible() && textField_argument.getText() != null)
				{
					var03 = textField_argument.getText();
					varArgument = 0;
					if(button_test.displayString == "!=")
					{
						varArgument = 1;
					}
					else if(button_test.displayString == "~")
					{
						varArgument = 2;
					}
					//System.out.println("String "+varArgument+" data: " + var03);
					tileentity.posmachine = new Vector3(machine.xCoord, machine.yCoord, machine.zCoord);
					tileentity.machine_type = machine_type;
					tileentity.VarType = 1;
					tileentity.ComparType = varArgument;
					tileentity.var1 = var03;
					tileentity.methode = id_function;
					tileentity.updateInventory();
				}
			}
			
		}
		else if(button.id == 17)
		{
			if(var_type == boolean.class)
			{
				button_test.displayString = "=";
			}
			else if(var_type == int.class || var_type == long.class)
			{
				if(button_test.displayString == "=")
				{
					button_test.displayString = "<";
				}
				else if(button_test.displayString == "<")
				{
					button_test.displayString = ">";
				}
				else if(button_test.displayString == ">")
				{
					button_test.displayString = "=";
				}
			}
			else if(var_type == String.class)
			{
				if(button_test.displayString == "=")
				{
					button_test.displayString = "~";
				}
				else if(button_test.displayString == "~")
				{
					button_test.displayString = "!=";
				}
				else if(button_test.displayString == "!=")
				{
					button_test.displayString = "=";
				}
			}
		}
		
		
	}
	
	@Override
	protected void mouseClicked(int x, int y, int click)
	{
		if(textField_argument.getVisible())
			textField_argument.mouseClicked(x, y, click);
		super.mouseClicked(x, y, click);
	}
	
	@Override
	public void updateScreen()
	{
		if(textField_argument.getVisible())
			textField_argument.updateCursorCounter();
		if(width_save != width || height_save != height)
		{
			width_save = width; height_save = height;
			screenResized(width, height);
		}
		super.updateScreen();
	}
	
	private void screenResized(int w, int h)
	{
		button_slectUp.xPosition = w/2 - 116;
		button_slectDown.xPosition = w/2 - 116;
		button_slectNext.xPosition = w/2 + 76;
		
		button_slect2Up.xPosition = w/2 - 116;
		button_slect2Down.xPosition = w/2 - 116;
		button_slect2Next.xPosition = w/2 + 76;
		
		button_boolean_tf.xPosition = w/2 - 54;
		button_EndOk.xPosition = w/2 + 76;
		button_test.xPosition = w/2 - 76;
		textField_argument.xPosition = w/2 - 54;
		
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}
	
	private void drawElement(TileEntity tem, int x2, int y2)
	{
		mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
		if(tem instanceof Machine_FluxMeter) 			setColorRGBA(  0,   0, 128, 255);//
		else if(tem instanceof Machine_Counter) 		setColorRGBA(  0, 128,   0, 255);//
		else if(tem instanceof Machine_Switch) 			setColorRGBA(128,   0,   0, 255);//
		else if(tem instanceof Machine_StorageInfo) 	setColorRGBA(128,   0, 128, 255);//
		else if(tem instanceof Machine_FluidFlowMeter) 	setColorRGBA(  0, 128, 128, 255);
		else if(tem instanceof Machine_FluidName) 		setColorRGBA(128, 128,   0, 255);
		else if(tem instanceof Machine_FluidStorageInfo)setColorRGBA(128,   0,  64, 255);
		else if(tem instanceof Machine_FluidValve) 		setColorRGBA(  0, 128,  64, 255);
		else if(tem instanceof Machine_Redstone) 		setColorRGBA( 64,   0, 128, 255);
		else if(tem instanceof Machine_Switch)			setColorRGBA(  0,  64, 128, 255);
		else if(tem instanceof Machine_FluidCounter) 	setColorRGBA(128,  64,   0, 255);
		else setColorRGBA(64, 64, 64, 255);
		if(tem instanceof MachineNetWork)
		{
			//setColorRGBA(128, 0, 0, 255);
			drawTexturedModalRect(x2, y2, 0, 0, 150, 40);
			drawString(fontRendererObj, "Type: "+((MachineNetWork)tem).name(), x2+1, y2+1, 0xffffff);
			drawString(fontRendererObj, "CustomName: "+((MachineNetWork)tem).CustomName(), x2+1, y2+11, 0xffffff);
			drawString(fontRendererObj, "Pos: ["+tem.xCoord+","+tem.yCoord+","+tem.zCoord+"]", x2+1, y2+21, 0xffffff);
			drawString(fontRendererObj, ((MachineNetWork)tem).GetSyntaxe(), x2+1, y2+31, 0xffffff);
		}
	}
	
	private void drawBigNumber(int x, int y, int n)
	{
		mc.renderEngine.bindTexture(ResourceLocationRegister.texture_number);
		drawTexturedModalRect(x, y, n*24, 0, 24, 28);
	}
	
	private void setColorRGBA(int i, int j, int k, int l)
	{
		GL11.glColor4f((1f/255f)*(float)i, (1f/255f)*(float)j, (1f/255f)*(float)k, (1f/255f)*(float)l);
	}

}
