execute unless score #frame aj.i = #frame aj.i run scoreboard players set #frame aj.i 0
execute unless score #variant aj.i = #variant aj.i run scoreboard players set #variant aj.i 0
execute unless score #animation aj.i = #animation aj.i run scoreboard players set #animation aj.i -1
scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajsorcererm1.rig_loaded 1
scoreboard players operation @s aj.ajsorcererm1.export_version = aj.ajsorcererm1.export_version aj.i
execute store result score @s aj.id run scoreboard players add .aj.last_id aj.id 1
tp @s ~ ~ ~ ~ ~
execute at @s on passengers run function animated_java:ajsorcererm1/zzzzzzzz/summon/as_rig_entities
execute if score #variant aj.i = $aj.ajsorcererm1.variant.default aj.id run function animated_java:ajsorcererm1/zzzzzzzz/apply_variant/default/as_root
execute if score #variant aj.i = $aj.ajsorcererm1.variant.slash aj.id run function animated_java:ajsorcererm1/zzzzzzzz/apply_variant/slash/as_root
execute if score #variant aj.i = $aj.ajsorcererm1.variant.invis aj.id run function animated_java:ajsorcererm1/zzzzzzzz/apply_variant/invis/as_root
execute if score #animation aj.i matches 0.. run scoreboard players operation @s aj.anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm1.animation.rise aj.id run function animated_java:ajsorcererm1/zzzzzzzz/animations/rise/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm1.animation.rise aj.id run scoreboard players operation @s aj.ajsorcererm1.animation.rise.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm1.animation.idle aj.id run function animated_java:ajsorcererm1/zzzzzzzz/animations/idle/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm1.animation.idle aj.id run scoreboard players operation @s aj.ajsorcererm1.animation.idle.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm1.animation.falltransition aj.id run function animated_java:ajsorcererm1/zzzzzzzz/animations/falltransition/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm1.animation.falltransition aj.id run scoreboard players operation @s aj.ajsorcererm1.animation.falltransition.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm1.animation.halobeam aj.id run function animated_java:ajsorcererm1/zzzzzzzz/animations/halobeam/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm1.animation.halobeam aj.id run scoreboard players operation @s aj.ajsorcererm1.animation.halobeam.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm1.animation.shardhail aj.id run function animated_java:ajsorcererm1/zzzzzzzz/animations/shardhail/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm1.animation.shardhail aj.id run scoreboard players operation @s aj.ajsorcererm1.animation.shardhail.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm1.animation.singularitycharge aj.id run function animated_java:ajsorcererm1/zzzzzzzz/animations/singularitycharge/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm1.animation.singularitycharge aj.id run scoreboard players operation @s aj.ajsorcererm1.animation.singularitycharge.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm1.animation.frozenblades aj.id run function animated_java:ajsorcererm1/zzzzzzzz/animations/frozenblades/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm1.animation.frozenblades aj.id run scoreboard players operation @s aj.ajsorcererm1.animation.frozenblades.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcererm1.animation.voidmeteor aj.id run function animated_java:ajsorcererm1/zzzzzzzz/animations/voidmeteor/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcererm1.animation.voidmeteor aj.id run scoreboard players operation @s aj.ajsorcererm1.animation.voidmeteor.local_anim_time = #frame aj.i
execute at @s run function #animated_java:ajsorcererm1/on_summon/as_root
tag @s remove aj.new
scoreboard players reset #frame aj.i
scoreboard players reset #variant aj.i
scoreboard players reset #animation aj.i