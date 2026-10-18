package com.yifei.nyctocosm.item;

import com.yifei.nyctocosm.CommonEvents;
import com.yifei.nyctocosm.combat.Combat;
import com.yifei.nyctocosm.item.WeaponAbilityCooldowns;
import com.yifei.nyctocosm.item.WeaponEffects;
import com.yifei.nyctocosm.spell.SpellBolt;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public final class WeaponItem extends SwordItem {
    public final int kind;
    private final Multimap<Attribute,AttributeModifier> modifiers;
    public WeaponItem(int kind, float totalAttack, float speed) {
        super(Tiers.NETHERITE,0,speed,new Properties().fireResistant().rarity(Rarity.EPIC));
        this.kind=kind;
        modifiers=ImmutableMultimap.<Attribute,AttributeModifier>builder()
            .put(Attributes.ATTACK_DAMAGE,new AttributeModifier(BASE_ATTACK_DAMAGE_UUID,"Weapon modifier",totalAttack-1,AttributeModifier.Operation.ADDITION))
            .put(Attributes.ATTACK_SPEED,new AttributeModifier(BASE_ATTACK_SPEED_UUID,"Weapon modifier",speed,AttributeModifier.Operation.ADDITION)).build();
    }
    @Override public Multimap<Attribute,AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) { return slot==EquipmentSlot.MAINHAND?modifiers:super.getDefaultAttributeModifiers(slot); }
    @Override public boolean isDamageable(ItemStack stack) { return false; }
    @Override public boolean isFoil(ItemStack stack){return kind==3||super.isFoil(stack);}
    @Override public boolean hurtEnemy(ItemStack stack,LivingEntity target,LivingEntity attacker) {
        if(kind==3){target.setSecondsOnFire(8);Vec3 push=attacker.getLookAngle().scale(1.5);target.push(push.x,.2,push.z);target.hurtMarked=true;}
        return true;
    }
    @Override public InteractionResultHolder<ItemStack> use(Level level,Player player,InteractionHand hand) {
        ItemStack stack=player.getItemInHand(hand);
        if(!WeaponAbilityCooldowns.supportsHand(kind,hand))return InteractionResultHolder.pass(stack);
        if (level instanceof ServerLevel server) {
            if((kind==2||kind==3)&&!WeaponAbilityCooldowns.start(player,kind,hand))
                return InteractionResultHolder.sidedSuccess(stack,false);
            switch(kind) {
                case 0 -> {
                    // 原包 dash：speed I 1 秒（隐藏粒子）+ 冲刺音效 master 20 音量 4
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,20,1,true,false));
                    if(!player.isCreative())player.hurt(Combat.source(server,player,Combat.Element.VOID),4);
                    if(player.isAlive()){
                        Vec3 direction=player.getLookAngle(),destination=player.position();
                        int distance=0;for(int step=1;step<=7;step++){
                            Vec3 offset=direction.scale(step);
                            if(!server.noCollision(player,player.getBoundingBox().move(offset)))break;
                            destination=player.position().add(offset);distance=step;
                        }
                        Combat.beam(server,player,distance,11,Combat.Element.VOID,1.5);
                        WeaponEffects.dash(server,player.position(),destination);
                        player.teleportTo(destination.x,destination.y,destination.z);player.fallDistance=0;
                    }
                }
                case 1 -> {
                    // 原包 slash：自施 slowness I 1 秒 + eye+1y 前方 1 格 flash(force) + +1.625y 前方 1.5 格 sweep_attack
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,20,1,true,false));
                    Vec3 flashAt=player.position().add(0,1,0).add(player.getLookAngle().scale(1));
                    for(var online:server.players())server.sendParticles(online,net.minecraft.core.particles.ParticleTypes.FLASH,false,flashAt.x,flashAt.y,flashAt.z,1,0,0,0,0);
                    Vec3 sweepAt=player.position().add(0,1.625,0).add(player.getLookAngle().scale(1.5));
                    server.sendParticles(net.minecraft.core.particles.ParticleTypes.SWEEP_ATTACK,sweepAt.x,sweepAt.y,sweepAt.z,1,0,0,0,0);
                    SpellBolt.launch(server,player,player.getEyePosition(),player.getLookAngle(),14,Combat.Element.NIGHTMARE,1.8f,.8f).weaponVisual(1);
                }
                case 2 -> {if(hand==InteractionHand.OFF_HAND){
                    // 原包 cast：自施 slowness I 1 秒 + +1.625y 前方 0.5 格 flash(force) + halobeam.shoot master 30 音量 5
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,20,1,true,false));
                    Vec3 flashAt=player.position().add(0,1.625,0).add(player.getLookAngle().scale(.5));
                    for(var online:server.players())server.sendParticles(online,net.minecraft.core.particles.ParticleTypes.FLASH,false,flashAt.x,flashAt.y,flashAt.z,1,0,0,0,0);
                    Vec3 end=Combat.beam(server,player,40,15.5f,Combat.Element.VOID,.7,false);WeaponEffects.offhand(server,player,kind,end);
                }else sweep(server,player,15.5f,Combat.Element.VOID);}
                case 3 -> {if(hand==InteractionHand.OFF_HAND){WeaponEffects.offhand(server,player,kind,null);SpellBolt.launch(server,player,player.getEyePosition(),player.getLookAngle(),19,Combat.Element.DISSONANCE,1.1f,2).weaponVisual(3);}else sweep(server,player,19,Combat.Element.DISSONANCE);}
            }
            if((kind==2||kind==3)&&hand==InteractionHand.MAIN_HAND)WeaponEffects.sweep(server,player,kind);
            // 原包音效均为 master 定向半径：dash/slash 20 vol4、虚缝主手 woosh 20 vol2、副手 halobeam 30 vol5、末日 30 vol1
            switch(kind){
                case 0 -> CommonEvents.soundMaster(server,player.position(),"items.scythe.dash",4,20);
                case 1 -> CommonEvents.soundMaster(server,player.position(),"items.wyrmsliver.slash",4,20);
                case 2 -> {if(hand==InteractionHand.MAIN_HAND)CommonEvents.soundMaster(server,player.position(),"sorcerer.staff.woosh",2,20);
                    else CommonEvents.soundMaster(server,player.position(),"sorcerer.halobeam.shoot",5,30);}
                default -> CommonEvents.soundMaster(server,player.position(),hand==InteractionHand.MAIN_HAND?"items.semblanceofdoom.swing":"items.semblanceofdoom.shoot",1,30);
            }
            if(kind==0||kind==1)player.getCooldowns().addCooldown(this,20);
        }
        return InteractionResultHolder.sidedSuccess(stack,level.isClientSide);
    }
    private static void sweep(ServerLevel level,Player player,float damage,Combat.Element element){
        if(element==Combat.Element.DISSONANCE){semblanceSweep(level,player,damage);return;}
        Vec3 forward=player.getLookAngle();
        for(LivingEntity target:level.getEntitiesOfClass(LivingEntity.class,player.getBoundingBox().inflate(4))) {
            Vec3 to=target.getBoundingBox().getCenter().subtract(player.getEyePosition());
            if(to.lengthSqr()<16 && to.normalize().dot(forward)>.2){
                if(Combat.hit(player,target,damage,element)&&element==Combat.Element.DISSONANCE)target.setSecondsOnFire(5);
            }
        }
        Vec3 p=player.getEyePosition().add(forward.scale(2));
        level.sendParticles(net.minecraft.core.particles.ParticleTypes.SWEEP_ATTACK,p.x,p.y,p.z,1,0,0,0,0);
    }
    private static void semblanceSweep(ServerLevel level,Player player,float damage){
        boolean grounded=player.onGround();
        Vec3 forward=player.getLookAngle().normalize();
        double yaw=Math.toRadians(player.getYRot()),pitch=Math.toRadians(player.getXRot());
        Vec3 left=new Vec3(Math.cos(yaw),0,Math.sin(yaw));
        Vec3 up=new Vec3(-Math.sin(yaw)*Math.sin(pitch),Math.cos(pitch),Math.cos(yaw)*Math.sin(pitch));
        Vec3 origin=player.position().add(0,grounded?1.5:.9,0);
        AABB[] samples=new AABB[72];
        for(int i=0;i<samples.length;i++){
            double angle=i*5.0;
            Vec3 center=origin.add((grounded?left:up).scale(Math.sin(angle)*2)).add(forward.scale(Math.cos(angle)*2));
            samples[i]=new AABB(center.x-.5,center.y-.5,center.z-.5,center.x+.5,center.y+.5,center.z+.5);
        }

        AABB query=new AABB(origin,origin).inflate(2.5);
        Map<UUID,LivingEntity> selected=new LinkedHashMap<>();
        for(LivingEntity target:level.getEntitiesOfClass(LivingEntity.class,query)){
            if(!target.getType().is(Combat.LIVING_THINGS))continue;
            for(AABB sample:samples)if(target.getBoundingBox().intersects(sample)){
                selected.putIfAbsent(target.getUUID(),target);
                break;
            }
        }
        for(LivingEntity target:selected.values()){
            Combat.hit(player,target,damage,Combat.Element.DISSONANCE);
            // The source applies Fire after its damage function, even when Forge rejects the damage.
            target.setSecondsOnFire(5);
        }
    }
}
