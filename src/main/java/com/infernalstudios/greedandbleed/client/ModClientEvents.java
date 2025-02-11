package com.infernalstudios.greedandbleed.client;

import com.infernalstudios.greedandbleed.GreedAndBleed;
import com.infernalstudios.greedandbleed.client.renderer.GBHoglinRenderer;
import com.infernalstudios.greedandbleed.client.renderer.PygmyRenderFactory;
import com.infernalstudios.greedandbleed.client.renderer.SkeletalPiglinRenderer;
import com.infernalstudios.greedandbleed.common.entity.piglin.PygmyEntity;
import com.infernalstudios.greedandbleed.common.registry.EntityTypeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
        modid = GreedAndBleed.MODID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT)
public class ModClientEvents {

    public static final PygmyRenderFactory<PygmyEntity> PYGMY_RENDER_FACTORY = new PygmyRenderFactory<>(false);

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event){
        RenderingRegistry.registerEntityRenderingHandler(
                EntityTypeRegistry.PYGMY.get(), PYGMY_RENDER_FACTORY
        );
        RenderingRegistry.registerEntityRenderingHandler(
                EntityTypeRegistry.SKELETAL_PIGLIN.get(),
                SkeletalPiglinRenderer::new
        );

        // Ideally I would replace this with a Mixin into the original renderer
        // However, couldn't figure out how to call super in the injection into the constructor
        RenderingRegistry.registerEntityRenderingHandler(
                EntityType.HOGLIN, GBHoglinRenderer::new
        );
    }
}