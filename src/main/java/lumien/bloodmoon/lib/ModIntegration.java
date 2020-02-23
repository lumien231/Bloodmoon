package lumien.bloodmoon.lib;

import net.minecraftforge.fml.common.Loader;

public class ModIntegration {

	   public static enum Mods {
	      CMS("customspawner", "DrZhark's CustomSpawner");

	      private final String modid;
	      private final String name;

	      private Mods(String modidIn, String nameIn) {
	         this.modid = modidIn;
	         this.name = nameIn;
	      }

	      public String getId() {
	         return this.modid;
	      }

	      public String getName() {
	         return this.name;
	      }

	      public boolean isLoaded() {
	         return Loader.isModLoaded(this.getId());
	      }
	   }
	}
