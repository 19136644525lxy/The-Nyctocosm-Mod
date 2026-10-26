package com.yifei.nyctocosm.spell;

import com.yifei.nyctocosm.combat.Combat;
import com.yifei.nyctocosm.item.WeaponEffects;
import com.yifei.nyctocosm.network.Network;
import com.yifei.nyctocosm.spell.Spell;
import com.yifei.nyctocosm.world.portal.PortalBlock;
import java.util.HashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** Resonance has its own penetrating 70-sample ray, rather than the generic blocked beam. */
public final class ResonanceSpell {
    public static final int RANGE=70;
    public static void fire(ServerLevel level,Player caster,Vec3 origin,Vec3 forward){
        var hit=new HashSet<java.util.UUID>();var checked=new HashSet<BlockPos>();
        for(int step=0;step<RANGE;step++){
            Vec3 at=origin.add(forward.scale(step));BlockPos block=BlockPos.containing(at);
            if(!level.hasChunkAt(block))continue;
            if(checked.add(block))PortalBlock.activate(level,block);
            for(LivingEntity target:level.getEntitiesOfClass(LivingEntity.class,new AABB(at,at).inflate(.5))){
                if(Combat.canHit(caster,target)&&hit.add(target.getUUID()))Combat.hit(caster,target,Spell.RESONANCE.damage,Combat.Element.RESONANCE);
            }
        }
        // 原包 cast 的 flash（~ ~1 ~ 5 个 0.2 散布）+ raycast 每步的 sonic_boom（force）
        WeaponEffects.forcedParticle(level,net.minecraft.core.particles.ParticleTypes.FLASH,caster.position().add(0,1,0),5,.2,0);
        for(int step=0;step<RANGE;step+=2)
            WeaponEffects.forcedParticle(level,net.minecraft.core.particles.ParticleTypes.SONIC_BOOM,origin.add(forward.scale(step)),1,0,0);
        Network.weaponEffect(caster,4,origin,origin.add(forward.scale(RANGE-1)),forward);
    }
    private ResonanceSpell(){}
}
