execute unless score #frame aj.i = #frame aj.i run scoreboard players set #frame aj.i 0
execute unless score #variant aj.i = #variant aj.i run scoreboard players set #variant aj.i 0
execute unless score #animation aj.i = #animation aj.i run scoreboard players set #animation aj.i -1
scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajsorcererm2.rig_loaded 1
scoreboard players operation @s aj.ajsorcererm2.export_version = aj.ajsorcererm2.export_version aj.i
execute store result score @s aj.id run scoreboard players add .aj.last_id aj.id 1
tp @s ~ ~ ~ ~ ~
execute at @s on passengers run function animated_java:ajsorcererm2/zzzzzzzz/summon/as_rig_entities
execute if score #variant aj.i = $aj.ajsorcererm2.variant.default aj.id run function animated_java:ajsorcererm2/zzzzzzzz/apply_variant/default/as_root
execute if score #variant aj.i = $aj.ajsorcererm2.variant.slash aj.id run function animated_java:ajsorcererm2/zzzzzzzz/apply_variant/slash/as_root
execute if score #variant aj.i = $aj.ajsorcererm2.variant.invis aj.id run function animated_java:ajsorcererm2/zzzzzzzz/apply_variant/invis/as_root
execute if score #variant aj.i = $aj.ajsorcererm2.variant.staffless aj.id run function animated_java:ajsorcererm2/zzzzzzzz/apply_variant/staffless/as_root
execute if score #variant aj.i = $aj.ajsorcererm2.variant.invisstaff aj.id run function animated_java:ajsorcererm2/zzzzzzzz/apply_variant/invisstaff/as_root
execute if score #animation aj.i matches 0.. run scoreboard players operation @s aj.anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm2.animation.fall aj.id run function animated_java:ajsorcererm2/zzzzzzzz/animations/fall/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm2.animation.fall aj.id run scoreboard players operation @s aj.ajsorcererm2.animation.fall.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm2.animation.idle aj.id run function animated_java:ajsorcererm2/zzzzzzzz/animations/idle/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm2.animation.idle aj.id run scoreboard players operation @s aj.ajsorcererm2.animation.idle.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm2.animation.risetransition aj.id run function animated_java:ajsorcererm2/zzzzzzzz/animations/risetransition/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm2.animation.risetransition aj.id run scoreboard players operation @s aj.ajsorcererm2.animation.risetransition.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm2.animation.dashstrike aj.id run function animated_java:ajsorcererm2/zzzzzzzz/animations/dashstrike/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm2.animation.dashstrike aj.id run scoreboard players operation @s aj.ajsorcererm2.animation.dashstrike.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm2.animation.voidwave aj.id run function animated_java:ajsorcererm2/zzzzzzzz/animations/voidwave/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm2.animation.voidwave aj.id run scoreboard players operation @s aj.ajsorcererm2.animation.voidwave.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm2.animation.javelinswing aj.id run function animated_java:ajsorcererm2/zzzzzzzz/animations/javelinswing/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm2.animation.javelinswing aj.id run scoreboard players operation @s aj.ajsorcererm2.animation.javelinswing.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm2.animation.airdash aj.id run function animated_java:ajsorcererm2/zzzzzzzz/animations/airdash/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm2.animation.airdash aj.id run scoreboard players operation @s aj.ajsorcererm2.animation.airdash.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm2.animation.voidbeam aj.id run function animated_java:ajsorcererm2/zzzzzzzz/animations/voidbeam/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm2.animation.voidbeam aj.id run scoreboard players operation @s aj.ajsorcererm2.animation.voidbeam.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm2.animation.seekingsingularities aj.id run function animated_java:ajsorcererm2/zzzzzzzz/animations/seekingsingularities/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm2.animation.seekingsingularities aj.id run scoreboard players operation @s aj.ajsorcererm2.animation.seekingsingularities.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm2.animation.die aj.id run function animated_java:ajsorcererm2/zzzzzzzz/animations/die/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm2.animation.die aj.id run scoreboard players operation @s aj.ajsorcererm2.animation.die.local_anim_time = #frame aj.i
execute at @s run function #animated_java:ajsorcererm2/on_summon/as_root
tag @s remove aj.new
scoreboard players reset #frame aj.i
scoreboard players reset #variant aj.i
scoreboard players reset #animation aj.i