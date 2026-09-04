execute unless score #frame aj.i = #frame aj.i run scoreboard players set #frame aj.i 0
execute unless score #variant aj.i = #variant aj.i run scoreboard players set #variant aj.i 0
execute unless score #animation aj.i = #animation aj.i run scoreboard players set #animation aj.i -1
scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajmobilespellplane.rig_loaded 1
scoreboard players operation @s aj.ajmobilespellplane.export_version = aj.ajmobilespellplane.export_version aj.i
execute store result score @s aj.id run scoreboard players add .aj.last_id aj.id 1
tp @s ~ ~ ~ ~ ~
execute at @s on passengers run function zzz_ajmobilespellplane_internal:summon/as_rig_entities
execute if score #variant aj.i = $aj.ajmobilespellplane.variant.default aj.id run function zzz_ajmobilespellplane_internal:apply_variant/default_as_root
execute if score #animation aj.i matches 0.. run scoreboard players operation @s aj.anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajmobilespellplane.animation.idle aj.id run function zzz_ajmobilespellplane_internal:animations/idle/apply_frame_as_root
execute if score #animation aj.i = $aj.ajmobilespellplane.animation.idle aj.id run scoreboard players operation @s aj.ajmobilespellplane.animation.idle.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajmobilespellplane.animation.cast aj.id run function zzz_ajmobilespellplane_internal:animations/cast/apply_frame_as_root
execute if score #animation aj.i = $aj.ajmobilespellplane.animation.cast aj.id run scoreboard players operation @s aj.ajmobilespellplane.animation.cast.local_anim_time = #frame aj.i
execute if score #animation aj.i = $aj.ajmobilespellplane.animation.fizzle aj.id run function zzz_ajmobilespellplane_internal:animations/fizzle/apply_frame_as_root
execute if score #animation aj.i = $aj.ajmobilespellplane.animation.fizzle aj.id run scoreboard players operation @s aj.ajmobilespellplane.animation.fizzle.local_anim_time = #frame aj.i
execute at @s run function #ajmobilespellplane:on_summon_as_root
tag @s remove aj.new
scoreboard players reset #frame aj.i
scoreboard players reset #variant aj.i
scoreboard players reset #animation aj.i