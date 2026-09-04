execute unless score #frame aj.i = #frame aj.i run scoreboard players set #frame aj.i 0
execute unless score #variant aj.i = #variant aj.i run scoreboard players set #variant aj.i 0
execute unless score #animation aj.i = #animation aj.i run scoreboard players set #animation aj.i -1
scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajnullpoint.rig_loaded 1
scoreboard players operation @s aj.ajnullpoint.export_version = aj.ajnullpoint.export_version aj.i
execute store result score @s aj.id run scoreboard players add .aj.last_id aj.id 1
tp @s ~ ~ ~ ~ ~
execute at @s on passengers run function zzz_ajnullpoint_internal:summon/as_rig_entities
execute if score #variant aj.i = $aj.ajnullpoint.variant.default aj.id run function zzz_ajnullpoint_internal:apply_variant/default_as_root
execute if score #animation aj.i matches 0.. run scoreboard players operation @s aj.anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajnullpoint.animation.idle aj.id run function zzz_ajnullpoint_internal:animations/idle/apply_frame_as_root
execute if score #animation aj.i = $aj.ajnullpoint.animation.idle aj.id run scoreboard players operation @s aj.ajnullpoint.animation.idle.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajnullpoint.animation.emit aj.id run function zzz_ajnullpoint_internal:animations/emit/apply_frame_as_root
execute if score #animation aj.i = $aj.ajnullpoint.animation.emit aj.id run scoreboard players operation @s aj.ajnullpoint.animation.emit.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajnullpoint.animation.recall aj.id run function zzz_ajnullpoint_internal:animations/recall/apply_frame_as_root
execute if score #animation aj.i = $aj.ajnullpoint.animation.recall aj.id run scoreboard players operation @s aj.ajnullpoint.animation.recall.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajnullpoint.animation.hurt aj.id run function zzz_ajnullpoint_internal:animations/hurt/apply_frame_as_root
execute if score #animation aj.i = $aj.ajnullpoint.animation.hurt aj.id run scoreboard players operation @s aj.ajnullpoint.animation.hurt.local_anim_time = #frame aj.i
execute at @s run function #ajnullpoint:on_summon_as_root
tag @s remove aj.new
scoreboard players reset #frame aj.i
scoreboard players reset #variant aj.i
scoreboard players reset #animation aj.i