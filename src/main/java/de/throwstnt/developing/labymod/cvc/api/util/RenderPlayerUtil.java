package de.throwstnt.developing.labymod.cvc.api.util;

public class RenderPlayerUtil {

	/*public static void renderPlayer(GameProfile gameProfile, RenderManager renderManager, EntityPlayer player, int x, int y, int z, float entityYaw, float partialTicks) {
		Minecraft.getInstance().getSkinManager().loadProfileTextures(gameProfile, (type, resourceLocation, a) -> {
			Render<Entity> render = null;
			
			try {
	            render = renderManager.<Entity>getEntityRenderObject(player);

	            if (render != null && renderManager.renderEngine != null)
	            {
	                try
	                {
	                	render.bindTexture(resourceLocation);
	                    render.doRender(player, x, y, z, entityYaw, partialTicks);
	                }
	                catch (Throwable throwable2)
	                {
	                    throw new ReportedException(CrashReport.makeCrashReport(throwable2, "Rendering entity in world"));
	                }
	            }
	        } catch (Throwable throwable3) {}
		}, false);
		
        
	}
	
	public static EntityPlayer createFakePlayer(World worldIn, GameProfile gameProfileIn) {
		EntityPlayer newPlayer = new EntityOtherPlayerMP(worldIn, gameProfileIn);
		
		S38PacketPlayerListItem playerInfoOut = reflectPlayerListItem(gameProfileIn, gameProfileIn.getName());
	    //S0CPacketSpawnPlayer playerSpawnOut = new S0CPacketSpawnPlayer(newPlayer);
	      
	    Minecraft.getInstance().getNetHandler().handlePlayerListItem(playerInfoOut);
		//Minecraft.getInstance().getNetHandler().handleSpawnPlayer(playerSpawnOut);
	    
		return newPlayer;
	}
	
	public static S38PacketPlayerListItem reflectPlayerListItem(GameProfile gameProfileIn, String nameIn) {
		S38PacketPlayerListItem packet = new S38PacketPlayerListItem();
		
		List<S38PacketPlayerListItem.AddPlayerData> list = new ArrayList<>();
		list.add(packet.new AddPlayerData(gameProfileIn, 10, GameType.CREATIVE, new ChatComponentText(nameIn)));
		
		List<PropertyDescriptor> descriptors = new ArrayList<>(PropertyUtils.getPropertyDescriptors(packet));
		
		descriptors.forEach((descriptor) -> {
			if(descriptor.getPropertyType() == Action.class) {
				PropertyUtils.write(packet, descriptor, Action.ADD_PLAYER);
			} else if(descriptor.getPropertyType() == List.class) {
				PropertyUtils.write(packet, descriptor, list);
			} else {
				Debug.log(EnumDebugMode.ADDON, "Unknown class " + descriptor.getPropertyType().getName());
			}
		});
		
		/*PropertyDescriptor actionDescriptor = PropertyUtils.getPropertyDescriptor(S38PacketPlayerListItem.class, S38PacketPlayerListItem::func_179768_b);
		PropertyUtils.write(packet, actionDescriptor, Action.ADD_PLAYER);
		
		PropertyDescriptor listDescriptor = PropertyUtils.getPropertyDescriptor(S38PacketPlayerListItem.class, S38PacketPlayerListItem::func_179767_a);
		PropertyUtils.write(packet, listDescriptor, list);*/
		
		//return packet;
	//}
}
