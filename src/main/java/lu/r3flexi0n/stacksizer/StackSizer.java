package lu.r3flexi0n.stacksizer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;

public class StackSizer extends JavaPlugin {

    private String version;

    @Override
    public void onEnable() {

        version = getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

        List<String> list = new ArrayList<>();
        list.add("1:16");
        getConfig().addDefault("StackSize", list);
        getConfig().options().copyDefaults(true);
        saveConfig();

        for (String items : getConfig().getStringList("StackSize")) {
            String[] data = items.split(":");
            int id = Integer.valueOf(data[0]);
            int size = Integer.valueOf(data[1]);
            setMaxStackSize(id, size);
        }
    }

    private void setMaxStackSize(int id, int amount) {
        try {
            Class<?> itemClass = Class.forName("net.minecraft.server." + version + ".Item");
            Method method = itemClass.getDeclaredMethod("getById", int.class);
            Object item = method.invoke(null, id);
            Field field = itemClass.getDeclaredField("maxStackSize");
            field.setAccessible(true);
            field.setInt(item, amount);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
