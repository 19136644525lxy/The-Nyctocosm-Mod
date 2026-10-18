package com.yifei.nyctocosm;

import com.yifei.nyctocosm.boss.Boss;
import com.yifei.nyctocosm.boss.sorcerer.NullpointStaff;
import com.yifei.nyctocosm.network.Network;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NyctocosmMod.ID)
public final class NyctocosmMod {
    public static final String ID = "nyctocosm";
    public NyctocosmMod(FMLJavaModLoadingContext context) {
        // 47.3.10+ 支持构造函数注入上下文，替代已标记移除的静态 get()
        var bus = context.getModEventBus();
        Content.ITEMS.register(bus);
        Content.ENTITIES.register(bus);
        Content.TABS.register(bus);
        Content.SOUNDS.register(bus);
        Content.BLOCKS.register(bus);
        Content.MENUS.register(bus);
        Content.LOOT_MODIFIERS.register(bus);
        Network.register();
        bus.addListener(this::attributes);
        MinecraftForge.EVENT_BUS.register(new CommonEvents());
    }
    private void attributes(EntityAttributeCreationEvent event) {
        event.put(Content.SORCERER_HAIL.get(),net.minecraft.world.entity.LivingEntity.createLivingAttributes().build());
        event.put(Content.SERPENT.get(), Boss.attributes(1000, 20,200).build());
        event.put(Content.SORCERER.get(), Boss.attributes(1000, 15, 50)
            .add(net.minecraft.world.entity.ai.attributes.Attributes.KNOCKBACK_RESISTANCE,1).build());
        event.put(Content.EMISSARY.get(), Boss.attributes(1200, 35,300).build());
        event.put(Content.DEATH_WALKER.get(), Boss.attributes(100, 15).build());
        event.put(Content.APPARITION.get(), Boss.attributes(30, 8).build());
        event.put(Content.NULLPOINT.get(), NullpointStaff.attributes().build());
    }
}
