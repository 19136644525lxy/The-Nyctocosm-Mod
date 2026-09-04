execute unless score #frame aj.i = #frame aj.i run scoreboard players set #frame aj.i 0
execute unless score #variant aj.i = #variant aj.i run scoreboard players set #variant aj.i 0
execute unless score #animation aj.i = #animation aj.i run scoreboard players set #animation aj.i -1
scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajemissary.rig_loaded 1
scoreboard players operation @s aj.ajemissary.export_version = aj.ajemissary.export_version aj.i
execute store result score @s aj.id run scoreboard players add .aj.last_id aj.id 1
tp @s ~ ~ ~ ~ ~
execute at @s on passengers run function animated_java:ajemissary/zzzzzzzz/summon/as_rig_entities
execute if score #variant aj.i = $aj.ajemissary.variant.default aj.id run function animated_java:ajemissary/zzzzzzzz/apply_variant/default/as_root
execute if score #variant aj.i = $aj.ajemissary.variant.armorful aj.id run function animated_java:ajemissary/zzzzzzzz/apply_variant/armorful/as_root
execute if score #variant aj.i = $aj.ajemissary.variant.armorless aj.id run function animated_java:ajemissary/zzzzzzzz/apply_variant/armorless/as_root
execute if score #variant aj.i = $aj.ajemissary.variant.sweepful aj.id run function animated_java:ajemissary/zzzzzzzz/apply_variant/sweepful/as_root
execute if score #variant aj.i = $aj.ajemissary.variant.sweepless aj.id run function animated_java:ajemissary/zzzzzzzz/apply_variant/sweepless/as_root
execute if score #variant aj.i = $aj.ajemissary.variant.swordful aj.id run function animated_java:ajemissary/zzzzzzzz/apply_variant/swordful/as_root
execute if score #variant aj.i = $aj.ajemissary.variant.swordless aj.id run function animated_java:ajemissary/zzzzzzzz/apply_variant/swordless/as_root
execute if score #variant aj.i = $aj.ajemissary.variant.wing_config_0 aj.id run function animated_java:ajemissary/zzzzzzzz/apply_variant/wing_config_0/as_root
execute if score #variant aj.i = $aj.ajemissary.variant.wing_config_1 aj.id run function animated_java:ajemissary/zzzzzzzz/apply_variant/wing_config_1/as_root
execute if score #variant aj.i = $aj.ajemissary.variant.wing_config_2 aj.id run function animated_java:ajemissary/zzzzzzzz/apply_variant/wing_config_2/as_root
execute if score #variant aj.i = $aj.ajemissary.variant.wing_config_3 aj.id run function animated_java:ajemissary/zzzzzzzz/apply_variant/wing_config_3/as_root
execute if score #variant aj.i = $aj.ajemissary.variant.wingless aj.id run function animated_java:ajemissary/zzzzzzzz/apply_variant/wingless/as_root
execute if score #animation aj.i matches 0.. run scoreboard players operation @s aj.anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.testpose aj.id run function animated_java:ajemissary/zzzzzzzz/animations/testpose/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.testpose aj.id run scoreboard players operation @s aj.ajemissary.animation.testpose.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.spawn aj.id run function animated_java:ajemissary/zzzzzzzz/animations/spawn/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.spawn aj.id run scoreboard players operation @s aj.ajemissary.animation.spawn.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase1_idle aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase1_idle/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase1_idle aj.id run scoreboard players operation @s aj.ajemissary.animation.phase1_idle.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase1.attack.lightning_javelin aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase1.attack.lightning_javelin/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase1.attack.lightning_javelin aj.id run scoreboard players operation @s aj.ajemissary.animation.phase1.attack.lightning_javelin.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase1.attack.meteor_shower aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase1.attack.meteor_shower/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase1.attack.meteor_shower aj.id run scoreboard players operation @s aj.ajemissary.animation.phase1.attack.meteor_shower.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase1.attack.wingbeat aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase1.attack.wingbeat/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase1.attack.wingbeat aj.id run scoreboard players operation @s aj.ajemissary.animation.phase1.attack.wingbeat.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase1.attack.pillars aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase1.attack.pillars/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase1.attack.pillars aj.id run scoreboard players operation @s aj.ajemissary.animation.phase1.attack.pillars.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase1_transition aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase1_transition/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase1_transition aj.id run scoreboard players operation @s aj.ajemissary.animation.phase1_transition.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase2_idle aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase2_idle/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase2_idle aj.id run scoreboard players operation @s aj.ajemissary.animation.phase2_idle.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase2.attack.straferight aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase2.attack.straferight/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase2.attack.straferight aj.id run scoreboard players operation @s aj.ajemissary.animation.phase2.attack.straferight.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase2.attack.strafeleft aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase2.attack.strafeleft/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase2.attack.strafeleft aj.id run scoreboard players operation @s aj.ajemissary.animation.phase2.attack.strafeleft.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase2.attack.sword_plunge aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase2.attack.sword_plunge/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase2.attack.sword_plunge aj.id run scoreboard players operation @s aj.ajemissary.animation.phase2.attack.sword_plunge.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase2.attack.wing_blast aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase2.attack.wing_blast/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase2.attack.wing_blast aj.id run scoreboard players operation @s aj.ajemissary.animation.phase2.attack.wing_blast.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase2.attack.celestial_smite aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase2.attack.celestial_smite/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase2.attack.celestial_smite aj.id run scoreboard players operation @s aj.ajemissary.animation.phase2.attack.celestial_smite.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase2.attack.flame_geysers aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase2.attack.flame_geysers/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase2.attack.flame_geysers aj.id run scoreboard players operation @s aj.ajemissary.animation.phase2.attack.flame_geysers.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase2_transition aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase2_transition/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase2_transition aj.id run scoreboard players operation @s aj.ajemissary.animation.phase2_transition.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase3_idle aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase3_idle/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase3_idle aj.id run scoreboard players operation @s aj.ajemissary.animation.phase3_idle.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.parry aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase3.parry/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.parry aj.id run scoreboard players operation @s aj.ajemissary.animation.phase3.parry.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.parry.break aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase3.parry.break/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.parry.break aj.id run scoreboard players operation @s aj.ajemissary.animation.phase3.parry.break.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.straferight aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase3.attack.straferight/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.straferight aj.id run scoreboard players operation @s aj.ajemissary.animation.phase3.attack.straferight.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.strafeleft aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase3.attack.strafeleft/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.strafeleft aj.id run scoreboard players operation @s aj.ajemissary.animation.phase3.attack.strafeleft.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.run aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase3.attack.run/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.run aj.id run scoreboard players operation @s aj.ajemissary.animation.phase3.attack.run.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.swordswing aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase3.attack.swordswing/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.swordswing aj.id run scoreboard players operation @s aj.ajemissary.animation.phase3.attack.swordswing.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.cross aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase3.attack.cross/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.cross aj.id run scoreboard players operation @s aj.ajemissary.animation.phase3.attack.cross.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.swordspin aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase3.attack.swordspin/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.swordspin aj.id run scoreboard players operation @s aj.ajemissary.animation.phase3.attack.swordspin.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.ground_slam aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase3.attack.ground_slam/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.ground_slam aj.id run scoreboard players operation @s aj.ajemissary.animation.phase3.attack.ground_slam.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.flipfar aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase3.attack.flipfar/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.flipfar aj.id run scoreboard players operation @s aj.ajemissary.animation.phase3.attack.flipfar.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.frontkick aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase3.attack.frontkick/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.frontkick aj.id run scoreboard players operation @s aj.ajemissary.animation.phase3.attack.frontkick.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.flame_slash aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase3.attack.flame_slash/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.flame_slash aj.id run scoreboard players operation @s aj.ajemissary.animation.phase3.attack.flame_slash.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.seeking_missiles aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase3.attack.seeking_missiles/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.seeking_missiles aj.id run scoreboard players operation @s aj.ajemissary.animation.phase3.attack.seeking_missiles.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.lightning_field aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase3.attack.lightning_field/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase3.attack.lightning_field aj.id run scoreboard players operation @s aj.ajemissary.animation.phase3.attack.lightning_field.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase3_transition aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase3_transition/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase3_transition aj.id run scoreboard players operation @s aj.ajemissary.animation.phase3_transition.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase4_idle aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase4_idle/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase4_idle aj.id run scoreboard players operation @s aj.ajemissary.animation.phase4_idle.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase4_idle_flying aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase4_idle_flying/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase4_idle_flying aj.id run scoreboard players operation @s aj.ajemissary.animation.phase4_idle_flying.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase4_idle_transition_flying aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase4_idle_transition_flying/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase4_idle_transition_flying aj.id run scoreboard players operation @s aj.ajemissary.animation.phase4_idle_transition_flying.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase4_flying_transition_idle aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase4_flying_transition_idle/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase4_flying_transition_idle aj.id run scoreboard players operation @s aj.ajemissary.animation.phase4_flying_transition_idle.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase4.attack.godrays aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase4.attack.godrays/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase4.attack.godrays aj.id run scoreboard players operation @s aj.ajemissary.animation.phase4.attack.godrays.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase4.attack.apocalypse_rain aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase4.attack.apocalypse_rain/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase4.attack.apocalypse_rain aj.id run scoreboard players operation @s aj.ajemissary.animation.phase4.attack.apocalypse_rain.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase4.attack.fireball aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase4.attack.fireball/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase4.attack.fireball aj.id run scoreboard players operation @s aj.ajemissary.animation.phase4.attack.fireball.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase4.attack.corkscrew aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase4.attack.corkscrew/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase4.attack.corkscrew aj.id run scoreboard players operation @s aj.ajemissary.animation.phase4.attack.corkscrew.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase4.attack.divine_blast aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase4.attack.divine_blast/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase4.attack.divine_blast aj.id run scoreboard players operation @s aj.ajemissary.animation.phase4.attack.divine_blast.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase4.attack.grapple aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase4.attack.grapple/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase4.attack.grapple aj.id run scoreboard players operation @s aj.ajemissary.animation.phase4.attack.grapple.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.phase4.attack.grapple_grab aj.id run function animated_java:ajemissary/zzzzzzzz/animations/phase4.attack.grapple_grab/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.phase4.attack.grapple_grab aj.id run scoreboard players operation @s aj.ajemissary.animation.phase4.attack.grapple_grab.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajemissary.animation.die aj.id run function animated_java:ajemissary/zzzzzzzz/animations/die/apply_frame_as_root
execute if score #animation aj.i = $aj.ajemissary.animation.die aj.id run scoreboard players operation @s aj.ajemissary.animation.die.local_anim_time = #frame aj.i
execute at @s run function #animated_java:ajemissary/on_summon/as_root
tag @s remove aj.new
scoreboard players reset #frame aj.i
scoreboard players reset #variant aj.i
scoreboard players reset #animation aj.i