execute unless score #frame aj.i = #frame aj.i run scoreboard players set #frame aj.i 0
execute unless score #variant aj.i = #variant aj.i run scoreboard players set #variant aj.i 0
execute unless score #animation aj.i = #animation aj.i run scoreboard players set #animation aj.i -1
scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajsorcerer.rig_loaded 1
scoreboard players operation @s aj.ajsorcerer.export_version = aj.ajsorcerer.export_version aj.i
execute store result score @s aj.id run scoreboard players add .aj.last_id aj.id 1
tp @s ~ ~ ~ ~ ~
execute at @s on passengers run function zzz_ajsorcerer_internal:summon/as_rig_entities
execute if score #variant aj.i = $aj.ajsorcerer.variant.defaultog aj.id run function zzz_ajsorcerer_internal:apply_variant/defaultog_as_root
execute if score #variant aj.i = $aj.ajsorcerer.variant.staffless aj.id run function zzz_ajsorcerer_internal:apply_variant/staffless_as_root
execute if score #variant aj.i = $aj.ajsorcerer.variant.slash aj.id run function zzz_ajsorcerer_internal:apply_variant/slash_as_root
execute if score #variant aj.i = $aj.ajsorcerer.variant.staff aj.id run function zzz_ajsorcerer_internal:apply_variant/staff_as_root
execute if score #variant aj.i = $aj.ajsorcerer.variant.slashstaffless aj.id run function zzz_ajsorcerer_internal:apply_variant/slashstaffless_as_root
execute if score #animation aj.i matches 0.. run scoreboard players operation @s aj.anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcerer.animation.test aj.id run function zzz_ajsorcerer_internal:animations/test/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcerer.animation.test aj.id run scoreboard players operation @s aj.ajsorcerer.animation.test.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajsorcerer.animation.initial_spawn aj.id run function zzz_ajsorcerer_internal:animations/initial_spawn/apply_frame_as_root
execute if score #animation aj.i = $aj.ajsorcerer.animation.initial_spawn aj.id run scoreboard players operation @s aj.ajsorcerer.animation.initial_spawn.local_anim_time = #frame aj.i
execute at @s run function #ajsorcerer:on_summon_as_root
tag @s remove aj.new
scoreboard players reset #frame aj.i
scoreboard players reset #variant aj.i
scoreboard players reset #animation aj.i