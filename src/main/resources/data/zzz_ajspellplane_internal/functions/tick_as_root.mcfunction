execute unless score @s aj.ajspellplane.rig_loaded matches 1 run function zzz_ajspellplane_internal:on_load
scoreboard players add @s aj.life_time 1
execute at @s on passengers run tp @s ~ ~ ~ ~ ~
function #ajspellplane:on_tick
function zzz_ajspellplane_internal:animations/tick