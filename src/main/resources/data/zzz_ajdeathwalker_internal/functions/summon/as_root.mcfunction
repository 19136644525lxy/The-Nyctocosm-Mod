execute unless score #frame aj.i = #frame aj.i run scoreboard players set #frame aj.i 0
execute unless score #variant aj.i = #variant aj.i run scoreboard players set #variant aj.i 0
execute unless score #animation aj.i = #animation aj.i run scoreboard players set #animation aj.i -1
scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajdeathwalker.rig_loaded 1
scoreboard players operation @s aj.ajdeathwalker.export_version = aj.ajdeathwalker.export_version aj.i
execute store result score @s aj.id run scoreboard players add .aj.last_id aj.id 1
tp @s ~ ~ ~ ~ ~
execute at @s on passengers run function zzz_ajdeathwalker_internal:summon/as_rig_entities
execute if score #variant aj.i = $aj.ajdeathwalker.variant.default aj.id run function zzz_ajdeathwalker_internal:apply_variant/default_as_root
execute if score #animation aj.i matches 0.. run scoreboard players operation @s aj.anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajdeathwalker.animation.walk aj.id run function zzz_ajdeathwalker_internal:animations/walk/apply_frame_as_root
execute if score #animation aj.i = $aj.ajdeathwalker.animation.walk aj.id run scoreboard players operation @s aj.ajdeathwalker.animation.walk.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajdeathwalker.animation.slam aj.id run function zzz_ajdeathwalker_internal:animations/slam/apply_frame_as_root
execute if score #animation aj.i = $aj.ajdeathwalker.animation.slam aj.id run scoreboard players operation @s aj.ajdeathwalker.animation.slam.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajdeathwalker.animation.idle aj.id run function zzz_ajdeathwalker_internal:animations/idle/apply_frame_as_root
execute if score #animation aj.i = $aj.ajdeathwalker.animation.idle aj.id run scoreboard players operation @s aj.ajdeathwalker.animation.idle.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajdeathwalker.animation.death aj.id run function zzz_ajdeathwalker_internal:animations/death/apply_frame_as_root
execute if score #animation aj.i = $aj.ajdeathwalker.animation.death aj.id run scoreboard players operation @s aj.ajdeathwalker.animation.death.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajdeathwalker.animation.death_alt aj.id run function zzz_ajdeathwalker_internal:animations/death_alt/apply_frame_as_root
execute if score #animation aj.i = $aj.ajdeathwalker.animation.death_alt aj.id run scoreboard players operation @s aj.ajdeathwalker.animation.death_alt.local_anim_time = #frame aj.i
execute at @s run function #ajdeathwalker:on_summon_as_root
tag @s remove aj.new
scoreboard players reset #frame aj.i
scoreboard players reset #variant aj.i
scoreboard players reset #animation aj.i